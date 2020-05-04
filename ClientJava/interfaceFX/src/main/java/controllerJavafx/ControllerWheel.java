package controllerJavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWheel implements Initializable {


    @FXML
    Canvas canvas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle circle = new Circle();
        circle.setCenterX(300.0f);
        circle.setCenterY(135.0f);
        circle.setRadius(100.0f);


    }



}
