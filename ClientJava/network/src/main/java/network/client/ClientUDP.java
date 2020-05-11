package network.client;

import network.message.Packet;
import network.message.PacketMessage;
import network.share.DataListener;
import network.share.ListenerState;
import network.udp.NetworkManagerUDP;
import network.udp.ProtocolEventsUDP;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientUDP implements Runnable {

    NetworkManagerUDP manager;

    public ClientUDP(String defaultS, ListenerState listener) throws SocketException, UnknownHostException {
        manager = new NetworkManagerUDP(defaultS,listener);
    }

    public <T> void OnEvent(Class<T> co, ProtocolEventsUDP<T> proto, DataListener<T> listener){
        manager.evtManager.OnEvent(co,proto,listener);
    }

    public void send(PacketMessage<?> obj) throws IOException {
        manager.sendMessage(obj);
    }

    @Override
    public void run() {
        manager.startListening();
    }

    public void stop() {
        manager.stopListening();
    }

    public String getIpAddressDefault() {
        return manager.ipAddrDefault();
    }

    public void createNewGame() {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsUDP.NEWGAME.eventName;
        msg.data = "create";
        try {
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeGame(String name) {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsUDP.REMOVEGAME.eventName;
        msg.data = name;
        try {
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
