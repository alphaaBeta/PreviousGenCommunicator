package structs;

import server.ConnectedClient;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to contain users currently connected to the server.
 * Underused, in future possible handling all passwords and other info.
 *
 * @see User
 */
public class UserStorage
{
    /**
     * Method used to get the list of users currently connected to the server.
     * @return An ArrayList of users currently connected
     */
    public ArrayList<ConnectedClient> getCurrentUsers()
    {
        return new ArrayList<>(currentUsers);
    }

    /**
     * Adds user specified by username to the current userlist of server
     * @param username username of user to be added
     */
    public ConnectedClient AddConnectedClient(Socket clientSocket, String username)
    {
        ConnectedClient joiningUser;

        //If new user joins in
        if(!userDatabase.containsKey(username))
        {
            joiningUser = new ConnectedClient(clientSocket,new User(username));
            currentUsers.add(joiningUser);
            userDatabase.put(username, joiningUser.getUser());
        }
        else
        {
            joiningUser = new ConnectedClient(clientSocket, userDatabase.get(username));
            currentUsers.add(joiningUser);
        }
        return joiningUser;
    }

    /**
     * Removes a connected user from the list.
     * @param connectedClient User that's connected to the server
     */
    public void RemoveCurrentUser(ConnectedClient connectedClient)
    {
        currentUsers.remove(connectedClient);
    }

    /*/**
     * Get a copy of user struct, based on username
     * @param username username of user to get info for
     * @return User struct of given username
     */
    /*public User GetUserInfo(String username)
    {
        return new User(userDatabase.get(username));
    }*/

    /**
     * User storage constructor. Creates an empty list and hashmap.
     * Hashmap currently unused.
     */
    public UserStorage()
    {

        currentUsers = new ArrayList<>();
        userDatabase = new HashMap<>();
    }


    /**
     * List of users currently connected to the server.
     */
    private ArrayList<ConnectedClient> currentUsers;

    /**
     * Holds matches of User class to usernames.
     */
    private Map<String, User> userDatabase;
}
