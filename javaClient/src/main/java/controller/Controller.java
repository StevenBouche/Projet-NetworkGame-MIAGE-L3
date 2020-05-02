package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Pane paneRoot;
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init controller");
     //   textArea.append("hello");
    }



}
