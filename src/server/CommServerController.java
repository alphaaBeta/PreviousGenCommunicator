package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


        this.logFile = filename;


        /* Create or open a logfile */
        try
        {
            fileInputStream = new FileInputStream(logFile);
        } catch(FileNotFoundException e)
        {
            //If no file found, create one
            try
            {
                new File(logFile);
                fileInputStream = new FileInputStream(logFile);
            }catch (Exception el)
            {
                el.printStackTrace();
                System.out.printf("Error creating logfile");
            }

        }



    }

    //Handles receiving & sending messages to clients
    private CommServerSocketHandler SocketHandler;

    //For storing logs
    private String logFile = new String();

    private FileInputStream fileInputStream = null;
}
