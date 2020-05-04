package network.tcp;

import network.message.obj.Choice;
import network.message.obj.DataServerGame;
import network.protocol.ProtocolEvents;
import network.udp.ProtocolEventsUDP;

public class ProtocolEventsTCP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsTCP<String> CONNECTION = new ProtocolEventsTCP<String>("CONNECTION");

    private ProtocolEventsTCP(String name){
        super(name);
    }
}
