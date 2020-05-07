using Serveur.GameServer.Game.Helper;
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
using System.Linq;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.Game
{
    class ServerGame : INotifyStateSocket, ISenderAtClient
    {
        ServerTCP network;
        Thread threadNetwork;
        GameEngine gameManager;
        protected ManualResetEvent allDone = new ManualResetEvent(false);
        String id; 

        public int GetNbCurrentPlayer { get => gameManager.listPlayers.Count;  }

        HelperLobby helperLobby;

        public ServerGame(String id, int port) 
        {
            this.id = id;
            network = new ServerTCP(port,this);
            gameManager = new GameEngine(this);
            helperLobby = new HelperLobby(ref gameManager,ref network,ref allDone);
            InitEventAndStartServerTCP();
        }

        public ServerGameInfo GetInfo()
        {
            ServerGameInfo srv = new ServerGameInfo()
            {
                addr = "127.0.0.1", //todo change
                port = this.network.Port,
                name = this.id,
                nbPlayerMax = 3,
                nbPlayerCurrent = this.GetNbCurrentPlayer
            };
            return srv;
        }

        private void InitEventAndStartServerTCP()
        {
            //event lobby
            network.OnEvent<String>(ProtocolEventsTCP<String>.IDENTITY, helperLobby.OnIdentityReceived);
            network.OnEvent<Boolean>(ProtocolEventsTCP<Boolean>.NOTIFYPLAYERREADY, helperLobby.OnReadyReceived);
            
            //event game choice client
            network.OnEvent<String>(ProtocolEventsTCP<String>.PROPOSALRESPONSE, OnProposalResponse);

            //Start thread network
            threadNetwork = new Thread(new ThreadStart(network.Run));
            threadNetwork.Start();
        }

        private void OnProposalResponse(String obj, String id)
        {
            gameManager.NotifyReceivePlayer(obj, id);
        }

        public void Run()
        {
            gameManager.gameState = GameState.WAITING_PLAYER;

            while(gameManager.gameState != GameState.STARTED)
            {
                allDone.Reset();
                allDone.WaitOne();
            }

            notifyGameIsReady();
            gameManager.Play();


            //todo prevenir que je termine le server ou Reset ou choix ?

        }

        public void OnConnect(string id)
        {
            if(gameManager.listPlayers.Count == 3)
            {
                network.disconnectClient(id);
            }
        }

        public void OnDisconnect(string id)
        {
            Debug.WriteLine("(TCP exchange) On Disconnect player : " + id);
            gameManager.RemovePlayer(id);
        }

        private void notifyGameIsReady()
        {
            String s = "Game is ready";
            PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.NOTIFYGAMEREADY.eventName, data = s };
            SendAllClientInGame(msg);
        }

        public void SendClient<T>(PacketMessage<T> msg, String id)
        {
            network.Send(msg, id);
        }

        public void SendAllClientInGame<T>(PacketMessage<T> msg)
        {
            network.SendAll(msg, gameManager.listPlayers.Keys.ToList<String>());
        }

    }

}