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
        Dictionary<String, ServerGame> gamesList;
        Dictionary<String, Thread> gamesListThread;

        Lobby gameLobby;
        Thread lobby;

        int port = 11000;
        String ipAddr;

        public Launcher()
        {
            ipAddr = "51.210.12.245";
            gamesList = new Dictionary<string, ServerGame>();
            gamesListThread = new Dictionary<string, Thread>();
            gameLobby = new Lobby(this);
        }

        public void createNewGame()
        {
            lock (this)
            {
                Console.WriteLine("Create and start game on port " + port);
                String id = ShortId.Generate(true, true, 12);
                ServerGame sg = new ServerGame(id, port,ipAddr);
                Thread t = new Thread(new ThreadStart(sg.Run));
                gamesList.Add(id, sg);
                gamesListThread.Add(id, t);
                t.Start();
                port++;
            }
        }

        public void removeGame(string obj)
        {
            lock (this)
            {
                gamesListThread.TryGetValue(obj, out Thread t);
                if (t != null)
                {
                    if (t.IsAlive) t.Interrupt(); // to cancel thread
                    t.Join();
                    gamesListThread.Remove(obj);
                    gamesList.Remove(obj);
                }
            }
        }

        public DataServerGame GetDataServerGame()
        {
            lock (this)
            {
                DataServerGame data = new DataServerGame();
                foreach (KeyValuePair<String, ServerGame> pair in gamesList)
                {
                    data.listServer.Add(pair.Value.GetInfo());
                }
                return data;
            }
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
            Console.WriteLine("Create and start game on port "+port);
            String id = ShortId.Generate(true, true, 12);
            ServerGame sg = new ServerGame(id,port, ipAddr);
            Thread t = new Thread(new ThreadStart(sg.Run));
            gamesList.Add(id, sg);
            gamesListThread.Add(id, t);
            t.Start();
            port++;
        }

        private void StartLobby()
        {
            Console.WriteLine("Start Lobby thread");
            lobby = new Thread(new ThreadStart(gameLobby.Run));
            lobby.Start();
        }

    }
}
