using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace Share.Network.Protocol
{
    public abstract class ProtocolNetwork
    {
        public string EventName { get; set; }
        public Type type;

        protected ProtocolNetwork(Type type)
        {
            this.type = type;
        }

        public Type GetTypeDataEvent()
        {
            return this.type;
        }

    }
}
