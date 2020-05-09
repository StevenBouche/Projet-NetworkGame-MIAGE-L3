package network.tcp;

import network.message.obj.*;
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
    public static ProtocolEventsTCP<Proposal> BADPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("BADPROPOSALRESPONSE");
    public static ProtocolEventsTCP<Proposal> GOODPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("GOODPROPOSALRESPONSE");
    public static ProtocolEventsTCP<String> NOTIFYCURRENTPLAYER = new ProtocolEventsTCP<String>("NOTIFYCURRENTPLAYER");
    public static ProtocolEventsTCP<DataMoneyInfo> UPDATEROUNDMONEY = new ProtocolEventsTCP<DataMoneyInfo>("UPDATEROUNDMONEY");
    public static ProtocolEventsTCP<Proposal> BADPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("BADPROPOSALLETTER");
    public static ProtocolEventsTCP<Proposal> GOODPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("GOODPROPOSALLETTER");

    /*
        PROTOCOL SEND AND RECEIVE
     */
    public static ProtocolEventsTCP<ChoiceStep> CHOICESTEP = new ProtocolEventsTCP<ChoiceStep>("CHOICESTEP");
    public static ProtocolEventsTCP<String> ASKFORALETTER = new ProtocolEventsTCP<String>("ASKFORALETTER");

    private ProtocolEventsTCP(String name){
        super(name);
    }

}
