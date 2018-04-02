package structs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.ConnectedClient;

import java.io.*;
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

        //String userdataFilename = "userdata";
        currentUsers = new ArrayList<>();
        userDatabase = new HashMap<>();




        /*//Read a user data file
        _userdataFile = new File(userdataFilename);
        FileInputStream fis = null;
        try
        {
            File _userdataFile = _userdataFile.createNewFile();
            fis = new FileInputStream(_userdataFile);
            XStream xStream = new XStream(new DomDriver());
            userDatabase = (Map<String, User>) xStream.fromXML(fis);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Could not load user data file");
        }*/
    }

    /**
     * Saves userDatabase in file
     */
    public void Close()
    {
        /*if (!userDatabase.isEmpty())
        {
            try
            {
                XStream xStream = new XStream(new DomDriver());
                xStream.alias("userDatabase", java.util.Map.class);
                String xml = xStream.toXML(userDatabase);

                PrintWriter printWriter = new PrintWriter(_userdataFile);
                printWriter.append(xml);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
                System.out.println("Could not save to file");
            }
        }*/
    }

    private ArrayList<ConnectedClient> currentUsers;
    private Map<String, User> userDatabase;
    //private File _userdataFile;
}
