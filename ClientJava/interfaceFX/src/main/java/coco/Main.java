package coco;

import coco.controller.ControllerGameUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    ControllerGameUI managerGameUI;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadPrimaryScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void loadPrimaryScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gameUI.fxml"));
        managerGameUI = new ControllerGameUI(null,null, null,null);
        fxmlLoader.setController(managerGameUI);
        root = fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
