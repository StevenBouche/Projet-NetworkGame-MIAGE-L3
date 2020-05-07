using System;

namespace Serveur.GameServer.GameModel
{

    public class TypeCase
    {
        public static TypeCase CASH = new TypeCase("CASH");
        public static TypeCase PASSE = new TypeCase("PASSE");
        public static TypeCase BANQUEROUTE = new TypeCase("BANQUEROUTE");
        public static TypeCase SUPERCASH = new TypeCase("SUPER_CASH");
        public static TypeCase MYSTERE = new TypeCase("MYSTERE");
        public static TypeCase CAVERNE = new TypeCase("CAVERNE");
        public static TypeCase HOLDUP = new TypeCase("HOLDUP");
        public static TypeCase VOYAGE = new TypeCase("VOYAGE");
        public static TypeCase FINAL = new TypeCase("FINAL");


        string NameType { get; }

        public TypeCase(string n)
        {
            NameType = n;
        }

        public override string ToString()
        {
            return NameType;
        }
    }


}
