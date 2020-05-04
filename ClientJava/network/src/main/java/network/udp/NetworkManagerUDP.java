package network.udp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.message.obj.Choice;
import network.share.EventNetworkManager;
import network.share.IPEndPoint;
import network.message.PacketMessage;
import network.share.ListenerState;

import java.io.IOException;
import java.net.*;
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

    public NetworkManagerUDP(ListenerState listener) throws SocketException, UnknownHostException {
        this.listener = listener;
        addr = InetAddress.getByName("127.0.0.1");
        portClient = new Random().nextInt(11100 - 11001 + 1) + 11001;
        client = new DatagramSocket();
        evtManager = new EventNetworkManager();
    }

    public void startListening() throws IOException {

        running = true;
        int cpt = 0;

        listener.onRunning("Running on "+addr.toString()+":"+portClient);

        while(running && !Thread.currentThread().isInterrupted()){

         /*   packet.data.message = "hello "+cpt;
            String str = mapper.writeValueAsString(packet);
            sendMessage(str);*/

            //Et on récupère la réponse du serveur
            byte[] buffer2 = new byte[8196];
            DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, addr, portClient);
            client.receive(packet2);
            println(" J'ai reçu une réponse du serveur : "+new String(packet2.getData()));
            IPEndPoint ipep = new IPEndPoint();
            ipep.addr = packet2.getAddress().toString();
            ipep.port = packet2.getPort();
            evtManager.OnReceivedData(new String(packet2.getData()),ipep);
            cpt++;
        }

        listener.onShutdown();

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


}
