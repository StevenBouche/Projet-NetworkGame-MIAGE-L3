package controllerJavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLobbiesGame implements Initializable {

    @FXML
    SubScene scenePlayer1;
    @FXML
    SubScene scenePlayer2;
    @FXML
    SubScene scenePlayer3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
    }

}
