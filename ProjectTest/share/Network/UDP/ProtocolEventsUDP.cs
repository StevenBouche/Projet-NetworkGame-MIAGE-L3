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

        private ProtocolEventsUDP(String name) : base(name)
        {

        }

    }
}
