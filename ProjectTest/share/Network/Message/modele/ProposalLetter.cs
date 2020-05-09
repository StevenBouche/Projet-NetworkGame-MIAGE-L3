using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class ProposalLetter
    {
        public String id;
        public char letter;

        public ProposalLetter(String idP, char c)
        {
            this.id = idP;
            this.letter = c;
        }
    }
}
