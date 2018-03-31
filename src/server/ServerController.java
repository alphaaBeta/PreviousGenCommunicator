package server;

import structs.Message;
import structs.XMLOperations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ServerController
{
    ServerController(int port)
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

            Message message = XMLOperations.FromXML(line);

            //Command to disconnect from server
            while(message.Content.compareTo("/q") != 0)
            {
                System.out.println("Message received: \"" + message.Content + "\"");
                out.println("<" + message.Username + "> " + message.Content);

                line = in.readLine();
                message = XMLOperations.FromXML(line);
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
