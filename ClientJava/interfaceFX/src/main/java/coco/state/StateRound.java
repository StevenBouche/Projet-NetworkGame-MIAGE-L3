package coco.state;

import coco.controller.ControllerGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.message.obj.ChoiceStep;
import network.message.obj.ChoiceStepEnum;
import network.tcp.ProtocolEventsTCP;

public class StateRound extends StateGameUI {

    Boolean stateButton;

    public StateRound(ControllerGameUI controllerGameUI, String var) {
        super(controllerGameUI,true,true);
        stateButton = !controller.handlerIdentity.myId.equals(var);
     //   controller.animEnigmRound();
    }

    @Override
    public void execute() {

        controller.cbdV.setDisable(stateButton);
        controller.cbdC.setDisable(true);
        controller.switchVoyCons.setDisable(stateButton);
        controller.buttonWheel.setDisable(stateButton);
        controller.proposEnigm.setDisable(stateButton);
        controller.validChoice.setDisable(stateButton);

        // todo simplifier
        if(!stateButton && controller.switchVoyCons.isSelected()) controller.validLetter.setDisable(false);
        else controller.validLetter.setDisable(true);

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

    @Override
    public void onClickOnSwitch(Boolean switchActive) {
        controller.validLetter.setDisable(!switchActive);
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

        if(controller.proposEnigm.getText().equals("")) return;

        ChoiceStep ch = new ChoiceStep();
        ch.choiceStep = ChoiceStepEnum.PROPOSAL.name();
        ch.proposal = controller.proposEnigm.getText();
        PacketMessage<ChoiceStep> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CHOICESTEP.eventName;
        msg.data = ch;
        controller.dataLoad.client.sendMsg(msg);
    }

    private void handlePropositionLetter() {

        if(controller.cbdV.getValue().equals(" ")) return;

        ChoiceStep ch = new ChoiceStep();
        ch.choiceStep = ChoiceStepEnum.BUYVOY.name();
        ch.voy = controller.cbdV.getValue();
        PacketMessage<ChoiceStep> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.CHOICESTEP.eventName;
        msg.data = ch;
        controller.dataLoad.client.sendMsg(msg);
    }

}
