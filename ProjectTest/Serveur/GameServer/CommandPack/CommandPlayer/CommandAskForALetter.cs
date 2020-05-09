using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandAskForALetter : CommandReceiverClient<String>
    {
        public CommandAskForALetter(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            SendALetterAsked(); // notify client he need choice a letter 

            WaitReceiveClient(); // waiting choice player
             
            // if enigma containe letter and is not a vowel and is not already bought
            if (Context.CurrentEnigma.label.Contains(Data) && !isAVowel(char.Parse(Data)) && !Context.LetterIsAlreadyBuy(Data))
            {
                char d = char.Parse(Data);
                Context.letterBuyInARound.Add(d); // Add letter to already buy TODO : test char.Parse(Data)
                this.nbOfOccurrences = GetNbOfOccurencesInEnigma(Context.CurrentEnigma.label, d); //TODO return 0 but 1 H les ho...
                SendGoodLetter();
                commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager, nbOfOccurrences));
            }
            else
            {
                SendBadLetter();
                Console.WriteLine("La lettre n'est pas contenue dans l'enigme ou est une voyelle, c'est au joueur suivant \n");
            }
        }

        private void SendGoodLetter()
        {
            Proposal p = new Proposal(idClient, Data);

            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.GOODPROPOSALLETTER.eventName,
                data = p 
            };

            Context.SendAllClient(msg);
        }

        private void SendBadLetter()
        {
            Proposal p = new Proposal(idClient, Data);

            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.BADPROPOSALLETTER.eventName,
                data = p
            };

            Context.SendAllClient(msg);
        }

        private void SendALetterAsked()
        {
            String id = Context.CurrentPlayer.id;
            String str = "Entrez une consonne";
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.ASKFORALETTER.eventName,
                data = str
            };

            Context.SendClient(msg, id);
        }

        private int GetNbOfOccurencesInEnigma(String label, char letter)
        {
            int count = 0;

            for(int i = 0; i < label.Length; i++)
            {
                if(label[i] == letter)
                {
                    count++;
                }
            }
            return count;
        }

        private Boolean isAVowel(char letter)
        {
            var vowels = new HashSet<char> { 'A', 'E', 'I', 'O', 'U', 'Y' };

            if (vowels.Contains(letter))
            {
                return true;
            }
            return false;
        }
    }
}
