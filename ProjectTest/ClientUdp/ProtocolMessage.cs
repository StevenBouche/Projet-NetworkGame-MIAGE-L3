using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace Serveur.Network
{
    public class ProtocolEvents
    {
        public static ProtocolEvents SUBSCRIPTION = new ProtocolEvents("SUBSCRIPTION");

        public string eventName;

        private ProtocolEvents(String name)
        {
            eventName = name;
        }

        public ProtocolEvents()
        {
        
        }

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
