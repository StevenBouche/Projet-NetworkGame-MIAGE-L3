package coco.state;

import coco.controller.ControllerGameUI;

public class StateEnigme extends StateGameUI {
    public StateEnigme(ControllerGameUI controller) {
        super(controller, true, false);
    }

    @Override
    public void execute() {
        //controller.manager.setEnigm("PETIT OISEAU SUR L'EAU");
        //controller.manager.setRectWithLetter();
    }
}
