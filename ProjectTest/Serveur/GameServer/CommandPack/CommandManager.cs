using Serveur.GameServer.CommandPack.ReceiverNetwork;
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

        public void OnEndExecute<T>(Command<T> command) where T : GameEngine
        {
            stack.TryPop(out Command<GameEngine> cmdPop);
            if (cmdPop != null)
            {
                //todo
            }
           
        }

        public void TriggerCommand(Command<GameEngine> cmd)
        {
            stack.Push(cmd);
            cmd.Trigger();
        }

        public void TriggerWheelTurn()
        {
            this.TriggerCommand(new CommandWheelTurn(engine, this));
        }

        public void TriggerNextPlayer()
        {
            this.TriggerCommand(new CommandNextPlayer(engine, this));
        }

        public void AddCashCommand()
        {
            this.TriggerCommand(new CommandCash(engine,this));
        }

        public void AddPassCommand()
        {
            this.TriggerCommand(new CommandPass(engine,this));
        }

        public void TriggerCurrentPlayer()
        {
            this.TriggerCommand(new CommandSetPlayerActif(engine, this));
        }

        public void TriggerCurrentCaseAction()
        {
            this.TriggerCommand(new CommandCurrentCaseAction(engine, this));
        }

        public void TriggerActivePlayer()
        {
            this.TriggerCommand(new CommandGetPlayerActif(engine, this));
        }

        public void NotifyLastCommandReceivePlayer<T>(T obj, string id)
        {
            stack.TryPop(out Command<GameEngine> cmd);
            if(cmd != null)
            {
                CommandReceiverClient<T> cmdSafeCast = cmd as CommandReceiverClient<T>;
                cmdSafeCast.NotifyReceiveClient(obj, id);
                stack.Push(cmd);
            }
            else
            {
                Console.WriteLine("reponse but stack have not cmd actually");
            }

        }

    }

}
