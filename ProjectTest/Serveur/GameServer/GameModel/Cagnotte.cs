using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.GameModel
{
    public class Cagnotte : IComparable
    {
        int _montant_Total;
        int _montant_Manche;
        int _montant_Caverne;
        Voyage _voyage;


        public Cagnotte()
        {
            Montant_Total = 0;
            Montant_Manche = 0;
            Montant_Caverne = 0;
            Voyage = new Voyage();
        }

        public int Montant_Total { get => _montant_Total; set => _montant_Total = value; }
        public int Montant_Manche { get => _montant_Manche; set => _montant_Manche = value; }
        public int Montant_Caverne { get => _montant_Caverne; set => _montant_Caverne = value; }
        public Voyage Voyage { get => _voyage; set => _voyage = value; }

        public int CompareTo(object obj)
        {
            return _montant_Total.CompareTo(obj);
        }
    }
}
