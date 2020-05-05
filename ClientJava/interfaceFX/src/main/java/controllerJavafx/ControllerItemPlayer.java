package controllerJavafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import network.main.IMain;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerItemPlayer implements Initializable {

    @FXML
    public Label name;
    @FXML
    public Label state;
    @FXML
    public ProgressIndicator loader;
    @FXML
    public Button ready;
    @FXML
    public Button cancel;

    String nameValue;

    IMain main;
    public ControllerItemPlayer(String name, IMain main){
        nameValue = name;
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.setText(nameValue);
        this.state.setText("Waiting Connexion");
        ready.setDisable(true);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               backToLobbies();
            }
        });
    }

    private void backToLobbies(){
        //todo ? deconnecte
        System.out.println("todo ? deconnecte");
        Platform.runLater(() -> {
            main.backToMainLobbies();
        });
    }

}
