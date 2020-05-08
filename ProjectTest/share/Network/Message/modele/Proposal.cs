using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class Proposal 
    {
        public String id;
        public String proposal;

        public Proposal(String idP, String prop)
        {
            this.id = idP;
            this.proposal = prop;
        }


    }
}
