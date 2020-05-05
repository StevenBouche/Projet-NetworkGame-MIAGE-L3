using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.CommandPack
{
    class CommandPass : Command<GameEngine>
    {
        public CommandPass(GameEngine context) : base(context) { }
        public override void onExecute()
        {
            Console.WriteLine("Case : " + this.Context.CurrentCase.ToString());
            if (this.Context.IndexJoueur == 2)
            {
                this.Context.IndexJoueur = 0;
            }
            else
                this.Context.IndexJoueur++;
        }
    }
}
