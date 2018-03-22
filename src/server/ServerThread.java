package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/** Code in courtesy of:
*@author Devi Prabhaharan
*{@see <a href="https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server">stackoverflow page</a> }
*/
class ServerThread extends Thread
{

    String line = null;
    BufferedReader inputStream = null;
    PrintWriter outputStream = null;
    Socket clientSocket = null;

    public ServerThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run()
    {
        try
        {
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(clientSocket.getOutputStream());

        }catch(IOException e)
        {
            System.out.println("IO error in server thread");
        }


        try
        {
            line = inputStream.readLine();
            while(line.compareTo("QUIT")!=0)
            {

                outputStream.println(line);
                outputStream.flush();
                System.out.println("Response to Client  :  "+line);
                line= inputStream.readLine();
            }
        } catch (IOException e)
        {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e)
        {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        }


        finally
        {
            try
            {
                System.out.println("Connection Closing..");
                if (inputStream !=null){
                    inputStream.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if(outputStream !=null){
                    outputStream.close();
                    System.out.println("Socket Out Closed");
                }
                if (clientSocket !=null){
                    clientSocket.close();
                    System.out.println("Socket Closed");
                }

            } catch(IOException ie)
            {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}