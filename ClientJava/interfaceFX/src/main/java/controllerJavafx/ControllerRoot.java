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

    FXMLLoader fxmlLoaderLobbies;
    FXMLLoader fxmlLoaderLobbiesGame;
    Parent rootLobbies;
    Parent rootLobbiesGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxmlLoaderLobbies = new FXMLLoader(getClass().getClassLoader().getResource("lobbie.fxml"));

        manager = new ControllerLobbies(this);
        fxmlLoaderLobbies.setController(manager);

        try {
            rootLobbies = fxmlLoaderLobbies.load();
            setSizeOfParentSubScene(rootLobbies);
            setScene(rootLobbies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Parent p ){
        subscene.setRoot(p);
    }

    private void setSizeOfParentSubScene(Parent p){
        p.maxWidth(subscene.getWidth());
        p.maxHeight(subscene.getHeight());
    }

    @Override
    public void playerWantJoinGame(ServerGame srvGame) {
        System.out.println("Player has select a game server");
        fxmlLoaderLobbiesGame = new FXMLLoader(getClass().getClassLoader().getResource("lobbiesGame.fxml"));
        managerLobbiesGame = new ControllerLobbiesGame();
        fxmlLoaderLobbiesGame.setController(managerLobbiesGame);
        try {
            rootLobbiesGame = fxmlLoaderLobbiesGame.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSizeOfParentSubScene(rootLobbiesGame);
        subscene.setRoot(rootLobbiesGame);
    }

}
