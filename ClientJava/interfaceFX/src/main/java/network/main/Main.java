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
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        loadPrimaryScene();
        this.primaryStage.setTitle("Hello World");
        this.primaryStage.resizableProperty().setValue(false);
        this.primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        managerRoot.stop();

    }

    public void setScene(Parent p ){
        primaryStage.setScene(new Scene(root));
    }

    private void loadPrimaryScene() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample.fxml"));
            managerRoot = new ControllerRoot(new IMain() {
                @Override
                public void backToMainLobbies() {
                    managerRoot.backToMainLobbies();
                }

                @Override
                public void switchScene(Parent p) {
                    primaryStage.setScene(new Scene(p));
                }

                @Override
                public double getWidth() {
                    return primaryStage.getWidth();
                }

                @Override
                public double getHeight() {
                    return primaryStage.getHeight();
                }
            });
            fxmlLoader.setController(managerRoot);
            root = fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
