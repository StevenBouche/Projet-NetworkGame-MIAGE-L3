package controllerJavafx.testwheel;

import coco.controller.PlayerData;
import controllerJavafx.ControllerRoot;
import controllerJavafx.LoaderRessource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.client.ClientTCP;
import network.main.IMain;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    Parent root;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        loadPrimaryScene();
        this.primaryStage.setTitle("Wheel of fortune");
        this.primaryStage.resizableProperty().setValue(false);
        this.primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void setScene(Parent p ){
        primaryStage.setScene(new Scene(root));
    }

    private void loadPrimaryScene() throws IOException {
            LoaderRessource.getInstance();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("wheel.fxml"));
            fxmlLoader.setController(new ControllerWheel());
            root = fxmlLoader.load();
            setScene(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
