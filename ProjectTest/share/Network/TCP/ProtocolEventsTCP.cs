using Share.Network.Message.modele;
using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Protocol
{
    public class ProtocolEventsTCP<T> : ProtocolEvents<T>
    {
       
        public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");
        public static ProtocolEventsTCP<ListPlayerGame> NOTIFYLOBBYPLAYER = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYLOBBYPLAYER");
        public static ProtocolEventsTCP<ListPlayerGame> NOTIFYPLAYERREADY = new ProtocolEventsTCP<ListPlayerGame>("NOTIFYPLAYERREADY");


        private ProtocolEventsTCP(String name) : base(name)
        {

        }

    }
}
