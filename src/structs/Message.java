package structs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public class Message //implements ContainerListener,
                      //                  ActionListener
{
    /*@Override
    public void componentAdded(ContainerEvent e)
    {

    }

    @Override
    public void componentRemoved(ContainerEvent e)
    {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }*/

    public String Message;

    public String Username;

    public String Date;

    public String ToXML()
    {
        return "test";
    }

    public Message FromXML()
    {
        return this;
    }
}
