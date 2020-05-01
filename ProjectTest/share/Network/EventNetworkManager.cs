﻿using Newtonsoft.Json.Linq;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net;
using System.Threading;

namespace Serveur.Network
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
            OnEvent<String>(ProtocolEvents<String>.SUBSCRIPTION, Test); // test
        }

        public void Test(String str, EndPoint ep)
        {
            Console.WriteLine("ON SUBSCRIPTION " + str); //todo delete
        }

        public void Run()
        {
            running = true;
            while (running)
            {      
                HandleQueue();
            }
        }

        public void OnEvent<T>(ProtocolEvents<T> proto, ProtocolUDP<T>.OnReceiveEvent callback)
        {

            if (!mapEvents.ContainsKey(proto.eventName))
            {
                mapEvents.Add(proto.eventName, proto.GetProtocol());
            }

            dynamic evt = mapEvents[proto.eventName];
            if (evt.GetTypeDataEvent() == typeof(T))
            {
                evt.AddDelegate(callback);
            }

        }

        private void HandleQueue()
        {
            allDone.Reset();

            while (!packets.IsEmpty)
            {
                packets.TryDequeue(out JObject p);
                
                if (p.Count == 4 && p["evt"] != null && p["data"] != null && p["typeData"] != null && p["endpoint"] != null)
                {
                    IPAddress ip = IPAddress.Parse(p["endpoint"]["ip"].ToString());
                    int port = p["endpoint"]["port"].ToObject<int>();
                    EndPoint ep = new IPEndPoint(ip, port);
                    String str = p["evt"].ToString();
                    dynamic callbacks = mapEvents[str];
                    callbacks.OnReceive(p["data"].ToObject(callbacks.GetTypeDataEvent()), ep);
                }
            }

            allDone.WaitOne();
        }

        public void OnReceivedData(String obj, EndPoint endPoint)
        {
            JObject data = JObject.Parse(obj);
            IPEndPoint ip = (IPEndPoint)endPoint;
            JObject jobj = new JObject(new JProperty("ip", ip.Address.ToString()), new JProperty("port", ip.Port));
            data.Add("endpoint", jobj);
            packets.Enqueue(data);
            allDone.Set();
        }
    }

}
