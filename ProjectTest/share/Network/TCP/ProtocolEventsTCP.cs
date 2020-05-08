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
        public static ProtocolEventsTCP<int> SENDCASEVALUE = new ProtocolEventsTCP<int>("SENDCASEVALUE"); // ??


        /*
        *  PROTOCOL GAME SEND
        */
        public static ProtocolEventsTCP<Proposal> BADPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("BADPROPOSALRESPONSE");
        public static ProtocolEventsTCP<Proposal> GOODPROPOSALRESPONSE = new ProtocolEventsTCP<Proposal>("GOODPROPOSALRESPONSE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMEPRINCIPALE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMEPRINCIPALE");
        public static ProtocolEventsTCP<String> NOTIFYCURRENTPLAYER = new ProtocolEventsTCP<String>("NOTIFYCURRENTPLAYER");
        public static ProtocolEventsTCP<PlayerMoneyInfo> UPDATEROUNDMONEY = new ProtocolEventsTCP<PlayerMoneyInfo>("UPDATEROUNDMONEY");

        /*
       *  PROTOCOL GAME SEND AND RECEIVE
       */
        public static ProtocolEventsTCP<ChoiceStep> CHOICESTEP = new ProtocolEventsTCP<ChoiceStep>("CHOICESTEP");
        public static ProtocolEventsTCP<String> ASKFORALETTER = new ProtocolEventsTCP<String>("ASKFORALETTER");


        private ProtocolEventsTCP(String name) : base(name){ }

    }

}
