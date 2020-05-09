using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    class CommandHoldUp : CommandReceiverClient<String>
    {
        public CommandHoldUp(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            SendAClientIsNeeded();

            WaitReceiveClient();

            String id = (String)Data;

            Context.listPlayers.TryGetValue(id, out Joueur j);
            Context.CurrentPlayer.cagnotte.Montant_Manche += j.cagnotte.Montant_Manche;
            j.cagnotte.Montant_Manche = 0;
            commandManager.TriggerUpdateMoney();
        }


        public void SendAClientIsNeeded()
        {
            String id = Context.CurrentPlayer.id;
            String str = "Choisissez un joueur ";
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.ASKFORIDPLAYER.eventName,
                data = str
            };

            Context.SendClient(msg, id);
        }
    }
    
 }
