using System;
using System.Net;

namespace Share.Network.Protocol
{
    public class Protocol<T> : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj, EndPoint endPoint);
        public OnReceiveEvent del;

        public void AddDelegate(OnReceiveEvent call)
        {
            del += call;
        }

        public void OnReceive(T obj, EndPoint ep)
        {
            del(obj, ep);
        }

        public Type GetTypeDataEvent()
        {
            return typeof(T);
        }
    }
}
