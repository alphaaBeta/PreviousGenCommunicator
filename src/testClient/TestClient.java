package testClient;

import java.io.*;
import java.net.Socket;

public class TestClient
{

    TestClient(String hostName, int portNumber)
    {
        try
        {
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


    class UserInput implements Runnable
    {
        private UserInput(TestClient client)
        {
            in = new BufferedReader(new InputStreamReader(System.in));
            this.client = client;

            clientOut = client.out;
        }

        private BufferedReader in;

        @Override
        public void run() {

            System.out.println("Awaiting input to send: ");
            String line = "";
            try
            {
                while (line.compareTo("/q") != 0) {

                    if(!line.isEmpty())
                    {
                        clientOut.println(line);
                        clientOut.flush();
                    }

                    line = in.readLine();


                }
            }   catch(IOException e)
            {
                e.printStackTrace();
            } finally
            {
                client.Quit();
            }
        }

        private PrintWriter clientOut;
        private TestClient client;
    }

}


