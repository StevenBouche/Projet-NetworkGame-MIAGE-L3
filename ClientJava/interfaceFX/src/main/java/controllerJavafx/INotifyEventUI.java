package controllerJavafx;

import network.message.obj.ServerGame;

import java.io.IOException;

public interface INotifyEventUI {

    void playerWantJoinGame(ServerGame srvGame) throws IOException;


}
