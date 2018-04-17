package server.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import server.ConnectedClient;
import server.ServerController;
import structs.UserStorage;

public class FXMLServer
{

    @FXML
    public Accordion connected_list;
    public Button refreshButton;

    @FXML
    public void refresh_connected_list(ActionEvent actionEvent)
    {
        ObservableList<TitledPane> userList = FXCollections.observableArrayList();

        for (ConnectedClient connectedClient:
                ServerController.getInstance().getConnectedUsers())
        {
            TitledPane userPane = new TitledPane();
            Button kickButton = new Button("Kick");

            userPane.setText(connectedClient.getUser().get_username());
            kickButton.setOnAction(e -> ServerController.getInstance().messageListenerAndSender.sendMessageThatUserIsKicked(connectedClient.getUser().get_username()));

            userPane.setContent(kickButton);
            userList.add(userPane);
            /*More info...*/
        }
        connected_list.getPanes().setAll(userList);
    }
}
