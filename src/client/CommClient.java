package client;

import javafx.application.Platform;
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
                ClientApplication.getInstance().fxmlMessengerController.new_message_arrived(message);
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
            Quit();
        }
    }

    public void new_message_to_send(Message message)
    {
        String line = XMLOperations.ToXML(message);

        out.println(line);
    }

    private void Quit() {
        try {
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    private String username;


    /*//Class to run user input in another thread
    class UserInput implements Runnable
    {
        private UserInput(CommClient client)
        {
            in = new BufferedReader(new InputStreamReader(System.in));
            //this.client = client;

            clientOut = client.out;
        }

        private BufferedReader in;

        @Override
        public void run()
        {
            //Send username to identify the user
            clientOut.println(username);

            System.out.println("Awaiting input to send: ");
            String line = "";
            Message message = new Message(line, username);
            try
            {
                while (true)
                {
                    if(!message.Content.isEmpty())
                    {
                        line = XMLOperations.ToXML(message);
                        clientOut.println(line);
                    }

                    line = in.readLine();
                    message.Refresh(line, username);
                }
            }   catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        private PrintWriter clientOut;
    }*/
}
