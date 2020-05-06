using share;
using Share.Network.Message;
using Share.Network.Message.obj;
using Share.Network.NetworkManager;
using Share.Network.Protocol;
using Share.Network.Protocol.UDP;
using System;
using System.Collections.Generic;
using System.Net;
using System.Reflection.Metadata.Ecma335;
using System.Text;
using System.Threading;

namespace Share.Network.Server
{
    public class ServerUDP
    {

        NetworkManagerUDP manager;

        public bool IsRunning { get => manager.running;  }

        public ServerUDP()
        {
            manager = new NetworkManagerUDP();
        }

        public void OnEvent<T>(ProtocolEvents<T> proto, ProtocolUDP<T>.OnReceiveEvent callback)
        {
            manager.evtNetManager.OnEvent(proto, callback);
        }

        public void Send<T>(PacketMessage<T> data, EndPoint ep)
        {
            manager.Send(data, ep);
        }

        public void Run()
        {
            manager.StartListening();
        }

    }
}
