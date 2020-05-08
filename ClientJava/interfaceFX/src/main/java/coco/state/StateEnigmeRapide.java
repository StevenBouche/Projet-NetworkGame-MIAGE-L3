package coco.state;

import coco.controller.ControllerGameUI;

public class StateEnigmeRapide extends StateGameUI {

    public StateEnigmeRapide(ControllerGameUI controller) {
        super(controller,true, false);
    }

    @Override
    public void execute() {
        stateButton();
        setEnigme();
        controller.animEnigmRapide();
    }

    private void setEnigme() {
        controller.preSetEnigm();
        controller.animationDisplayLetters();
        controller.updateThemeEnigm();
    }

    @Override
    protected void stateButton() {
        controller.buttonSetEnigm.setDisable(!buttonDisable);
        controller.buttonShowEnigm.setDisable(buttonDisable);
        controller.buttonReset.setDisable(buttonDisable);
        controller.clientChoic.setVisible(true);
        controller.clientChoic.setDisable(choicePlayerDisable);
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(true);
        controller.switchVoyCons.setDisable(true);
        controller.validLetter.setDisable(true);
    }

}
