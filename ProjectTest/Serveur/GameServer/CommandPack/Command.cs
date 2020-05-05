using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    public abstract class Command<GameEngine>
    {
        private GameEngine context;

        public Command(GameEngine context) {
            this.context = context;
        }

        public void Trigger()
        {
            this.onExecute();
        }

        public abstract void onExecute();

        public GameEngine Context { get => context; set => context = value; }
    }
}
