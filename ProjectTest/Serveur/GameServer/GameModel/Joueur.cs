using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Text;

namespace Serveur.GameServer.GameModel

{
    public class Joueur : IComparable<Joueur>
    {
        public String nom;

        public Cagnotte cagnotte;

        public String id;

        public Joueur(String id)
        {
            this.id = id;
          //  this.nom = name;
            this.cagnotte = new Cagnotte();
        }

        public int CompareTo(Joueur other)
        {
            return nom.CompareTo(other.nom);
        }
    }
}
