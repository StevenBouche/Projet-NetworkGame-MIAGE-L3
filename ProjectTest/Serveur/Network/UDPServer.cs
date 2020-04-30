using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Serveur.Network
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

    class UDPServer : IReceiverNetwork
    {

        ManualResetEvent allDone = new ManualResetEvent(false);
        EndPoint localEndPoint = new IPEndPoint(IPAddress.Any, 11000);
        Socket listener;
        int totReceive = 0;

        private EventNetworkManager evtNetManager;

        private delegate void OnReceivedDataDel(String obj);
        OnReceivedDataDel delegates;
        Dictionary<IReceiverNetwork, OnReceivedDataDel> listDelegates;

        public UDPServer()
        {
            listDelegates = new Dictionary<IReceiverNetwork, OnReceivedDataDel>();
            evtNetManager = new EventNetworkManager();
            AddListenerReceivedData(this);
            AddListenerReceivedData(evtNetManager);
            //  delegates = new OnReceivedDataDel(evtNetManager.OnReceivedData);
            Thread t = new Thread(new ThreadStart(evtNetManager.Run));
            t.Start();
        }

        public void OnReceivedData(String obj)
        {
           // Console.WriteLine("SERVER : "+ obj);
        }

        private void AddListenerReceivedData(IReceiverNetwork irn)
        {
            if (listDelegates.ContainsKey(irn)) return;
            listDelegates.Add(irn, new OnReceivedDataDel(irn.OnReceivedData));
            listDelegates.TryGetValue(irn, out OnReceivedDataDel res);
            delegates += res;
        }

        private void RemoveListenerReceivedData(IReceiverNetwork irn)
        {
            listDelegates.TryGetValue(irn, out OnReceivedDataDel res);
            if (res != null)
            {
                delegates -= res;
                listDelegates.Remove(irn);
            }
        }


        public  void StartListening()
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
                    listener.BeginReceiveFrom(state.buffer, 0, StateObject.BufferSize, SocketFlags.None, ref localEndPoint, new AsyncCallback(ReadCallback),state);

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
            int bytesRead = listener.EndReceiveFrom(ar,ref ep);
            //  Console.WriteLine("END RECEIVE FROM " + bytesRead);

            if (bytesRead > 0)
            {
                // There  might be more data, so store the data received so far.  
                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));
               
                // Check for end-of-file tag. If it is not there, read
                // more data.  
                content = state.sb.ToString();
                delegates(content);
                totReceive++;
          //      Console.WriteLine($"Received  from {ep} : {content}  TOT {totReceive}");
    
                Send(listener, content, ep);
          
            }
        }

        private void Send(Socket handler, String data, EndPoint ep)
        {
            // Convert the string data to byte data using ASCII encoding.  
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            StateObject state = new StateObject();

            // Begin sending the data to the remote device.  
            handler.BeginSendTo(byteData, 0, byteData.Length, SocketFlags.None,  ep,
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
