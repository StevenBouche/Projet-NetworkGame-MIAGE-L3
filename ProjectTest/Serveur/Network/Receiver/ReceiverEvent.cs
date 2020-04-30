using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.Network.Receiver
{

    public class ReceiverEvent<T,K> where T : System.Enum where K : class
    {

        public delegate void OnReceiveEvent(K obj);
        public OnReceiveEvent del;
        public readonly T valueEnum;

        public ReceiverEvent(T enumElem)
        {
            valueEnum = enumElem;
        }

         void AddReceiver(OnReceiveEvent obj)
        {
            del += obj;
        }

        public void OnReceive(K data)
        {
            del(data);
        }

    }

}
