using Serveur.GameServer.Game;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    public class CommandManager
    {
        ConcurrentStack<Command<GameEngine>> stack;
        GameEngine engine;

        public CommandManager(GameEngine context)
        {
            stack = new ConcurrentStack<Command<GameEngine>>();
            engine = context;
        }
           
        public void TriggerCommand(Command<GameEngine> cmd)
        {
            stack.Push(cmd);
            cmd.Trigger();
        }

        public void AddCashCommand()
        {
            this.TriggerCommand(new CommandCash(engine,this));
        }

        public void AddPassCommand()
        {
            this.TriggerCommand(new CommandPass(engine,this));
        }

        public void OnEndExecute(Command<GameEngine> cmd)
        {
            
            stack.TryPop(out Command<GameEngine> cmdPop);
            if(cmdPop != null)
            {
                //todo
            }
        }
    }
}
