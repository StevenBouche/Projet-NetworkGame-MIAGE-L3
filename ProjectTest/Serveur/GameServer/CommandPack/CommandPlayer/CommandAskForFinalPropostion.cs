using Serveur.GameServer.CommandPack.ReceiverNetwork;
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
    public class CommandAskForFinalPropostion : CommandReceiverClient<Proposal>
    {
        public CommandAskForFinalPropostion(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            SendAskForProposal();

            WaitReceiveClient();

            checkProposal();
        }

        private void checkProposal()
        {
            Enigme e = Context.CurrentEnigma;

            if (e.label.Equals(Data.proposal))
            {
                Context.listPlayers.TryGetValue(idClient, out Joueur j);
                j.cagnotte.Montant_Total += Context.wheel.CurrentCase.valeur;
                commandManager.TriggerUpdateMoney();
                SendFinalValue();
            }
            else
            {
                SendFinalValue();
            }
        }

        private void SendFinalValue()
        {
            PacketMessage<int> msg = new PacketMessage<int>()
            {
                evt = ProtocolEventsTCP<Proposal>.SENDFINALVALUE.eventName,
                data = Context.wheel.CurrentCase.valeur
            };

            Context.SendAllClient(msg);
        }

        private void SendAskForProposal()
        {
            String s = "Proposez une énigme, vous avez 10 secondes";
            Proposal p = new Proposal(idClient, s);

            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.ASKFORFINALPROPOSITION.eventName,
                data = p
            };

            Context.SendClient(msg, idClient);
        }
    }
}
