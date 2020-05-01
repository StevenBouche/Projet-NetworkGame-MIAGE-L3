using share;
using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Protocol
{
    public class ProtocolEventsUDP<T> : ProtocolEvents<T>
    {

        public static ProtocolEventsUDP<Choice> SUBSCRIPTION = new ProtocolEventsUDP<Choice>("SUBSCRIPTION");


        private ProtocolEventsUDP(String name) : base(name)
        {

        }

    }
}
