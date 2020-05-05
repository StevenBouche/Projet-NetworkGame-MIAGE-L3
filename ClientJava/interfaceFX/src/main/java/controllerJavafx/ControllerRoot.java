package controllerJavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.MenuBar;
import network.main.IMain;
import network.message.obj.ServerGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRoot implements Initializable, INotifyEventUI {

    ControllerLobbies manager;
    ControllerLobbiesGame managerLobbiesGame;

    FXMLLoader fxmlLoaderLobbies;
    FXMLLoader fxmlLoaderLobbiesGame;
    Parent rootLobbies;
    Parent rootLobbiesGame;

    IMain main;

    public ControllerRoot(IMain iMain) {
        main = iMain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLobbiesScene();
    }

    private void initLobbiesScene(){
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
        main.switchScene(p);;
    }

    private void setSizeOfParentSubScene(Parent p){
        p.maxWidth(main.getWidth());
        p.maxHeight(main.getHeight());
    }

    @Override
    public void playerWantJoinGame(ServerGame srvGame, String name) {
        System.out.println("Player has select a game server");
        fxmlLoaderLobbiesGame = new FXMLLoader(getClass().getClassLoader().getResource("lobbiesGame.fxml"));
        managerLobbiesGame = new ControllerLobbiesGame(main,srvGame,name);
        fxmlLoaderLobbiesGame.setController(managerLobbiesGame);
        try {
            rootLobbiesGame = fxmlLoaderLobbiesGame.load();
            setSizeOfParentSubScene(rootLobbiesGame);
            setScene(rootLobbiesGame);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void backToMainLobbies() {
        initLobbiesScene();
    }

    public void stop() {
        if(manager != null) manager.stop();
        if(managerLobbiesGame != null) managerLobbiesGame.stop();
    }
}
