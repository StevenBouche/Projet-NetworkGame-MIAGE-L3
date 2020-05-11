using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandAddRoundMoneyToPlayer : Command<GameEngine>
    {
        int amount;
        String id;

        public CommandAddRoundMoneyToPlayer(GameEngine game, CommandManager CM, int amount, String id) : base(game, CM) 
        {
            this.amount = amount;
            this.id = id;
        }

        public override void onExecute()
        {

            Context.listPlayers.TryGetValue(id, out Joueur j);
            j.cagnotte.Montant_Manche += amount;

            commandManager.TriggerUpdateMoney();
        }
    }
}
