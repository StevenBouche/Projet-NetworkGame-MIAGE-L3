package coco.state;

import coco.controller.ControllerGameUI;
import coco.controller.ControllerSceneRectancle;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class StateStartGame extends StateGameUI {

    public StateStartGame(ControllerGameUI controller) {
        super(controller);
    }

    @Override
    public void execute() {

    }

    @Override
    public void loadSubScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("panneau.fxml"));
        controller.manager = new ControllerSceneRectancle();
        fxmlLoader.setController(controller.manager);
        try {
            controller.root = fxmlLoader.load();
            controller.subScene.setRoot(controller.root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadButtons() {

    }

    @Override
    public void loadCBD() {

    }

    @Override
    public void loadSwitch() {

    }

    @Override
    public void loadScoreBoard() {

    }


}
