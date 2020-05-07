using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Protocol;
using System;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandAskForALetter : Command<GameEngine>
    {
        public CommandAskForALetter(GameEngine context, CommandManager CM) : base(context, CM) { }

        public override void onExecute()
        {
            SendALetterAsked();
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
    }
}
