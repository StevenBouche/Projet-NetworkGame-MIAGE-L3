using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandBanqueroute : CommandCase
    {

        public CommandBanqueroute(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            Context.endTurn = true;
            Context.CurrentPlayer.cagnotte.Montant_Manche = 0;
            commandManager.TriggerUpdateMoney();
        }
    }
}
