using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Text;

namespace Serveur.GameServer.GameModel

{
    public class Voyage
    {
        public string Pays_Destination;

        public Voyage()
        {
            Pays_Destination = "";
        }

        public void GetRandomVoyage()
        {
            Random r = new Random();
            Pays_Destination = (String)Enum.GetValues(typeof(ListVoyage)).GetValue(r.Next(12));
        }

    }
}
