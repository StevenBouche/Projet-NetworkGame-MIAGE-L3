package network.client;

import network.message.obj.ChoiceStep;
import network.message.obj.Enigme;

public interface INotifyPlayersGame {

    void startActionEnigmeRapide(Enigme var);
    void receiveFromServeurBadProposalResponse(String currentPlayer, String barRep);
    void receiveFromServeurGoodProposalResponse(String id, String var);
    void receiveFromServeurNotifyCurrentPlayerRound(String var);
    void receiveFromServeurChoiceStep(ChoiceStep var);
    void receiveFromServeurEnigmaOfRound(Enigme var);

}
