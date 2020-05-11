using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandPass : CommandCase
    {
        public CommandPass(GameEngine context, CommandManager manager) : base(context,manager) { }
        public override void onExecute()
        {
            Context.endTurn = true;
        }
    }
}
