using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Serveur.Network
{
    public interface IReceiverNetwork
    {

        public void OnReceivedData(String obj, EndPoint endPoint);



    }
}
