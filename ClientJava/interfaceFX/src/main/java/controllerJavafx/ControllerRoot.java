package controllerJavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRoot implements Initializable {

    @FXML
    public SubScene subscene;
    @FXML
    public MenuBar menu;

    ControllerLobbies manager;

    Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("lobbie.fxml"));
        manager = new ControllerLobbies();
        fxmlLoader.setController(manager);
        try {
            root = fxmlLoader.load();
            root.maxWidth(subscene.getWidth());
            root.maxHeight(subscene.getHeight());
            subscene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
