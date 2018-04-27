package server;

import structs.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A helper class encapsulating some information about connected user.
 */
public class ConnectedClient
{
    /**
     * Reference to client socket.
     */
    Socket clientSocket;
    /**
     * Stream to print to if one wishes to send message to the user.
     */
    PrintWriter broadcastStream;
    /**
     * User object.
     *
     * @see User
     */
    private User user;

    /**
     * Constructor.
     *
     * @param clientSocket Socket the client is connected on/
     * @param user         User connected on the given socket.
     */
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

    /**
     * User class getter.
     *
     * @return A copy of user.
     */
    public User getUser()
    {
        return user;
    }

}
