using System;

namespace Share.Network.Protocol
{
    public abstract class ProtocolEvents<T>
    {

        public readonly string eventName;
        public readonly Type typeObjProtocol;

        public ProtocolEvents(string name)
        {
            eventName = name;
            typeObjProtocol = typeof(T);
        }

        public ProtocolNetwork GetProtocol()
        {
            Type t1 = typeof(Protocol<>);
            Type constructed = t1.MakeGenericType(typeObjProtocol);
            ProtocolNetwork pn = (ProtocolNetwork)Activator.CreateInstance(constructed);
            pn.EventName = eventName;
            return pn;
        }

    }
}
