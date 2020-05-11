using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class CaseInfo
    {
        public int value;
        public String type;

        public CaseInfo(int v, String t)
        {
            this.value = v;
            this.type = t;
        }
    }
}
