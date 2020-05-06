﻿using Serveur.GameServer.CommandPack;
using Serveur.GameServer.GameModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.Game
{
    public class GameLoop
    {
        private readonly CommandManager commandManager;

        private Boolean isEnignaDiscovered = false;
        public int roundsNb { get; set; }
        private Boolean isFinal = false;

        public GameLoop(CommandManager commandManager)
        {
            this.commandManager = commandManager;
            roundsNb = 0;
        }

        public void ExecuteGame()
        {
            if (isFinal)
            {
                return;
            }

            if (roundsNb < 4)
            {
                Console.WriteLine("Manche : " + roundsNb + 1);
                ExecuteQuickRound();
                ExecuteRound();
            }

            if (roundsNb == 4)
            {
                ExecuteFinalRound();
            }

            ExecuteGame();
        }


        public void ExecuteRound()
        {
            if (isEnignaDiscovered)
            {
                roundsNb++;
                return;
            }

            ExecuteTurn();
            ExecuteRound();
        }

        public void ExecuteTurn()
        {
            commandManager.TriggerActivePlayer();
            commandManager.TriggerWheelTurn(); //sets the current case fallen and trigger action of the corresponding case
            commandManager.TriggerNextPlayer(); //sets the next player 
        }

        public void ExecuteFinalRound()
        {
            isFinal = true;
        }

        public void ExecuteQuickRound()
        {
            commandManager.TriggerCurrentPlayer();
        }

    }
}
