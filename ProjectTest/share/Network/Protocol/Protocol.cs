using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Diagnostics;
using System.Net;

namespace Share.Network.Protocol
{
    public class Protocol<T> : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj, EndPoint endPoint);
        public OnReceiveEvent del;

        public Protocol() : base(typeof(T))
        {

        }

        public void AddDelegate(OnReceiveEvent call)
        {
            del += call;
        }

        public override void OnReceive(JToken jToken, EndPoint ep)
        {
            try {
                T o = (T) jToken.ToObject(GetTypeDataEvent());
                del(o, ep);
            } catch (JsonSerializationException e){
                Debug.WriteLine(e);
            }
        }

    }
}
