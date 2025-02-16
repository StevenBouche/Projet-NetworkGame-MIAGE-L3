package network.udp;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.share.EventNetworkManager;
import network.share.IPEndPoint;
import network.message.PacketMessage;
import network.share.ListenerState;

import java.io.IOException;
import java.net.*;
import java.util.Base64;
import java.util.Random;

public class NetworkManagerUDP {

    int portServer = 11000;
    int portClient;
    InetAddress addr;
    DatagramSocket client;
    boolean running;
    public EventNetworkManager evtManager;
    ObjectMapper mapper = new ObjectMapper();
    ListenerState listener;
    String defaultServer;

    public NetworkManagerUDP(String ip, ListenerState listener) throws SocketException, UnknownHostException {
        this.listener = listener;
        this.defaultServer = ip;
        addr = InetAddress.getByName(defaultServer);
        portClient = new Random().nextInt(11100 - 11001 + 1) + 11001;
        client = new DatagramSocket();
        evtManager = new EventNetworkManager();
    }

    public void startListening() {

        running = true;
        listener.onRunning("Running on "+addr.toString()+":"+portClient);

        while(running){

                byte[] buffer2 = new byte[8196];
                DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, addr, portClient);
            try {
                client.receive(packet2);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }

            if(running) {
                String str = new String(packet2.getData()).trim();
                print("J'ai reçu une reponse du serveur : " + str+"\n" );
                IPEndPoint ipep = new IPEndPoint();
                ipep.addr = packet2.getAddress().toString();
                ipep.port = packet2.getPort();
                evtManager.OnReceivedData(str, ipep);
            }

        }

        if(!client.isClosed()) client.close();

       // listener.onShutdown();
    }

    public void stopListening() {
        running = false;
        client.close();
    }

    private void sendMessage(String str) throws IOException {
        byte[] buffer = str.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, portServer);
        //On lui affecte les données à envoyer
        packet.setData(buffer);
        //On envoie au serveur
        client.send(packet);
    }

    public void sendMessage(PacketMessage<?> proto) throws IOException {
        String str = mapper.writeValueAsString(proto);
        sendMessage(str);
    }

    public void print(String str){
        System.out.print(str);
    }
    public void println(String str){
        System.err.println(str);
    }

    public String ipAddrDefault() {
        return defaultServer;
    }
}
