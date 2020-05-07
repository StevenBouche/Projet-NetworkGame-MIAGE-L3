using System;
using System.Collections;
using System.Reflection;

namespace Serveur.GameServer.GameModel
{
    public class Case{
        public int valeur { get; set; }
        public TypeCase type { get; set; }

        public Case(int v) {
            valeur = v;
        }

        public Case(TypeCase t) {
            this.type = t;
            Random r = new Random();

            FieldInfo[] listCase = typeof(TypeCase).GetFields(BindingFlags.Static | BindingFlags.Public);

            if(t.Equals(TypeCase.CASH)) {
                valeur = (int)Enum.GetValues(typeof(Cash)).GetValue(r.Next(0, 7));
            }
            else if (t.Equals(TypeCase.SUPERCASH)) {
                valeur = (int)Enum.GetValues(typeof(Cash)).GetValue(7);
            }
            else
            {
                valeur = 0;
            }
        }

        override public String ToString()
        {
            return valeur + " " + type.ToString();
        }

        

        
    }
}