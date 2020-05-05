using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Protocol
{
    public class ProtocolEventsTCP<T> : ProtocolEvents<T>
    {
        public static ProtocolEventsTCP<String> CONNECTION = new ProtocolEventsTCP<String>("CONNECTION");
        public static ProtocolEventsTCP<String> IDENTITY = new ProtocolEventsTCP<String>("IDENTITY");






        private ProtocolEventsTCP(String name) : base(name)
        {

        }

    }
}
