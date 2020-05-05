using Serveur.GameServer.Game;

namespace Serveur.GameServer.CommandPack
{
    public abstract class Command<T> where T : GameEngine
    {
        private T context;
        protected CommandManager commandManager;

        public Command(T context, CommandManager manager) {
            this.context = context;
            this.commandManager = manager;
        }

        public void Trigger()
        {
            this.onExecute();
            commandManager.OnEndExecute(this);
        }

        public abstract void onExecute();

        public T Context { get => context; set => context = value; }
    }
}
