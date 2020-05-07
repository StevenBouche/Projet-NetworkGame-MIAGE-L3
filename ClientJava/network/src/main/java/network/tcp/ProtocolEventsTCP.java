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
     *  PROTOCOL GAME FOR SEND
     */

    public static ProtocolEventsTCP<String> PROPOSALRESPONSE = new ProtocolEventsTCP<String>("PROPOSALRESPONSE");
    public static ProtocolEventsTCP<Integer> SENDCASEVALUE = new ProtocolEventsTCP<Integer>("SENDCASEVALUE");

    /*
     *  PROTOCOL GAME FOR RECEIVE
     */
    public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
    public static ProtocolEventsTCP<Enigme> ACTIONENIGMEPRINCIPALE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMEPRINCIPALE");
    public static ProtocolEventsTCP<String> BADPROPOSALRESPONSE = new ProtocolEventsTCP<String>("BADPROPOSALRESPONSE");
    public static ProtocolEventsTCP<String> GOODPROPOSALRESPONSE = new ProtocolEventsTCP<String>("GOODPROPOSALRESPONSE");




    private ProtocolEventsTCP(String name){
        super(name);
    }

}
