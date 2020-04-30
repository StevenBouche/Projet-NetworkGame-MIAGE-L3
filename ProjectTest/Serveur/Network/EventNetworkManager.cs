using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json.Schema;
using Newtonsoft.Json.Schema.Generation;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Threading;

namespace Serveur.Network
{

    class StackCallBack<T> where T : ProtocolNetwork
    {
        public delegate void OnReceiveEvent(T obj) ;
        public OnReceiveEvent delegates;
    }

    class EventNetworkManager : IReceiverNetwork
    {

        ConcurrentQueue<String> objs;

        Boolean running;
        ManualResetEvent allDone = new ManualResetEvent(false);

       // Dictionary<ProtocolEvents, StackCallBack> callbackEvents;

        public EventNetworkManager()
        {
        //    callbackEvents = new Dictionary<ProtocolEvents, StackCallBack<T>();
            objs = new ConcurrentQueue<String>();
        }

        public void OnEvent<T>(T del, StackCallBack<T>.OnReceiveEvent call) where T : ProtocolNetwork
        {
       /*     if (callbackEvents.ContainsKey(del.evt))
            {
                StackCallBack<T.GetType()> stack = new StackCallBack();
                stack
                callbackEvents.Add(del.evt, call);
            } else
            {
                callbackEvents[del.evt] += call;
            }*/
        }

       public void Run()
        {
            running = true;

            while (running)
            {

                // Set the event to nonsignaled state.  
                allDone.Reset();

                HandleQueue();

                // Wait until a connection is made before continuing.  
                allDone.WaitOne();
            }
        }

        private void HandleQueue()
        {
            while (!objs.IsEmpty)
            {
                objs.TryDequeue(out String str);
                Console.WriteLine("EVENT NET WORK MANAGER : " + str + " buffer : " + objs.Count);
            }
        }

        public void OnReceivedData(String obj)
        {
         //   JSchemaGenerator generator = new JSchemaGenerator();
         //   JSchema schema = generator.Generate(typeof(ProtocolMessage<String>));

            try
            {
                ProtocolNetwork dese = JsonConvert.DeserializeObject<ProtocolNetwork>(obj);
                if (dese.evt == ProtocolEvents.SUBSCRIBE)
                {
                    ProtocolMessage<String> pm = JsonConvert.DeserializeObject<ProtocolMessage<String>>(obj);
                    messages.Enqueue(pm);
                }
            } catch (JsonReaderException e)
            {
                Console.WriteLine(e);
            }
            allDone.Set();
        }
    }


}
