package network.server;

import network.share.Choice;
import network.share.DataListener;
import network.share.IPEndPoint;
import network.udp.NetworkManagerUDP;
import network.udp.ProtocolEventsUDP;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerUDP implements Runnable {

    NetworkManagerUDP manager;

    public ServerUDP() throws SocketException, UnknownHostException {
        manager = new NetworkManagerUDP();
    }

    public <T> void OnEvent(Class<T> co, ProtocolEventsUDP<T> proto, DataListener<T> listener){
        manager.evtManager.OnEvent(co,proto,listener);
    }

    @Override
    public void run() {
        try {
            manager.startListening();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
