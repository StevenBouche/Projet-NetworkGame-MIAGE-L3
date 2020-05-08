package controllerJavafx;
import javafx.scene.media.MediaPlayer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import coco.controller.PlayerData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import network.client.ClientTCP;
import network.client.INotifyPlayersLobby;
import network.main.IMain;
import network.message.PacketMessage;
import network.message.obj.ListPlayerGame;
import network.message.obj.PlayerGame;
import network.message.obj.ServerGame;
import network.tcp.ProtocolEventsTCP;
import com.jfoenix.controls.JFXButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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
    Thread clientThread;
    //List contenant les joueurs dans le lobby
    ListPlayerGame listPlayer;
    //Un controller par joueur dans le lobby
    Map<Integer, ControllerItemPlayer> mapControllerPlayer;
    //to switch other scene
    IMain main;

    //MY DATA
    String name;
    String id;
    private boolean alreadyStop;

    public ControllerLobbiesGame(IMain main, ServerGame serverInfo, String name){
        mapSubScene = new HashMap<>();
        this.serverInfo = serverInfo;
        mapControllerPlayer = new HashMap<>();
        listPlayer = new ListPlayerGame();
        this.name = name;
        this.main = main;
        alreadyStop=false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
        title.setText("Room "+serverInfo.addr+":"+serverInfo.port);
        client = new ClientTCP(serverInfo.addr,serverInfo.port,this.name);
        client.setNotifierLobby(this);
        clientThread = new Thread(client);
        clientThread.setName("Thread Client TCP");
        clientThread.start();
        loadBackGround();
        loadMp3();
    }

    private void loadMp3() {
        Timer t = new Timer();
        t.schedule( new TimerTask() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = new MediaPlayer(LoaderRessource.getInstance().wheelFortuneMp3);
                mediaPlayer.play();
                t.cancel();
            }
        }, 0, 2000);

    }

    private void loadBackGround() {

        Image image = LoaderRessource.getInstance().wheelBackground;

        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // create Background
        Background background = new Background(backgroundimage);

        // set background
        mainPane.setBackground(background);

    }

    private void createItemPlayer(int i, String name, String id, boolean ready, int nbPlayerLobby){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPlayerWaitingStartGame.fxml"));
        ControllerItemPlayer ctr = new ControllerItemPlayer(id,name,this, id.equals(this.id), ready,nbPlayerLobby);
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
        alreadyStop = true;
        client.stop();
    }

    @Override
    public void notifyReceiveMyId(String id) {
        this.id = id;
    }

    @Override
    public void notifyReceiveListPlayer(ListPlayerGame l) {
        this.listPlayer.listPlayers = l.listPlayers;
        Platform.runLater(() -> {
            mainPane.getChildren().removeAll(mapSubScene.values());
            mapSubScene.clear();
            int cpt = 1;
            for(PlayerGame i : this.listPlayer.listPlayers){
                createItemPlayer(cpt,i.name,i.id,i.isReady,l.listPlayers.size());
                cpt++;
            }
        });
    }

    @Override
    public void notifyDisconnect() {
        onCancelAction();
    }

    @Override
    public void notifyGameStart() {
        client.removeNotifierLobby();
        List<PlayerData> listData = buildPlayerDataForGame();
        Platform.runLater(() -> {
           main.startSceneGame(client,clientThread,listData,id);
        });
    }

    private List<PlayerData> buildPlayerDataForGame(){
        List<PlayerData> listData = new ArrayList<>();
        for(PlayerGame p : this.listPlayer.listPlayers){
            PlayerData p2 = new PlayerData();
            p2.id = p.id;
            p2.namePlayer = p.name;
            p2.cashRound = 0;
            p2.cashTotal = 0;
            listData.add(p2);
        }
        return listData;
    }

    public void onCancelAction() {
        if(!alreadyStop){
            alreadyStop = true;
            stop();
            main.backToMainLobbies();
        }
    }

    public void UpdateReadyPlayer(boolean b) {
        PacketMessage<Boolean> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.NOTIFYPLAYERREADY.eventName;
        msg.data =b;
        client.sendMsg(msg);
    }
}
