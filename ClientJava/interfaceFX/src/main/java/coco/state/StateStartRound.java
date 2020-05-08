package coco.state;

import coco.controller.ControllerGameUI;
import coco.state.StateGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.tcp.ProtocolEventsTCP;

public class StateStartRound extends StateGameUI {

    Boolean stateButton;

    public StateStartRound(ControllerGameUI controllerGameUI, String var) {
        super(controllerGameUI,true,true);
        stateButton = !controller.handlerIdentity.myId.equals(var);
    }

    @Override
    public void execute() {
        controller.buttonSetEnigm.setDisable(stateButton);
        controller.buttonShowEnigm.setDisable(stateButton);
        controller.buttonReset.setDisable(stateButton);
        controller.clientChoic.setDisable(stateButton);
        controller.clientChoic.setDisable(stateButton);
        controller.cbdV.setDisable(stateButton);
        controller.cbdC.setDisable(stateButton);
        controller.switchVoyCons.setDisable(stateButton);
        controller.validLetter.setDisable(stateButton);
        controller.buttonWheel.setDisable(stateButton);

        /** set Event to propose the enigm chosen */
        controller.validLetter.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handlePropositionLetter();
                me.consume();
            }
        });

        /** set Event to propose the enigm chosen */
        controller.validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handlePropositionEnigma();
                me.consume();
            }
        });

    }

    private void handlePropositionEnigma() {

    }

    private void handlePropositionLetter() {

    }

}
