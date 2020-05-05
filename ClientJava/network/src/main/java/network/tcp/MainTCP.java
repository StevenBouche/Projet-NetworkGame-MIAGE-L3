package network.tcp;

import network.client.ClientTCP;

public class MainTCP {

    public static void main(String[] args) throws InterruptedException {
        ClientTCP client = new ClientTCP("127.0.0.1",10001,"test");

        Thread t = new Thread(client);
        t.start();
        t.join();
    }
}
