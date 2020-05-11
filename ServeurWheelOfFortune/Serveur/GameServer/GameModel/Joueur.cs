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

        public Boolean isReady;

        public Joueur(String id, String name)
        {
            this.id = id;
            this.nom = name;
            this.cagnotte = new Cagnotte();
            this.isReady = false;
        }

        public int CompareTo(Joueur other)
        {
            return nom.CompareTo(other.nom);
        }
    }
}