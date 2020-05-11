using Share.Network.Message;
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
        readonly NetworkManagerTCP managerTCP;
        public int Port { get => managerTCP.port;  }
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

        public void SendAll<T>(PacketMessage<T> data, List<String> ids)
        {
            foreach(String id in ids)
            {
                managerTCP.Send(data, id);
                Thread.Sleep(50);
            } 
        }

        public void ClearEvent()
        {
            this.managerTCP.eventManager.resetEvent();
        }

        public void Run()
        {
            managerTCP.StartListening();
        }

        public void stop()
        {
            managerTCP.stop();
        }

        public void disconnectClient(String id)
        {
            managerTCP.disconnectClient(id);
        }

    }
}
