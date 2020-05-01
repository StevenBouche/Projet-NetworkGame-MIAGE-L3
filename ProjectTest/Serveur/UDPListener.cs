using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.Network
{
    using System;
    using System.Net;
    using System.Net.Sockets;
    using System.Text;
    using System.Threading;

    public class UDPListener
    {
        private const int listenPort = 11000;

        public void StartListener()
        {
            UdpClient listener = new UdpClient(listenPort);
            IPEndPoint groupEP = new IPEndPoint(IPAddress.Any, listenPort);
            String data;
            Boolean loop = true;

            try
            {
                while (loop)
                {
                    Console.WriteLine("Waiting for broadcast");
                   // listener.ReceiveAsync
                    byte[] bytes = listener.Receive(ref groupEP);
                    Console.WriteLine($"Received broadcast from {groupEP} :");
                    data = Encoding.ASCII.GetString(bytes, 0, bytes.Length);
                    Console.WriteLine(data);
                    byte[] sendbuf = Encoding.ASCII.GetBytes(data);
                    listener.Send(sendbuf, sendbuf.Length, groupEP.Address.ToString(), groupEP.Port);
                    //SendData(data, groupEP);
                }
            }
            catch (SocketException e)
            {
                Console.WriteLine(e);
            }
            catch (ThreadInterruptedException e)
            {
                Console.WriteLine(e);
                loop = false;

            }

            listener.Close();
            
        }

        public void SendData(String str, IPEndPoint endpoint)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            byte[] sendbuf = Encoding.ASCII.GetBytes(str);
            s.SendTo(sendbuf, endpoint);
            Console.WriteLine("Message sent to the broadcast address");
        }

    }
}
