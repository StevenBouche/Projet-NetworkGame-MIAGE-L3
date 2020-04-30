using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.Network
{
    public interface IReceiverEvent<T>
    {
       
      //  public void AddReceive(OnReceiveEvent obj);
        public void OnReceive(T data);
    }
}
