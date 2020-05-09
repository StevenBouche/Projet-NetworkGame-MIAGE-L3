using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.CommandPlayer;
using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandWheel
{
    class CommandWheelTurn : Command<GameEngine>
    {
        public CommandWheelTurn(GameEngine game, CommandManager CM) : base (game, CM) { }
        
        public override void onExecute()
        {
            Random r = new Random();
            Context.wheel.CurrentCase = Context.wheel.GetWheelCases(r.Next(23));

            SendCaseToClient(Context.CurrentPlayer.id);
          
            if(!Context.wheel.CurrentCase.type.isLetterNeeded)
            {
                Console.WriteLine("Je suis tombé sur banqueroute ou passe, pas de lettre");
                commandManager.TriggerCommand(new CommandCurrentCaseAction(Context, commandManager));
            }
            else
            {
                Console.WriteLine("Je suis tombé sur autre chose que banqueroute ou passe, je demande une lettre");
                commandManager.TriggerCommand(new CommandAskForALetter(Context, commandManager));
            }
            
        }

        private void SendCaseToClient(String id)
        {
            Case c = Context.wheel.CurrentCase;
            CaseInfo myCaseToSend = new CaseInfo(c.valeur, c.type.ToString());

            PacketMessage<CaseInfo> msg = new PacketMessage<CaseInfo>()
            {
                evt = ProtocolEventsTCP<int>.SENDCASE.eventName,
                data = myCaseToSend
            };

            Context.SendAllClient(msg);
        }
    }
}
