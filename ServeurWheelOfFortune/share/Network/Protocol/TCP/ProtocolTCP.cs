using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;

namespace Share.Network.Protocol.TCP
{
    public class ProtocolTCP<T> : ProtocolNetwork
    {

        public ProtocolTCP() : base(typeof(T))
        {

        }

        public delegate void OnReceiveEventTCP(T obj, String id);
        public OnReceiveEventTCP delTCP;

        public void AddDelegateTCP(OnReceiveEventTCP call)
        {
            delTCP += call;
        }

        public void OnReceive(JToken jToken, JToken id)
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
