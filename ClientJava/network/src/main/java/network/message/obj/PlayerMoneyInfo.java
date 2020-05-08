package network.message.obj;

public class PlayerMoneyInfo
{
    public String id;
    public Integer CagnotteRound;
    public Integer CagnotteTotal;

    public PlayerMoneyInfo(String identifiant, int cagR, int cagT)
    {
        this.id = identifiant;
        this.CagnotteRound = cagR;
        this.CagnotteTotal = cagT;
    }

    public PlayerMoneyInfo(){

    }

}
