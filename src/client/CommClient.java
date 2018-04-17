package client;

import structs.Message;
import structs.XMLOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class CommClient implements Runnable
{
    CommClient(String hostName, int portNumber, String username)
    {
        try
        {
            this.username = username;

            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Send the username...
            out.println(this.username);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public void run()
    {
        try
        {

            String line = in.readLine();

            Message message = XMLOperations.FromXML(line);
            while (message.Content.compareTo("//kick") != 0)
            {
                ClientApplication.getInstance().fxmlMessengerController.NewMessageArrived(message);
                line = in.readLine();

                message = XMLOperations.FromXML(line);
            }
        } catch (SocketException e)
        {
            System.out.println("Disconnected from server.");
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            DisconnectAndQuit();
        }
    }

    public void DisconnectAndQuit()
    {
        //Instance of this class exists, so socket must be connected
        Message message = new Message("/q", username);
        SendMessage(message);
        try
        {
            ClientApplication.getInstance().fxmlMessengerController.NotifyOfDisconnect();
            wait(1);
            try {
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            try {
                socket.close();
            } catch (IOException e2)
            {
                e.printStackTrace();
            }
        }

    }

    public void SendMessage(Message message)
    {
        String line = XMLOperations.ToXML(message);

        out.println(line);
    }


    private Socket socket = null;

    private PrintWriter out = null;
    private BufferedReader in = null;

    private String username;
}
