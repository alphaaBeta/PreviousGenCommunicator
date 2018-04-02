package testClient;

import structs.Message;
import structs.XMLOperations;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class TestClient
{

    TestClient(String hostName, int portNumber)
    {
        try
        {
            //Get username
            BufferedReader aux = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your nickname: \n");
            username = aux.readLine();

            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        Runnable runnable = new UserInput(this);
        Thread t = new Thread(runnable);
        t.start();

        try
        {
            String line = in.readLine();
            while (line.compareTo("//kick") != 0)
            {
                System.out.println(line);
                line = in.readLine();
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

    //placeholder
    private String username;


    //Class to run user input in another thread
    class UserInput implements Runnable
    {
        private UserInput(TestClient client)
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
        //private TestClient client;
    }

}


