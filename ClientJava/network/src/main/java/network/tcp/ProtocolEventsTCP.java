package network.tcp;

import network.message.obj.ListPlayerGame;
import network.protocol.ProtocolEvents;

public class ProtocolEventsTCP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");
    public static ProtocolEventsTCP<ListPlayerGame> NOTIFYLOBBYPLAYER = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYLOBBYPLAYER");
    public static ProtocolEventsTCP<ListPlayerGame> NOTIFYPLAYERREADY = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYPLAYERREADY");

    private ProtocolEventsTCP(String name){
        super(name);
    }
}
