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

    public ClientTCP(String addr, int port, String name) {
        this.name = name;
        managerTCP = new NetworkManagerTCP(this,addr,port);
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
    }

    public void setNotifierLobby(INotifyPlayersLobby not){
        this.notifier = not;
    }

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

    @Override
    public void onDisconnect() {
    }

    public void stop() {
        managerTCP.stop();
    }
}
