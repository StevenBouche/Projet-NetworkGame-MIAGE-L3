using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Linq;

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

            Context.stateLoop = EnumStateGameLoop.QUICKROUND;

            sendQuickEnigma();
            Console.WriteLine("\n En attente du gagnant de l'enigme rapide");

            while (!e.label.Equals(Data))
            {
                if(idClient != null)
                {
                    Proposal p = new Proposal(idClient, Data);
                    SendClientBadResponse(p);
                }
                Data = null;
                WaitReceiveClient();
            }
            
            Console.WriteLine("\n Reponse valide par : " + this.idClient + "\n");
            
            SendGoodResponse();
        }

        private void SendGoodResponse()
        {
            Context.CurrentPlayer = Context.listPlayers[this.idClient];
            Context.CurrentPosPlayer = Context.listIdPlayers.IndexOf(this.idClient);

            commandManager.TriggerAddRoundMoneyToPlayer(this.idClient, 500);
            Proposal p = new Proposal(idClient, Data);
            SendClientGoodResponse(p);
            SendNotifyCurrentPlayer();
        }

        private void SendClientBadResponse(Proposal p)
        {
            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.BADPROPOSALRESPONSE.eventName,
                data = p
            };
            Context.SendAllClient(msg);
            idClient = null;
        }

        private void SendClientGoodResponse(Proposal p)
        {
            PacketMessage<Proposal> msg = new PacketMessage<Proposal>()
            {
                evt = ProtocolEventsTCP<Proposal>.GOODPROPOSALRESPONSE.eventName,
                data = p
            };
            Context.SendAllClient(msg);
        }

        private void SendNotifyCurrentPlayer()
        {
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.NOTIFYCURRENTPLAYER.eventName,
                data = idClient
            };

            Context.SendAllClient(msg);
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
