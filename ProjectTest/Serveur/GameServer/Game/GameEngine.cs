using Serveur.GameServer.CommandPack;
using Serveur.GameServer.GameModel;
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

        public GameEngine()
        {
            wheel = new Roue();
            listPlayers = new Dictionary<string, Joueur>();
            listIdPlayers = new List<string>();
            CM = new CommandManager(this);
            gameLoop = new GameLoop(CM);
            FillDemo();
        }

        public void Play()
        {
            gameLoop.ExecuteGame();
        }

        public void AddPlayer(String id)
        {
            listIdPlayers.Add(id);
            listPlayers.Add(id, new Joueur(id));
        }

        public void RemovePlayer(string id)
        {
            //t
            throw new NotImplementedException();
        }

        public void GameFinish()
        {

        }

        internal void FillDemo() 
        {
            this.AddPlayer("RAMON");
            this.AddPlayer("GILLES");
            this.AddPlayer("MICHEL");

        }
    }
}

