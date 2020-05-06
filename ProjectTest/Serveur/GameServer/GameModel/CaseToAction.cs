using Serveur.GameServer.CommandPack;
using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Text;

namespace Serveur.GameServer.GameModel
{
    public class CaseToAction
    {
        public static CaseToAction PASSACTION = new CaseToAction(typeof(CommandPass), TypeCase.PASSE);
        public static CaseToAction CASHACTION = new CaseToAction(typeof(CommandCash), TypeCase.CASH);
        public static CaseToAction SCACTION = new CaseToAction(typeof(CommandCash), TypeCase.SUPERCASH);
        public static CaseToAction BANQUEROUTEACTION = new CaseToAction(typeof(CommandBanqueroute), TypeCase.BANQUEROUTE);
        public static CaseToAction MYSTERYACTION = new CaseToAction(typeof(CommandMystery), TypeCase.MYSTERE);
        public static CaseToAction CAVERNEACTION = new CaseToAction(typeof(CommandCaverne), TypeCase.CAVERNE);
        public static CaseToAction HOLDUPACTION = new CaseToAction(typeof(CommandHoldUp), TypeCase.HOLDUP);
        //public static CaseToAction VOYAGE = new CaseToAction(typeof(CommandBonus), TypeCase.VOYAGE);
        Type actionType { get; }
        TypeCase caseType { get; }

        private CaseToAction(Type t, TypeCase t1)
        {
            this.actionType = t;
            this.caseType = t1;
        }

        public static Command<T> getCommand<T>(TypeCase caseType, GameEngine context, CommandManager CM) where T : GameEngine
        {
            
            foreach (FieldInfo t in typeof(CaseToAction).GetFields(BindingFlags.Static | BindingFlags.Public))
            {
                CaseToAction current = (CaseToAction)t.GetValue(null);            
                if(caseType.Equals(current.caseType))
                {
                    return (Command<T>)Activator.CreateInstance(current.actionType, new Object[] {context, CM});
                }
            }
            return null;
        }
    }
}
