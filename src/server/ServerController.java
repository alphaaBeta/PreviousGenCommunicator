package server;

import structs.Message;
import structs.UserStorage;
import structs.XMLOperations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class awaiting new connectiong to the server, creating new threads if needed.
 * Singleton. Also contains some methods that ease the extraction of information from the whole server.
 *
 * @see ServerThread
 */
public class ServerController implements Runnable
{
    /**
     * Singleton instance of class.
     */
    private static volatile ServerController mInstance;
    /**
     * Instance of message listener and sender object.
     *
     * @see MessageListenerAndSender
     */
    public MessageListenerAndSender messageListenerAndSender;
    /**
     * Boolean determining if the controller should be on.
     * Used to check if it's been given a signal from FXMLServer to shut itself down.
     *
     * @see server.fxml.FXMLServer
     */
    boolean isOn;
    /**
     * Array list of messages to be broadcasted.
     */
    ArrayList<Message> inputForBroadcast;
    /**
     * A server socket.
     */
    private ServerSocket serverSocket;

    /**
     * Class constructor. Sets some params and creates a server socket on given port.
     *
     * @param port Port to create server socket on.
     */
    private ServerController(int port)
    {
        isOn = true;
        //Create new server socket
        try
        {
            serverSocket = new ServerSocket(port);
            messageListenerAndSender = new MessageListenerAndSender();
            inputForBroadcast = new ArrayList<>();
            connectedUsers = new UserStorage();

            System.out.println("Server online");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Singleton getter.
     *
     * @return Singleton ServerController instance.
     */
    public static ServerController getInstance()
    {
        if (mInstance == null)
        {
            System.out.println("Server Controller has not been created!");
        }
        return mInstance;
    }

    /**
     * Singleton creator.
     *
     * @param port Port to create server on.
     * @return Instance of serverController.
     */
    static ServerController create(int port)
    {
        if (mInstance == null)
        {
            synchronized (ServerController.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ServerController(port);
                }
            }
            return mInstance;
        } else
            return null;

    }

    /**
     * Class for thread to start on. Creates a loop as long as {@link #isOn} value is true.
     * Awaits for new connections coming to server socket, and creates a new {@link ServerThread} instance for each one.
     */
    public void run()
    {
        //Accept connection from client
        try
        {
            while (isOn)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted! New client: " + clientSocket.toString());

                //Create a new thread for each client
                Runnable runnable = new ServerThread(clientSocket, this);
                Thread t = new Thread(runnable);
                t.start();

            }
        } catch (IOException e)
        {
            System.out.println("Server socket gone offline");
        }

    }

    UserStorage connectedUsers;

    /**
     * Getter for list of currently connected users.
     *
     * @return List of currently connected users.
     */
    public ArrayList<ConnectedClient> getConnectedUsers()
    {
        return connectedUsers.getCurrentUsers();
    }

    /**
     * Kicks the given user from the server.
     *
     * @param connectedClient User to be kicked.
     */
    void RemoveCurrentUser(ConnectedClient connectedClient)
    {
        connectedUsers.RemoveCurrentUser(connectedClient);
        System.out.println("User has disconnected: " + connectedClient.getUser().get_username());
        this.messageListenerAndSender.sendMessageThatUserIsKicked(connectedClient.getUser().get_username());
    }

    /**
     * Ends the server, closing connections.
     */
    void Close()
    {
        try
        {
            for (ConnectedClient client : getConnectedUsers())
            {
                client.clientSocket.close();
                RemoveCurrentUser(client);
            }
            serverSocket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        isOn = false;
    }

    /**
     * Class managing sending any new messages that have arrived.
     */
    public class MessageListenerAndSender implements NewMessageEventListener.NewGlobalMessageArrived,
            NewMessageEventListener.NewDirectedMessageArrived
    {

        /**
         * Sends a DM to given user.
         *
         * @param message        Reference to message to be sent.
         * @param targetUsername Username of the user the message is directed to.
         */
        @Override
        public void newDirectedMessageArrived(Message message, String targetUsername)
        {
            for (ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                if (client.getUser().get_username().compareTo(targetUsername) == 0)
                    //TODO:
                    //Add a flag to message if it's directed or not. Currently not showing if it's a DM.
                    client.broadcastStream.println(XMLOperations.ToXML(message));
            }

        }

        /**
         * Sends a message to all users currently connected to the server.
         */
        @Override
        public void newGlobalMessageArrived()
        {
            Message message = inputForBroadcast.remove(0);

            for (ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                client.broadcastStream.println(XMLOperations.ToXML(message));
            }
        }

        /**
         * Sends message to given user that he has been kicked from the server.
         * Forces some behaviour on the client side.
         *
         * @param targetUsername Username of the user to be kicked.
         */
        public void sendMessageThatUserIsKicked(String targetUsername)
        {
            Message kickmsg = new Message("//kick", "SERVER");
            for (ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                if (client.getUser().get_username().compareTo(targetUsername) == 0)
                {
                    client.broadcastStream.println(XMLOperations.ToXML(kickmsg));
                    return;
                }
            }
        }
    }
}

