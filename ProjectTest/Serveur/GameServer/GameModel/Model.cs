using System;
using System.Collections.Generic;

namespace Serveur.GameServer.GameModel
{

    class Model 
    {

        public Dictionary<Joueur, Cagnotte> ListJoueur { get; set; }
        public Roue R { get; set; }


        public Model()
        {
            R = new Roue();
            ListJoueur = new Dictionary<Joueur, Cagnotte>();
            Fill_Demo();
        }

        public void Fill_Demo()
        {
            ListJoueur.Add(new Joueur("RAMON"), new Cagnotte());
            ListJoueur.Add(new Joueur("GILLES"), new Cagnotte());
            ListJoueur.Add(new Joueur("MICHEL"), new Cagnotte());
        }

        public void AddJoueur(Joueur J)
        {
            ListJoueur.Add(J, new Cagnotte());
        }

        public void AddMontant(Joueur J, Case C)
        {
            if (ListJoueur.TryGetValue(J, out Cagnotte value))
            {
                value.Montant_Total += C.valeur;
                value.Montant_Manche += C.valeur;
            }
        }

        public void AddCaverne(Joueur J)
        {
            Random r = new Random();
            int montant = r.Next(100, 2001);
            if (ListJoueur.TryGetValue(J, out Cagnotte value))
            {
                value.Montant_Total += montant;
                value.Montant_Caverne = montant;
            }
        }

        public void Banqueroute(Joueur J)
        {
            if (ListJoueur.TryGetValue(J, out Cagnotte value))
            {
                value.Montant_Manche = 0;
            }
        }

        override public string ToString()
        {
            string res = R.ToString();
            foreach(KeyValuePair<Joueur, Cagnotte> joueur in ListJoueur)
            {
                res += ToString(joueur.Key, 0);
            }
            return res;
        }


        public string ToString(Joueur J, int manche)
        {
           
            if (ListJoueur.TryGetValue(J, out Cagnotte value))
                return "\n Joueur : " + J.nom + ", Cagnotte :\n - Montant Total = " + value.Montant_Total +
                    "\n - Montant Manche " + manche + " = " + value.Montant_Manche +
                    "\n - Montant Caverne = " + value.Montant_Caverne +
                    "\n - Voyage : " + value.Voyage.Pays_Destination;
            else return "Joueur non présent";
        }

    }

}