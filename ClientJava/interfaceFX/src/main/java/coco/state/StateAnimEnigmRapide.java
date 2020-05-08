package coco.state;

import coco.controller.ControllerGameUI;

public class StateAnimEnigmRapide extends StateGameUI  {
    public StateAnimEnigmRapide(ControllerGameUI controller) {
        super(controller, true, false);
    }

    @Override
    public void execute() {
        animEnigmRapide();
    }

    private void animEnigmRapide() {
        controller.animEnigmRapide();
    }
}
