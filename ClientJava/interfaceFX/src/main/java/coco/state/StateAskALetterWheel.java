package coco.state;

import coco.controller.ControllerGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.tcp.ProtocolEventsTCP;

public class StateAskALetterWheel extends StateGameUI {


    public StateAskALetterWheel(ControllerGameUI controller, boolean buttonAvailable, boolean choicePlayerDisible) {
        super(controller, buttonAvailable, choicePlayerDisible);
    }

    @Override
    public void execute() {
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(false);
        controller.switchVoyCons.setDisable(false);
        controller.validLetter.setDisable(false);
        controller.buttonWheel.setDisable(true);
        controller.proposEnigm.setDisable(true);
        controller.validChoice.setDisable(true);
        initButton();
    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {
        controller.validLetter.setDisable(switchActive);
    }

    private void initButton() {

        controller.validLetter.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handleAskALetter();
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

    private void handleAskALetter() {
        if(!controller.cbdC.getValue().equals(" ")){
            PacketMessage<String> msg = new PacketMessage<>();
            msg.evt = ProtocolEventsTCP.ASKFORALETTER.eventName;
            msg.data = controller.cbdC.getValue();
            controller.dataLoad.client.sendMsg(msg);
            controller.validLetter.setDisable(true);
        }
    }

}
