using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.CommandPack
{
    public class CommandSetPlayerActif : CommandReceiverClient<String>
    {

        public CommandSetPlayerActif(GameEngine context, CommandManager manager) : base(context, manager)
        {
            
        }

        public override void onExecute()
        {


            //Un joueur aléatoire découvre l'énigme, il prend la main
            Random r = new Random();
            int RandParam = r.Next(3);
            String id = Context.listIdPlayers.ToArray()[RandParam];
            Context.CurrentPlayer = Context.listPlayers[id];
            Context.CurrentPosPlayer = RandParam;

            /*Context.listIdPlayers.RemoveAt(RandParam);
            Context.listIdPlayers.Insert(0, id);
            Context.CurrentPosPlayer = 0*/
            

            Console.WriteLine("\n Gagnant de l'echauffement : " + Context.CurrentPlayer.id + "\n");


        }

        public override void NotifyReceiveClient(string data, string id)
        {
            throw new NotImplementedException();
        }

    }
}
