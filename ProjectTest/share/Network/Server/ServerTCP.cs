﻿using Share.Network.Message;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using Share.Network.Protocol.TCP;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace Share.Network.Server
{
    public class ServerTCP
    {
        NetworkManagerTCP managerTCP;

        public ServerTCP(int port, INotifyStateSocket observer) { 
            managerTCP = new NetworkManagerTCP(port, observer);
        }

       public void OnEvent<T>(ProtocolEvents<T> proto, ProtocolTCP<T>.OnReceiveEventTCP callback)
        {
            managerTCP.eventManager.OnEvent<T>(proto, callback);
        }

        public void Send<T>(PacketMessage<T> data, String id)
        {
            managerTCP.Send(data, id);
        }

        public void Run()
        {
            managerTCP.StartListening();
        }

        public void stop()
        {
            managerTCP.stop();
        }


    }
}
