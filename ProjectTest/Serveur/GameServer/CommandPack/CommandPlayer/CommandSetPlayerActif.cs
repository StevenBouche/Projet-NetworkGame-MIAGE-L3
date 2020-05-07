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
            /*  //Un joueur aléatoire découvre l'énigme, il prend la main
              Random r = new Random();
              int RandParam = r.Next(3);
              String id = Context.listIdPlayers.ToArray()[RandParam];
              Context.CurrentPlayer = Context.listPlayers[id];
              Context.CurrentPosPlayer = RandParam;
              */

            /*Context.listIdPlayers.RemoveAt(RandParam);
            Context.listIdPlayers.Insert(0, id);
            Context.CurrentPosPlayer = 0*/

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
            Context.CurrentPlayer = Context.listPlayers[this.idClient];
            //bien recu les données

        }

        private void SendClientBadResponse(String id)
        {
            //todo
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
            Context.SendAllClient(msg);
            Context.gameEnigmaPool.Remove(e.category);
        }

    }
}
