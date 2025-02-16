package controllerJavafx;

import coco.controller.ControllerGameUI;
import coco.controller.PlayerData;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import network.client.ClientTCP;
import network.main.IMain;
import network.message.obj.ServerGame;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRoot implements Initializable, INotifyEventUI {

    ControllerLobbies manager;
    FXMLLoader fxmlLoaderLobbies;
    Parent rootLobbies;

    ControllerLobbiesGame managerLobbiesGame;
    FXMLLoader fxmlLoaderLobbiesGame;
    Parent rootLobbiesGame;

    ControllerGameUI managerGameUI;
    Parent rootGameUI;

    IMain main;

    public ControllerRoot(IMain iMain) {
        main = iMain;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLobbiesScene();
    }

    private void initLobbiesScene(){
        manager = new ControllerLobbies(this);
        fxmlLoaderLobbies = new FXMLLoader(getClass().getClassLoader().getResource("lobbie.fxml"));
        fxmlLoaderLobbies.setController(manager);
        try {
            rootLobbies = fxmlLoaderLobbies.load();
            setSizeOfParentSubScene(rootLobbies);
            setScene(rootLobbies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startSceneLobbyGame(ServerGame srvGame, String name) {
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

    @Override
    public void startSceneGame() {

    }

    public void setScene(Parent p ){
        main.switchScene(p);;
    }

    private void setSizeOfParentSubScene(Parent p){
        p.maxWidth(main.getWidth());
        p.maxHeight(main.getHeight());
    }

    public void backToMainLobbies() {
        initLobbiesScene();
    }

    public void stop() {
        if(managerGameUI != null) managerGameUI.stop();
        if(managerLobbiesGame != null) managerLobbiesGame.stop();
        if(manager != null) manager.stop();
    }

    public void startSceneGame(ClientTCP client, Thread clientThread, List<PlayerData> data, String myId) {
        //TODO SCENE COCO
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gameUI.fxml"));
        managerGameUI = new ControllerGameUI(client,clientThread,data,myId,main);
        fxmlLoader.setController(managerGameUI);
        try {
            rootGameUI = fxmlLoader.load();
            setScene(rootGameUI);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
