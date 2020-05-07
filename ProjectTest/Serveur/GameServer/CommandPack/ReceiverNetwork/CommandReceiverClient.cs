using Serveur.GameServer.Game;
using Share.Network;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.CommandPack.ReceiverNetwork
{
    public abstract class CommandReceiverClient<T> : Command<GameEngine>, IReceiverClient<T>
    {

        public CommandReceiverClient(GameEngine engine, CommandManager cm) : base(engine,cm)
        {
            AllDone = new ManualResetEvent(false);
        }

        public T Data { get; set; }

        protected ManualResetEvent AllDone { get; set; }

        protected String idClient;

        protected void WaitReceiveClient()
        {
            while (Data == null)
            {
                AllDone.Reset();
                AllDone.WaitOne();
            }
        }

        public virtual void NotifyReceiveClient(T data, string id)
        {
            Data = data;
            idClient = id;
            AllDone.Set();
        }

    }

}
