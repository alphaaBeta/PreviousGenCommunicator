package server;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class ServerApplication extends Application
{
    public static void main (String[] args)
    {
        int port = Integer.parseInt(args[0]);

        //Create server controller and run it
        try
        {
            ServerController serverController = new ServerController(port);
            Thread t = new Thread(serverController);
            //serverController.run();
            t.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("server.fxml"));
        //AnchorPane root = new AnchorPane();
        primaryStage.setTitle("Hellossss World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        Button button = new Button();
        button.setText("guzikhahahhaahahhahahahahahaha");
        button.setOnAction(event -> System.out.println("xddd"));




    }
}
