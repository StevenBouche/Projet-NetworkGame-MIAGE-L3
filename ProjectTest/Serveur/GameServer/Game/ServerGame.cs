using Share.Network.Server;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.Game
{
    class ServerGame
    {
        ServerTCP gameServer;
        GameEngine myGame;

        public ServerGame(int port)
        {
            gameServer = new ServerTCP(port);
            myGame = new GameEngine();
        }
    }
}
