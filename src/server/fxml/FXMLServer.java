package server.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import server.ConnectedClient;
import server.ServerApplication;
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
                ServerApplication.getInstance().serverController.getConnectedUsers())
        {
            TitledPane userPane = new TitledPane();
            HBox hBox = new HBox();
            Button kickButton = new Button("Kick");

            userPane.setText(connectedClient.getUser().get_username());
            hBox.prefHeight(60);
            hBox.setAlignment(Pos.CENTER_LEFT);
            kickButton.setOnAction(event -> {
                    ServerApplication.getInstance().serverController.messageListenerAndSender.sendMessageThatUserIsKicked(connectedClient.getUser().get_username());
                    kickButton.setDisable(true);
            });

            hBox.getChildren().add(kickButton);
            userPane.setContent(hBox);
            userList.add(userPane);
            /*More info...*/
        }
        connected_list.getPanes().setAll(userList);
    }
}
