package testClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class main
{
    public static void main (String[] args)
    {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try(
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                )
        {
            String line = consoleIn.readLine();

            while(line.compareTo("/q") != 0)
            {//TODO:

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
