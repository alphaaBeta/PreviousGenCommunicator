package client.fxml;

import client.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.concurrent.Semaphore;

public class FXMLLoginController
{

    public FXMLLoginController()
    {
        ClientApplication.getInstance().fxmlLoginController = this;
    }
    @FXML public TextField login_field;
    @FXML public PasswordField passwd_field;

    @FXML
    public void attempt_login(ActionEvent actionEvent)
    {
        ClientApplication.getInstance().username = login_field.getText();

        ClientApplication.getInstance().startClient();

    }
}
