using Newtonsoft.Json.Linq;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using Share.Network.Protocol.TCP;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network.Event
{
    public class EventNetworkManagerTCP : EventNetworkManager
    {

        public EventNetworkManagerTCP() :base()
        {
            
        }

        public override void Run()
        {
            running = true;
            while (running)
            {
                HandleQueue();
            }
        }

        private void HandleQueue()
        {
            allDone.Reset();
            while (!packets.IsEmpty)
            {
                packets.TryDequeue(out JObject p);

                if (p.Count == 3 && p["evt"] != null && p["data"] != null && p["id"] != null)
                {
                    dynamic d = mapEvents[p["evt"].ToString()];
                    if(d!=null) d.OnReceive(p["data"], p["id"]);
                }
            }
            allDone.WaitOne();
        }

        public override void OnReceivedData(string obj, StateObjectTCP state)
        {
            BuildJObjectBeforeQueue(obj, state.id, out JObject data);
            packets.Enqueue(data);
            allDone.Set();
        }

        private void BuildJObjectBeforeQueue(String obj, String id, out JObject o)
        {
            o = JObject.Parse(obj);
            o.Add("id", id);
        }

        public override void OnReceivedData(string obj, EndPoint endPoint)
        {
            throw new NotImplementedException(); //Cannot receive UDP datagrams
        }

        public void OnEvent<T>(ProtocolEvents<T> proto, ProtocolTCP<T>.OnReceiveEventTCP callback)
        {

            if (!mapEvents.ContainsKey(proto.eventName))
            {
                mapEvents.Add(proto.eventName, proto.GetProtocolTCP());
            }

            if (mapEvents[proto.eventName].GetTypeDataEvent() == typeof(T))
            {
                ((dynamic)mapEvents[proto.eventName]).AddDelegateTCP(callback);
            }

        }

        public void close()
        {
            running = false;
            allDone.Set();
        }
    }
}
