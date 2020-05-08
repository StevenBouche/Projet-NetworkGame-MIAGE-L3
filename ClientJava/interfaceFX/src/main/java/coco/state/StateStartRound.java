package coco.state;

import coco.controller.ControllerGameUI;
import coco.state.StateGameUI;

public class StateStartRound extends StateGameUI {

    String idPlayerCurrent;
    Boolean stateButton;

    public StateStartRound(ControllerGameUI controllerGameUI, String var) {
        super(controllerGameUI,true,true);
        stateButton = !controller.myId.equals(var);
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
    }

}
