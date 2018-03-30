package testClient;

public class TestClientApplication
{
    public static void main (String[] args)
    {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        TestClient client = new TestClient(hostName, portNumber);

        client.run();
    }


}
