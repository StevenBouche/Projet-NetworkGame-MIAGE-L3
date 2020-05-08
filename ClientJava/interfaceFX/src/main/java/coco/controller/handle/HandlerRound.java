package coco.controller.handle;

import coco.controller.PlayerData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HandlerRound {

    int manche;
    private String currentPlayerId;
    private String idPlayerHaveProposal; /** quand recu event bad proposal set id player in this variable**/

    private Label displayManche;

    public HandlerRound(Label display){
        this.displayManche = display;
    }
    /**
     * This function set the number of the round
     *
     * @param numM is the number of the round
     */
    public void setManche(int numM){
        manche = numM;
        displayManche.setText("Manche n°" + manche);
    }

    public void setIdPlayerHaveProposal(String idPlayerHaveProposal){
        this.idPlayerHaveProposal = idPlayerHaveProposal;
    }

    public String getIdPlayerHaveProposal(){
        return this.idPlayerHaveProposal;
    }

    public String getCurrentIdPlayer(){
        return this.currentPlayerId;
    }

    public void setCurrentPlayerId(String var) {
        this.currentPlayerId = var;
    }
}
