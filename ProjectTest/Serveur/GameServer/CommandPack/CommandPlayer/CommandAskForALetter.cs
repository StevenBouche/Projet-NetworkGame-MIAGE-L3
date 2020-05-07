using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
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

            if (Context.CurrentEnigma.label.Contains(Data))
            {
                this.nbOfOccurrences = GetNbOfOccurencesInEnigma(Context.CurrentEnigma.label, Data);
                commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager, nbOfOccurrences));
            }
            else
            {
                Console.WriteLine("La lettre n'est pas contenue dans l'enigme, c'est au joueur suivant \n");
            }

        }

        private void SendALetterAsked()
        {
            String id = Context.CurrentPlayer.id;
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.ASKFORALETTER.eventName,
                data = "Please enter a letter"
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
    }
}
