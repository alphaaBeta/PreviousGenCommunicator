package server;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerApplication extends Application
{

    public ServerController serverController = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int port = Integer.parseInt(getParameters().getRaw().get(0));
        //Create server controller and run it
        try
        {
            ServerApplication.getInstance().serverController = ServerController.create(port);
            Thread t = new Thread(ServerApplication.getInstance().serverController);
            t.start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }


        BorderPane root = FXMLLoader.load(getClass().getResource("fxml/server.fxml"));

        primaryStage.setTitle("PreviousGeneratorCommunicator Server");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try
            {
                if(serverController != null)
                {
                    ServerApplication.getInstance().serverController.Close();
                    stop();
                } else
                    stop();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    public static void main (String[] args)
    {
        launch(args);
    }

    private static ServerApplication mInstance;

    public ServerApplication()
    {
        mInstance = this;
    }

    public static ServerApplication getInstance()
    {
        return mInstance;
    }
}
