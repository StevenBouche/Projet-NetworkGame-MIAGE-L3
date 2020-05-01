
using share;
using System;
using System.Net;

namespace Serveur.Network
{
    public class ProtocolEvents<T>
    {
        public static ProtocolEvents<String> SUBSCRIPTION = new ProtocolEvents<String>("SUBSCRIPTION");
        public static ProtocolEvents<Choice> TEST = new ProtocolEvents<Choice>("TEST");

        public readonly string eventName;
        public readonly Type typeProtocol;

        private ProtocolEvents(String name)
        {
            eventName = name;
            typeProtocol = typeof(T);
        }

        public ProtocolEvents()
        {

        }

        public ProtocolNetwork GetProtocol()
        {
            Type t1 = typeof(ProtocolUDP<>);
            Type constructed = t1.MakeGenericType(typeProtocol);
            return (ProtocolNetwork) Activator.CreateInstance(constructed);
        }

    }

    public class ProtocolNetwork
    {
        
    }

    public  class ProtocolUDP<T> : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj, EndPoint endPoint);
        public OnReceiveEvent del;

        public void AddDelegate(OnReceiveEvent call) 
        {
            del += call;
        }

        public void OnReceive(T obj, EndPoint ep)
        {
            del(obj,ep);
        }

        public Type GetTypeDataEvent()
        {
            return typeof(T);
        }
    }

}
