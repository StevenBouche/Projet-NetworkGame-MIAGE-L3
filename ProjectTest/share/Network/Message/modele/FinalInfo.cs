﻿using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class FinalInfo
    {
        String id;
        Enigme enigme;

        public FinalInfo(String id, Enigme e)
        {
            this.id = id;
            this.enigme = e;
        }

    }
}
