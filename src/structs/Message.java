package structs;

public class Message
{
    public String Content;

    public String Username;

    //public String Date;

    public Message(String content, String username)
    {
        this.Username = username;
        this.Content = content;
    }

    public void Refresh(String content, String username)
    {
        this.Username = username;
        this.Content = content;
    }

}

