
using share;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using System;
using System.Net;
using System.Threading;

namespace Serveur.Network
{
   
    class UDPServer 
    {

        NetworkManagerUDP manager;
    
        public UDPServer()
        {
            manager = new NetworkManagerUDP();
            manager.evtNetManager.OnEvent<Choice>(ProtocolEventsUDP<Choice>.SUBSCRIPTION, OnSubscription);
        }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(manager.StartListening));
            t.Start();
            t.Join();
        }

        private void OnSubscription(Choice content, EndPoint ep)
        {
            Console.WriteLine("On sub receive " + content + " from " + ep.ToString());
        }

    }
}
