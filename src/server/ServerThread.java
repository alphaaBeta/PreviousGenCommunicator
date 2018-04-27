package server;

import structs.Message;
import structs.XMLOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Runs a thread that will manage a single connection between the server and user.
 * Creates a socket, connects it and awaits input from connected client.
 * Processes the message content and performs special actions, if any are to take place.
 *
 * @see ServerController
 */
public class ServerThread implements Runnable
{

    /**
     * Reference to ConnectedClient object, containing some information about connectee.
     */
    private ConnectedClient connectedClient;
    /**
     * Reference to ServerController class.
     */
    private ServerController serverController;
    /**
     * Input stream, coming from the client.
     */
    private BufferedReader in;


    /**
     * Class constructor.
     * Sets params and creates connection.
     *
     * @param client           Client socket given by ServerController.
     * @param serverController Reference to ServerController creating the thread.
     */
    ServerThread(Socket client, ServerController serverController)
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            this.serverController = serverController;

            connectedClient = this.serverController.connectedUsers.AddConnectedClient(client, in.readLine());

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Keeps running in a loop, awaiting the message to arrive.
     */
    @Override
    public void run()
    {

        try
        {
            String line = in.readLine();

            Message message = XMLOperations.FromXML(line);

            //Command to disconnect from server
            while (serverController.isOn && message.Content.compareTo("/q") != 0)
            {
                ProcessContent(message);


                line = in.readLine();
                message = XMLOperations.FromXML(line);
            }

            //If we disconnect, close that socket
            this.serverController.RemoveCurrentUser(connectedClient);
            connectedClient.clientSocket.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Checks the message content and performs requested action (private msg, kick...)
     *
     * @param message Reference to message to be checked.
     */
    private void ProcessContent(Message message)
    {
        String array[] = message.Content.split(" ", 3);

        //DM
        if (array[0].compareTo("/msg") == 0)
        {
            message.Content = array[2];
            serverController.messageListenerAndSender.newDirectedMessageArrived(message, array[1]);
        } else
        {
            //Else just send it through
            serverController.inputForBroadcast.add(message);
            serverController.messageListenerAndSender.newGlobalMessageArrived();
        }

    }

}
