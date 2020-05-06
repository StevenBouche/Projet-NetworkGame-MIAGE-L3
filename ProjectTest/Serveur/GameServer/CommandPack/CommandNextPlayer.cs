using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using System;

namespace Serveur.GameServer.CommandPack
{
    class CommandNextPlayer : Command<GameEngine>
    {
        public CommandNextPlayer(GameEngine game, CommandManager CM) : base(game, CM)
        {

        }
        public override void onExecute()
        {
            int pos = Context.CurrentPosPlayer;

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
            
        }
    }
}
