﻿using Serveur.GameServer.Enigma;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Net.NetworkInformation;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandBuyAVoy : Command<GameEngine>
    {

        String Data;
        String id;

        public CommandBuyAVoy(GameEngine context, CommandManager CM, String Data, String id) : base(context, CM) {
            this.Data = Data;
            this.id = id;
        }

        public override void onExecute()
        {

            // if enigma containe letter and is a vowel and is not already bought
            char d = char.Parse(Data);
            if (Context.CurrentEnigma.label.Contains(Data) && EnigmePool.isAVowel(char.Parse(Data)) && !Context.LetterIsAlreadyBuy(d))
            {
                if(Context.CurrentPlayer.cagnotte.Montant_Manche >= 150)
                {
                    Context.letterBuyInARound.Add(d); // Add letter to already buy TODO : test char.Parse(Data)
                    Context.CurrentPlayer.cagnotte.Montant_Manche -= 150;
                    SendGoodLetter();
                    SendUpdateMoney();
                }
                else
                {
                    Context.endTurn = true;
                    Console.WriteLine("Pas assez d'argent\n");
                }
               
            }
            else
            {
                Context.endTurn = true;
                SendBadLetter();
                Console.WriteLine("La lettre n'est pas contenue dans l'enigme ou est une consonne, c'est au joueur suivant \n");
            }
        }

        private void SendGoodLetter()
        {
            Proposal p = new Proposal(id, Data);

            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.GOODPROPOSALLETTER.eventName,
                data = p
            };

            Context.SendAllClient(msg);
        }

        private void SendBadLetter()
        {
            Proposal p = new Proposal(id, Data);

            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.BADPROPOSALLETTER.eventName,
                data = p
            };

            Context.SendAllClient(msg);
        }

        private void SendUpdateMoney()
        {
            commandManager.TriggerUpdateMoney();
        }
    }
}
