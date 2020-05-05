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
            s = socket;

            input = new BufferedInputStream(s.getInputStream());
            output = new BufferedOutputStream(s.getOutputStream());

            observer.onConnect();
            while (s.isConnected()) {

                int character = 0;
                StringBuilder data = new StringBuilder();
                byte[] tab = new byte[128];

                while ((character = input.read(tab)) != -1) {
                    data.append(new String(tab, 0, character));
                    eventManager.OnReceivedData(data.toString());
                    data = new StringBuilder();
                }
            }
            output.close();
            input.close();
            s.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        observer.onDisconnect();
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
}
