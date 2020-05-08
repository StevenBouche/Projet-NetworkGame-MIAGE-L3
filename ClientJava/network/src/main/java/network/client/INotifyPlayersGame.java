package network.client;

import javafx.application.Platform;
import network.message.obj.ChoiceStep;
import network.message.obj.Enigme;
import network.message.obj.PlayerMoneyInfo;

public interface INotifyPlayersGame {

    void startActionEnigmeRapide(Enigme var);
    void receiveFromServeurBadProposalResponse(String currentPlayer, String barRep);
    void receiveFromServeurGoodProposalResponse(String id, String var);
    void receiveFromServeurNotifyCurrentPlayerRound(String var);
    void receiveFromServeurChoiceStep(ChoiceStep var);
    void receiveFromServeurEnigmaOfRound(Enigme var);
    void receiveFromServeurPlayerMoneyInfo(PlayerMoneyInfo var);
    
    void notifyDisconnect();

    void receiveFromServeurAskForALetter(String var);

    void receiveFromServeurBadAskForALetter(String id);
    void receiveFromServeurGoodAskForALetter(String id, String var);
}
