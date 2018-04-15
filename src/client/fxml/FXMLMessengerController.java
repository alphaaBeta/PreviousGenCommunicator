package client.fxml;

import client.ClientApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import structs.Message;
import structs.User;

public class FXMLMessengerController
{

    public FXMLMessengerController()
    {
        ClientApplication.getInstance().fxmlMessengerController = this;
    }
    private Message message = new Message("null", "empty");


    @FXML
    public VBox textbox;
    public TextField textfield;

    @FXML
    public void send_message(ActionEvent actionEvent)
    {
        message.Refresh(textfield.getText(), ClientApplication.getInstance().username);
        textfield.clear();

        ClientApplication.getInstance().client.new_message_to_send(message);

    }

    @FXML
    public void send_message_enter(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            send_message(new ActionEvent());
        }
    }

    public void new_message_arrived(Message message)
    {
        HBox hBox = new HBox();
        Text username = new Text(message.Username);
        Text content = new Text(message.Content);

        username.setFill(Color.RED);
        content.setWrappingWidth(360.00);


        hBox.getChildren().addAll(username, content);

        Platform.runLater(() -> textbox.getChildren().add(hBox));

    }
}
