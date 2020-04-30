using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace Serveur.Network
{
    public enum ProtocolEvents
    {
        SUBSCRIBE,
        UNSUBSCRIBE
    }

    public class ProtocolNetwork
    {
        public ProtocolEvents evt;
    }

    public class ProtocolMessage<T> : ProtocolNetwork
    {
        public T data;
    }

}
