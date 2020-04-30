
using System;

namespace Serveur.Network
{
    public class ProtocolEvents
    {
        public static ProtocolEvents SUBSCRIPTION = new ProtocolEvents("SUBSCRIPTION", typeof(Protocol<String>));

        public string eventName;
        private Type typeProtocol;

        private ProtocolEvents(String name, Type type)
        {
            eventName = name;
            typeProtocol = type;
   
        }

        public ProtocolEvents()
        {

        }

        public ProtocolNetwork GetProtocol()
        {
            return (ProtocolNetwork) Activator.CreateInstance(typeProtocol);
        }

    }

    public class ProtocolNetwork
    {
        public ProtocolEvents evt;
    }

    public  class Protocol<T> : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj);
        public OnReceiveEvent del;

   

        public void AddDelegate(OnReceiveEvent call) 
        {
            del += call;
        }

        public void OnReceive(T obj)
        {
            del(obj);
        }
    }

    public class ProtocolMessage<T> : ProtocolNetwork
    {
        public T data;
    }

}
