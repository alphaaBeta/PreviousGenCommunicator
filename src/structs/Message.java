package structs;

/**
 * A class to information about message sent and received.
 * Currently stores the content and username of entity that sent it.
 * Is used to work with message internally; to send or receive it, one needs to change it from or to XML format.
 *
 * @see XMLOperations
 */
public class Message
{
    /**
     * Content of a message.
     */
    public String Content;

    /**
     * Username of the user that sent the message.
     */
    public String Username;

    //public String Date;

    /**
     * Constructor for message.
     *
     * @param content Content of the message
     * @param username Username of the user that sent the message
     */
    public Message(String content, String username)
    {
        this.Username = username;
        this.Content = content;
    }

    /**
     * Refreshes the message content and username; used to avoid creation of new messages, and to avoid accessing
     * attributes.
     * @param content Content of the message to be set
     * @param username Username of the user that sent the message to be set
     */
    public void Refresh(String content, String username)
    {
        this.Username = username;
        this.Content = content;
    }

}

