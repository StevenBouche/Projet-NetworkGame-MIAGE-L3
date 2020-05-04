using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Share.Network.NetworkManager;
using System;
using System.Diagnostics;
using System.Net;

namespace Share.Network.Protocol
{
    public class Protocol<T> : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj, EndPoint endPoint);
        public OnReceiveEvent del;

        public delegate void OnReceiveEventTCP(T obj, String id);
        public OnReceiveEventTCP delTCP;

        public Protocol() : base(typeof(T))
        {

        }

        public void AddDelegate(OnReceiveEvent call)
        {
            del += call;
        }

        public void AddDelegateTCP(OnReceiveEventTCP call)
        {
            delTCP += call;
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

        public override void OnReceive(JToken jToken, JToken id)
        {
            try
            {
                T o = (T)jToken.ToObject(GetTypeDataEvent());
                String str_id = id.ToString();
                delTCP(o, str_id);
            }
            catch (JsonSerializationException e)
            {
                Debug.WriteLine(e);
            }
        }

    }
}
