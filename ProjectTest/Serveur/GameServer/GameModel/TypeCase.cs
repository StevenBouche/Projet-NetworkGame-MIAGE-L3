using System;

namespace Serveur.GameServer.GameModel
{
    [AttributeUsage(AttributeTargets.All)]
    public class TypeCase : Attribute
    {
        [TypeCase("CASH")] public static TypeCase CASH = new TypeCase("CASH");
        [TypeCase("PASSE")]public static TypeCase PASSE = new TypeCase("PASSE");
        [TypeCase("BANQUEROUTE")] public static TypeCase BANQUEROUTE = new TypeCase("BANQUEROUTE");
        [TypeCase("SUPER_CASH")] public static TypeCase SUPERCASH = new TypeCase("SUPER_CASH");
        [TypeCase("MYSTERE")] public static TypeCase MYSTERE = new TypeCase("MYSTERE");
        [TypeCase("CAVERNE")] public static TypeCase CAVERNE = new TypeCase("CAVERNE");
        [TypeCase("HOLDUP")] public static TypeCase HOLDUP = new TypeCase("HOLDUP");
        [TypeCase("VOYAGE")] public static TypeCase VOYAGE = new TypeCase("VOYAGE");


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
