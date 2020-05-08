using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{
    public class CommandSetCurrentEnigma : Command<GameEngine>
    {
        public CommandSetCurrentEnigma(GameEngine game, CommandManager CM) : base(game, CM) { }
        
        Enigme e;
        
        public override void onExecute()
        {
            e = Context.gameEnigmaPool.Values.ToList<Enigme>()[0];
            Context.CurrentEnigma = e;
            Context.letterBuyInARound = new List<char>();

            PacketMessage<Enigme> msg = new PacketMessage<Enigme>()
            {
                evt = ProtocolEventsTCP<Enigme>.ACTIONENIGMEPRINCIPALE.eventName,
                data = e
            };
            Console.WriteLine(e.toString());
            Context.SendAllClient(msg);
        }
    }
}
