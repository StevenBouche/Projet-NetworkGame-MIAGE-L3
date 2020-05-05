using Share.Network.Server;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.LobbiesServer
{
    class Lobby
    {
        ServerUDP myLobby;

        public Lobby()
        {
            myLobby = new ServerUDP();
        }
    }
}
