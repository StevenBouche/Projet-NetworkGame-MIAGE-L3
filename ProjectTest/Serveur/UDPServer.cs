
using share;
using Share.Network.Message;
using Share.Network.Message.obj;
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
            manager.evtNetManager.OnEvent(ProtocolEventsUDP<Choice>.SUBSCRIPTION, OnSubscription);
            manager.evtNetManager.OnEvent(ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME, OnDataServerGame);
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

        private void OnDataServerGame(DataServerGame dataServ, EndPoint ep)
        {

            dataServ.listServer.Add(new ServerGame()
            {
                addr = "127.0.0.1",
                port = 10000,
                name = "TEST",
                nbPlayerMax = 3,
                nbPlayerCurrent = 0
            });
            dataServ.listServer.Add(new ServerGame()
            {
                addr = "127.0.0.1",
                port = 10000,
                name = "TEST2",
                nbPlayerMax = 3,
                nbPlayerCurrent = 3
            });
            PacketMessage<DataServerGame> packet = new PacketMessage<DataServerGame>()
            {
                evt = ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME.eventName,
                data = dataServ
            };
            manager.Send(packet, ep);    
        }

    }
}
