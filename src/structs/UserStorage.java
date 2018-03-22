package structs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStorage
{
    public List<User> get_currentUsers ()
    {
        //List<String> userList = new ArrayList<>(_currentUsers);
        return new ArrayList<>(_currentUsers);
    }

    /**
     * Adds user specified by username to the current userlist of server
     * @param username username of user to be added
     */
    public void AddCurrentUser(String username)
    {
        User joiningUser;

        //If new user joins in
        if(!_userDatabase.containsKey(username))
        {
            joiningUser = new User(username);
            _currentUsers.add(joiningUser);
            _userDatabase.put(joiningUser.get_username(), joiningUser);
        }
        else
        {
            joiningUser = new User(_userDatabase.get(username));
            _currentUsers.add(joiningUser);
        }
    }

    public void RemoveCurrentUser(String username)
    {
        User leavingUser = new User(_userDatabase.get(username));
        _currentUsers.remove(leavingUser);
    }

    /**
     * Get a copy of user struct, based on username
     * @param username username of user to get info for
     * @return User struct of given username
     */
    public User GetUserInfo(String username)
    {
        return new User(_userDatabase.get(username));
    }

    public UserStorage()
    {

        String userdataFilename = "userdata";
        _currentUsers = new ArrayList<>();
        _userDatabase = new HashMap<>();




        //Read a user data file
        _userdataFile = new File(userdataFilename);
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(_userdataFile);
            XStream xStream = new XStream(new DomDriver());
            _userDatabase = (Map<String, User>) xStream.fromXML(fis);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Could not load user data file");
        }
        finally
        {
            try
            {
                fis.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }

    /**
     * Saves _userDatabase in file
     */
    public void Close()
    {
        if (!_userDatabase.isEmpty())
        {
            try
            {
                XStream xStream = new XStream(new DomDriver());
                xStream.alias("_userDatabase", java.util.Map.class);
                String xml = xStream.toXML(_userDatabase);

                PrintWriter printWriter = new PrintWriter(_userdataFile);
                printWriter.append(xml);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
                System.out.println("Could not save to file");
            }
        }
    }

    private List<User> _currentUsers;
    private Map<String, User> _userDatabase;
    private File _userdataFile;
}
