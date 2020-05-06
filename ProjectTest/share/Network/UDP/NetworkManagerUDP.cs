using Newtonsoft.Json;
using Share.Network.Event;
using Share.Network.Message;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Threading;

namespace Share.Network.NetworkManager
{

    public class NetworkManagerUDP
    {
        public EventNetworkManagerUDP evtNetManager;

        private ManualResetEvent allDone = new ManualResetEvent(false);
        private EndPoint localEndPoint = new IPEndPoint(IPAddress.Any, 11000);
        private Socket listener;

        private delegate void OnReceivedDataDel(String obj, EndPoint endPoint);
        private OnReceivedDataDel _delegates;
        private Dictionary<IReceiverNetwork, OnReceivedDataDel> _listDelegates;

        public Boolean running;

        public NetworkManagerUDP()
        {
            _listDelegates = new Dictionary<IReceiverNetwork, OnReceivedDataDel>();
            evtNetManager = new EventNetworkManagerUDP();
            AddListenerReceivedData(evtNetManager);
            Thread t = new Thread(new ThreadStart(evtNetManager.Run));
            t.Start();
        }

        private void AddListenerReceivedData(IReceiverNetwork irn)
        {
            if (_listDelegates.ContainsKey(irn)) return;
            _listDelegates.Add(irn, new OnReceivedDataDel(irn.OnReceivedData));
            _listDelegates.TryGetValue(irn, out OnReceivedDataDel res);
            _delegates += res;
        }

        private void RemoveListenerReceivedData(IReceiverNetwork irn)
        {
            _listDelegates.TryGetValue(irn, out OnReceivedDataDel res);
            if (res != null)
            {
                _delegates -= res;
                _listDelegates.Remove(irn);
            }
        }

        public void StartListening()
        {

            listener = new Socket(localEndPoint.AddressFamily, SocketType.Dgram, ProtocolType.Udp);

            // Bind the socket to the local endpoint and listen for incoming connections.  
            try
            {

                listener.Bind(localEndPoint);
                running = true;
                while (running)
                {
                    // Set the event to nonsignaled state.  
                    allDone.Reset();

                    StateObjectUDP state = new StateObjectUDP();
                    Console.WriteLine("Waiting receive data...");
                    listener.BeginReceiveFrom(state.buffer, 0, StateObjectUDP.BufferSize, SocketFlags.None, ref localEndPoint, new AsyncCallback(ReadCallback), state);

                    // Wait until a connection is made before continuing.  
                    allDone.WaitOne();
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

            listener.Shutdown(SocketShutdown.Both);
            listener.Close();

            Console.WriteLine("\nPress ENTER to continue...");
            Console.Read();

        }

        public void Stop()
        {
            running = false;
            allDone.Set();
        }

        public void Send<T>(PacketMessage<T> data, EndPoint ep)
        {
            String jsonString = JsonConvert.SerializeObject(data);
            Send(listener, jsonString, ep);
        }

        private void ReadCallback(IAsyncResult ar)
        {

            allDone.Set();

            String content;

            // Signal the main thread to continue.  
            Console.WriteLine("Trigger ReadCallback");

            // Retrieve the state object and the handler socket  
            // from the asynchronous state object.  
            StateObjectUDP state = (StateObjectUDP)ar.AsyncState;
            EndPoint ep = new IPEndPoint(IPAddress.Any, 11000);

            // Read data from the client socket.
            int bytesRead = listener.EndReceiveFrom(ar, ref ep); //todo SocketException fermeture socket client try catch pendant reception message 
            //  Console.WriteLine("END RECEIVE FROM " + bytesRead);

            if (bytesRead > 0)
            {
                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));
                content = state.sb.ToString();
                Console.WriteLine($" Network Manager Received  from {ep} : {content}");
                _delegates(content, ep); // notify receive
            }

        }

        private void Send(String data, EndPoint ep)
        {
            Send(listener, data, ep);
        }

        private void Send(Socket handler, String data, EndPoint ep)
        {
            byte[] byteData = Encoding.ASCII.GetBytes(data);
            StateObjectUDP state = new StateObjectUDP();
            handler.BeginSendTo(byteData, 0, byteData.Length, SocketFlags.None, ep,new AsyncCallback(SendCallback), state);
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            { 
                int bytesSent = listener.EndSend(ar);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }

    }

}
