using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading;

namespace Serveur.Network
{

    class EventNetworkManager : IReceiverNetwork
    {

        ConcurrentQueue<String> objs;
        Boolean running;
        ManualResetEvent allDone = new ManualResetEvent(false);
        Dictionary<String, ProtocolNetwork> mapEvents;

        public EventNetworkManager()
        {
            objs = new ConcurrentQueue<String>();
            mapEvents = new Dictionary<String, ProtocolNetwork>();
            mapEvents.Add(ProtocolEvents.SUBSCRIPTION.eventName, ProtocolEvents.SUBSCRIPTION.GetProtocol());
            dynamic sub = mapEvents[ProtocolEvents.SUBSCRIPTION.eventName];
 
        }

     

        public void Test(String str)
        {
            Console.WriteLine("ON SUBSCRIPTION "+ str);
        }

       public void Run()
        {
            running = true;

            while (running)
            {
                allDone.Reset();
                HandleQueue();
                allDone.WaitOne();
            }
        }

        private void HandleQueue()
        {
            while (!objs.IsEmpty)
            {
                objs.TryDequeue(out String str);
            }
        }

        public void OnReceivedData(String obj)
        {
            try
            {
                dynamic dese = JsonConvert.DeserializeObject<ProtocolNetwork>(obj);
                if (dese.evt.eventName == ProtocolEvents.SUBSCRIPTION.eventName)
                {
                    ProtocolMessage<String> pm = JsonConvert.DeserializeObject<ProtocolMessage<String>>(obj);
                    dynamic callback = mapEvents[dese.evt.eventName];
                    callback.OnReceive(pm.data); 
                }
            } catch (JsonReaderException e)
            {
                Console.WriteLine(e);
            }
            objs.Enqueue(obj);
            allDone.Set();
        }
    }


}
