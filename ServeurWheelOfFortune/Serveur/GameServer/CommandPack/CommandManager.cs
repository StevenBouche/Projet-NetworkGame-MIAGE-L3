﻿using Serveur.GameServer.CommandPack.CommandCase;
using Serveur.GameServer.CommandPack.CommandPlayer;
using Serveur.GameServer.CommandPack.CommandWheel;
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

        public void TriggerHandleTurn()
        {
            this.TriggerCommand(new CommandHandleTurn(engine,this));
        }

        public void TriggerHoldUp()
        {
            this.TriggerCommand(new CommandHoldUp(engine, this));
        }

        public void TriggerWheelTurn()
        {
            this.TriggerCommand(new CommandWheelTurn(engine, this));
        }

        public void TriggerAddRoundMoneyToPlayer(String idClient, int amount)
        {
            this.TriggerCommand(new CommandAddRoundMoneyToPlayer(engine, this, amount, idClient));
        }

        public void TriggerActivePlayerWantBuyAVoy(string idClient, string voy)
        {
            this.TriggerCommand(new CommandBuyAVoy(engine, this,voy, idClient));
        }

        public void TriggerNextPlayer()
        {
            this.TriggerCommand(new CommandNextPlayer(engine, this));
        }

        public void AddCashCommand()
        {
            this.TriggerCommand(new CommandCash(engine,this));
        }

        public void TriggerWheelTurnFinal()
        {
            this.TriggerCommand(new CommandWheelTurnFinal(engine, this));
        }

        public void TriggerChangeWheel()
        {
            this.TriggerCommand(new CommandResetWheel(engine, this));
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

        public void TriggerCurrentEnigma()
        {
            this.TriggerCommand(new CommandSetCurrentEnigma(engine, this));
        }

        public void TriggerAskForFinalLetters()
        {
            this.TriggerCommand(new CommandAskForFinalLetters(engine, this));
        }

        public void TriggerAskForFinalPropostion()
        {
            this.TriggerCommand(new CommandAskForFinalPropostion(engine, this));
        }

        public void TriggerSetFinalEnigma()
        {
            this.TriggerCommand(new CommandSetFinalEnigma(engine, this));
        }

        public void TriggerUpdateMoney()
        {
            this.TriggerCommand(new CommandUpdateMoney(engine, this));
        }

        public void TriggerCommand(Command<GameEngine> cmd)
        {
            stack.Push(cmd);
            cmd.Trigger();
        }

        public void OnEndExecute<T>(Command<T> command) where T : GameEngine
        {
            stack.TryPop(out Command<GameEngine> cmdPop);
            if (cmdPop == null)
            {
                Console.WriteLine("ERROR ON STACK COMMAND MANAGER");
            }
        }

        public void NotifyLastCommandReceivePlayer<T>(T obj, string id)
        {
            stack.TryPeek(out Command<GameEngine> cmd);
            
            if(cmd != null)
            {
                CommandReceiverClient<T> cmdSafeCast = cmd as CommandReceiverClient<T>;
                if(cmdSafeCast != null) cmdSafeCast.NotifyReceiveClient(obj, id);
            }
            else
            {
                Console.WriteLine("reponse but stack have not cmd actually");
            }

        }

        public void NotifyLastCommandIsSetPlayerActif<T>(T obj, String id)
        {
            stack.TryPeek(out Command<GameEngine> cmd);
            if (cmd != null)
            {
                CommandSetPlayerActif cmdSafeCast = cmd as CommandSetPlayerActif;
                String str = obj as String;
                if(cmdSafeCast != null && str !=null) cmdSafeCast.NotifyReceiveClient(str, id);
            }
            else
            {
                Console.WriteLine("reponse but stack have not cmd actually");
            }

        }

    }

}
