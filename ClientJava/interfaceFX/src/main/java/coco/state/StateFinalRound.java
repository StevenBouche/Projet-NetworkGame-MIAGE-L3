package coco.state;

import coco.controller.ControllerGameUI;
import coco.controller.ControllerLetterFinal;
import com.jfoenix.controls.JFXPopup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import network.message.PacketMessage;
import network.message.obj.Enigme;
import network.message.obj.FinalLetters;
import network.message.obj.Proposal;
import network.tcp.ProtocolEventsTCP;

import java.io.IOException;

public class StateFinalRound extends StateGameUI {

    FXMLLoader fxmlLoader;
    Pane root;
    ControllerLetterFinal controllerLetter;
    Boolean stateButton;

    public StateFinalRound(ControllerGameUI controllerGameUI, String idPlayer, Enigme e) {
        super(controllerGameUI);
        stateButton = !controller.handlerIdentity.myId.equals(idPlayer);
    }

    @Override
    public void execute() {
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(true);
        controller.switchVoyCons.setDisable(true);
        controller.proposEnigm.setDisable(true);
        controller.validChoice.setDisable(true);
        controller.buttonWheel.setDisable(true);
        controller.validLetter.setDisable(true);

        /** set Event to propose the enigm chosen */
        controller.validLetter.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                me.consume();
            }
        });

        /** set Event to propose the enigm chosen */
        controller.validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                me.consume();
            }
        });

        controller.buttonWheel.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                me.consume();
            }
        });

    }

    private void loadRootPanePopUp(JFXPopup popup){
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("popUpFinalLetter.fxml"));
        try {
            controllerLetter = new ControllerLetterFinal(controller,popup);
            fxmlLoader.setController(controllerLetter);
            root = fxmlLoader.load();
            popup.setPopupContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {

    }

    public void askForAFinalLetter(FinalLetters var){
        System.out.println("Ask for a number popup");
        popUp();
    }

    public void popUp(){


            AnchorPane a = controller.board;
            JFXPopup popup = new JFXPopup();

            loadRootPanePopUp(popup);
            popup.setAutoHide(false);

            popup.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                        controller.board.setDisable(false);
                }
            });

            showPopup(popup,a);
            // https://stackoverflow.com/questions/12935953/javafx-class-controller-scene-reference

            controller.board.setDisable(true);



    }

    private void showPopup(JFXPopup popup,AnchorPane a) {
        popup.show(a, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT,
                (a.getWidth() - root.getPrefWidth()) / 2,
                (a.getHeight() - root.getPrefHeight()) / 2);
    }


    public void switchToPropositionFinal(Proposal var) {
        controller.proposEnigm.setDisable(false);
        controller.validChoice.setDisable(false);
        /** set Event to propose the enigm chosen */
        controller.validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                sendFinalProposition(var);
                me.consume();
            }
        });

    }

    private void sendFinalProposition(Proposal var) {
        if(!controller.proposEnigm.getText().equals("")){
            var.id = controller.handlerIdentity.myId;
            var.proposal = controller.proposEnigm.getText();
            PacketMessage<Proposal> msg = new PacketMessage<>();
            msg.evt = ProtocolEventsTCP.ASKFORFINALPROPOSITION.eventName;
            msg.data = var;
            controller.dataLoad.client.sendMsg(msg);
            controller.proposEnigm.setText("");
        }
    }
}
