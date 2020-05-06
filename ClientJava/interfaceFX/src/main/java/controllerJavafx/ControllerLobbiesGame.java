package controllerJavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import network.client.ClientTCP;
import network.client.INotifyPlayersLobby;
import network.main.IMain;
import network.message.obj.ListPlayerGame;
import network.message.obj.PlayerGame;
import network.message.obj.ServerGame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerLobbiesGame implements Initializable, INotifyPlayersLobby {

    @FXML
    public Pane mainPane;
    @FXML
    public Label title;

    Map<Integer,SubScene> mapSubScene;

    // Informations concernant le serveur ou l'on vient de se connecter
    ServerGame serverInfo;
    // Client tcp pour joindre le serveur
    ClientTCP client;
    //List contenant les joueurs dans le lobby
    ListPlayerGame listPlayer;
    //Un controller par joueur dans le lobby
    Map<Integer, ControllerItemPlayer> mapControllerPlayer;
    //to switch other scene
    IMain main;

    //MY DATA
    String name;
    String id;

    public ControllerLobbiesGame(IMain main, ServerGame serverInfo, String name){
        mapSubScene = new HashMap<>();
        this.serverInfo = serverInfo;
        mapControllerPlayer = new HashMap<>();
        listPlayer = new ListPlayerGame();
        this.name = name;
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
        title.setText("Room "+serverInfo.addr+":"+serverInfo.port);
        client = new ClientTCP(serverInfo.addr,serverInfo.port,this.name);
        client.setNotifierLobby(this);
        Thread t = new Thread(client);
        t.start();
    }

    private void createItemPlayer(int i, String name, String id){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPlayerWaitingStartGame.fxml"));
        ControllerItemPlayer ctr = new ControllerItemPlayer(id,name,main, id.equals(this.id));
        fxmlLoader.setController(ctr);
        try {
            Parent p = fxmlLoader.load();
            mapControllerPlayer.put(i,ctr);
            SubScene sub = new SubScene(p,ctr.vbox.getPrefWidth(),ctr.vbox.getPrefHeight());
            sub.setLayoutY(128);
            if(i == 1){
                mapSubScene.put(1,sub);
                sub.setLayoutX(54);
                sub.setRoot(p);
                mainPane.getChildren().add(sub);
            }else if (i==2){
                mapSubScene.put(2,sub);
                sub.setLayoutX(232);
                sub.setRoot(p);
                mainPane.getChildren().add(sub);
            }else if(i == 3){
                mapSubScene.put(3,sub);
                sub.setLayoutX(422);
                sub.setRoot(p);
                mainPane.getChildren().add(sub);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }

    @Override
    public void notifyReceiveMyId(String id) {
        this.id = id;
    }

    @Override
    public void notifyReceiveListPlayer(ListPlayerGame l) {
        this.listPlayer.listPlayers = l.listPlayers;
        Platform.runLater(() -> {
            int cpt = 1;
            for(PlayerGame i : this.listPlayer.listPlayers){
                createItemPlayer(cpt,i.name,i.id);
                cpt++;
            }

        });

    }
}
