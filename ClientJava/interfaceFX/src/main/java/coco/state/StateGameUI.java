package coco.state;

import coco.controller.ControllerGameUI;

public abstract class StateGameUI {

    protected boolean buttonDisable;
    protected boolean choicePlayerDisable;

    protected ControllerGameUI controller;

    public StateGameUI(ControllerGameUI controller, boolean buttonAvailable, boolean choicePlayerDisible){
        this.controller = controller;
        this.buttonDisable = buttonAvailable;
        this.choicePlayerDisable = choicePlayerDisible;
    }

    public abstract void execute();

    protected void stateButton() {
    /*    controller.buttonSetEnigm.setDisable(this.buttonDisable);
        controller.buttonShowEnigm.setDisable(this.buttonDisable);
        controller.buttonReset.setDisable(this.buttonDisable);*/
    }

    protected void stateChoiceP(){
      //  controller.clientChoic.setDisable(this.choicePlayerDisable);
    }

}
