using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandHoldUp : Command<GameEngine>
    {
        public CommandHoldUp(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            /*int index_hold_up = choix client event
            * Context.CurrentPlayer.cagnotte.Montant_Manche += Context.listPlayers[index_hold_up].cagnotte.Montant_Manche;
            * Context.listPlayers[index_hold_up].cagnotte.Montant_Manche = 0;
            */

        }
    
    }
}
