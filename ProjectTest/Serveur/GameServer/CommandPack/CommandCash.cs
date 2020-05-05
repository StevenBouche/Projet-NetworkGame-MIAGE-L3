using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Text;


namespace Serveur.GameServer.CommandPack
{
    class CommandCash : Command<GameEngine>
    {
        public CommandCash(GameEngine context, CommandManager manager) : base(context,manager) { }
        public override void onExecute()
        {
            Console.WriteLine("Case : " + this.Context.CurrentCase.ToString());
            this.Context.m.AddMontant(this.Context.CurrentPlayer, this.Context.CurrentCase);
        }
    }
}
