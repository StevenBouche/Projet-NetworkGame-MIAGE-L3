package network.main;

import controllerJavafx.ControllerRoot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    ControllerRoot managerRoot;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample.fxml"));
            managerRoot = new ControllerRoot();
            fxmlLoader.setController(managerRoot);
            root = fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
