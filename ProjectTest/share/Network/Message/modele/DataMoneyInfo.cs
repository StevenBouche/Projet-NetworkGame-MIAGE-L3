using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class DataMoneyInfo
    {
        public List<PlayerMoneyInfo> ListInfo;
        

        public DataMoneyInfo()
        {
            ListInfo = new List<PlayerMoneyInfo>();
        }


        public void AddInfo(params PlayerMoneyInfo[] p)
        {
            foreach(PlayerMoneyInfo pm in p)
            {
                ListInfo.Add(pm);
            }
        }
    }
}
