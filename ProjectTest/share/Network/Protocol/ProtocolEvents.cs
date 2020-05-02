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

        public ProtocolNetwork GetProtocol()
        {
            ProtocolNetwork pn = new Protocol<T>
            {
                EventName = eventName
            };
            return pn;
        }

    }
}
