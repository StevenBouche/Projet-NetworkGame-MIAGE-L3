using Share.Network.Protocol.TCP;
using Share.Network.Protocol.UDP;
using System;

namespace Share.Network.Protocol
{
    public abstract class ProtocolEvents<T>
    {

        public readonly string eventName;

        public ProtocolEvents(string name)
        {
            eventName = name;
        }

        public ProtocolNetwork GetProtocolUDP()
        {
            ProtocolNetwork pn = new ProtocolUDP<T>
            {
                EventName = eventName
            };
            return pn;
        }

        public ProtocolNetwork GetProtocolTCP()
        {
            ProtocolNetwork pn = new ProtocolTCP<T>
            {
                EventName = eventName
            };
            return pn;
        }

    }
}
