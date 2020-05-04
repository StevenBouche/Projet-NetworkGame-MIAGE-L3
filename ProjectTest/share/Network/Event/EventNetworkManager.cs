using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
using System.Threading;

namespace Share.Network
{

    public abstract class EventNetworkManager : IReceiverNetwork
    {
        protected Boolean running;
        protected ManualResetEvent allDone = new ManualResetEvent(false);
        protected ConcurrentQueue<JObject> packets;

        public EventNetworkManager()
        {
            packets = new ConcurrentQueue<JObject>();
        }
        
        //delegate to childrens depending on protocol UDP / TCP
        public abstract void Run();

        public abstract void OnReceivedData(string obj, EndPoint endPoint);

        public abstract void OnReceivedData(string obj, StateObjectTCP state);
        
    }

}
