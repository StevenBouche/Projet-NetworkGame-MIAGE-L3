using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Text;


namespace Serveur.GameServer.CommandPack
{
    class CommandCash : Command<GameEngine>
    {
        public CommandCash(GameEngine context, CommandManager manager) : base(context,manager) { }
        public override void onExecute()
        {
            Context.CurrentPlayer.cagnotte.Montant_Total += Context.wheel.CurrentCase.valeur;
            Context.CurrentPlayer.cagnotte.Montant_Manche += Context.wheel.CurrentCase.valeur;
        }
    }
}
