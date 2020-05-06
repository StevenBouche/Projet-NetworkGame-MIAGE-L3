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
    boolean readyPlayer;
    int nbPlayerLobby;
    @FXML
    public VBox vbox;

    ControllerLobbiesGame main;
    public ControllerItemPlayer(String id, String name, ControllerLobbiesGame main, boolean currentPlayer, boolean ready, int nbPlayerLobby){
        idValue = id;
        nameValue = name;
        this.main = main;
        this.currentplayer = currentPlayer;
        this.readyPlayer = ready;
        this.nbPlayerLobby = nbPlayerLobby;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtonReady();
        initButtonCancel();
        initLabel();
    }

    private void initLabel() {
        this.name.setText(nameValue);
        if(this.readyPlayer) this.state.setText("Ready");
        else this.state.setText("Not Ready");

    }

    private void initButtonCancel() {
        cancel.setVisible(currentplayer);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backToLobbies();
            }
        });
    }

    private void initButtonReady() {
        ready.setVisible(currentplayer);
        if(readyPlayer) {
            ready.setText("Not ready");
            ready.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    actionButtonReady(false);
                }
            });
        } else {
            ready.setText("Ready");
            ready.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    actionButtonReady(true);
                }
            });
        }
    }

    private void actionButtonReady(boolean b) {
        main.UpdateReadyPlayer(b);
    }

    private void backToLobbies(){
        main.onCancelAction();
    }

}
