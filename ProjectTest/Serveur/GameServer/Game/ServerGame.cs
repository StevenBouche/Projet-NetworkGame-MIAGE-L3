using Share.Network.Message;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using Share.Network.Server;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.Game
{
    class ServerGame : INotifyStateSocket
    {
        ServerTCP network;
        Thread threadNetwork;
        GameEngine gameManager;
        protected ManualResetEvent allDone = new ManualResetEvent(false);

        public ServerGame(int port) 
        {
            network = new ServerTCP(port,this);
            gameManager = new GameEngine();

            gameManager.addCallbackPlayerJoined(notifyPlayerHaveJoined);
            InitEventAndStartServerTCP();
        }

        private void InitEventAndStartServerTCP()
        {
            //Sub on events to receive data
            network.OnEvent<String>(ProtocolEventsTCP<String>.CONNECTION, OnConnectionTest);
            network.OnEvent<String>(ProtocolEventsTCP<String>.IDENTITY, OnIdentityReceived);
            //Start thread network
            threadNetwork = new Thread(new ThreadStart(network.Run));
            threadNetwork.Start();
        }

        public void run()
        {
            
            while(gameManager.listIdPlayers.Count != 3)
            {
                allDone.Reset();
                allDone.WaitOne();
            }

            gameManager.Play();
            
        }

        public void notifyPlayerHaveJoined(String id)
        {
            allDone.Set();
        }

        public void OnConnect(string id)
        {
            
        }

        public void OnDisconnect(string id)
        {
            Debug.WriteLine("On Disconnect player : " + id);
            gameManager.RemovePlayer(id);
        }

        public void OnConnectionTest(String obj, String id)
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.CONNECTION.eventName, data = obj };
            network.Send(msg, id);
        }

        public void OnIdentityReceived(String obj, String id)
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.IDENTITY.eventName, data = obj };
            Debug.WriteLine("On Connect player : " + id);
            gameManager.AddPlayer(id);
            network.Send(msg, id);
        }
    }
}
