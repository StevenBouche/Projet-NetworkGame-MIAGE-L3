using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
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
            SendALetterAsked();


            WaitReceiveClient();

            if (Context.CurrentEnigma.label.Contains(Data) && isAVowel(char.Parse(Data))) //TODO Verifier que la consonne n'est pas déjà proposée
            {
                this.nbOfOccurrences = GetNbOfOccurencesInEnigma(Context.CurrentEnigma.label, Data);
                commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager, nbOfOccurrences));

            }
            else
            {
                Console.WriteLine("La lettre n'est pas contenue dans l'enigme ou est une voyelle, c'est au joueur suivant \n");
            }

        }

        private void SendALetterAsked()
        {
            String id = Context.CurrentPlayer.id;
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.ASKFORALETTER.eventName,
                data = "Entrez une consonne"
            };

            Context.SendClient(msg, id);
        }

        private int GetNbOfOccurencesInEnigma(String label, String letter)
        {
            int count = 0;

            for(int i = 0; i < label.Length; i++)
            {
                if(label[i].Equals(letter))
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
