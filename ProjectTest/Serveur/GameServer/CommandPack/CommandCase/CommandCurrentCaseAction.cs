using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandCurrentCaseAction : Command<GameEngine>
    {
        public CommandCurrentCaseAction(GameEngine game, CommandManager CM) : base(game, CM) { }

        public override void onExecute()
        {
            
            commandManager.TriggerCommand(CaseToAction.getCommand<GameEngine>(Context.wheel.CurrentCase.type, Context, commandManager));
            
        }
    }
}

