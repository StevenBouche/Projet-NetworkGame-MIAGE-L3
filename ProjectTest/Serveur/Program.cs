
using Share.Network.Message;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using Share.Network.Server;
using System;
using System.Diagnostics;
using System.Threading;

namespace Serveur
{
    internal class TestConnection : INotifyStateSocket
    {
        public void OnConnect(string id)
        {
            Debug.WriteLine("On Connect player : " + id);
        }

        public void OnDisconnect(string id)
        {
            Debug.WriteLine("On Disconnect player : " + id);
        }
    }

    class Program
    {
        static ServerTCP server;

        static void Main(string[] args)
        {
            // Supply the state information required by the task.

             ServerUDP myServer = new ServerUDP();
        //     server = new ServerTCP(10001, new TestConnection());
       //      server.OnEvent<String>(ProtocolEventsTCP<String>.CONNECTION, Program.OnConnection);
            // Create a thread to execute the task, and then
            // start the thread.

            Thread t = new Thread(new ThreadStart(myServer.Run));
            t.Start();
            Console.WriteLine("Main thread does some work, then waits.");
            t.Join();
            Console.WriteLine("Independent task has completed; main thread ends.");
        }

        public static void OnConnection(String obj, String id)
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.CONNECTION.eventName, data = obj };
            server.Send(msg, id);
        }
    }
}
