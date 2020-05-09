using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Enigma;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandAskForALetter : CommandReceiverClient<String>
    {

        private int nb;

        public CommandAskForALetter(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            SendALetterAsked(); // notify client he need choice a letter 

            WaitReceiveClient(); // waiting choice player
             
            // if enigma containe letter and is not a vowel and is not already bought
            if (Context.CurrentEnigma.label.Contains(Data) && !EnigmePool.isAVowel(char.Parse(Data)) && !Context.LetterIsAlreadyBuy(Data))
            {
                char d = char.Parse(Data);
                Context.letterBuyInARound.Add(d); 
                this.nb = EnigmePool.GetNbOfOccurencesInEnigma(Context.CurrentEnigma.label, d);
                if(this.nb == 0)
                {
                    throw new Exception(" ERREUR : contenu dans la phrase, pas une voyelle ni deja acheter mais Conciderer comme bonne proposition");
                }
                SendGoodLetter();
                commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager, nb));
            }
            else
            {
                Context.endTurn = true;
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

       
    }
}