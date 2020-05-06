using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandWheelTurn : Command<GameEngine>
    {
        public CommandWheelTurn(GameEngine game, CommandManager CM) : base (game, CM) { }
        
        public override void onExecute()
        {
            Random r = new Random();
            Context.wheel.CurrentCase = Context.wheel.getWheelCases(r.Next(23));

            Console.WriteLine("La case tombée est " + Context.wheel.CurrentCase.ToString());

            commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager));
        }
    }
}
