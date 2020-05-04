using Share.Network.NetworkManager;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Share.Network.Server
{
    class ServerTCP
    {
        NetworkManagerTCP managerTCP;

        public ServerTCP() { managerTCP = new NetworkManagerTCP(); }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(managerTCP.StartListening));
            t.Start();

            t.Join();
        }


    }
}
