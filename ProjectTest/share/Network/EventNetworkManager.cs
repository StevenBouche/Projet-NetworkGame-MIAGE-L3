using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Share.Network.Protocol;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
using System.Threading;

namespace Share.Network
{

    public class EventNetworkManager : IReceiverNetwork
    {

        Boolean running;
        ManualResetEvent allDone = new ManualResetEvent(false);
        Dictionary<String, ProtocolNetwork> mapEvents;
        ConcurrentQueue<JObject> packets;

        public EventNetworkManager()
        {
            packets = new ConcurrentQueue<JObject>();
            mapEvents = new Dictionary<String, ProtocolNetwork>();
        }

        public void Run()
        {
            running = true;
            while (running)
            {      
                HandleQueue();
            }
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
                
                if (p.Count == 3 && p["evt"] != null && p["data"] != null  && p["endpoint"] != null)
                {
                    mapEvents[p["evt"].ToString()].OnReceive(p["data"], GetEndPointFromJObject(p));
                }
            }
            allDone.WaitOne();
        }

        public void OnReceivedData(String obj, EndPoint endPoint)
        {
            BuildJObjectBeforeQueue(obj, endPoint, out JObject data);
            packets.Enqueue(data);
            allDone.Set();
        }

        private void BuildJObjectBeforeQueue(String obj, EndPoint endPoint, out JObject o )
        {
            o = JObject.Parse(obj);
            IPEndPoint ip = (IPEndPoint)endPoint;
            JObject jobj = new JObject(new JProperty("ip", ip.Address.ToString()), new JProperty("port", ip.Port));
            o.Add("endpoint", jobj);
        }

        private EndPoint GetEndPointFromJObject(JObject p)
        {
            IPAddress ip = IPAddress.Parse(p["endpoint"]["ip"].ToString());
            int port = p["endpoint"]["port"].ToObject<int>();
            return new IPEndPoint(ip, port);
        }
    }

}
