package controllerJavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import network.message.obj.ServerGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRoot implements Initializable, INotifyEventUI {

    @FXML
    public SubScene subscene;
    @FXML
    public MenuBar menu;

    ControllerLobbies manager;
    ControllerLobbiesGame managerLobbiesGame;

    Parent rootLobbies;
    Parent rootLobbiesGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("lobbie.fxml"));
        manager = new ControllerLobbies(this);
        fxmlLoader.setController(manager);
        try {
            rootLobbies = fxmlLoader.load();
            rootLobbies.maxWidth(subscene.getWidth());
            rootLobbies.maxHeight(subscene.getHeight());
            subscene.setRoot(rootLobbies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playerWantJoinGame(ServerGame srvGame) {

    }
}
