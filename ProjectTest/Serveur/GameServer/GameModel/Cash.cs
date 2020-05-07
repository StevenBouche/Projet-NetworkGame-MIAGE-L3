using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.GameModel

{
    enum Cash
    {
        //Cash Classique
        CASH_50 = 50, CASH_100 = 100, CASH_150 = 150, CASH_200 = 200, CASH_250 = 250, CASH_300 = 300, CASH_500 = 500,

        //Super Cash + Final
        CASH_1000 = 1000, CASH_2500 = 2500, CASH_5000 = 5000, CASH_10K = 10000,

        //Final Only
        CASH_20K = 20000, CASH_35K = 35000, CASH_50K = 50000, CASH_70K = 70000, CASH_90K = 90000, CASH_100K = 100000
    }
}
