using System;
using System.Collections;

namespace Serveur.GameServer.GameModel
{
    public class Case{
        public int valeur { get; set; }
        public TypeCase t { get; set; }

        public Case(int v) {
            valeur = v;
        }

        public Case(TypeCase t) {
            this.t = t;
            Random r = new Random();
            if(t == TypeCase.CASH) {
                valeur = (int)Enum.GetValues(typeof(Cash)).GetValue(r.Next(0, 7));
            }
            else if (t == TypeCase.SUPER_CASH) {
                valeur = (int)Enum.GetValues(typeof(Cash)).GetValue(7); ;
            }
            else
            {
                valeur = 0;
            }
        }

        override public String ToString()
        {
            return valeur + " " + t.ToString();
        }

        

        
    }
}