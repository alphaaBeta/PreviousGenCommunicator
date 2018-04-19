package client;

import client.fxml.FXMLLoginController;
import client.fxml.FXMLMessengerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to start the javaFX application and switch scenes when needed.
 * Contains references to specific scene controller, reference to CoommClient and some information
 * returned by CommClient, like `username`.
 *
 * @see CommClient
 * @see FXMLLoginController
 * @see FXMLMessengerController
 */
public class ClientApplication extends Application
{


    /**
     * Instance of the class, used by singleton.
     */
    private static ClientApplication mInstance;
    /**
     * References to specific controllers.
     */
    public FXMLLoginController fxmlLoginController = null;
    /**
     * Username, set by FXMLLoginController.
     *
     * @see FXMLLoginController
     */
    public String username = null;
    public FXMLMessengerController fxmlMessengerController = null;
    /**
     * Reference to CommClient instance.
     *
     * @see CommClient
     */
    public CommClient client;
    /**
     * Current stage of javaFX application.
     */
    private Stage stage;
    /**
     * Hostname specifying the address of the server to connect to.
     * Currently being set by application starting argument.
     */
    private String hostName;

    /**
     * Port of the server to connect to.
     * Currently being set by application starting argument.
     */
    private int port;


    /**
     * Class constructor, made for singleton.
     */
    public ClientApplication()
    {
        mInstance = this;
    }

    /**
     * Singleton getInstance() method.
     *
     * @return The instance of this class.
     */
    public static ClientApplication getInstance()
    {
        return mInstance;
    }

    /**
     * Main method launching the {@link #start(Stage)} method of JavaFX by using jFX's lunch(args)
     *
     * @param args Application start arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * JavaFX method, used to start the application, launched by launch() from main() method.
     *
     * @param primaryStage Default application stage.
     */
    @Override
    public void start(Stage primaryStage)
    {
        hostName = getParameters().getRaw().get(0);
        port = Integer.parseInt(getParameters().getRaw().get(1));


        stage = primaryStage;
        gotoLogin();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            if (client != null)
            {
                //primaryStage.close();
                client.DisconnectAndQuit();
            } else
            {
                try
                {
                    //primaryStage.close();
                    stop();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Starts the CommClient instance in another thread and changes the scene.
     * Scene is changed to Messenger scene using {@link #gotoMessenger()}
     *
     * @see CommClient
     */
    public void startClient()
    {
        client = new CommClient(hostName, port, username);
        Thread t = new Thread(client);
        t.start();

        gotoMessenger();
    }

    /**
     * Replaces the scene with Messenger scene, by loading it from corresponding FXML file.
     * Calls {@link #replaceSceneContent(String)}
     */
    private void gotoMessenger()
    {
        try
        {
            replaceSceneContent("fxml/messenger.fxml");
            stage.setTitle("Messenger");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Replaces the scene with Login scene, by loading it from corresponding FXML file.
     * Calls {@link #replaceSceneContent(String)}
     */
    private void gotoLogin()
    {
        try
        {
            replaceSceneContent("fxml/login.fxml");
            stage.setTitle("Login");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Replaces the scene content with one specified in FXML file in param.
     * Uses FXMLLoader.
     *
     * @param fxmlFile FXML file to load scene from.
     * @throws Exception Error in loading the file. File does not exist or is incorrect.
     */
    private void replaceSceneContent(String fxmlFile) throws Exception
    {

        Parent parent = FXMLLoader.load(ClientApplication.class.getResource(fxmlFile), null, new JavaFXBuilderFactory());

        Scene scene = stage.getScene();
        if (scene == null)
        {
            scene = new Scene(parent);
            stage.setScene(scene);
        } else
        {
            stage.getScene().setRoot(parent);
        }

        stage.sizeToScene();
    }
}
