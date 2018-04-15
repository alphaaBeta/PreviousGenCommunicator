package structs;

import server.ConnectedClient;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserStorage
{
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

    public UserStorage()
    {

        currentUsers = new ArrayList<>();
        userDatabase = new HashMap<>();
    }



    private ArrayList<ConnectedClient> currentUsers;
    private Map<String, User> userDatabase;
}
