package coco.state;

import coco.controller.ControllerGameUI;

public class StateCurrentPlayerRound extends StateGameUI {

    public StateCurrentPlayerRound(ControllerGameUI controller) {
        super(controller,false, false);
    }

    @Override
    public void execute() {
        stateButton();
        stateChoiceP();
    }

}
