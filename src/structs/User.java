package structs;


public class User
{
    private String _username;

    public String get_username ()
    {
        return _username;
    }

    //TODO: implement some info about user (passwords?)
    //private Date _firstSeen;
    //private Date _lastSeen;


    public User(String username)
    {
        _username = username;
    }

    public User(User user)
    {
        this._username = user._username;
        //...
    }
}
