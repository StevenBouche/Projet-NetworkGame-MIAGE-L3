using Share.Network.Message;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Share.Network.Server
{
    public class ServerTCP
    {
        NetworkManagerTCP managerTCP;

        public ServerTCP() { 
            managerTCP = new NetworkManagerTCP();
            managerTCP.eventManager.OnEvent<String>(ProtocolEventsTCP<String>.CONNECTION, OnConnection);
        }

        public void OnConnection(String obj, String id)
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() {evt = ProtocolEventsTCP<String>.CONNECTION.eventName, data = obj};
            managerTCP.Send(msg, id);
        }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(managerTCP.StartListening));
            t.Start();
            t.Join();
        }


    }
}
