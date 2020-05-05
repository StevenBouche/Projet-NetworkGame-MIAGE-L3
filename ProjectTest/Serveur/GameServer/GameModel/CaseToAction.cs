using Serveur.GameServer.CommandPack;
using Serveur.GameServer.Game;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.GameModel
{
    public class CaseToAction
    {
        public static List<CaseToAction> listAction = new List<CaseToAction>();

        public static CaseToAction MISTERYACTION = new CaseToAction(typeof(CommandMystery), TypeCase.MYSTERE, ref listAction);

        Type actionType;
        TypeCase caseType;

        private CaseToAction(Type t, TypeCase t1, ref List<CaseToAction> list)
        {
            this.actionType = t;
            this.caseType = t1;
            list.Add(this);
        }

        public static Command<GameEngine> getCommand<T>(TypeCase caseType, T engine, CommandManager cmd) where T : GameEngine
        {

            foreach(CaseToAction c in listAction)
            {
                if(c.caseType == caseType)
                {
                    return (Command<GameEngine>) Activator.CreateInstance(c.actionType, engine, cmd);
                }
            }

            return null;
        }
    }
}
