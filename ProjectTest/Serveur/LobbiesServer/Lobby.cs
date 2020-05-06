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
        }

        public void Run()
        {
            Thread t = new Thread(new ThreadStart(myLobby.Run));
            t.Start();
        }

        private void OnDataServerGame(DataServerGame dataServ, EndPoint ep)
        {
            dataServ.listServer.Add(new ServerGameInfo()
            {
                addr = "127.0.0.1",
                port = 10000,
                name = "TEST",
                nbPlayerMax = 3,
                nbPlayerCurrent = 0
            });
            dataServ.listServer.Add(new ServerGameInfo()
            {
                addr = "127.0.0.1",
                port = 10000,
                name = "TEST2",
                nbPlayerMax = 3,
                nbPlayerCurrent = 3
            });
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
