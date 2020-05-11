using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    public class CommandBonus : CommandCase
    {
        public CommandBonus(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            throw new NotImplementedException();
        }
    }
}
