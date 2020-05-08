package coco.state;

import coco.controller.ControllerGameUI;
import coco.state.StateGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.message.obj.Choice;
import network.message.obj.ChoiceStep;
import network.message.obj.ChoiceStepEnum;
import network.tcp.ProtocolEventsTCP;

public class StateStartRound extends StateGameUI {

    Boolean stateButton;

    public StateStartRound(ControllerGameUI controllerGameUI, String var) {
        super(controllerGameUI,true,true);
        stateButton = !controller.handlerIdentity.myId.equals(var);
    }

    @Override
    public void execute() {
        controller.buttonSetEnigm.setDisable(stateButton);
        controller.buttonShowEnigm.setDisable(stateButton);
        controller.buttonReset.setDisable(stateButton);
        controller.clientChoic.setDisable(stateButton);
        controller.clientChoic.setDisable(stateButton);
        controller.cbdV.setDisable(stateButton);
        controller.cbdC.setDisable(stateButton);
        controller.switchVoyCons.setDisable(stateButton);
        controller.validLetter.setDisable(stateButton);
        controller.buttonWheel.setDisable(stateButton);

        /** set Event to propose the enigm chosen */
        controller.validLetter.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handlePropositionLetter();
                me.consume();
            }
        });

        /** set Event to propose the enigm chosen */
        controller.validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handlePropositionEnigma();
                me.consume();
            }
        });

        controller.buttonWheel.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handleTurnWheel();
                me.consume();
            }
        });

    }

    private void handleTurnWheel() {
        ChoiceStep ch = new ChoiceStep();
        ch.choiceStep = ChoiceStepEnum.TURNWHEEL.name();
        PacketMessage<ChoiceStep> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CHOICESTEP.eventName;
        msg.data = ch;
        controller.dataLoad.client.sendMsg(msg);
    }

    private void handlePropositionEnigma() {
        ChoiceStep ch = new ChoiceStep();
        ch.choiceStep = ChoiceStepEnum.PROPOSAL.name();
        ch.proposal = controller.proposEnigm.getText();
        PacketMessage<ChoiceStep> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CHOICESTEP.eventName;
        msg.data = ch;
        controller.dataLoad.client.sendMsg(msg);
    }

    private void handlePropositionLetter() {
        ChoiceStep ch = new ChoiceStep();
        ch.choiceStep = ChoiceStepEnum.BUYVOY.name();
        ch.voy = controller.cbdV.getValue();
        PacketMessage<ChoiceStep> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CHOICESTEP.eventName;
        msg.data = ch;
        controller.dataLoad.client.sendMsg(msg);
    }

}
