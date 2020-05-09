using Share.Network.Message.modele;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network.Protocol
{
    public class ProtocolEventsTCP<T> : ProtocolEvents<T>
    {

        /*
         * PROTOCOL LOBBY
        */
        public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");
        public static ProtocolEventsTCP<ListPlayerGame> NOTIFYLOBBYPLAYER = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYLOBBYPLAYER");
        public static ProtocolEventsTCP<Boolean> NOTIFYPLAYERREADY = new ProtocolEventsTCP<Boolean>("NOTIFYPLAYERREADY");
        public static ProtocolEventsTCP<String> NOTIFYGAMEREADY = new ProtocolEventsTCP<String>("NOTIFYGAMEREADY");

        /*
         *  PROTOCOL GAME RECEIVE
         */
        public static ProtocolEventsTCP<String> PROPOSALRESPONSE = new ProtocolEventsTCP<String>("PROPOSALRESPONSE");
        public static ProtocolEventsTCP<int> SENDCASEVALUE = new ProtocolEventsTCP<int>("SENDCASEVALUE");


        /*
        *  PROTOCOL GAME SEND
        */
        public static ProtocolEventsTCP<Proposal> BADPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("BADPROPOSALRESPONSE");
        public static ProtocolEventsTCP<Proposal> GOODPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("GOODPROPOSALRESPONSE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMEPRINCIPALE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMEPRINCIPALE");
        public static ProtocolEventsTCP<String> NOTIFYCURRENTPLAYER = new ProtocolEventsTCP<String>("NOTIFYCURRENTPLAYER");
        public static ProtocolEventsTCP<PlayerMoneyInfo> UPDATEROUNDMONEY = new ProtocolEventsTCP<PlayerMoneyInfo>("UPDATEROUNDMONEY");
        public static ProtocolEventsTCP<DataMoneyInfo> UPDATEMONEYALL = new ProtocolEventsTCP<DataMoneyInfo>("UPDATEMONEYALL");
        public static ProtocolEventsTCP<Proposal> BADPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("BADPROPOSALLETTER");
        public static ProtocolEventsTCP<Proposal> GOODPROPOSALLETTER = new ProtocolEventsTCP<Proposal>("GOODPROPOSALLETTER");
        public static ProtocolEventsTCP<ProposalLetter> BADPROPOSALFINALLETTER = new ProtocolEventsTCP<ProposalLetter>("BADPROPOSALFINALLETTER");
        public static ProtocolEventsTCP<ProposalLetter> GOODPROPOSALFINALLETTER = new ProtocolEventsTCP<ProposalLetter>("GOODPROPOSALFINALLETTER");

        /*
       *  PROTOCOL GAME SEND AND RECEIVE
       */
        public static ProtocolEventsTCP<ChoiceStep> CHOICESTEP = new ProtocolEventsTCP<ChoiceStep>("CHOICESTEP");
        public static ProtocolEventsTCP<String> ASKFORALETTER = new ProtocolEventsTCP<String>("ASKFORALETTER");
        public static ProtocolEventsTCP<FinalLetters> ASKFORFINALLETTER = new ProtocolEventsTCP<FinalLetters>("ASKFORFINALLETTER");
        public static ProtocolEventsTCP<Proposal> ASKFORFINALPROPOSITION = new ProtocolEventsTCP<Proposal>("ASKFORFINALPROPOSITION");
        public static ProtocolEventsTCP<String> ASKFORIDPLAYER = new ProtocolEventsTCP<String>("ASKFORIDPLAYER");



        private ProtocolEventsTCP(String name) : base(name){ }

    }

}
