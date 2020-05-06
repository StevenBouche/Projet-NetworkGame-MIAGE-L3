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

        public ServerGame(int port) 
        {
            network = new ServerTCP(port,this);
            gameManager = new GameEngine();
            InitEventAndStartServerTCP();
        }

        private void InitEventAndStartServerTCP()
        {
            //Sub on events to receive data
            network.OnEvent<String>(ProtocolEventsTCP<String>.CONNECTION, OnConnectionTest);
            //Start thread network
            threadNetwork = new Thread(new ThreadStart(network.Run));
            threadNetwork.Start();
        }

        public void OnConnect(string id)
        {
            Debug.WriteLine("On Connect player : " + id);
            gameManager.AddPlayer(id);
        }

        public void OnDisconnect(string id)
        {
            Debug.WriteLine("On Disconnect player : " + id);
            gameManager.RemovePlayer(id);
        }

<<<<<<< HEAD
        public void OnIdentityReceived(String obj, String id)
        {
            if(gameManager.listPlayers.Count <= 3)
            {
                Console.WriteLine("(TCP exchange) Client " + id + "have sent " + obj);
                PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.IDENTITY.eventName, data = id };
                Debug.WriteLine("On Connect player : " + id);
                network.Send(msg, id);
                gameManager.AddPlayer(id, obj);
            }
            else
            {
                Console.WriteLine("Too much Identity msg received");

            }
        }

        public void OnReadyReceived(Boolean isReady, String id)
        {
            Console.WriteLine("(TCP exchange) Received" + id + " is ready");
            gameManager.setReadyPlayer(isReady, id);

            ListPlayerGame list = gameManager.GetListOfPlayerLobbies();
            updateClientLobby(ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER, list);

            if (checkAllPlayersReady())
            {
                gameManager.gameState = GameState.STARTED;
                allDone.Set();
            }
        }

        private Boolean checkAllPlayersReady()
        {
            if (gameManager.listIdPlayers.Count != 3)
            {
                return false;
            }
            foreach (KeyValuePair<String, Joueur> p in gameManager.listPlayers)
            {
                if (!p.Value.isReady)
                {
                    return false;
                }                
            }
            return true;
        }

        private void updateClientLobby<T>(ProtocolEventsTCP<T> p, ListPlayerGame l)
=======
        public void OnConnectionTest(String obj, String id)
>>>>>>> ffd4f100c032a7c58f3cc6bfbfab1b82c9dcdf8d
        {
            Console.WriteLine("Client " + id + "have sent " + obj);
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.CONNECTION.eventName, data = obj };
            network.Send(msg, id);
        }
    }
}
