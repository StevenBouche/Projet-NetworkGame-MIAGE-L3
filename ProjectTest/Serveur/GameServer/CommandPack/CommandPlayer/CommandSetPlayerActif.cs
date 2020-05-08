using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Enigma;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Text;
using System.Threading;
using System.Timers;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandSetPlayerActif : CommandReceiverClient<String>
    {

        private Enigme e;

        public CommandSetPlayerActif(GameEngine context, CommandManager manager) : base(context, manager)
        {
            
        }

        public override void onExecute()
        {
            sendQuickEnigma();
            Console.WriteLine("\n En attente du gagnant de l'enigme rapide");

            while (!e.label.Equals(Data))
            {
                if(idClient != null)
                {
                    SendClientBadResponse(idClient);
                }
                Data = null;
                WaitReceiveClient();
            }
            
            Console.WriteLine("\n Reponse valide par : " + this.idClient + "\n");
            

            SendGoodResponse();
            //bien recu les données

        }

        private void SendGoodResponse()
        {
            //Sets the current player with the one that has won the quick enigma
            //Sending an event to notify this player has the upper hand
            Context.CurrentPlayer = Context.listPlayers[this.idClient];
            Context.CurrentPosPlayer = Context.listIdPlayers.IndexOf(this.idClient);

            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.NOTIFYCURRENTPLAYER.eventName,
                data = idClient
            };

            Context.SendAllClient(msg);

            throw new NotImplementedException();

        }

        private void SendClientBadResponse(String id)
        {
            
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.BADPROPOSALRESPONSE.eventName,
                data = id
            };
            Context.SendAllClient(msg);
            idClient = null;
        }

        public override void NotifyReceiveClient(String data, string id)
        {
            //faire autre truc
            if (Data == null)
            {
                base.NotifyReceiveClient(data, id);
            }
        }

        private void sendQuickEnigma()
        {
            e = Context.gameEnigmaPool.Values.ToList<Enigme>()[0];
            PacketMessage<Enigme> msg = new PacketMessage<Enigme>()
            {
                evt = ProtocolEventsTCP<Enigme>.ACTIONENIGMERAPIDE.eventName,
                data = e
            };

            Console.WriteLine(e.toString());
            Context.SendAllClient(msg);
            Context.gameEnigmaPool.Remove(e.category);
        }

    }
}
