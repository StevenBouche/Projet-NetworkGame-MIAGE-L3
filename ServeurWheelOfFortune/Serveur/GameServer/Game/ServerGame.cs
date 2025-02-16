﻿using Serveur.GameServer.Game.Helper;
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
using System.Net;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.Game
{
    class ServerGame : INotifyStateSocket, ISenderAtClient
    {
        ServerTCP network;
        Thread threadNetwork;
        GameEngine gameManager;
        Thread executeGame;
        protected ManualResetEvent allDone = new ManualResetEvent(false);
        String id;
        Boolean interrupt = false;

        public int GetNbCurrentPlayer { get => gameManager.listPlayers.Count;  }

        HelperLobby helperLobby;

        Boolean firstExec = true;
        String ip;

        public ServerGame(String id, int port, String ip) 
        {
            this.id = id;
            network = new ServerTCP(port,this);
            gameManager = new GameEngine(this,ref allDone);
            helperLobby = new HelperLobby(ref gameManager,ref network,ref allDone);
            InitEventAndStartServerTCP();
            StartThreadNetwork();
            this.ip = ip;
        }

        public ServerGameInfo GetInfo()
        {

            ServerGameInfo srv = new ServerGameInfo()
            {
                addr = ip, //todo change
                port = this.network.Port,
                name = this.id,
                nbPlayerMax = 3,
                nbPlayerCurrent = this.GetNbCurrentPlayer
            };
            return srv;
        }

        private void InitEventAndStartServerTCP()
        {

            initLobby();

            //event game choice client
            network.OnEvent<String>(ProtocolEventsTCP<String>.PROPOSALRESPONSE, OnProposalResponse);
            network.OnEvent<String>(ProtocolEventsTCP<String>.ASKFORALETTER, OnLetterProposed);
            network.OnEvent<ChoiceStep>(ProtocolEventsTCP<ChoiceStep>.CHOICESTEP, OnChoiceStep);

            network.OnEvent<FinalLetters>(ProtocolEventsTCP<FinalLetters>.ASKFORFINALLETTER, OnReceiveAskForALetter );
            network.OnEvent<Proposal>(ProtocolEventsTCP<Proposal>.ASKFORFINALPROPOSITION, OnReceiveAskForFinalProposition);
            network.OnEvent<String>(ProtocolEventsTCP<String>.ASKFORIDPLAYER, OnAskForIdPlayer);
       
        }

        private void StartThreadNetwork()
        {
            //Start thread network
            threadNetwork = new Thread(new ThreadStart(network.Run));
            threadNetwork.Start();
        }

        private void initLobby()
        {
            //event lobby
            network.OnEvent<String>(ProtocolEventsTCP<String>.IDENTITY, helperLobby.OnIdentityReceived);
            network.OnEvent<Boolean>(ProtocolEventsTCP<Boolean>.NOTIFYPLAYERREADY, helperLobby.OnReadyReceived);
        }

        private void OnAskForIdPlayer(string obj, string id)
        {
            gameManager.NotifyReceivePlayer(obj, id);
        }

        private void OnReceiveAskForFinalProposition(Proposal obj, string id)
        {
            gameManager.NotifyReceivePlayer(obj, id);
        }

        private void OnReceiveAskForALetter(FinalLetters obj, string id)
        {
            gameManager.NotifyReceivePlayer(obj, id);
        }

        private void OnChoiceStep(ChoiceStep obj, string id)
        {
            gameManager.NotifyReceivePlayer(obj, id);
        }

        private void OnLetterProposed(String letter, String id)
        {
            gameManager.NotifyReceivePlayer(letter, id);
        }

        private void OnProposalResponse(String obj, String id)
        {
            gameManager.NotifyReceivePlayerProposal(obj, id);
        }

        public void Run()
        {

            try
            {
                Execute();
            }
            catch (ThreadInterruptedException)
            {
                interrupt = true;
                gameManager.gameState = GameState.STOP;
                allDone.Set();

            }
            
        }

        private void Execute()
        {
            if (interrupt) return;

            resetServer();

            gameManager.gameState = GameState.WAITING_PLAYER;

            while (gameManager.gameState != GameState.STARTED)
            {
                allDone.Reset();
                allDone.WaitOne();
                if (gameManager.gameState == GameState.STOP) return;
            }

            
            notifyGameIsReady();

            //execute game in other thread to cancel game if we want
            executeGame = new Thread(new ThreadStart(gameManager.Play));
            executeGame.Start(); //start play

            // reveiller par le threaD game quand fini ou stop
            Boolean running = true;
            while (running) // BUG MYSTIC
            {
                allDone.Reset();
                allDone.WaitOne();
                if (gameManager.gameState == GameState.FINISHED || gameManager.gameState == GameState.STOP) running = false;
            }

            if (executeGame.IsAlive) executeGame.Interrupt(); // to cancel thread
            executeGame.Join();

            // TODO HERE
            if (gameManager.listPlayers.Count != 0) // if player last game remove it, to waiting new player
            {
                Dictionary<String, Joueur> clone = new Dictionary<string, Joueur>(gameManager.listPlayers); //clone list for iterate
                foreach (KeyValuePair<String, Joueur> elem in clone)
                {
                    network.disconnectClient(elem.Value.id);
                }
            }

            while (gameManager.listPlayers.Count > 0)
            {
                allDone.Reset();
                allDone.WaitOne();
            }

            firstExec = false;
            //reset gameManager ?

            //STOP SERVER or RESET SERVER or WANT REPLAY GAME WITH SAME PLAYER ?
            Execute(); // RUN ALORS ?
        }

        private void resetServer()
        {
            lock (this)
            {
                if (!firstExec)
                {
                    network.ClearEvent();
                    gameManager = new GameEngine(this, ref allDone);
                    helperLobby = new HelperLobby(ref gameManager, ref network, ref allDone);
                    InitEventAndStartServerTCP();
                }
            }
        }

        public void OnConnect(string id)
        {
            lock (this)
            {
                if (gameManager.listPlayers.Count == 3)
                {
                    network.disconnectClient(id);
                }
            }
        }

        public void OnDisconnect(string id)
        {
            lock (this)
            {
                Debug.WriteLine("(TCP exchange) On Disconnect player : " + id);
                gameManager.RemovePlayer(id);
            }
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