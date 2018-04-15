package server;

import structs.Message;
import structs.XMLOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;



public class ServerThread implements Runnable
{
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
                ProcessContent(message);


                line = in.readLine();
                message = XMLOperations.FromXML(line);
            }

            //If we disconnect, close that socket
            this.serverController.RemoveCurrentUser(connectedClient);
            connectedClient.clientSocket.close();

        }   catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    private void ProcessContent(Message message)
    {
        String array[] = message.Content.split(" ", 3);

        //DM
        if(array[0].compareTo("/msg") == 0)
        {
            message.Content = array[2];
            serverController.messageListenerAndSender.newDirectedMessageArrived(message, array[1]);
        }

        else
        {
        //Else just send it through
        serverController.inputForBroadcast.add(message);
        serverController.messageListenerAndSender.newGlobalMessageArrived();
        }

    }



    private ConnectedClient connectedClient;
    private ServerController serverController;

    private BufferedReader in;

}
