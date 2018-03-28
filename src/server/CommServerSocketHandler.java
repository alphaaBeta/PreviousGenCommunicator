package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;


public class CommServerSocketHandler// implements ActionListener
{
    public CommServerSocketHandler(InetSocketAddress socketAddress) throws IOException
    {

        Socket clientSocket = null;

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(socketAddress.getPort());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Server error");
        }

        while(true)
        {
            try{
                clientSocket = serverSocket.accept();
                ++numberOfConnections;
                System.out.printf("Connection estabilished with Client no %d:\n", numberOfConnections);
                System.out.println(clientSocket.getInetAddress());

                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.printf("Connection error");
            }
        }
    }


    private int numberOfConnections = 0;

}