package server;

import structs.Message;
import structs.UserStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController implements Runnable
{
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
    MessageListenerAndSender messageListenerAndSender;
    UserStorage connectedUsers;

    void RemoveCurrentUser(ConnectedClient connectedClient)
    {
        connectedUsers.RemoveCurrentUser(connectedClient);
        System.out.println("User has disconnected: " + connectedClient.user.get_username());
    }

    class MessageListenerAndSender implements NewGlobalMessageEventListener,
                                                NewDirectedMessageEventListener
    {

        @Override
        public void newDirectedMessageArrived(Message message, String targetUsername)
        {
            for(ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                if(client.user.get_username().compareTo(targetUsername) == 0)
                    client.broadcastStream.println("DM: <" + message.Username + "> " + message.Content);
            }

        }

        @Override
        public void newGlobalMessageArrived()
        {
            Message message = inputForBroadcast.remove(0);

            for(ConnectedClient client : connectedUsers.getCurrentUsers())
            {
                client.broadcastStream.println("<" + message.Username + "> " + message.Content);
            }
        }
    }
}

