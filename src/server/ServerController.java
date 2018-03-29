package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController
{
    public ServerController(int port)
    {
        //Create new server socket
        try {
            serverSocket = new ServerSocket(port);
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
            clientSocket = serverSocket.accept();

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }   catch(IOException e)
        {
            e.printStackTrace();
        }

        //Process input
        try
        {
            String line = in.readLine();
            String temp = null;

            //Command to disconnect from server
            while(line.compareTo("/q") != 0)
            {
                //Echo:
                temp = MessageProcessor.ServerSideProcess(line);
                out.println(temp);
                line = in.readLine();
            }

            //If we disconnect, close that socket
            clientSocket.close();

        }   catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;
}
