
using share;
using share.Network.NetworkManager;
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
            manager.evtNetManager.OnEvent<String>(ProtocolEvents<String>.SUBSCRIPTION, OnSubscription);
            manager.evtNetManager.OnEvent<Choice>(ProtocolEvents<Choice>.TEST, OnTest);
        }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(manager.StartListening));
            t.Start();
            t.Join();
        }

        private void OnSubscription(String content, EndPoint ep)
        {
            Console.WriteLine("On sub receive " + content + " from " + ep.ToString());
        }

        private void OnTest(Choice content, EndPoint ep)
        {
            Console.WriteLine("On sub receive " + content + " from " + ep.ToString());
        }
    }
}
