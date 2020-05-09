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
    public class CommandAskForFinalLetters : CommandReceiverClient<FinalLetters>
    {
        public CommandAskForFinalLetters(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {

            SendAskForFinalLetters(); // notify client he need choice 4 letters 

            WaitReceiveClient(); // waiting choice player

            if(Data.finalLetters.Count != 4)
            {
                Console.WriteLine("Mauvais nombre de caractères reçus");
                return;
            }

            foreach(char c in Data.finalLetters)
            {
                if (Context.CurrentEnigma.label.Contains(c))
                {
                    SendGoodLetter(c);
                }
                else
                {
                    SendBadLetter(c);
                }
            }

            commandManager.TriggerAskForFinalPropostion();
        }

        private void SendAskForFinalLetters()
        {
            String id = Context.CurrentPlayer.id;

            FinalLetters finalLetters = new FinalLetters();

            PacketMessage<FinalLetters> msg = new PacketMessage<FinalLetters>()
            {
                evt = ProtocolEventsTCP<FinalLetters>.ASKFORFINALLETTER.eventName,
                data = finalLetters
            };

            Context.SendClient(msg, id);
        }

        private void SendGoodLetter(char c)
        {
            ProposalLetter p = new ProposalLetter(idClient, c);

            PacketMessage<ProposalLetter> msg = new PacketMessage<ProposalLetter>()
            {
                evt = ProtocolEventsTCP<ProposalLetter>.GOODPROPOSALFINALLETTER.eventName,
                data = p
            };

            Context.SendAllClient(msg);
        }

        private void SendBadLetter(char c)
        {
            ProposalLetter p = new ProposalLetter(idClient, c);

            PacketMessage<ProposalLetter> msg = new PacketMessage<ProposalLetter>()
            {
                evt = ProtocolEventsTCP<ProposalLetter>.BADPROPOSALFINALLETTER.eventName,
                data = p
            };

            Context.SendAllClient(msg);
        }
    }
}
