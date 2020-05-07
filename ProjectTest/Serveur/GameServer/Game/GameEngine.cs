using Serveur.GameServer.CommandPack;
using Serveur.GameServer.Enigma;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using System;
using System.Collections.Generic;
using System.Linq;
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

        public Dictionary<Category, Enigme> gameEnigmaPool;

        public GameEngine(ISenderAtClient sender)
        {
            this.sender = sender;
            wheel = new Roue(false);
            listPlayers = new Dictionary<string, Joueur>();
            listIdPlayers = new List<string>();
            CM = new CommandManager(this);
            gameLoop = new GameLoop(CM);
            CreateGameEnigmaPool();
        }

        private void CreateGameEnigmaPool()
        {
            if(gameEnigmaPool.Count >= 9)
            {
                return;
            }

            Enigme e = GetARandomEnigma();

            if(!gameEnigmaPool.ContainsKey(e.category))
            {
                gameEnigmaPool.Add(e.category, e);
            }

            CreateGameEnigmaPool();
        }

        private Enigme GetARandomEnigma()
        {
            int size = EnigmePool.enigmePool.Count; //nb of category
            int r = new Random().Next(size); //random between 0 and nb of category
            int r2 = new Random().Next(EnigmePool.enigmePool.Values.ToList<List<Enigme>>()[r].Count); //random between 0 and nb of list<Enigme>.size on the specified category

            return EnigmePool.enigmePool.Values.ToList<List<Enigme>>()[r][r2];
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

