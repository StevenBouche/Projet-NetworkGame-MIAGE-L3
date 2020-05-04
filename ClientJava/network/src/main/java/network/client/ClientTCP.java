package network.client;

import network.message.PacketMessage;
import network.share.DataListener;
import network.share.DataListenerTCP;
import network.share.IPEndPoint;
import network.tcp.INotifyState;
import network.tcp.NetworkManagerTCP;
import network.tcp.ProtocolEventsTCP;

import java.io.IOException;

public class ClientTCP implements Runnable, INotifyState {

    NetworkManagerTCP managerTCP = new NetworkManagerTCP(this);

    public ClientTCP(){
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.CONNECTION, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                System.out.println(var);

                try {
                    Thread.sleep(1000);
                    msgLoop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void run() {

        managerTCP.startListening();

    }


    @Override
    public void onConnect() {
        msgLoop();
    }

    private void msgLoop(){
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CONNECTION.eventName;
        msg.data = "I'm connected";
        managerTCP.sendMsg(msg);
    }

    @Override
    public void onDisconnect() {
    }
}
