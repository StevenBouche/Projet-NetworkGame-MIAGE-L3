package coco.state;

import coco.controller.ControllerGameUI;
import coco.controller.ControllerSceneRectancle;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class StateStartGame extends StateGameUI {

    public StateStartGame(ControllerGameUI controller) {
         super(controller,true, true);
    }

    @Override
    public void execute() {
        stateButton();
    }

    @Override
    protected void stateButton() {
        controller.buttonSetEnigm.setDisable(!buttonDisable);
        controller.buttonShowEnigm.setDisable(buttonDisable);
        controller.buttonReset.setDisable(buttonDisable);
    }

}
