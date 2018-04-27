package server;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Class to start the server javaFX application and handle 'soft quit'.
 * Singleton. Contains reference to instance of ServerController class.
 *
 * @see ServerController
 * @see server.fxml.FXMLServer
 */
public class ServerApplication extends Application
{

    /**
     * Singleton instance field.
     */
    private static ServerApplication mInstance;
    /**
     * Reference to ServerController class, running in another thread.
     *
     * @see ServerController
     */
    public ServerController serverController = null;

    /**
     * Singleton constructor.
     */
    public ServerApplication()
    {
        mInstance = this;
    }

    /**
     * Main function of the application, launching the start method.
     *
     * @param args Arguments given while launching application.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Singleton getter method.
     *
     * @return ServerApplication instance.
     */
    public static ServerApplication getInstance()
    {
        return mInstance;
    }

    /**
     * JavaFX application start method, launched from within main function.
     *
     * @param primaryStage Stage that will be set as primary.
     * @throws Exception Failure to load the fxml file
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
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
                if (serverController != null)
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
}
