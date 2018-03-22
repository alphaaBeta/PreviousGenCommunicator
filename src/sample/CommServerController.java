package sample;

import java.net.*;
//import java.net.ServerSocket;

public class CommServerController
{
    public CommServerController(int port, String filename)
    {
        InetSocketAddress socketAddress = new InetSocketAddress(port);

        SocketHandler = new CommServerSocketHandler(socketAddress);

        this.logFile = filename;
    }

    //Handles receiving & sending messages to clients,
    //returns messages that can be added to MessageStorage
    private CommServerSocketHandler SocketHandler;

    //For storing logs
    private String logFile = new String();
}
