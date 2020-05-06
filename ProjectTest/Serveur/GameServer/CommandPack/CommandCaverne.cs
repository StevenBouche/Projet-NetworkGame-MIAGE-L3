﻿using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandCaverne : Command<GameEngine>
    {
        public CommandCaverne(GameEngine context, CommandManager CM) : base(context, CM) { }
        public override void onExecute()
        {
            Random r = new Random();
            int montant = r.Next(100, 2001);
            Context.CurrentPlayer.cagnotte.Montant_Caverne += montant;
            Context.CurrentPlayer.cagnotte.Montant_Total += montant;
        }
    }
}
