﻿using Share.Network.Message.modele;
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
         *  PROTOCOL GAME
         */
        public static ProtocolEventsTCP<String> PROPOSALRESPONSE = new ProtocolEventsTCP<String>("PROPOSALRESPONSE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMERAPIDE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMERAPIDE");
        public static ProtocolEventsTCP<Enigme> ACTIONENIGMEPRINCIPALE = new ProtocolEventsTCP<Enigme>("ACTIONENIGMEPRINCIPALE");

        private ProtocolEventsTCP(String name) : base(name)
        {

        }

    }
}
