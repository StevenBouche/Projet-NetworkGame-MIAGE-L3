using Serveur.GameServer.CommandPack;
using Serveur.GameServer.GameModel;
using Share.Network.Message.modele;
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

namespace Serveur.GameServer.Game
{
    public class GameEngine
    {
        //Hold On

        // public Model m { get; }

        public Roue wheel { get; set; }

        public Dictionary<String, Joueur> listPlayers;

        public List<String> listIdPlayers;

        public GameLoop gameLoop;

        public Joueur CurrentPlayer { get; set; }

        public int CurrentPosPlayer = 0;
        
        CommandManager CM;

        public delegate void notifyPlayerJoined(String id, ListPlayerGame l);
        public delegate void notifyPlayerLeaved(String id, ListPlayerGame l);
        public notifyPlayerJoined callbackJoin;
        public notifyPlayerLeaved callbackLeave;

        public GameEngine()
        {
            wheel = new Roue();
            listPlayers = new Dictionary<string, Joueur>();
            listIdPlayers = new List<string>();
            CM = new CommandManager(this);
            gameLoop = new GameLoop(CM);
        }

        public void addCallbackPlayerJoined(notifyPlayerJoined x)
        {
            callbackJoin = x;
        }

        public void addCallbackPlayerLeaved(notifyPlayerLeaved x)
        {
            callbackLeave = x;
        }

        public void Play()
        {
            gameLoop.ExecuteGame();
        }

        public void AddPlayer(String id, String name)
        {
            listIdPlayers.Add(id);
            listPlayers.Add(id, new Joueur(id,name));
            callbackJoin(id, GetListOfPlayerLobbies());
        }

        public void RemovePlayer(string id)
        {
            //TODO
            //depend de si la game est lancer ou pas
            //si game lancer stop partie sinon remove
            listIdPlayers.Remove(id);
            listPlayers.Remove(id);
            callbackLeave(id, GetListOfPlayerLobbies());
        }

        public void GameFinish()
        {

        }

        private ListPlayerGame GetListOfPlayerLobbies()
        {
            ListPlayerGame l = new ListPlayerGame();

            foreach (KeyValuePair<String, Joueur> p in listPlayers)
            {
                l.listPlayers.Add(new PlayerGame()
                {
                    id = p.Key,
                    name = p.Value.nom
                });
            }
            return l;
        }
    }
}

