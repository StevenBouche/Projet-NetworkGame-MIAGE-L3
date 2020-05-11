using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    class CommandVoyage : CommandCase
    {
        Voyage data;
        public CommandVoyage(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            data = new Voyage();
            data.GetRandomVoyage();
            SendVoyage();
        }

        private void SendVoyage()
        {
            String voyage = data.Pays_Destination;

            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.SENDVOYAGEWIN.eventName,
                data = voyage
            };

            Context.SendAllClient(msg);
        }
    }
}
