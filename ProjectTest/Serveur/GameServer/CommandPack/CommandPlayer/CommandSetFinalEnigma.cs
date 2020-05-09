using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandSetFinalEnigma : Command<GameEngine>
    {
        public CommandSetFinalEnigma(GameEngine game, CommandManager CM) : base(game, CM) { }

        Enigme e;

        public override void onExecute()
        {
            e = Context.gameEnigmaPool.Values.ToList<Enigme>()[0];
            Context.CurrentEnigma = e;
            Context.gameEnigmaPool.Remove(e.category);

            String idWinner = DetermineWinner();
            if (Context.CurrentPlayer != null)
            {

                FinalInfo finalInfo = new FinalInfo(idWinner, e);

                PacketMessage<FinalInfo> msg = new PacketMessage<FinalInfo>()
                {
                    evt = ProtocolEventsTCP<FinalInfo>.ACTIONENIGMEFINALE.eventName,
                    data = finalInfo
                };

                Console.WriteLine(e.toString());
                Context.SendAllClient(msg);
            }
            else
            {
                Console.WriteLine("Error ?");
            }
           
        }

        private String DetermineWinner()
        {
            Joueur j = null;
            int max = 0;

            foreach (KeyValuePair<String, Joueur> entry in Context.listPlayers)
            {
                if (entry.Value.cagnotte.Montant_Total > max)
                {
                    max = entry.Value.cagnotte.Montant_Total;
                    j = entry.Value;
                }
                else
                {
                    entry.Value.cagnotte.Montant_Total = 0;
                }
            }
            Context.CurrentPlayer = j;
            return j.id;
        }
    }
}
