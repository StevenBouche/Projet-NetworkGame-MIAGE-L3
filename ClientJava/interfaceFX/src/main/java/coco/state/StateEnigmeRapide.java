package coco.state;

import coco.controller.ControllerGameUI;

public class StateEnigmeRapide extends StateGameUI {

    public StateEnigmeRapide(ControllerGameUI controller) {
        super(controller,true, true);
    }

    @Override
    public void execute() {
        stateButton();
    }

    @Override
    protected void stateButton() {
        controller.buttonSetEnigm.setDisable(!buttonDisable);
        controller.buttonShowEnigm.setDisable(buttonDisable);
        controller.buttonReset.setDisable(buttonDisable);
        controller.clientChoic.setVisible(true);
        controller.clientChoic.setDisable(choicePlayerDisable);
    }
    
}
