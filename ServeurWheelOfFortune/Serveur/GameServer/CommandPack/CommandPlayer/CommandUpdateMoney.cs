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
    class CommandUpdateMoney : Command<GameEngine>
    {
        public CommandUpdateMoney(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            DataMoneyInfo data = new DataMoneyInfo();
            foreach(KeyValuePair<String, Joueur> kv in Context.listPlayers)
            {
                data.AddInfo(new PlayerMoneyInfo(kv.Key, kv.Value.cagnotte.Montant_Manche, kv.Value.cagnotte.Montant_Total));
            }
            SendToClient(data);
        }

        public void SendToClient(DataMoneyInfo d)
        {
            PacketMessage<DataMoneyInfo> msg = new PacketMessage<DataMoneyInfo>()
            {
                evt = ProtocolEventsTCP<DataMoneyInfo>.UPDATEMONEYALL.eventName,
                data = d
            };
            Context.SendAllClient(msg);
        }
    }
}
