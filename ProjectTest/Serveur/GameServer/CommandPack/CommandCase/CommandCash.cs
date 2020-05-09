using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Text;


namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandCash : Command<GameEngine>
    {
        public CommandCash(GameEngine context, CommandManager manager) : base(context,manager) { }

        public CommandCash(GameEngine context, CommandManager manager, int nb) : base(context, manager) 
        {
            this.nbOfOccurrences = nb;
        }

        public override void onExecute() // TODO UPDATE ROUND MONEY
        {
            if(nbOfOccurrences != -1)
            {
                Context.CurrentPlayer.cagnotte.Montant_Manche += Context.wheel.CurrentCase.valeur * nbOfOccurrences;
            }
            else
            {
                Context.CurrentPlayer.cagnotte.Montant_Manche += Context.wheel.CurrentCase.valeur;
            }
            
        }
    }
}
