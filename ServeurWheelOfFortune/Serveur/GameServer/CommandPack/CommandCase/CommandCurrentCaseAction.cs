﻿using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandCurrentCaseAction : CommandCase
    {

        public CommandCurrentCaseAction(GameEngine game, CommandManager CM) : base(game, CM) { }

        public CommandCurrentCaseAction(GameEngine game, CommandManager CM, int nbOfOccurrences) : base(game, CM,nbOfOccurrences) 
        {

        }

        public override void onExecute()
        {
            if(nb <= 0)
            {
                commandManager.TriggerCommand(CaseToAction.getCommand<GameEngine>(Context.wheel.CurrentCase.type, Context, commandManager));
            }
            else
            {
                commandManager.TriggerCommand(CaseToAction.getCommand<GameEngine>(Context.wheel.CurrentCase.type, Context, commandManager, nb));
            }
            
        }
    }
}

