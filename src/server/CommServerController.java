package server;

import java.io.*;
import java.net.*;
//import java.net.ServerSocket;

public class CommServerController
{
    public CommServerController(int port, String filename)
    {
        InetSocketAddress socketAddress = new InetSocketAddress(port);

        /* Create a CommServerSocketHandler instance */
        try
        {
            SocketHandler = new CommServerSocketHandler(socketAddress);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.printf("CommServerController error");
        }


        FileInputStream fileInputStream;


        /* Create or open a logfile */
        try
        {
            fileInputStream = new FileInputStream(filename);
        } catch(FileNotFoundException e)
        {
            //If no file found, create one
            try
            {
                new File(filename);
                fileInputStream = new FileInputStream(filename);
            }catch (Exception el)
            {
                el.printStackTrace();
                System.out.printf("Error creating logfile");
            }

        }



    }

    public void Start()
    {
        String line;
        try
        {
            while (true)
            {
                line = inputStream.readLine();
                wait(10);
                outputStreamBroadcast.append(line);
                outputStreamBroadcast.flush();
                wait(20);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error while running the server.");
        }
    }

    //Handles receiving & sending messages to clients
    private CommServerSocketHandler SocketHandler;

    private FileInputStream fileInputStream = null;

    public BufferedWriter outputStreamBroadcast;
    public BufferedReader inputStream;
}
