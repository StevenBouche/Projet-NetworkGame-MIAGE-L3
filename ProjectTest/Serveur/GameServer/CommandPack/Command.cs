using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    public abstract class Command<GameEngine>
    {
        private GameEngine context;
        private CommandManager commandManager;

        public Command(GameEngine context, CommandManager manager) {
            this.context = context;
            this.commandManager = manager;
        }

        public void Trigger()
        {
            this.onExecute();
        }

        public abstract void onExecute();

        public GameEngine Context { get => context; set => context = value; }
    }
}
