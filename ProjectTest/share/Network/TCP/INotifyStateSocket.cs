namespace Share.Network.NetworkManager
{
    public interface INotifyStateSocket
    {
        void OnConnect(string id);
        void OnDisconnect(string id);
    }
}