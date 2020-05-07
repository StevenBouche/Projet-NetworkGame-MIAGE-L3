package network.client;

import network.message.obj.Enigme;

public interface INotifyPlayersGame {

    void startActionEnigmeRapide(Enigme var);
    void receiveFromServeurBadProposalResponse(String var);
}
