
using Share.Network.Server;
using System;
using System.Threading;

namespace Serveur
{
    class Program
    {
        static void Main(string[] args)
        {
            // Supply the state information required by the task.

             //ServerUDP myServer = new ServerUDP();
             ServerTCP myServer = new ServerTCP(10001);

            // Create a thread to execute the task, and then
            // start the thread.

            Thread t = new Thread(new ThreadStart(myServer.Run));
            t.Start();
            Console.WriteLine("Main thread does some work, then waits.");
            t.Join();
            Console.WriteLine("Independent task has completed; main thread ends.");
        }
    }
}
