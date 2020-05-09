using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandWheel
{
    class CommandWheelTurnFinal : Command<GameEngine>
    {
        public CommandWheelTurnFinal(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            Random r = new Random();
            Context.wheel.Fill_Final();
            Context.wheel.CurrentCase = Context.wheel.GetWheelCases(r.Next(10)); //TODO Check if not out bound

        }
    }
}
