using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
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
