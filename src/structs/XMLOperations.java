package structs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLOperations
{
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

    static public Message FromXML(String xml)
    {
        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.alias("message", Message.class);
        return (Message)xStream.fromXML(xml);
    }

    static private Class<?>[] classes = new Class[] { Message.class };
}
