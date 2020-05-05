package network.tcp;

import network.protocol.ProtocolEvents;

public class ProtocolEventsTCP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsTCP<String> CONNECTION = new ProtocolEventsTCP<String>("CONNECTION");
    public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");

    private ProtocolEventsTCP(String name){
        super(name);
    }
}
