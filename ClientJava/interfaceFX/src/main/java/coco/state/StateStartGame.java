package coco.state;

import coco.controller.ControllerGameUI;

public class StateStartGame extends StateGameUI {

    public StateStartGame(ControllerGameUI controller) {
         super(controller,true, true);
    }

    @Override
    public void execute() {
        stateButton();
    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {

    }

    protected void stateButton() {
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(true);
        controller.switchVoyCons.setDisable(true);
        controller.validLetter.setDisable(true);
        controller.buttonWheel.setDisable(true);
        controller.proposEnigm.setDisable(true);
        controller.validChoice.setDisable(true);
    }

}
