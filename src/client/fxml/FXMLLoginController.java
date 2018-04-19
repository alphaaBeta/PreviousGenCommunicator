package client.fxml;

import client.ClientApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Class containing methods to control the Login scene of the app.
 */
public class FXMLLoginController
{

    /**
     * Login field as text field in application.
     */
    @FXML
    public TextField login_field;
    /**
     * Password field in application.
     */
    @FXML
    public PasswordField passwd_field;

    /**
     * Class constructor.
     * Assigns this object to ClientApplication reference of this type.
     *
     * @see ClientApplication#fxmlLoginController
     */
    public FXMLLoginController()
    {
        ClientApplication.getInstance().fxmlLoginController = this;
    }

    /**
     * Takes (currently) username from {@link #login_field} and starts the client given this information.
     *
     * @see ClientApplication#username
     * @see ClientApplication#startClient()
     */
    @FXML
    public void attempt_login()
    {
        ClientApplication.getInstance().username = login_field.getText();

        ClientApplication.getInstance().startClient();

    }
}
