using Serveur.GameServer.Game;
using Serveur.LobbiesServer;
using Share.Network.Message.obj;
using shortid;
using System;
using System.Collections.Generic;
using System.Threading;

namespace Serveur.MainProgram
{
    class Launcher : IHandlerGame
    {
        Dictionary<String, GameServer.Game.ServerGame> gamesList;
        Dictionary<String, Thread> gamesListThread;

        Lobby gameLobby;
        Thread lobby;

        public Launcher()
        {
            gamesList = new Dictionary<string, GameServer.Game.ServerGame>();
            gamesListThread = new Dictionary<string, Thread>();
            gameLobby = new Lobby(this);
        }

        public DataServerGame GetDataServerGame()
        {
            DataServerGame data = new DataServerGame();
            foreach (KeyValuePair<String, ServerGame> pair in gamesList)
            {
                data.listServer.Add(pair.Value.GetInfo());
            }
            return data;
        }

        public void Run()
        {
            StartLobby();
            StartGames();

            lobby.Join();
            foreach(KeyValuePair<String,Thread> pair in gamesListThread)
            {
                pair.Value.Join();
            }
            Console.WriteLine("Program wheel of fortune finish");
        }

        private void StartGames()
        {
            int port = 11000;
            for (int i = 0; i < 2; i++)
            {
                Console.WriteLine("Create and start game on port "+port+i);
                String id = ShortId.Generate(true, true, 12);
                ServerGame sg = new ServerGame(id,port + i);
                Thread t = new Thread(new ThreadStart(sg.Run));
                gamesList.Add(id, sg);
                gamesListThread.Add(id, t);
                t.Start();
            }
        }

        private void StartLobby()
        {
            Console.WriteLine("Start Lobby thread");
            lobby = new Thread(new ThreadStart(gameLobby.Run));
            lobby.Start();
        }

    }
}
