package coco.state;

import coco.controller.ControllerGameUI;

public abstract class StateGameUI {

    protected ControllerGameUI controller;

    public StateGameUI(ControllerGameUI controller){
        this.controller = controller;
    }

    public abstract void execute();

    public abstract void loadSubScene();
    public abstract void loadButtons();
    public abstract void loadCBD();
    public abstract void loadSwitch();
    public abstract void loadScoreBoard();

}
