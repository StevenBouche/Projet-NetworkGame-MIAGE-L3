package coco.controller;

import com.jfoenix.controls.JFXPopup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import network.message.PacketMessage;
import network.message.obj.FinalLetters;
import network.tcp.ProtocolEventsTCP;

import java.net.URL;
import java.util.*;

public class ControllerLetterFinal implements Initializable {

    @FXML
    public ChoiceBox<Character> cons1;
    @FXML
    public ChoiceBox<Character> cons2;
    @FXML
    public ChoiceBox<Character> cons3;
    @FXML
    public ChoiceBox<Character> voy1;
    @FXML
    public Button choiceLetter;

    public Boolean notChoice;

    private ControllerGameUI controllerGameUI;
    JFXPopup popup;

    public ControllerLetterFinal(ControllerGameUI controllerGameUI, JFXPopup popup){
        this.controllerGameUI =  controllerGameUI;
        this.popup = popup;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notChoice = true;
        buildLetters();

    }

    private void buildLetters() {
        Character[] voyC = new Character[]{'A','E','I','O','U','Y'};
        Character[] consC  = new Character[]{'B','C','D','F', 'G', 'H', 'J', 'K','L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W','X', 'Z'};
        List<Character> l = Arrays.asList(consC);
        List<Character> l2 = Arrays.asList(voyC);
        cons1.getItems().addAll(l);
        cons2.getItems().addAll(l);
        cons3.getItems().addAll(l);
        voy1.getItems().addAll(l2);
        choiceLetter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    sendChoiceToServer();
            }
        });
    }

    private void sendChoiceToServer() {

        if(cons1.getValue() != null  && cons2.getValue() != null && cons3.getValue() != null && voy1.getValue() != null){
            FinalLetters f = new FinalLetters();
            f.finalLetters = new ArrayList<Character>();
            f.finalLetters.add(cons1.getValue());
            f.finalLetters.add(cons2.getValue());
            f.finalLetters.add(cons3.getValue());
            f.finalLetters.add(voy1.getValue());

            PacketMessage<FinalLetters> msg  = new PacketMessage<>();
            msg.evt = ProtocolEventsTCP.ASKFORFINALLETTER.eventName;
            msg.data = f;
            controllerGameUI.dataLoad.client.sendMsg(msg);
            popup.hide();
        }

    }

}
