package structs;

/**
 * Class to store each user's info in.
 * Currently stores nothing but username; might be set to set date of first join, date of last join, password, etc.
 */
public class User
{
    /**
     * Attribute. Use #get_username.
     */
    private String _username;

    /**
     * Gets the user's username.
     * @return  User's username
     */
    public String get_username ()
    {
        return _username;
    }

    //TODO: implement some info about user (passwords?)
    //private Date _firstSeen;
    //private Date _lastSeen;


    /**
     * Constructor for user, currently only sets the username.
     * @param username Username of the user to be set
     */
    public User(String username)
    {
        _username = username;
    }

    /**
     * Copying constructor.
     * @param user Other user.
     */
    public User(User user)
    {
        this._username = user._username;
        //...
    }
}
