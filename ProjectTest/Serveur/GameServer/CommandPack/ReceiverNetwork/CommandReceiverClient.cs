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

        }

        public T Data { get; set; }

        protected ManualResetEvent AllDone { get; set; }

        public abstract void NotifyReceiveClient(T data, string id);

    }

}
