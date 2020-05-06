using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class PlayerGame
    {
        public String name;
        public String id;

        public PlayerGame()
        {

        }

    }

    public class ListPlayerGame
    {
        public List<PlayerGame> listPlayers;

        public ListPlayerGame()
        {
            listPlayers = new List<PlayerGame>();
        }


    }
}
