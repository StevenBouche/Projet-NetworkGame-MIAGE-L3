using Serveur.GameServer.CommandPack;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
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

        public GameState gameState;

        public delegate void notifyPlayerJoined(String id, ListPlayerGame l);
        public delegate void notifyPlayerLeaved(String id, ListPlayerGame l);

        public notifyPlayerJoined callbackJoin;
        public notifyPlayerLeaved callbackLeave;

        ISenderAtClient sender;

        public GameEngine(ISenderAtClient sender)
        {
            this.sender = sender;
            wheel = new Roue(false);
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
            this.gameState = GameState.FINISHED;
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

        public void NotifyReceivePlayer<T>(T obj, string id)
        {
            CM.NotifyLastCommandReceivePlayer(obj, id);
        }

        public void GameFinish()
        {

        }

        public ListPlayerGame GetListOfPlayerLobbies()
        {
            ListPlayerGame l = new ListPlayerGame();

            foreach (KeyValuePair<String, Joueur> p in listPlayers)
            {
                l.listPlayers.Add(new PlayerGame()
                {
                    id = p.Key,
                    name = p.Value.nom,
                    isReady = p.Value.isReady
                });
            }
            return l;
        }

       public void SendClient<T>(PacketMessage<T> msg, String idClient)
       {
            sender.SendClient(msg, idClient);
       }

        public void SendAllClient<T>(PacketMessage<T> msg)
        {
            sender.SendAllClientInGame(msg);
        }

        public void setReadyPlayer(Boolean state, String id)
        {
            listPlayers.TryGetValue(id, out Joueur j);

            if(j != null)
            {
                j.isReady = state;
            }
        }
    }
}

