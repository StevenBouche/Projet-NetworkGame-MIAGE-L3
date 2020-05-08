using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class PlayerMoneyInfo
    {
        String id;
        int CagnotteRound;
        int CagnotteTotal;

        public PlayerMoneyInfo(String identifiant, int cagR, int cagT)
        {
            this.id = identifiant;
            this.CagnotteRound = cagR;
            this.CagnotteTotal = cagT;
        }
    }
}
