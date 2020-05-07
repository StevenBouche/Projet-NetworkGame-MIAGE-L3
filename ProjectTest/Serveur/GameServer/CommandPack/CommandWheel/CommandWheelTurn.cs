using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandWheel
{
    class CommandWheelTurn : CommandReceiverClient<String>
    {
        public CommandWheelTurn(GameEngine game, CommandManager CM) : base (game, CM) { }
        
        public override void onExecute()
        {
            Random r = new Random();
            Context.wheel.CurrentCase = Context.wheel.GetWheelCases(r.Next(23));

            SendCaseToClient(idClient);

            WaitReceiveClient();

            if (Context.CurrentEnigma.label)
          
            commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager));



        }

        private void SendCaseToClient(String id)
        {
            int value = Context.wheel.CurrentCase.valeur;
            PacketMessage<int> msg = new PacketMessage<int>()
            {
                evt = ProtocolEventsTCP<int>.SENDCASEVALUE.eventName,
                data = value
            };
            Context.SendAllClient(msg);
        }
    }
}
