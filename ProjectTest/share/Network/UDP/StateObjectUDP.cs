using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Protocol
{
    class StateObjectUDP
    {
        // Size of receive buffer.  
        public const int BufferSize = 1024;
        // Receive buffer.  
        public byte[] buffer = new byte[BufferSize];
        // Received data string.  
        public StringBuilder sb = new StringBuilder();

    }
}
