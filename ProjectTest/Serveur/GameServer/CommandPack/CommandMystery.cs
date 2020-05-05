using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    public class CommandMystery : Command<GameEngine>
    {
        public CommandMystery(GameEngine engine, CommandManager cmd) : base(engine,cmd) { }
        public override void onExecute()
        {
            throw new NotImplementedException();
        }
    }
}
