using Share.Network.Message;
using System;

namespace Serveur.GameServer.Game
{
    public interface ISenderAtClient
    {

        public void SendClient<T>(PacketMessage<T> msg, String id);
        public void SendAllClientInGame<T>(PacketMessage<T> msg);

    }
}