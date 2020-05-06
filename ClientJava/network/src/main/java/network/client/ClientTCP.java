package network.client;

import network.message.PacketMessage;
import network.message.obj.ListPlayerGame;
import network.message.obj.PlayerGame;
import network.protocol.Protocol;
import network.share.DataListenerTCP;
import network.tcp.INotifyState;
import network.tcp.NetworkManagerTCP;
import network.tcp.ProtocolEventsTCP;

import java.util.List;

public class ClientTCP implements Runnable, INotifyState {

    NetworkManagerTCP managerTCP ;
    String name;
    INotifyPlayersLobby notifier;
    INotifyPlayersGame notifierGame;

    public ClientTCP(String addr, int port, String name) {
        this.name = name;
        managerTCP = new NetworkManagerTCP(this,addr,port);
        initEventLobbyTCP();
        initEventGameTCP();
    }

    private void initEventGameTCP() {

    }

    private void initEventLobbyTCP() {
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.IDENTITY, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                System.out.println("IDENTITY SET "+var);
                if(notifier != null) notifier.notifyReceiveMyId(var);
            }
        });
        managerTCP.eventManager.OnEvent(ListPlayerGame.class, ProtocolEventsTCP.NOTIFYLOBBYPLAYER, new DataListenerTCP<ListPlayerGame>() {
            @Override
            public void onData(ListPlayerGame var) {
                if(notifier != null) notifier.notifyReceiveListPlayer(var);
            }
        });
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.NOTIFYGAMEREADY, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                if(notifier != null) notifier.notifyGameStart();
            }
        });
    }

    public void setNotifierLobby(INotifyPlayersLobby not){
        this.notifier = not;
    }
    public void removeNotifierLobby(){ this.notifier = null; }

    public void setNotifierGame(INotifyPlayersGame not){
        this.notifierGame = not;
    }
    public void removeNotifierGame(){ this.notifier = null; }

    @Override
    public void run() {
        managerTCP.startListening();
    }

    @Override
    public void onConnect() {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.IDENTITY.eventName;
        msg.data = this.name;
        managerTCP.sendMsg(msg);
    }

    public void sendMsg(PacketMessage<?> msg){
        managerTCP.sendMsg(msg);
    }

    @Override
    public void onDisconnect() {
        if(notifier != null) notifier.notifyDisconnect();
    }

    public void stop() {
        managerTCP.stop();
    }
}
