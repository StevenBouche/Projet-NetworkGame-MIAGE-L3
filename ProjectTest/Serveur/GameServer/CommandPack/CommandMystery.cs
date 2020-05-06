using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandMystery : Command<GameEngine>
    {
        public CommandMystery(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            Context.wheel.UnveiledMysteryCase(Context.wheel.CurrentCase);
            if(Context.wheel.CurrentCase.valeur != 0)
            {
                commandManager.AddCashCommand();
            }
            else
            {
                commandManager.AddPassCommand();
            }
        }
    }
}
