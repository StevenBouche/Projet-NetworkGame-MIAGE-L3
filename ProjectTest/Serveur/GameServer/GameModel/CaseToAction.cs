using Serveur.GameServer.CommandPack;
using Serveur.GameServer.Game;
using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.GameModel
{
    public class CaseToAction
    {
        public static CaseToAction MISTERYACTION = new CaseToAction(typeof(CommandMystery), TypeCase.MYSTERE);
        Type actionType;
        TypeCase caseType;

        private CaseToAction(Type t, TypeCase t1)
        {
            this.actionType = t;
            this.caseType = t1;
        }

        public static Command<GameEngine> getCommand<T>(TypeCase caseType, T engine, CommandManager cmd) where T : GameEngine
        {
            if(MISTERYACTION.caseType == caseType)
            {
                return new CommandMystery(engine, cmd);
            }
            
            return null; //todo
        }
    }
}
