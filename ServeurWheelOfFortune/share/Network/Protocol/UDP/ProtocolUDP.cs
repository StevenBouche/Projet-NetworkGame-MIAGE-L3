using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
using System.Text;

namespace Share.Network.Protocol.UDP
{
    public class ProtocolUDP<T> : ProtocolNetwork
    {

        public delegate void OnReceiveEvent(T obj, EndPoint endPoint);
        public OnReceiveEvent del;

        public ProtocolUDP() : base(typeof(T))
        {

        }

        public void AddDelegate(OnReceiveEvent call)
        {
            del += call;
        }

        public void OnReceive(JToken jToken, EndPoint ep)
        {
            try
            {
                T o = (T)jToken.ToObject(GetTypeDataEvent());
                del(o, ep);
            }
            catch (JsonSerializationException e)
            {
                Debug.WriteLine(e);
            }
        }
    }
}
