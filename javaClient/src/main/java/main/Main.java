package main;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    Controller manager;
    Parent root;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
  //      loadPrimaryScene();
        primaryStage.setTitle("Hello World");
    //    primaryStage.setScene(new Scene());
        primaryStage.show();
    }

    private void loadPrimaryScene() throws IOException {
//        URL fxmlURL = getClass().getResource("/sample.fxml");
    //    URL fxmlURL = getClass().getResource("sample.fxml");
  //      final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
   //     manager = fxmlLoader.getController();
   //     root = FXMLLoader.load(fxmlURL);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
