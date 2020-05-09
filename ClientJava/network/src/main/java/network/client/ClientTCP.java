package network.client;

import network.message.PacketMessage;
import network.message.obj.*;
import network.protocol.Protocol;
import network.share.DataListenerTCP;
import network.tcp.INotifyState;
import network.tcp.NetworkManagerTCP;
import network.tcp.ProtocolEventsTCP;

import java.util.List;

public class ClientTCP implements Runnable, INotifyState {

    NetworkManagerTCP managerTCP ;
    String name;
    INotifyPlayersLobby notifier;
    INotifyPlayersGame notifierGame;

    public ClientTCP(String addr, int port, String name) {
        this.name = name;
        managerTCP = new NetworkManagerTCP(this,addr,port);
        initEventLobbyTCP();
        initEventGameTCP();
    }

    /**
     * TO RECEIVE DATA FROM SERVER IN GAME
     */
    private void initEventGameTCP() {

        managerTCP.eventManager.OnEvent(Enigme.class, ProtocolEventsTCP.ACTIONENIGMERAPIDE, new DataListenerTCP<Enigme>() {
            @Override
            public void onData(Enigme var) {
                if(notifierGame != null) notifierGame.startActionEnigmeRapide(var);
            }
        });
        managerTCP.eventManager.OnEvent(Proposal.class, ProtocolEventsTCP.BADPROPOSALRESPONSE, new DataListenerTCP<Proposal>() {
            @Override
            public void onData(Proposal var) {
                if(notifierGame != null) notifierGame.receiveFromServeurBadProposalResponse(var.id,var.proposal);
            }
        });
        managerTCP.eventManager.OnEvent(Proposal.class, ProtocolEventsTCP.GOODPROPOSALRESPONSE, new DataListenerTCP<Proposal>() {
            @Override
            public void onData(Proposal var) {
                if(notifierGame != null) notifierGame.receiveFromServeurGoodProposalResponse(var.id,var.proposal);
            }
        });
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.NOTIFYCURRENTPLAYER, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                if(notifierGame != null) notifierGame.receiveFromServeurNotifyCurrentPlayerRound(var);
            }
        });

        managerTCP.eventManager.OnEvent(ChoiceStep.class, ProtocolEventsTCP.CHOICESTEP, new DataListenerTCP<ChoiceStep>() {
            @Override
            public void onData(ChoiceStep var) {
                if(notifierGame != null) notifierGame.receiveFromServeurChoiceStep(var);
            }
        });
        managerTCP.eventManager.OnEvent(Enigme.class, ProtocolEventsTCP.ACTIONENIGMEPRINCIPALE, new DataListenerTCP<Enigme>() {
            @Override
            public void onData(Enigme var) {
                if(notifierGame != null) notifierGame.receiveFromServeurEnigmaOfRound(var);
            }
        });
        managerTCP.eventManager.OnEvent(DataMoneyInfo.class, ProtocolEventsTCP.UPDATEROUNDMONEY, new DataListenerTCP<DataMoneyInfo>() {
            @Override
            public void onData(DataMoneyInfo var) {
                if(notifierGame != null) notifierGame.receiveFromServeurPlayerMoneyInfo(var);
            }
        });
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.ASKFORALETTER, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                if(notifierGame != null) notifierGame.receiveFromServeurAskForALetter(var);
            }
        });
        managerTCP.eventManager.OnEvent(Proposal.class, ProtocolEventsTCP.BADPROPOSALLETTER, new DataListenerTCP<Proposal>() {
            @Override
            public void onData(Proposal var) {
                if(notifierGame != null) notifierGame.receiveFromServeurBadAskForALetter(var.id,var.proposal);
            }
        });
        managerTCP.eventManager.OnEvent(Proposal.class, ProtocolEventsTCP.GOODPROPOSALLETTER, new DataListenerTCP<Proposal>() {
            @Override
            public void onData(Proposal var) {
                if(notifierGame != null) notifierGame.receiveFromServeurGoodAskForALetter(var.id,var.proposal);
            }
        });

    //    public static ProtocolEventsTCP<int> SENDCASEVALUE = new ProtocolEventsTCP<int>("SENDCASEVALUE");
    }

    private void initEventLobbyTCP() {
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.IDENTITY, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                System.out.println("IDENTITY SET "+var);
                if(notifier != null) notifier.notifyReceiveMyId(var);
            }
        });
        managerTCP.eventManager.OnEvent(ListPlayerGame.class, ProtocolEventsTCP.NOTIFYLOBBYPLAYER, new DataListenerTCP<ListPlayerGame>() {
            @Override
            public void onData(ListPlayerGame var) {
                System.out.println("EVENT PLAYER HAVE JOIN OR LEAVE OR READY");
                if(notifier != null) notifier.notifyReceiveListPlayer(var);
            }
        });
        managerTCP.eventManager.OnEvent(String.class, ProtocolEventsTCP.NOTIFYGAMEREADY, new DataListenerTCP<String>() {
            @Override
            public void onData(String var) {
                if(notifier != null) notifier.notifyGameStart();
            }
        });
    }

    public void setNotifierLobby(INotifyPlayersLobby not){
        this.notifier = not;
    }
    public void removeNotifierLobby(){ this.notifier = null; }

    public void setNotifierGame(INotifyPlayersGame not){
        this.notifierGame = not;
    }
    public void removeNotifierGame(){ this.notifier = null; }

    @Override
    public void run() {
        managerTCP.startListening();
    }

    @Override
    public void onConnect() {
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.IDENTITY.eventName;
        msg.data = this.name;
        managerTCP.sendMsg(msg);
    }

    public void sendMsg(PacketMessage<?> msg){
        managerTCP.sendMsg(msg);
    }

    @Override
    public void onDisconnect() {
        if(notifier != null) notifier.notifyDisconnect();
        if(notifierGame != null) notifierGame.notifyDisconnect();
    }

    public void stop() {
        managerTCP.stop();
    }
}
