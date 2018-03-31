package server;

import structs.Message;
import structs.XMLOperations;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ServerController
{
    ServerController(int port)
    {
        //Create new server socket
        try {
            serverSocket = new ServerSocket(port);
            messageListenerAndSender = new MessageListenerAndSender();
            broadcastList = new ArrayList<>();
            input = new ArrayList<>();//POWINNO DZIALAC TEORETYCZNIE WYJEBYWALO NA BROADCASTLIST.ADDl

            System.out.println("Server online");
        }   catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void run()
    {
        //Accept connection from client
        try
        {
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");

                //Create a new thread for each client
                Runnable runnable = new ServerThread(clientSocket, this);
                Thread t = new Thread(runnable);
                t.start();

            }
        }   catch(IOException e)
        {
            e.printStackTrace();
        }

        //Process input

    }

    private ServerSocket serverSocket;


    ArrayList<Message> input;
    ArrayList<PrintWriter> broadcastList;
    MessageListenerAndSender messageListenerAndSender;

    class MessageListenerAndSender implements ChangeListener
    {

        @Override
        public void stateChanged(ChangeEvent e)
        {
            Message message = input.remove(0);

            //String string = XMLOperations.ToXML(message);

            for(PrintWriter pw : broadcastList)
            {
                pw.println("<" + message.Username + "> " + message.Content);
                pw.flush();
            }
        }
    }
}

