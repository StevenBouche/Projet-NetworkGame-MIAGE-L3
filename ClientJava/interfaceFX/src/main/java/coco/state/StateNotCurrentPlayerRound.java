package coco.state;

import coco.controller.ControllerGameUI;

public class StateNotCurrentPlayerRound extends StateGameUI {
    public StateNotCurrentPlayerRound(ControllerGameUI controller) {
        super(controller, false, true);
    }

    @Override
    public void execute() {
        stateButton();
        stateChoiceP();
    }
}
