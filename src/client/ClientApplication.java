package client;

import client.fxml.FXMLLoginController;
import client.fxml.FXMLMessengerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {


    private static ClientApplication mInstance;
    private Stage stage;
    //private Scene login, messenger;
    public FXMLLoginController fxmlLoginController = null;
    public FXMLMessengerController fxmlMessengerController = null;

    public String username;
    public CommClient client;
    private String hostName;
    private int port;

    public ClientApplication()
    {
        mInstance = this;
    }

    public static ClientApplication getInstance()
    {
        return mInstance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        hostName = getParameters().getRaw().get(0);
        port = Integer.parseInt(getParameters().getRaw().get(1));


        stage = primaryStage;
        gotoLogin();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
                if (client != null)
                {
                    primaryStage.close();
                    client.Disconnect();
                }
                else
                {
                    try
                    {
                        primaryStage.close();
                        stop();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });



    }

    public void startClient()
    {
        client = new CommClient(hostName, port, username);
        Thread t = new Thread(client);
        t.start();

        gotoMessenger();
    }

    private void gotoMessenger()
    {
        try
        {
            replaceSceneContent("fxml/messenger.fxml");
            stage.setTitle("Messenger");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void gotoLogin()
    {
        try
        {
            replaceSceneContent("fxml/login.fxml");
            stage.setTitle("Login");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Parent replaceSceneContent(String fxmlFile) throws Exception
    {

        Parent parent = (Parent) FXMLLoader.load(ClientApplication.class.getResource(fxmlFile), null, new JavaFXBuilderFactory());

        Scene scene = stage.getScene();
        if (scene == null)
        {
            scene = new Scene(parent);
            stage.setScene(scene);
        }
        else
        {
            stage.getScene().setRoot(parent);
        }

        stage.sizeToScene();
        return parent;
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
