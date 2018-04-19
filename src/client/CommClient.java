package client;

import client.fxml.FXMLMessengerController;
import javafx.stage.Stage;
import structs.Message;
import structs.XMLOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Class containing all components to make java.net Sockets work.
 * Usually being started by ClientApplication.
 *
 * @see ClientApplication#start(Stage)
 */
public class CommClient implements Runnable
{
    /**
     * Socket used to connect to the server.
     */
    private Socket socket = null;

    /**
     * PrintWriter used to send information to the server.
     */
    private PrintWriter out = null;
    /**
     * BufferedReader used to read information being sent from the server.
     */
    private BufferedReader in = null;

    /**
     * Username used in creating the message to be sent to the server.
     */
    private String username;

    /**
     * Class constructor.
     * Initializes the socket, sets the out and in streams and send the username to the server.
     * @param hostName Address to connect to.
     * @param portNumber Server port listening for connections.
     * @param username Username.
     */
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


    /**
     * Main CommClient loop, listening for new messages.
     * Method from Implements interface.
     * Used to start thread. Constantly listening to input stream, converting the XML string into message struct
     * and sending it further to FXMLMessengerController, notifying of new message.
     * When a specific message arrives, it closes the connection from the server and quits.
     *
     * @see client.fxml.FXMLMessengerController#NewMessageArrived(Message)
     * @see CommClient#DisconnectAndQuit()
     */
    @Override
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

    /**
     * Closes the socket, tells the server this client has disconnected and calls a method from FXMLMessangerController.
     * Notification of disconnect is implemented by sending a message with specific content to the server, server
     * then processes it and send back answer message, which usually will not be answered.
     * @see FXMLMessengerController#NotifyOfDisconnect()
     */
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

    /**
     * Sends the message to the server.
     * Converts the message to XML string that can be sent using sockets.
     * @param message Message struct to be converted and sent as XML string.
     * @see XMLOperations#ToXML(Message)
     */
    public void SendMessage(Message message)
    {
        String line = XMLOperations.ToXML(message);

        out.println(line);
    }

}
