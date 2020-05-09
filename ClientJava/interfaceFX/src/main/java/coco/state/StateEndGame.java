package coco.state;

import coco.controller.ControllerGameUI;

public class StateEndGame extends StateGameUI {

    public StateEndGame(ControllerGameUI controller) {
        super(controller);
    }

    @Override
    public void execute() {
        controller.board.setDisable(true);
    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {

    }
}
