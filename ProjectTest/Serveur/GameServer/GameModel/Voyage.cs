﻿using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Text;

namespace Serveur.GameModel
{
    class Voyage
    {
        string _pays_Destination;

        public Voyage()
        {
            _pays_Destination = "";
        }

      
        public string Pays_Destination { get => _pays_Destination; set => _pays_Destination = value; }

    }
}
