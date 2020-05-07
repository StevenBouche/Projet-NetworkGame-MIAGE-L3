package network.tcp;

import network.message.obj.Enigme;
import network.message.obj.ListPlayerGame;
import network.protocol.ProtocolEvents;

public class ProtocolEventsTCP<T> extends ProtocolEvents<T> {

    /*
    LOBBY PROTOCOL
     */
    public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");
    public static ProtocolEventsTCP<ListPlayerGame> NOTIFYLOBBYPLAYER = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYLOBBYPLAYER");
    public static ProtocolEventsTCP<ListPlayerGame> NOTIFYPLAYERREADY = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYPLAYERREADY");
    public static ProtocolEventsTCP<String> NOTIFYGAMEREADY = new ProtocolEventsTCP<String>("NOTIFYGAMEREADY");

    /*
     *  PROTOCOL GAME
     */
    public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
    public static ProtocolEventsTCP<String> NOTIFYCURRENTPLAYERROUND = new ProtocolEventsTCP<String>("NOTIFYCURRENTPLAYERROUND");

    public static ProtocolEventsTCP<String> PROPOSALRESPONSE = new ProtocolEventsTCP<String>("PROPOSALRESPONSE");
    public static ProtocolEventsTCP<String> SENDLETTERCLIENT = new ProtocolEventsTCP<String>("SENDLETTERCLIENT");








    private ProtocolEventsTCP(String name){
        super(name);
    }

}
