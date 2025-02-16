package network.client;

import network.message.obj.*;

public interface INotifyPlayersGame {

    void startActionEnigmeRapide(Enigme var);
    void receiveFromServeurBadProposalResponse(String currentPlayer, String barRep);
    void receiveFromServeurGoodProposalResponse(String id, String var);
    void receiveFromServeurNotifyCurrentPlayerRound(String var);
    void receiveFromServeurChoiceStep(ChoiceStep var);
    void receiveFromServeurEnigmaOfRound(Enigme var);
    void receiveFromServeurPlayerMoneyInfo(DataMoneyInfo var);
    void notifyDisconnect();
    void receiveFromServeurAskForALetter(String var);
    void receiveFromServeurBadAskForALetter(String id, String letter);
    void receiveFromServeurGoodAskForALetter(String id, String var);
    void receiveFromServeurCaseOfWheel(CaseInfo var);
    void receiveFromServeurEnigmaConsAllBuy(String var);
    void receiveFromServeurAskForAFinalProposition(Proposal var);

    void receiveFromServeurActionFinal(String idPlayer, Enigme e);

    void receiveFromServeurAskForAFinalLetter(FinalLetters var);
    void receiveFromServeurNotifyGoodProposalLetterFinal(ProposalLetter var);

    void receiveFromServeurFinalResultMoney(Integer var);
}
