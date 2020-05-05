package controllerJavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerScene implements Initializable {

    @FXML
    public TextArea textareaid;

    private boolean debug;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init controller");
        debug = false;

     //   textArea.append("hello");
    }

    public void appendText(String s) {
        Platform.runLater(() -> {
            textareaid.appendText(s+"\n");
        });
    }

}
