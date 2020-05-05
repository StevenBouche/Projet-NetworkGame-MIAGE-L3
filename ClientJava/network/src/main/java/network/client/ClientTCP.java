package network.client;

import network.message.PacketMessage;
import network.protocol.Protocol;
import network.share.DataListenerTCP;
import network.tcp.INotifyState;
import network.tcp.NetworkManagerTCP;
import network.tcp.ProtocolEventsTCP;

public class ClientTCP implements Runnable, INotifyState {

    NetworkManagerTCP managerTCP ;
    String name;

    public ClientTCP(String addr, int port, String name) {
        this.name = name;
        managerTCP = new NetworkManagerTCP(this,addr,port);
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
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.IDENTITY, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                System.out.println("IDENTITY SET "+var);
            }
        });
    }

    @Override
    public void run() {
        managerTCP.startListening();
    }


    @Override
    public void onConnect() {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CONNECTION.eventName;
        msg.data = this.name;
        managerTCP.sendMsg(msg);
      //  msgLoop();
    }

    private void msgLoop() {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CONNECTION.eventName;
        msg.data = "I'm connected";
        managerTCP.sendMsg(msg);
    }

    @Override
    public void onDisconnect() {
    }
}
