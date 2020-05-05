using Serveur.GameServer.Game;
using Serveur.LobbiesServer;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.MainProgram
{
    class Launcher
    {
        Dictionary<String, ServerGame> gamesList;

        Lobby gameLobby;

        public Launcher()
        {
            gamesList = new Dictionary<string, ServerGame>();
            gameLobby = new Lobby();
        }
    }
}
