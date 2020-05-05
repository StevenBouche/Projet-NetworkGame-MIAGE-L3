using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Text;

namespace Serveur.GameModel
{
    class Joueur : IComparable<Joueur>
    {
        public String nom;

        public Joueur(String n)
        {
            nom = n;
        }

        public int CompareTo(Joueur other)
        {
            return nom.CompareTo(other.nom);
        }
    }
}
