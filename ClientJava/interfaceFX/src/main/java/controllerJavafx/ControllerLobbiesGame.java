package controllerJavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import network.client.ClientTCP;
import network.main.IMain;
import network.message.obj.ServerGame;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerLobbiesGame implements Initializable {

    @FXML
    public SubScene scenePlayer1;
    @FXML
    public SubScene scenePlayer2;
    @FXML
    public SubScene scenePlayer3;
    @FXML
    public Label title;

    ServerGame serverInfo;

    ClientTCP tcp;

    Map<Integer, String> mapPlayer;
    Map<Integer, ControllerItemPlayer> mapControllerPlayer;

    IMain main;

    String name;

    public ControllerLobbiesGame(IMain main, ServerGame serverInfo, String name){
        this.serverInfo = serverInfo;
        mapControllerPlayer = new HashMap<>();
        mapPlayer = new HashMap<>();
        this.name = name;
        mapPlayer.put(1,name);
       // mapPlayer.put(2,"Jose");
       // mapPlayer.put(3,"Mopolo");
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
            title.setText("Room "+serverInfo.addr+":"+serverInfo.port);
            for(Integer i : mapPlayer.keySet()){
                createItemPlayer(i,mapPlayer.get(i));
            }
            ClientTCP client = new ClientTCP("127.0.0.1",10001,this.name); // todo change brut
            Thread t = new Thread(client);
            t.start();
    }

    private void createItemPlayer(int i, String name){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPlayerWaitingStartGame.fxml"));
        ControllerItemPlayer ctr = new ControllerItemPlayer(name,main);
        fxmlLoader.setController(ctr);
        try {
            Parent p = fxmlLoader.load();
            if(i == 1){
                scenePlayer1.setRoot(p);
            }else if (i==2){
                scenePlayer2.setRoot(p);
            }else if(i == 3){
                scenePlayer3.setRoot(p);
            }
            mapControllerPlayer.put(i,ctr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
