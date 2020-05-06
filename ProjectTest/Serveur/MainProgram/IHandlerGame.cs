using Share.Network.Message.obj;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.MainProgram
{
    public interface IHandlerGame
    {
        DataServerGame GetDataServerGame();
    }
}
