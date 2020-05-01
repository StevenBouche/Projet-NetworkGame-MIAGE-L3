using System;

namespace Share.Network.Message
{
    public class PacketMessage<T> : Packet
    {
        public T data;
        public Type typeData;
    }

}
