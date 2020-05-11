using Serveur.MainProgram;
using Share.Network.Message;
using Share.Network.Message.obj;
using Share.Network.Protocol;
using Share.Network.Server;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Threading;

namespace Serveur.LobbiesServer
{
    public class Lobby
    {
        ServerUDP myLobby;
        IHandlerGame handlerGame;

        public Lobby(IHandlerGame handlerGame)
        {
            this.handlerGame = handlerGame;
            myLobby = new ServerUDP();
            myLobby.OnEvent(ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME, OnDataServerGame);
            myLobby.OnEvent(ProtocolEventsUDP<String>.NEWGAME, OnNewGame);
            myLobby.OnEvent(ProtocolEventsUDP<String>.REMOVEGAME, OnRemoveGame);
        }

        private void OnRemoveGame(string obj, EndPoint endPoint)
        {
            handlerGame.removeGame(obj);
            DataServerGame dataServ = handlerGame.GetDataServerGame();
            PacketMessage<DataServerGame> packet = new PacketMessage<DataServerGame>()
            {
                evt = ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME.eventName,
                data = dataServ
            };
            myLobby.Send(packet, endPoint);
        }

        private void OnNewGame(string obj, EndPoint endPoint)
        {
            handlerGame.createNewGame();
            DataServerGame dataServ = handlerGame.GetDataServerGame();
            PacketMessage<DataServerGame> packet = new PacketMessage<DataServerGame>()
            {
                evt = ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME.eventName,
                data = dataServ
            };
            myLobby.Send(packet, endPoint);
        }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(myLobby.Run));
            t.Start();
        }

        private void OnDataServerGame(DataServerGame dataServ, EndPoint ep)
        {

            dataServ = handlerGame.GetDataServerGame();
            PacketMessage<DataServerGame> packet = new PacketMessage<DataServerGame>()
            {
                evt = ProtocolEventsUDP<DataServerGame>.GETLISTSERVERGAME.eventName,
                data = dataServ
            };
            myLobby.Send(packet, ep);
        }


    }
}
