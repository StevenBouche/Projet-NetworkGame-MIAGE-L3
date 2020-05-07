using Serveur.GameServer.GameModel;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using Share.Network.Server;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Threading;

namespace Serveur.GameServer.Game.Helper
{
    public class HelperLobby
    {
        GameEngine gameManager;
        ServerTCP network;

        private readonly ManualResetEvent allDone;

        public HelperLobby(ref GameEngine manager, ref ServerTCP serverTCP, ref ManualResetEvent all)
        {
            gameManager = manager;
            network = serverTCP;
            allDone = all;
            gameManager.addCallbackPlayerJoined(notifyPlayerHaveJoined);
            gameManager.addCallbackPlayerLeaved(notifyPlayerHaveLeaved);
        }

        public void notifyPlayerHaveJoined(String id, ListPlayerGame l)
        {
            updateClientLobby(ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER, l);
        }

        public void notifyPlayerHaveLeaved(String id, ListPlayerGame l)
        {
            updateClientLobby(ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER, l);
        }

        public void OnIdentityReceived(String obj, String id)
        {
            if (gameManager.listPlayers.Count <= 3)
            {
                Console.WriteLine("(TCP exchange) Client " + id + "have sent " + obj);
                PacketMessage<String> msg = new PacketMessage<string>() { evt = ProtocolEventsTCP<String>.IDENTITY.eventName, data = id };
                Debug.WriteLine("On Connect player : " + id);
                network.Send(msg, id);
                gameManager.AddPlayer(id, obj);
            }
            else
            {
                Console.WriteLine("Too much Identity msg received");

            }
        }

        public void OnReadyReceived(Boolean isReady, String id)
        {
            Console.WriteLine("(TCP exchange) Received" + id + " is ready");

            if (!gameManager.listPlayers.ContainsKey(id)) return; // drop packet

            gameManager.setReadyPlayer(isReady, id);

            ListPlayerGame list = gameManager.GetListOfPlayerLobbies();
            updateClientLobby(ProtocolEventsTCP<String>.NOTIFYLOBBYPLAYER, list);

            if (checkAllPlayersReady())
            {
                gameManager.gameState = GameState.STARTED;
                allDone.Set();
            }
        }

        private Boolean checkAllPlayersReady()
        {
            if (gameManager.listIdPlayers.Count != 3)
            {
                return false;
            }
            foreach (KeyValuePair<String, Joueur> p in gameManager.listPlayers)
            {
                if (!p.Value.isReady)
                {
                    return false;
                }
            }
            return true;
        }

        private void updateClientLobby<T>(ProtocolEventsTCP<T> p, ListPlayerGame l)
        {
            PacketMessage<ListPlayerGame> msgP = new PacketMessage<ListPlayerGame>() { evt = p.eventName, data = l };

            foreach (PlayerGame player in l.listPlayers)
            {
                network.Send(msgP, player.id);
            }
            allDone.Set();
        }

    }

}
