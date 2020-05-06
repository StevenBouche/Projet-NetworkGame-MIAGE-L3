using System;
using System.Collections.Generic;

namespace Serveur.GameServer.GameModel
{

    public class Roue {
        List<Case> Cases;

        public Case CurrentCase { get; set; }

        public Roue()
        {
            Cases = new List<Case>();
            fill_Start();

        }
        
        public Case getWheelCases(int pos)
        {
            return Cases[pos];
        }

        public void fill_Start()
        {
            /* CONFIG ROUE : 
             * 4 Cases PASSE
             * 2 Cases BANQUEROUTE
             * 1 Case SUPER CASH
             * 1 Case BONUS
             * 16 Cases CASH Fixe
             */

            for (int i = 0; i <= 21; i++)
            {
                if (i < 15)
                {
                    Cases.Add(new Case(TypeCase.CASH));
                }
                else if (i >= 15 && i < 19)
                {
                    Cases.Add(new Case(TypeCase.PASSE));
                }
                else if (i >= 19 && i < 21)
                {
                    Cases.Add(new Case(TypeCase.BANQUEROUTE));
                }
                else
                {
                    Cases.Add(new Case(TypeCase.SUPER_CASH));
                    Cases.Add(new Case(TypeCase.MYSTERE));
                }
            }
        }

        public void UnveiledMysteryCase(Case C)
        {
            Random r = new Random();
            if (C.type == TypeCase.MYSTERE) {
                if (r.Next(2) == 1)
                {
                    C.type = TypeCase.CASH;
                    C.valeur = (int)Cash.CASH_500;
                }
            }
            else
                C.type = TypeCase.BANQUEROUTE;
        }

        override
        public String ToString()
        {
            String res = "";
            foreach (Case c in Cases)
            {
                res += c.ToString() + " | ";
            }
            return res;

        }
    }
}