package client.fxml;

import client.ClientApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structs.Message;

public class FXMLMessengerController
{

    @FXML
    public Button disconnect_button;


    public FXMLMessengerController()
    {
        ClientApplication.getInstance().fxmlMessengerController = this;
    }
    private Message message = new Message("null", "empty");


    @FXML
    public VBox textbox;
    @FXML
    public TextField textfield;

    @FXML
    public void send_message_button(ActionEvent actionEvent)
    {
        message.Refresh(textfield.getText(), ClientApplication.getInstance().username);
        textfield.clear();

        SendMessage(message);
    }

    @FXML
    public void send_message_enter(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            send_message_button(new ActionEvent());
        }
    }

    private void SendMessage(Message message)
    {
        ClientApplication.getInstance().client.SendMessage(message);
    }

    public void NewMessageArrived(Message message)
    {
        //Using stringbuilder to make addition to text
        StringBuilder usernameNew = new StringBuilder();
        usernameNew.append('<');
        usernameNew.append(message.Username);
        usernameNew.append('>');

        HBox hBox = new HBox();
        Text username = new Text(usernameNew.toString());
        Text content = new Text(message.Content);

        hBox.setSpacing(4);
        username.setFill(Color.RED);
        content.setWrappingWidth(360.00);

        hBox.getChildren().addAll(username, content);

        Platform.runLater(() -> textbox.getChildren().add(hBox));

    }

    @FXML
    public void disconnect(ActionEvent actionEvent)
    {
        message.Refresh("DISCONNECTED", "SERVER");
        disconnect_button.setDisable(true);
        NewMessageArrived(message);
        Disconnect();
    }

    public void Disconnect()
    {
        ClientApplication.getInstance().client.Disconnect();
    }
}
