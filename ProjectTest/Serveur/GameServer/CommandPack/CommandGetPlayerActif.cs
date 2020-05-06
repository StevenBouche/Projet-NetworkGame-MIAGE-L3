using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandGetPlayerActif : Command<GameEngine>
    {
        public CommandGetPlayerActif(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            Console.WriteLine("Joueur Actif : " + Context.CurrentPlayer.id);
        }
    }
}
