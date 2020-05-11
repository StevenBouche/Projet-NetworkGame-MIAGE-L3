using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.obj
{
    public class DataServerGame
    {

        public List<ServerGameInfo> listServer;

        public DataServerGame()
        {
            listServer = new List<ServerGameInfo>();
        }
    }
}
