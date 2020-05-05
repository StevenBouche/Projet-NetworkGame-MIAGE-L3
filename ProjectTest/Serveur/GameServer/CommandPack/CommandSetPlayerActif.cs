using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    public class CommandSetPlayerActif : Command<GameEngine>
    {
        public CommandSetPlayerActif(GameEngine context, CommandManager manager) : base(context, manager)
        {

        }
        public override void onExecute()
        {
            //Un joueur aléatoire découvre l'énigme, il prend la main
            Random r = new Random();
            int RandParam = r.Next(3);
            String id = Context.listIdPlayers.ToArray()[RandParam];
            Context.CurrentPlayer = Context.listPlayers[id];

            Context.listIdPlayers.RemoveAt(RandParam);
            Context.listIdPlayers.Insert(0, id);
            Context.CurrentPosPlayer = 0;
            

            Console.WriteLine("\n Joueur actif : " + Context.CurrentPlayer.nom);
        }
    }
}
