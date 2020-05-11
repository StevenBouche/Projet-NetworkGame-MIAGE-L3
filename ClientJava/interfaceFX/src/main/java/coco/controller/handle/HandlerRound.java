package coco.controller.handle;

import coco.controller.PlayerData;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HandlerRound {

    int manche;
    private String currentPlayerId;
    private String idPlayerHaveProposal; /** quand recu event bad proposal set id player in this variable**/

    private Label displayManche;

    public HandlerRound(Label display){
        this.displayManche = display;
        manche=0;
    }

    public void incManche(){
        manche++;
        Platform.runLater(()->{
            if(manche > 4){
                displayManche.setText("Final");
            } else displayManche.setText("Manche " + manche);
        });
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
