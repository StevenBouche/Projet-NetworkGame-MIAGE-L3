package coco.state;

import coco.controller.ControllerGameUI;
import network.message.obj.Enigme;

public class StateFinalRound extends StateGameUI {


    public StateFinalRound(ControllerGameUI controllerGameUI, String idPlayer, Enigme e) {
        super(controllerGameUI);

    }

    @Override
    public void execute() {

    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {

    }
}
