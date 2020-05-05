package network.client;

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

    public ClientUDP(ListenerState listener) throws SocketException, UnknownHostException {
        manager = new NetworkManagerUDP(listener);
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
}
