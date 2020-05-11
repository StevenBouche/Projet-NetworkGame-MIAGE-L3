using Share.Network.NetworkManager;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network
{
    public interface IReceiverClient
    {

        public void OnReceivedData(String obj, EndPoint endPoint);

        public void OnReceivedData(String obj, StateObjectTCP state);



    }
}
