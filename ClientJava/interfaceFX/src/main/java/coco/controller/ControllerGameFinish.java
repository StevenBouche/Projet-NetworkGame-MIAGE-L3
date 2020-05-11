package coco.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import network.main.IMain;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameFinish implements Initializable {

    @FXML
    public Label text;
    @FXML
    public Button lobby;

    IMain main;
    PlayerData p;
    int valueFinal;

    public ControllerGameFinish(IMain main, PlayerData p, int valueFinal){
        this.main = main;
        this.p = p;
        this.valueFinal = valueFinal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String str = p.namePlayer+" have win with "+p.cashTotal+" total money \n and "+valueFinal+" with final";
        text.setText(str);
        lobby.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                main.backToMainLobbies();
            }
        });
    }
}
