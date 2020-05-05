using Newtonsoft.Json;
using Share.Network.Event;
using Share.Network.Message;
using shortid;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Share.Network.NetworkManager
{
    // State object for reading client data asynchronously  
    public class StateObjectTCP
    {
        public String id;
        // Client  socket.  
        public Socket workSocket = null;
        // Size of receive buffer.  
        public const int BufferSize = 8192;
        // Receive buffer.  
        public byte[] buffer = new byte[BufferSize];
        // Received data string.  
        public StringBuilder sb = new StringBuilder();
    }

    class NetworkManagerTCP
    {
        //HashMap containing all the clients
        Dictionary<String, StateObjectTCP> myClients;

        public EventNetworkManagerTCP eventManager;

        // Thread signal.  
        public ManualResetEvent allDone;

        public NetworkManagerTCP()
        {
            this.myClients = new Dictionary<string, StateObjectTCP>();
            this.eventManager = new EventNetworkManagerTCP();
            this.allDone = new ManualResetEvent(false);
        }

        public void StartListening()
        {
            // Service Event redirection
            Thread t = new Thread(new ThreadStart(eventManager.Run));
            t.Start();

            // Establish the local endpoint for the socket. 
            IPAddress ipAddress = IPAddress.Parse("127.0.0.1");
            IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 10001);

            // Create a TCP/IP socket.  
            Socket listener = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
                
            // Bind the socket to the local endpoint and listen for incoming connections.  
            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(100);

                while (true)
                {
                    // Set the event to nonsignaled state.  
                    allDone.Reset();

                    // Start an asynchronous socket to listen for connections.  
                    Console.WriteLine("Waiting for a connection...");
                    listener.BeginAccept(new AsyncCallback(AcceptCallback), listener);

                    // Wait until a connection is made before continuing.  
                    allDone.WaitOne();
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

            //
            //TODO myclients shutdown handler state object
            //

            listener.Shutdown(SocketShutdown.Both);
            listener.Close();

            Console.WriteLine("\nPress ENTER to continue...");
            Console.Read();

        }

        public void Send<T>(PacketMessage<T> data, String id)
        {
            if (myClients.ContainsKey(id))
            {
                StateObjectTCP state = myClients[id];
                String JSON = JsonConvert.SerializeObject(data);

                if (state.workSocket.Connected)
                {
                    Send(state.workSocket, JSON);
                }
            }
            else
            {
                Console.WriteLine("Error in sending");
            }
        }

        private void AcceptCallback(IAsyncResult ar)
        {
            // Signal the main thread to continue.  
            allDone.Set();

            // Get the socket that handles the client request.  
            Socket listener = (Socket)ar.AsyncState;
            Socket handler = listener.EndAccept(ar);

            // Create the state object
            StateObjectTCP state;
            OnConnect(out state, ref handler);
            StartReceiving(state);
        }

        private void StartReceiving(StateObjectTCP state)
        {
            state.buffer = new byte[4096];
            state.workSocket.BeginReceive(state.buffer, 0, StateObjectTCP.BufferSize, 0, new AsyncCallback(ReadCallback), state);
        }

        private void ReadCallback(IAsyncResult ar)
        {
            // Retrieve the state object and the handler socket  
            // from the asynchronous state object.  
            StateObjectTCP state = (StateObjectTCP) ar.AsyncState;
            Socket handler = state.workSocket;
            int r;

            try
            {
                if ((r = handler.EndReceive(ar)) > 1)
                {
                    String data = Encoding.ASCII.GetString(state.buffer, 0, r);
                    Console.WriteLine(data);
                    eventManager.OnReceivedData(data, state);
                    StartReceiving(state);
                }
                else
                {
                    OnDisconnect(state);
                }
            } catch (SocketException s)
            {
                Console.WriteLine("Socket disconnected because of " + s.Message);
                OnDisconnect(state);
            }
            
        }

        private void OnDisconnect(StateObjectTCP state)
        {
            myClients.Remove(state.id);
        }

        private void OnConnect(out StateObjectTCP state, ref Socket handler)
        {
            state = new StateObjectTCP()
            {
                id = ShortId.Generate(true, true, 12),
                workSocket = handler
            };
            state.workSocket.NoDelay = true;
            myClients.Add(state.id, state);
        }

        private void Send(Socket handler, String data)
        {
            // Convert the string data to byte data using ASCII encoding.  
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            // Begin sending the data to the remote device.  
            handler.BeginSend(byteData, 0, byteData.Length, 0, new AsyncCallback(SendCallback), handler);
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.  
                Socket handler = (Socket)ar.AsyncState;

                // Complete sending the data to the remote device.  
                int bytesSent = handler.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to client.", bytesSent);

            //    handler.Shutdown(SocketShutdown.Both);
            //    handler.Close();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}
