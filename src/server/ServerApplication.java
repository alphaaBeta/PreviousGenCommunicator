package server;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerApplication extends Application
{
    public static void main (String[] args)
    {
        int port = Integer.parseInt(args[0]);

        //Create server controller and run it
        try
        {
            ServerController serverController = ServerController.create(port);
            Thread t = new Thread(serverController);
            t.start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("fxml/server.fxml"));

        primaryStage.setTitle("PreviousGeneratorCommunicator Server");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
