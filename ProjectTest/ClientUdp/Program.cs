using Newtonsoft.Json;
using Serveur.Network;
using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ClientUdp
{
    class Program
    {
        static void Main(string[] args)
        {
            Random random = new Random();

            int portClient = random.Next(10001, 11000);
            UdpClient udpClient = new UdpClient(portClient);
            Boolean running = true;
            try
            {
                udpClient.Connect("51.210.12.245", 11000);
                int cpt = 0;

                ProtocolMessage<String> pm = new ProtocolMessage<String>();
                pm.evt = ProtocolEvents.SUBSCRIBE;

                while (running)
                {
                    
                    pm.data = "HI " + cpt;

                    string json = JsonConvert.SerializeObject(pm, Formatting.Indented);

                    // Sends a message to the host to which you have connected.
                    Byte[] sendBytes = Encoding.ASCII.GetBytes(json);

                    //       udpClient.Send(sendBytes, sendBytes.Length);

                    // Sends a message to a different host using optional hostname and port parameters.
              //      UdpClient udpClientB = new UdpClient();
                    udpClient.Send(sendBytes, sendBytes.Length);

                    //IPEndPoint object will allow us to read datagrams sent from any source.
                    Console.WriteLine("waiting message ");
                    IPEndPoint RemoteIpEndPoint = new IPEndPoint(IPAddress.Any, portClient);
                    Byte[] receiveBytes = udpClient.Receive(ref RemoteIpEndPoint);

                    // Blocks until a message returns on this socket from a remote host.
                    //      Byte[] receiveBytes = udpClient.Receive(ref RemoteIpEndPoint);
                    string returnData = Encoding.ASCII.GetString(receiveBytes);

                    ProtocolNetwork pn = JsonConvert.DeserializeObject<ProtocolNetwork>(returnData);

                    if(pn.evt == ProtocolEvents.SUBSCRIBE)
                    {
                        pm = JsonConvert.DeserializeObject <ProtocolMessage<String>>(returnData);
                    }

                    // Uses the IPEndPoint object to determine which of these two hosts responded.
                    Console.WriteLine("This is the message you received " +
                                                 returnData.ToString());
                    Console.WriteLine("This message was sent from " +
                                                RemoteIpEndPoint.Address.ToString() +
                                                " on their port number " +
                                                RemoteIpEndPoint.Port.ToString());


                    cpt++;
                    if (cpt == 2000) running = false;
                }
                

                udpClient.Close();
              //  udpClientB.Close();
               
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}
