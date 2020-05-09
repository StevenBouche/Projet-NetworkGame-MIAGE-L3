package coco.state;

import coco.controller.ControllerGameUI;

public abstract class StateGameUI {

    protected ControllerGameUI controller;

    public StateGameUI(ControllerGameUI controller){
        this.controller = controller;
    }

    public abstract void execute();
    public abstract void onClickOnSwitch(Boolean switchActive);

}
