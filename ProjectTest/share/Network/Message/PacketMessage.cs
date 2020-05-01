using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.Network.Message
{
    public class PacketMessage<T> : Packet
    {
        public T data;
        public Type typeData;
    }

}
