using Newtonsoft.Json;
using Share.Network.Message;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Share.Network.NetworkManager
{
    public class StateObject
    {
        // Size of receive buffer.  
        public const int BufferSize = 1024;
        // Receive buffer.  
        public byte[] buffer = new byte[BufferSize];
        // Received data string.  
        public StringBuilder sb = new StringBuilder();

    }

    public class NetworkManagerUDP : IReceiverNetwork
    {
        public EventNetworkManager evtNetManager;

        private ManualResetEvent allDone = new ManualResetEvent(false);
        private EndPoint localEndPoint = new IPEndPoint(IPAddress.Any, 11000);
        private Socket listener;

        private delegate void OnReceivedDataDel(String obj, EndPoint endPoint);
        private OnReceivedDataDel _delegates;
        private Dictionary<IReceiverNetwork, OnReceivedDataDel> _listDelegates;


        public NetworkManagerUDP()
        {
            _listDelegates = new Dictionary<IReceiverNetwork, OnReceivedDataDel>();
            evtNetManager = new EventNetworkManager();
            AddListenerReceivedData(this);
            AddListenerReceivedData(evtNetManager);
            Thread t = new Thread(new ThreadStart(evtNetManager.Run));
            t.Start();
        }

        public void OnReceivedData(String obj, EndPoint endpoint)
        {
            // Console.WriteLine("SERVER : "+ obj);
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

                while (true)
                {
                    // Set the event to nonsignaled state.  
                    allDone.Reset();

                    StateObject state = new StateObject();
                    Console.WriteLine("Waiting receive data...");
                    listener.BeginReceiveFrom(state.buffer, 0, StateObject.BufferSize, SocketFlags.None, ref localEndPoint, new AsyncCallback(ReadCallback), state);

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


        public void ReadCallback(IAsyncResult ar)
        {

            allDone.Set();

            String content;

            // Signal the main thread to continue.  
            Console.WriteLine("Trigger ReadCallback");

            // Retrieve the state object and the handler socket  
            // from the asynchronous state object.  
            StateObject state = (StateObject)ar.AsyncState;

            EndPoint ep = new IPEndPoint(IPAddress.Any, 11000);
            // Read data from the client socket.
            int bytesRead = listener.EndReceiveFrom(ar, ref ep);
            //  Console.WriteLine("END RECEIVE FROM " + bytesRead);

            if (bytesRead > 0)
            {
                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));
                content = state.sb.ToString();
                Console.WriteLine($" Network Manager Received  from {ep} : {content}");
                _delegates(content, ep); // notify receive
              //  Send(listener, content, ep);
            }
        }

        private void Send(String data, EndPoint ep)
        {
            Send(listener, data, ep);
        }

        public void Send<T>(PacketMessage<T> data, EndPoint ep)
        {
            String jsonString = JsonConvert.SerializeObject(data);
            Send(listener, jsonString, ep);
        }

        private void Send(Socket handler, String data, EndPoint ep)
        {
            // Convert the string data to byte data using ASCII encoding.  
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            StateObject state = new StateObject();

            // Begin sending the data to the remote device.  
            handler.BeginSendTo(byteData, 0, byteData.Length, SocketFlags.None, ep,
                new AsyncCallback(SendCallback), state);
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Complete sending the data to the remote device.  
                int bytesSent = listener.EndSend(ar);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }

    }
}
