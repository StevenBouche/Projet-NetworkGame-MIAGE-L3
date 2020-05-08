package coco.state;

import coco.controller.ControllerGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.message.obj.ChoiceStep;
import network.message.obj.ChoiceStepEnum;
import network.tcp.ProtocolEventsTCP;

public class StateAskALetterWheel extends StateGameUI {


    public StateAskALetterWheel(ControllerGameUI controller, boolean buttonAvailable, boolean choicePlayerDisible) {
        super(controller, buttonAvailable, choicePlayerDisible);
    }

    @Override
    public void execute() {

        controller.buttonSetEnigm.setDisable(true);
    //    controller.clientChoic.setDisable(true);
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(false);
        controller.switchVoyCons.setDisable(true);
        controller.validLetter.setDisable(false);
        controller.buttonWheel.setDisable(true);
        initButton();
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
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.ASKFORALETTER.eventName;
        msg.data = controller.cbdC.getValue();
        controller.dataLoad.client.sendMsg(msg);
    }


}
