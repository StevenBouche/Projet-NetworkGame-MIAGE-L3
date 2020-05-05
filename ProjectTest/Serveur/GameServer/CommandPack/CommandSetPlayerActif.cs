using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
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
            throw new NotImplementedException();
        }
    }
}
