package network.main;

import coco.controller.PlayerData;
import javafx.scene.Parent;
import network.client.ClientTCP;

import java.util.List;

public interface IMain {

    void backToMainLobbies();
    void switchScene(Parent p);
    double getWidth();
    double getHeight();
    void startSceneGame(ClientTCP client, Thread clientThread, List<PlayerData> listData);
}
