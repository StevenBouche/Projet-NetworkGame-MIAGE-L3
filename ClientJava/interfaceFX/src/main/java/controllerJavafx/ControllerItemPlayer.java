package controllerJavafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
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
    String idValue;
    boolean currentplayer;

    @FXML
    public VBox vbox;

    ControllerLobbiesGame main;
    public ControllerItemPlayer(String id, String name, ControllerLobbiesGame main, boolean currentPlayer){
        idValue = id;
        nameValue = name;
        this.main = main;
        this.currentplayer = currentPlayer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.setText(nameValue);
        this.state.setText("Waiting Connexion");
        ready.setVisible(currentplayer);
        cancel.setVisible(currentplayer);
        ready.setDisable(true);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               backToLobbies();
            }
        });
    }

    private void backToLobbies(){
        main.onCancelAction();
    }

}
