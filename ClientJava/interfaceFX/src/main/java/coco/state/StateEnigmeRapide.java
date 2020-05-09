package coco.state;

import coco.controller.ControllerGameUI;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import network.message.PacketMessage;
import network.tcp.ProtocolEventsTCP;

public class StateEnigmeRapide extends StateGameUI {

    public StateEnigmeRapide(ControllerGameUI controller) {
        super(controller);
    }

    @Override
    public void execute() {
        stateButton();
        setEnigme();
        controller.animEnigmRapide();
    }

    @Override
    public void onClickOnSwitch(Boolean switchActive) {

    }

    private void setEnigme() {
        controller.preSetEnigm();
    }

    protected void stateButton() {
        controller.cbdV.setDisable(true);
        controller.cbdC.setDisable(true);
        controller.switchVoyCons.setDisable(true);
        controller.validLetter.setDisable(true);
        controller.buttonWheel.setDisable(true);
        controller.proposEnigm.setDisable(false);
        controller.validChoice.setDisable(false);

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

    }

    private void handlePropositionEnigma() {

        /** If player propose enigme */
        controller.manager.compareProp(controller.proposEnigm.getText());
        System.out.println("client submit enigm");

        /** Send at server my proposal string **/
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.PROPOSALRESPONSE.eventName;
        msg.data = controller.proposEnigm.getText().trim();
        controller.dataLoad.client.sendMsg(msg);
    }

    private void handlePropositionLetter() {
        /** Compare state of switch button v/c */
        if(controller.switchActive){
            char charCb = controller.cbdV.getValue().toString().charAt(0);
            controller.manager.displayLetter(charCb);
        }
        else{
            char charCb = controller.cbdC.getValue().toString().charAt(0);
            controller.manager.displayLetter(charCb);
        }
        /** When the sending has been done */
        controller.cbdC.setValue(" ");
        controller.cbdV.setValue(" ");
        System.out.println("client submit letter");
    }

}
