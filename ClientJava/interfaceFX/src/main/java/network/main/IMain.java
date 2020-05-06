package network.main;

import javafx.scene.Parent;
import network.client.ClientTCP;

public interface IMain {

    void backToMainLobbies();
    void switchScene(Parent p);
    double getWidth();
    double getHeight();
    void startSceneGame(ClientTCP client, Thread clientThread);
}
