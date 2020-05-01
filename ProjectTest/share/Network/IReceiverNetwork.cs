using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network
{
    public interface IReceiverNetwork
    {

        public void OnReceivedData(String obj, EndPoint endPoint);



    }
}
