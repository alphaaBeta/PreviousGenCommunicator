package server;

import structs.Message;
import structs.XMLOperations;

import javax.swing.event.ChangeEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

interface ChangeEventGenerator
{
    void FireChangeEvent(ChangeEvent e);
}

public class ServerThread implements Runnable,
                                    ChangeEventGenerator
{
    ServerThread(Socket client, ServerController serverController)
    {
        this.client = client;

        try
        {
            PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

            this.serverController = serverController;


            this.serverController.broadcastList.ensureCapacity(10);
            this.serverController.broadcastList.add(out);


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {

        try
        {
            String line = in.readLine();

            Message message = XMLOperations.FromXML(line);

            //Command to disconnect from server
            while(message.Content.compareTo("/q") != 0)
            {
                //Add message to message list and fire an event that a new one has arrived
                serverController.input.add(message);
                FireChangeEvent(new ChangeEvent(serverController.input));

                line = in.readLine();
                message = XMLOperations.FromXML(line);
            }

            //If we disconnect, close that socket
            client.close();

        }   catch(IOException e)
        {
            e.printStackTrace();
        }

    }


    private Socket client;
    private ServerController serverController;

    private BufferedReader in;

    @Override
    public void FireChangeEvent(ChangeEvent e)
    {
        serverController.messageListenerAndSender.stateChanged(e);
    }
}
