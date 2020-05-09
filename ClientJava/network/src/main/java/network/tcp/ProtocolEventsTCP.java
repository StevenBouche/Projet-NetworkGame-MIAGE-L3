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
    public static ProtocolEventsTCP<CaseInfo> SENDCASE = new ProtocolEventsTCP<CaseInfo>("SENDCASE");

    /*
     *  PROTOCOL GAME FOR RECEIVE
     */
    public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
    public static ProtocolEventsTCP<Enigme> ACTIONENIGMEPRINCIPALE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMEPRINCIPALE");
    public static ProtocolEventsTCP<Proposal> BADPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("BADPROPOSALRESPONSE");
    public static ProtocolEventsTCP<Proposal> GOODPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("GOODPROPOSALRESPONSE");
    public static ProtocolEventsTCP<String> NOTIFYCURRENTPLAYER = new ProtocolEventsTCP<String>("NOTIFYCURRENTPLAYER");
    public static ProtocolEventsTCP<DataMoneyInfo> UPDATEMONEYALL = new ProtocolEventsTCP<DataMoneyInfo>("UPDATEMONEYALL");
    public static ProtocolEventsTCP<Proposal> BADPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("BADPROPOSALLETTER");
    public static ProtocolEventsTCP<Proposal> GOODPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("GOODPROPOSALLETTER");
    public static ProtocolEventsTCP<String> NOTIFYNOMORECONSONNANT = new ProtocolEventsTCP<String>("NOTIFYNOMORECONSONNANT");
    public static ProtocolEventsTCP<ProposalLetter> GOODPROPOSALFINALLETTER = new ProtocolEventsTCP<ProposalLetter>("GOODPROPOSALFINALLETTER");
    public static ProtocolEventsTCP<Integer> SENDFINALVALUE = new ProtocolEventsTCP<Integer>("SENDFINALVALUE");

    /*
        PROTOCOL SEND AND RECEIVE
     */
    public static ProtocolEventsTCP<ChoiceStep> CHOICESTEP = new ProtocolEventsTCP<ChoiceStep>("CHOICESTEP");
    public static ProtocolEventsTCP<String> ASKFORALETTER = new ProtocolEventsTCP<String>("ASKFORALETTER");
    public static ProtocolEventsTCP<FinalInfo> ACTIONENIGMEFINALE = new ProtocolEventsTCP<FinalInfo>("ACTIONENIGMEFINALE");
    public static ProtocolEventsTCP<FinalLetters> ASKFORFINALLETTER = new ProtocolEventsTCP<FinalLetters>("ASKFORFINALLETTER");
    public static ProtocolEventsTCP<Proposal> ASKFORFINALPROPOSITION = new ProtocolEventsTCP<Proposal>("ASKFORFINALPROPOSITION");
    public static ProtocolEventsTCP<String> ASKFORIDPLAYER = new ProtocolEventsTCP<String>("ASKFORIDPLAYER");

    private ProtocolEventsTCP(String name){
        super(name);
    }

}
