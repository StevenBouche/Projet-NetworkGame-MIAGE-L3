using share;
using Share.Network.Message.obj;
using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Protocol
{
    public class ProtocolEventsUDP<T> : ProtocolEvents<T>
    {

        public static ProtocolEventsUDP<Choice> SUBSCRIPTION = new ProtocolEventsUDP<Choice>("SUBSCRIPTION");
        public static ProtocolEventsUDP<DataServerGame> GETLISTSERVERGAME = new ProtocolEventsUDP<DataServerGame>("GETLISTSERVERGAME");
        public static ProtocolEventsUDP<String> NEWGAME = new ProtocolEventsUDP<String>("NEWGAME");
        public static ProtocolEventsUDP<String> REMOVEGAME = new ProtocolEventsUDP<String>("REMOVEGAME");
        private ProtocolEventsUDP(String name) : base(name)
        {

        }

    }
}
