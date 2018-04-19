package client.fxml;

import client.ClientApplication;
import client.CommClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structs.Message;

/**
 * Class containing methods to control the Messenger scene of the app.
 */
public class FXMLMessengerController
{

    @FXML
    public Button disconnect_button;
    public VBox textbox;
    public TextField textfield;
    public Button send_button;
    public Label user_label;

    /**
     * Auxiliary message struct to avoid creation of new objects.
     */
    private Message message = new Message("null", "empty");

    /**
     * Class constructor.
     * Assigns this object to ClientApplication reference of this type.
     * Also sets the {@link #user_label}.
     *
     * @see ClientApplication#fxmlMessengerController
     */
    public FXMLMessengerController()
    {
        ClientApplication.getInstance().fxmlMessengerController = this;
        setUser_label(ClientApplication.getInstance().username);
    }


    /**
     * Clears the {@link #textfield} and sends the contents by calling {@link #SendMessage(Message)}.
     */
    @FXML
    public void send_message_button()
    {
        message.Refresh(textfield.getText(), ClientApplication.getInstance().username);
        textfield.clear();

        SendMessage(message);
    }

    /**
     * Method sending the message with content taken from {@link #textfield}.
     * Calls {@link #send_message_button()}.
     * @param keyEvent Used to check what key was pressed. If it was ENTER, message is sent.
     */
    @FXML
    public void send_message_enter(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            send_message_button();
        }
    }

    /**
     * Calls {@link #Disconnect()}
     */
    @FXML
    public void disconnect()
    {
        Disconnect();
    }

    /**
     * Calls {@link client.CommClient#SendMessage(Message)} with given message.
     * @param message Message to be sent.
     */
    private void SendMessage(Message message)
    {
        ClientApplication.getInstance().client.SendMessage(message);
    }

    /**
     * Adds a newly arrived message to the textbox.
     * Creates a string, adds it to a new HBox and adds it to the textbox.
     * @param message Received message.
     */
    public void NewMessageArrived(Message message)
    {
        String usernameNew = "<" + message.Username + ">";

        HBox hBox = new HBox();
        Text username = new Text(usernameNew);
        Text content = new Text(message.Content);

        hBox.setSpacing(4);
        username.setFill(Color.RED);
        content.setWrappingWidth(360.00);

        hBox.getChildren().addAll(username, content);

        Platform.runLater(() -> textbox.getChildren().add(hBox));

    }

    /**
     * When called, disables text field and buttons, not allowing for messages to be sent into void.
     * Also notifies the user by showing a special message.
     */
    public void NotifyOfDisconnect()
    {
        message.Refresh("DISCONNECTED", "SERVER");
        Platform.runLater(() -> {
            disconnect_button.setDisable(true);
            textfield.setDisable(true);
            send_button.setDisable(true);
        });
        NewMessageArrived(message);
    }

    /**
     * Calls {@link CommClient#DisconnectAndQuit()} method.
     */
    private void Disconnect()
    {
        ClientApplication.getInstance().client.DisconnectAndQuit();
    }

    /**
     * Uses Platform.runLater() to change the text on the {@link #user_label}.
     *
     * @param username Username. New text to set user label to.
     */
    private void setUser_label(String username)
    {
        Platform.runLater(()->user_label.setText(username));
    }
}
