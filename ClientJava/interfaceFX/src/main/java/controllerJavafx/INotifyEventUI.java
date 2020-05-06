package controllerJavafx;

import network.message.obj.ServerGame;

import java.io.IOException;

public interface INotifyEventUI {

    void startSceneLobbyGame(ServerGame srvGame, String name) throws IOException;
    void startSceneGame();

}
