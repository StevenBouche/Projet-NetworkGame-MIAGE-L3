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
        public int roundsNb = 0;
        private Boolean isFinal = false;

        public GameLoop(CommandManager commandManager)
        {
            this.commandManager = commandManager;
        }

        public void ExecuteGame()
        {
            if (isFinal) return;

            if (roundsNb < 4)
            {
                isEnignaDiscovered = false;
                Console.WriteLine("Manche : " + roundsNb + 1);
                ExecuteQuickRound();
                commandManager.TriggerCurrentEnigma();
                ExecuteRound();
            } else {
                ExecuteFinalRound();
            }

            ExecuteGame();
        }


        public void ExecuteRound()
        {
            if (isEnignaDiscovered)
            {
                //TODO REMOVE
                roundsNb++;
                commandManager.TriggerChangeWheel();
                return;
            }

            ExecuteTurn();
            ExecuteRound();
        }

        public void ExecuteTurn()
        {
           // isEnignaDiscovered = true; //cheat mod
            commandManager.TriggerHandleTurn(); //trigger handler for turn of one player
            if (!isEnignaDiscovered) commandManager.TriggerNextPlayer(); //sets the next player 
        }

        public void ExecuteFinalRound()
        {
            commandManager.TriggerSetFinalEnigma(); //Sends a FinalInfo with the winner and the final enigma
            commandManager.TriggerWheelTurnFinal(); //Sets the current case 
            commandManager.TriggerAskForFinalLetters(); //Sends askForFinalLetters and askForFinalProposition
            isFinal = true;
        }

        public void ExecuteQuickRound()
        {
            commandManager.TriggerCurrentPlayer();
        }

        public void NotifyPlayerHaveWinRound(string idClient)
        {
            // TRIGGER COMMAND WIN ROUND FOR AN ACTION
            isEnignaDiscovered = true;
        }
    }
}