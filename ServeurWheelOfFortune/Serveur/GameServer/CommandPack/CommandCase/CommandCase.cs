using Serveur.GameServer.Game;

namespace Serveur.GameServer.CommandPack.CommandCase
{
    public abstract class CommandCase : Command<GameEngine>
    {
        protected int nb;

        public CommandCase(GameEngine game, CommandManager CM) : base(game, CM) {
            nb = -1;
        }

        public CommandCase(GameEngine game, CommandManager CM, int nbOfOccurrences) : base(game, CM)
        {
            this.nb = nbOfOccurrences;
        }
    }
}
