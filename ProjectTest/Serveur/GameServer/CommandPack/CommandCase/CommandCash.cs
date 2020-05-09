using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Text;


namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandCash : CommandCase
    {
        public CommandCash(GameEngine context, CommandManager manager) : base(context,manager) { }

        public CommandCash(GameEngine context, CommandManager manager, int nb) : base(context, manager, nb) 
        {

        }

        public override void onExecute() 
        {
            if(nb != -1)
            {
                Context.CurrentPlayer.cagnotte.Montant_Manche += Context.wheel.CurrentCase.valeur * nb;
            }
            else
            {
                Context.CurrentPlayer.cagnotte.Montant_Manche += Context.wheel.CurrentCase.valeur;
            }
            commandManager.TriggerUpdateMoney();

        }
    }
}
