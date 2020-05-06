using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandResetWheel : Command<GameEngine>
    {
        public CommandResetWheel(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            int round = Context.gameLoop.roundsNb;
            Context.wheel.getWheelCases(21).valeur = (int) Enum.GetValues(typeof(Cash)).GetValue(7 + round);
            Context.wheel.getWheelCases(22).type = (TypeCase) Enum.GetValues(typeof(TypeCase)).GetValue(4 + round);

        }
    }
    
}
