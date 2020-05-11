using System;

namespace Serveur.GameServer.GameModel
{

    public class TypeCase
    {
        public static TypeCase CASH = new TypeCase("CASH", true);
        public static TypeCase PASSE = new TypeCase("PASSE", false);
        public static TypeCase BANQUEROUTE = new TypeCase("BANQUEROUTE", false);
        public static TypeCase SUPERCASH = new TypeCase("SUPER_CASH", true);
        public static TypeCase MYSTERE = new TypeCase("MYSTERE", true);
        public static TypeCase CAVERNE = new TypeCase("CAVERNE", true);
        public static TypeCase HOLDUP = new TypeCase("HOLDUP", true);
        public static TypeCase VOYAGE = new TypeCase("VOYAGE", true);
        public static TypeCase FINAL = new TypeCase("FINAL", true);

        public Boolean isLetterNeeded;

        string NameType { get; }

        public TypeCase(string n, Boolean letterNeeded)
        {
            NameType = n;
            this.isLetterNeeded = letterNeeded;
        }

        public override string ToString()
        {
            return NameType;
        }
    }


}
