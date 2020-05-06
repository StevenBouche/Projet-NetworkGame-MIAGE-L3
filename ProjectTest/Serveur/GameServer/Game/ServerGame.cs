using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Message.obj;
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
        String id; 

        public int GetNbCurrentPlayer { get => gameManager.listPlayers.Count;  }

        public ServerGame(String id, int port) 
        {
            this.id = id;
            network = new ServerTCP(port,this);
            gameManager = new GameEngine();
            gameManager.addCallbackPlayerJoined(notifyPlayerHaveJoined);
            gameManager.addCallbackPlayerLeaved(notifyPlayerHaveLeaved);
            InitEventAndStartServerTCP();
        }

        public ServerGameInfo GetInfo()
        {
            ServerGameInfo srv = new ServerGameInfo()
            {
                addr = "127.0.0.1",
                port = this.network.Port,
                name = this.id,
                nbPlayerMax = 3,
                nbPlayerCurrent = this.GetNbCurrentPlayer
            };
            return srv;
        }

        private void InitEventAndStartServerTCP()
        {
            //Sub on events to receive data
            network.OnEvent<String>(ProtocolEventsTCP<String>.IDENTITY, OnIdentityReceived);
            //Start thread network
            threadNetwork = new Thread(new ThreadStart(network.Run));
            threadNetwork.Start();
        }

        public void Run()
        {
            
            while(gameManager.listIdPlayers.Count != 3)
            {
                allDone.Reset();
                allDone.WaitOne();
            }

            gameManager.Play();
            
        }

        public void notifyPlayerHaveJoined(String id, ListPlayerGame l)
        {
     //       ListPlayerGame l = gameManager.GetListOfPlayerLobbies();
            PacketMessage<ListPlayerGame> msgP = new PacketMessage<ListPlayerGame>() { evt = ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER.eventName, data = l };
            foreach (PlayerGame p in l.listPlayers)
            {
                network.Send(msgP, p.id);
            }
            allDone.Set();
        }

        public void notifyPlayerHaveLeaved(String id, ListPlayerGame l)
        {
            PacketMessage<ListPlayerGame> msgP = new PacketMessage<ListPlayerGame>() { evt = ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER.eventName, data = l };
            foreach (PlayerGame p in l.listPlayers)
            {
                network.Send(msgP, p.id);
            }
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

        public void OnIdentityReceived(String obj, String id)
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.IDENTITY.eventName, data = id };
            Debug.WriteLine("On Connect player : " + id);
            network.Send(msg, id);
            gameManager.AddPlayer(id, obj);
        }
    }
}
