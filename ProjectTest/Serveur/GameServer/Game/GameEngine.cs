using GameModel;
using Serveur.CommandPack;
using Serveur.GameModel;
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

namespace Serveur.Game
{
    class GameEngine
    {
        //Hold On

        public Model m { get; }
        
        int manche;
        public int IndexLoop { get; set; }
        public int IndexJoueur { get; set; }
        
        public Joueur[] Joueurs { get; }
        public Joueur CurrentPlayer { get; set; }
        public Case CurrentCase { get; set; }
        
        CommandManager CM;

        public GameEngine()
        {
            m = new Model();
            Joueurs = new Joueur[3];
            CM = new CommandManager(this);
        }

        public void Play()
        {
            GameInit();
            GameLoop();
            GameFinish();
        }

        public void GameInit()
        {
            manche = 0;
            m.ListJoueur.Keys.CopyTo(Joueurs, 0);
            IndexLoop = 0;
            IndexJoueur = 0;
            Console.WriteLine(" Init : \n" + m.ToString());

        }

        public void GameLoop()
        {
            while (IndexLoop < 15)
            {
                Random r = new Random();
                manche++;

                if (IndexLoop == 0)
                {
                    //Un joueur aléatoire découvre l'énigme, il prend la main
                    int RandParam = r.Next(3);
                    this.CurrentPlayer = Joueurs[RandParam];
                    IndexJoueur = RandParam;
                    IndexLoop++;
                }
                else if (IndexJoueur > 2)
                {
                    IndexJoueur = 0;
                    CurrentPlayer = Joueurs[IndexJoueur];
                }
                else
                    CurrentPlayer = Joueurs[IndexJoueur];

                
                Console.WriteLine("Manche : " + IndexLoop + "\n Joueur actif : " + CurrentPlayer.nom);

                CurrentCase = m.R.OnWheelTurn();

                if (CurrentCase.t == TypeCase.CASH || CurrentCase.t == TypeCase.SUPER_CASH)
                {
                    CM.AddCashCommand();
                    IndexLoop++;
                    IndexJoueur++;
                }
                else if (CurrentCase.t == TypeCase.PASSE)
                {
                    CM.AddPassCommand();
                }
                else
                {
                    IndexLoop++;
                    IndexJoueur++;
                }
            }

            Console.WriteLine(m.ToString());

        }

        public void GameFinish()
        {

        }
    }
}

