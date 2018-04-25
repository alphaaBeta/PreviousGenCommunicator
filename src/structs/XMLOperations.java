package structs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Class containing static methods to change Message struct to XML and vice versa.
 * Used to send strings between server and client.
 * @see Message
 */
public class XMLOperations
{
    /**
     * Changes given message to XML string.
     * @param message Message to be converted to XML string.
     * @return XML string of message struct.
     */
    static public String ToXML(Message message)
    {

        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.alias("message", Message.class);
        String string = xStream.toXML(message);
        string = string.replaceAll("\n", " ");

        return string;
    }

    /**
     * Changes the XML string to Message struct.
     * @param xml XML string to be converted.
     * @return Message struct of converted string.
     */
    static public Message FromXML(String xml)
    {
        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.alias("message", Message.class);
        return (Message)xStream.fromXML(xml);
    }

    /**
     * List of classes that are allowed to be converted.
     * Security.
     */
    static private Class<?>[] classes = new Class[] { Message.class };
}
