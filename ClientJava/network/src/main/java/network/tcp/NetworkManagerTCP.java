package network.tcp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.message.PacketMessage;
import network.share.EventNetworkManager;
import java.net.*;
import java.io.*;

public class NetworkManagerTCP {

    Socket s;
    BufferedInputStream input;
    BufferedOutputStream output;
    public EventNetworkManager eventManager;
    INotifyState observer;
    ObjectMapper mapper = new ObjectMapper();

    String hostname;
    int port;

    boolean running;

    public NetworkManagerTCP(INotifyState client, String addr, int port){
        this.eventManager = new EventNetworkManager();
        observer = client;
        this.hostname = addr;
        this.port = port;
    }

    public void startListening() {

        Socket socket = null;

        try {
            socket = new Socket(hostname, port);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        s = socket;

        try {
            input = new BufferedInputStream(s.getInputStream());
            output = new BufferedOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        running = true;
        observer.onConnect();

        try {
            listening();
        } catch (IOException ex) {
            ex.printStackTrace();
            running = false;
            System.out.println("DECONNECT OF SERVER GAME");
        }

        System.out.println("STOP CLIENT TCP");

        try {
            output.close();
            input.close();
            if(!s.isClosed()) s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        observer.onDisconnect();
    }

    private void listening() throws IOException {
        while (running) {
            int character = 0;
            StringBuilder data = new StringBuilder();
            byte[] tab = new byte[4096];
            while ((character = input.read(tab)) != -1) {
                data.append(new String(tab, 0, character));
                eventManager.OnReceivedData(data.toString());
                data = new StringBuilder();
            }
        }
    }

    public void send(String msg) {
        try {
            this.output.write(msg.getBytes());
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(PacketMessage<?> msg){
        try {
            String str = mapper.writeValueAsString(msg);
            send(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
