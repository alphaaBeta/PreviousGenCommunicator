package server;

import structs.Message;
import structs.UserStorage;
import structs.XMLOperations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController implements Runnable
{
    private static volatile ServerController mInstance;

    ServerController(int port)
    {
        //Create new server socket
        try {
            serverSocket = new ServerSocket(port);
            messageListenerAndSender = new MessageListenerAndSender();
            inputForBroadcast = new ArrayList<>();
            connectedUsers = new UserStorage();

            System.out.println("Server online");
        }   catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static ServerController getInstance()
    {
        if(mInstance == null)
        {
            System.out.println("Server Controller has not been created!");
        }
        return mInstance;
    }

    public static ServerController create(int port)
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
        }
        else
            return null;

    }

    public void run()
    {
        //Accept connection from client
        try
        {
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted! New client: " + clientSocket.toString());

                //Create a new thread for each client
                Runnable runnable = new ServerThread(clientSocket, this);
                Thread t = new Thread(runnable);
                t.start();

            }
        }   catch(IOException e)
        {
            e.printStackTrace();
        }

        //Process input

    }

    private ServerSocket serverSocket;


    ArrayList<Message> inputForBroadcast;
    public MessageListenerAndSender messageListenerAndSender;
    UserStorage connectedUsers;

    public ArrayList<ConnectedClient> getConnectedUsers()
    {
        return connectedUsers.getCurrentUsers();
    }

    void RemoveCurrentUser(ConnectedClient connectedClient)
    {
        connectedUsers.RemoveCurrentUser(connectedClient);
        System.out.println("User has disconnected: " + connectedClient.getUser().get_username());
    }

    public class MessageListenerAndSender implements   NewMessageEventListener.NewGlobalMessageArrived,
                                                NewMessageEventListener.NewDirectedMessageArrived
    {

        @Override
        public void newDirectedMessageArrived(Message message, String targetUsername)
        {
            for(ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                if(client.getUser().get_username().compareTo(targetUsername) == 0)
                    //TODO:
                    //Add a flag to message if it's directed or not. Currently not showing if it's a DM.
                    client.broadcastStream.println(XMLOperations.ToXML(message));
            }

        }

        @Override
        public void newGlobalMessageArrived()
        {
            Message message = inputForBroadcast.remove(0);

            for(ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                client.broadcastStream.println(XMLOperations.ToXML(message));
            }
        }

        public void sendMessageThatUserIsKicked(String targetUsername)
        {
            Message kickmsg = new Message("//kick", "SERVER");
            for(ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                if(client.getUser().get_username().compareTo(targetUsername) == 0)
                {
                    client.broadcastStream.println(XMLOperations.ToXML(kickmsg));
                    return;
                }
            }
        }
    }
}

