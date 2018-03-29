package server;


public class ServerApplication
{
    public static void main (String[] args)
    {
        int port = Integer.parseInt(args[0]);

        //Create server controller and run it
        try
        {
            ServerController serverController = new ServerController(port);
            serverController.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
