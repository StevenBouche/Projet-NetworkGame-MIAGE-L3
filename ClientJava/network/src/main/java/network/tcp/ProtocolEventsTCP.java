package network.tcp;

import network.protocol.ProtocolEvents;

public class ProtocolEventsTCP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsTCP<String> CONNECTION = new ProtocolEventsTCP<String>("CONNECTION");

    private ProtocolEventsTCP(String name){
        super(name);
    }
}
