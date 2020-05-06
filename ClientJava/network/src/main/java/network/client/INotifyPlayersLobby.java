package network.client;

import network.message.obj.ListPlayerGame;

public interface INotifyPlayersLobby {
    void notifyReceiveMyId(String id);
    void notifyReceiveListPlayer(ListPlayerGame l);
    void notifyDisconnect();
    void notifyGameStart();
}
