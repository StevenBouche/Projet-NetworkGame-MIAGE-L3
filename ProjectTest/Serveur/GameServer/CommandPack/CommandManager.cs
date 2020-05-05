using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandManager
    {

        List<Command<GameEngine>> queue;
        GameEngine engine;

        public CommandManager(GameEngine context)
        {
            queue = new List<Command<GameEngine>>();
            engine = context;
        }
           
        public void TriggerCommand(Command<GameEngine> cmd)
        {
            queue.Add(cmd);
            cmd.Trigger();
        }

        public void AddCashCommand()
        {
            this.TriggerCommand(new CommandCash(engine));
        }

        public void AddPassCommand()
        {
            this.TriggerCommand(new CommandPass(engine));
        }
    }
}
