package server;

import structs.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient
{
    public ConnectedClient(Socket clientSocket, User user)
    {
        this.clientSocket = clientSocket;

        this.user = user;

        try
        {
            broadcastStream = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    Socket clientSocket;

    public User getUser()
    {
        return user;
    }

    User user;

    PrintWriter broadcastStream;

}
