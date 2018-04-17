package client.fxml;

import client.ClientApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

public class FXMLMessengerController
{

    @FXML
    public Button disconnect_button;
    public VBox textbox;
    public TextField textfield;
    public Button send_button;
    public Label user_label;


    private Message message = new Message("null", "empty");

    public FXMLMessengerController()
    {
        ClientApplication.getInstance().fxmlMessengerController = this;
        setUser_label(ClientApplication.getInstance().username);
    }




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

    @FXML
    public void disconnect(ActionEvent actionEvent)
    {
        Disconnect();
    }

    private void SendMessage(Message message)
    {
        ClientApplication.getInstance().client.SendMessage(message);
    }

    public void NewMessageArrived(Message message)
    {
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

    private void Disconnect()
    {
        ClientApplication.getInstance().client.DisconnectAndQuit();
    }

    public void setUser_label(String username)
    {
        Platform.runLater(()->user_label.setText(username));
    }
}
