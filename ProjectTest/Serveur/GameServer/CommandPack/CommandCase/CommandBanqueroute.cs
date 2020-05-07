using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandBanqueroute : Command<GameEngine>
    {

        public CommandBanqueroute(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            Context.CurrentPlayer.cagnotte.Montant_Manche = 0;
        }
    }
}
