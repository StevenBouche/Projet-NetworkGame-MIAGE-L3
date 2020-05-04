using Newtonsoft.Json.Linq;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network.Event
{
    public class EventNetworkManagerUDP : EventNetworkManager
    {
        protected Dictionary<String, ProtocolNetwork> mapEvents;

        public EventNetworkManagerUDP()
        {
            mapEvents = new Dictionary<String, ProtocolNetwork>();
        }

        public void OnEvent<T>(ProtocolEvents<T> proto, Protocol<T>.OnReceiveEvent callback)
        {

            if (!mapEvents.ContainsKey(proto.eventName))
            {
                mapEvents.Add(proto.eventName, proto.GetProtocol());
            }

            if (mapEvents[proto.eventName].GetTypeDataEvent() == typeof(T))
            {
                ((dynamic)mapEvents[proto.eventName]).AddDelegate(callback);
            }

        }

        private void HandleQueue()
        {
            allDone.Reset();
            while (!packets.IsEmpty)
            {
                packets.TryDequeue(out JObject p);

                if (p.Count == 3 && p["evt"] != null && p["data"] != null && p["endpoint"] != null)
                {
                    mapEvents[p["evt"].ToString()].OnReceive(p["data"], GetEndPointFromJObject(p));
                }
            }
            allDone.WaitOne();
        }

        private EndPoint GetEndPointFromJObject(JObject p)
        {
            IPAddress ip = IPAddress.Parse(p["endpoint"]["ip"].ToString());
            int port = p["endpoint"]["port"].ToObject<int>();
            return new IPEndPoint(ip, port);
        }

        private void BuildJObjectBeforeQueue(String obj, EndPoint endPoint, out JObject o)
        {
            o = JObject.Parse(obj);
            IPEndPoint ip = (IPEndPoint)endPoint;
            JObject jobj = new JObject(new JProperty("ip", ip.Address.ToString()), new JProperty("port", ip.Port));
            o.Add("endpoint", jobj);
        }

        public override void Run()
        {
            running = true;
            while (running)
            {
                HandleQueue();
            }
        }

        public override void OnReceivedData(String obj, EndPoint endPoint)
        {
            BuildJObjectBeforeQueue(obj, endPoint, out JObject data);
            packets.Enqueue(data);
            allDone.Set();
        }

        public override void OnReceivedData(string obj, StateObjectTCP state)
        {
            throw new NotImplementedException(); //Cannot receive TCP datagrams
        }

    }
}
