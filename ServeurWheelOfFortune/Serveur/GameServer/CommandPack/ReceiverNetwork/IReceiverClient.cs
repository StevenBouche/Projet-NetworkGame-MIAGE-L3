using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.CommandPack
{
    public interface IReceiverClient<T>
    {

        void NotifyReceiveClient(T data, String id);
        
    }

}
