using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Timers;

namespace Serveur.GameServer.CommandPack
{
    public class CommandSetPlayerActif : CommandReceiverClient<String>
    {

        private System.Timers.Timer aTimer;

        public CommandSetPlayerActif(GameEngine context, CommandManager manager) : base(context, manager)
        {
            
        }

        public override void onExecute()
        {

            SetTimer();

            Console.WriteLine("\nPress the Enter key to exit the application...\n");
            Console.WriteLine("The application started at {0:HH:mm:ss.fff}", DateTime.Now);
            Console.ReadLine();
            aTimer.Stop();
            aTimer.Dispose();

            Console.WriteLine("Terminating the application...");

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


            WaitReceiveClient();

            //bien recu les données

        }

        private void SetTimer()
        {
            // Create a timer with a two second interval.
            aTimer = new System.Timers.Timer(1000);
            // Hook up the Elapsed event for the timer. 
            aTimer.Elapsed += OnTimedEvent;
            aTimer.AutoReset = true;
            aTimer.Enabled = true;
        }

        private void OnTimedEvent(Object source, ElapsedEventArgs e)
        {
            Console.WriteLine("The Elapsed event was raised at {0:HH:mm:ss.fff}", e.SignalTime);
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.SENDLETTERCLIENT.eventName,
                data = "exemple"
            };
            Context.SendAllClient(msg);
    //        Context.SendClient(msg, idClient);
        }

        public override void NotifyReceiveClient(String data, string id)
        {
            //faire autre truc
            base.NotifyReceiveClient(data, id);
        }

    }
}
