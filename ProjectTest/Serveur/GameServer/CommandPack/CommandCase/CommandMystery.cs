using Serveur.GameServer.CommandPack.CommandPlayer;
using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandMystery : CommandCase
    {
        public CommandMystery(GameEngine context, CommandManager CM) : base(context, CM) { }

        public CommandMystery(GameEngine context, CommandManager CM, int nb) : base(context, CM,nb) { }
        public override void onExecute()
        {
            Context.wheel.UnveiledMysteryCase(Context.wheel.CurrentCase);
            if(Context.wheel.CurrentCase.valeur != 0)
            {
                commandManager.TriggerCommand(new CommandAskForALetter(Context, commandManager));
            }
            else
            {
                commandManager.AddPassCommand();
            }
        }
    }
}
