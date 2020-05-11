using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Protocol;
using System;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    class CommandNextPlayer : Command<GameEngine>
    {
        public CommandNextPlayer(GameEngine game, CommandManager CM) : base(game, CM)
        {

        }
        public override void onExecute()
        {
            int pos = Context.CurrentPosPlayer;

            //TODO VERIFIER BUG ? 

            if (pos + 1 >= Context.listIdPlayers.Count)
            {
                pos = 0;
                Context.CurrentPosPlayer = 0;
                Context.listPlayers.TryGetValue(Context.listIdPlayers[pos], out Joueur j);
                Context.CurrentPlayer = j;

            }
            else
            {
                pos++;
                Context.CurrentPosPlayer++;
                Context.listPlayers.TryGetValue(Context.listIdPlayers[pos], out Joueur j);
                Context.CurrentPlayer = j;
            }

            SendNotifyNewCurrentPlayer();
        }

        private void SendNotifyNewCurrentPlayer()
        {
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.NOTIFYCURRENTPLAYER.eventName,
                data = Context.CurrentPlayer.id
            };

            Context.SendAllClient(msg);
        }

    }

}
