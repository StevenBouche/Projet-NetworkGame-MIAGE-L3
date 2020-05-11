package coco.controller;

import coco.controller.handle.HandlerEnigma;
import coco.controller.handle.HandlerMyIdentity;
import coco.controller.handle.HandlerPlayerDataTable;
import coco.controller.handle.HandlerRound;
import coco.state.*;
import com.jfoenix.controls.JFXPopup;
import controllerJavafx.LoaderRessource;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import network.client.ClientTCP;
import network.client.INotifyPlayersGame;
import network.main.IMain;
import network.message.obj.*;

import javafx.scene.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControllerGameUI implements Initializable, INotifyPlayersGame {

    /** FXML elements */
    @FXML
    public SubScene subScene;
    @FXML
    public TextField proposEnigm;
    @FXML
    public Button validChoice;
    @FXML
    public CheckBox switchVoyCons;
    @FXML
    public Pane weelPane;
    @FXML
    public TableView<PlayerData> tableView;
    @FXML
    public TextArea log;
    @FXML
    public Label displayTheme; //for loadScoreBoard()
    @FXML
    public AnchorPane board;
    @FXML
    public Button buttonWheel;
    @FXML
    public ChoiceBox<String> cbdV; //for loadCBD()
    @FXML
    public ChoiceBox<String> cbdC;
    @FXML
    public Button validLetter;
    @FXML
    public Label labelCase;

    boolean finish = false;

 //   final WebView browser = new WebView();
    // public WebEngine webEngine;

    public Boolean switchActive; //switchActive = true -> voyelle switchActive = false -> consonne
    public int nbrRectWithLetter; // ?
    public StackPane rootpopup;

    /** Sub Scene Element **/
    public ControllerSceneRectancle manager;
    public Parent root;

  //  Timer timerAnimLetter;
 //   TimerTask taskTimer;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Thread taskQuickRound;
    ScheduledExecutorService executorPopup;

    /** Data comming from lobby**/
    public DataLoadGame dataLoad;

    /** Handler UI **/
    public HandlerEnigma handlerEnigme ;
    public HandlerPlayerDataTable handlerPlayerDataTable;
    public HandlerMyIdentity handlerIdentity;
    public HandlerRound handlerRound;

    /** My current state **/
    StateGameUI stateUI;
    IMain main;
    boolean alreadyStop;

    @FXML
    public Label nbManche;

    public Lock lock = new ReentrantLock();
    public Condition animLetter = lock.newCondition();

    public ControllerGameUI(ClientTCP client, Thread clientThread, List<PlayerData> data, String myId, IMain main) {
        alreadyStop = false;
        buildDataLoad(client,clientThread,data,myId);
        this.main = main;
    }

    private void buildDataLoad(ClientTCP client, Thread clientThread, List<PlayerData> data, String myId) {
        dataLoad = new DataLoadGame();
        dataLoad.client = client;
        dataLoad.clientThread = clientThread;
        dataLoad.listPlayerData = data;
        dataLoad.myId = myId;
        if(client != null) client.setNotifierGame(this); //sub event client tcp
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchActive = false;
        try {
            createHandler();
            loadTextField();
            loadSubScene();
            loadCBD();
            loadSwitch();
            loadBackGround();
        //    setPopup();
            labelCase.setText("");
            setAndExecuteState(new StateStartGame(this)); // todo tous doit etre desactiver et le panneau refresh
            loadWebWheel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHandler() {
        handlerEnigme = new HandlerEnigma(displayTheme,buttonWheel);
        handlerEnigme.setCallbackForEnigmaChange(manager);
        handlerPlayerDataTable = new HandlerPlayerDataTable(tableView,dataLoad.listPlayerData);
        handlerIdentity = new HandlerMyIdentity(dataLoad.myId);
        handlerRound = new HandlerRound(nbManche);
    }

    private void loadTextField() {
        proposEnigm.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    public void setAndExecuteState(StateGameUI state){
        stateUI = state;
        stateUI.execute();
    }

    /**
     * This function set up enigm board
     *
     * @throws IOException
     */
    private void loadSubScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("panneau.fxml"));
        manager = new ControllerSceneRectancle(handlerEnigme,this);
        fxmlLoader.setController(manager);
        root = fxmlLoader.load();
        subScene.setRoot(root);
    }

    /**
     * This function set Event switch button voyelle / consonne
     * and set default letter chosen
     */
    public void loadSwitch(){
        switchVoyCons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchActive = !switchActive;
                changeChoiceB(cbdV, cbdC);
                cbdC.setValue(" ");
                cbdV.setValue(" ");
                stateUI.onClickOnSwitch(switchActive);
                mouseEvent.consume();
            }
        });
    }

    /**
     * This function create 2 CheckChoice
     * 1st to consone
     * 2nd to voyelle
     */
    public void loadCBD(){
        cbdV.getItems().addAll(" ", "A", "E", "I", "O", "U", "Y");
        cbdC.getItems().addAll(" ", "B", "C", "D", "F", "G", "H", "J", "K","L", "M", "N", "P", "Q", "R", "S", "T", "V", "W","X", "Z");
        validLetter.setText("LETTRE");
        validChoice.setText("ENIGME");
        /** Set visibility of ChoiceBoxs */
        changeChoiceB(cbdV, cbdC);
    }

    /**
     * This function set the visibility of
     * the Choiceboxs depending on switch button
     *
     * @param cV is the CheckBox of Voyelles
     * @param cC is the CheckBox of Consonnes
     */
    public void changeChoiceB(ChoiceBox cV, ChoiceBox cC){
        if(switchActive) {
            cV.setVisible(true);
            cC.setVisible(false);
            validLetter.setText("Valid a voyelle");
        }
        else{
            cV.setVisible(false);
            cC.setVisible(true);
            validLetter.setText("Valid a conson");
        }
    }

    private void log(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String newString = formatter.format(date)+" : "+str+"\n";
        log.appendText(newString);
    }

    public Map<Integer, Rect> mapRectWithLetter;
    public void animEnigmRapide() {
        mapRectWithLetter = new HashMap<>();
        nbrRectWithLetter = 0;
        manager.mapRect.forEach((idR, r)->{
            if(manager.mapRect.get(idR).getStat() == StateOfRect.LETTRE_HIDDEN ||
                    manager.mapRect.get(idR).getStat() == StateOfRect.LETTRE_SPECIAL ){
                nbrRectWithLetter++;
                mapRectWithLetter.put(nbrRectWithLetter, r);
            }
        });
        animShowLetter();
    }

    public void animShowLetter(){

        Runnable quickRound = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("tryshowRandomLetter");
                try{
                    while(!Thread.currentThread().isInterrupted()){
                        Enigme e = handlerEnigme.getCurrentEnigme();
                        if(e == null) Thread.currentThread().interrupt();
                        System.out.println("TASK");
                        if(!e.order.isEmpty()){
                            char c = e.order.remove(0);
                            manager.displayLetter(c);
                            Thread.sleep(2000);
                        } else {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                    if(manager.tempoAnimColor!= null)manager.tempoAnimColor.cancel(true);
                    if(manager.tempoDisplayLetters!= null)manager.tempoDisplayLetters.cancel(true);
                    Thread.currentThread().interrupt();
                }
            }
        };

        taskQuickRound = new Thread(quickRound);
        taskQuickRound.start();

    }

    public void tryshowRandomLetter() {



    }

    private void loadBackGround() {

        Image image = LoaderRessource.getInstance().wheelBackground;

        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // create Background
        Background background = new Background(backgroundimage);

        // set background
        board.setBackground(background);

    }

    @Override
    public void startActionEnigmeRapide(Enigme varE) {
        ControllerGameUI controller = this;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("startActionEnigmeRapide");
                handlerEnigme.setCurrentEnigme(varE);
                setAndExecuteState(new StateEnigmeRapide(controller));
                handlerRound.incManche();
                manager.resetPanneau(handlerEnigme.getCurrentEnigmeLabel());
                lock.lock();
                try {
                    animLetter.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                animShowLetter();
            }
        };
        executor.submit(r);


    }

    int cpt;
    @Override
    public void receiveFromServeurBadProposalResponse(String var, String badReponse) {
        handlerRound.setIdPlayerHaveProposal(var);
        PlayerData p = handlerPlayerDataTable.getPlayerData(var);
        Platform.runLater(() -> {
            manager.compareProp(badReponse);
            log("Bad response from "+p.namePlayer+" : "+badReponse);
        });
    }

    private void waitAnimationQuickRound(){

        if(taskQuickRound != null){
            taskQuickRound.interrupt();
            try {
                taskQuickRound.join();
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void receiveFromServeurGoodProposalResponse(String id, String proposal) {
        ControllerGameUI ui = this;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("receiveFromServeurGoodProposalResponse");
                waitAnimationQuickRound();
                PlayerData p = handlerPlayerDataTable.getPlayerData(id);
                handlerRound.setIdPlayerHaveProposal(id);
                manager.compareProp(proposal); // couleur vert
                manager.displayEnigm(); // montre toute l'enigme
                log("Good response from "+p.namePlayer+" : "+proposal);
                manager.waitDisplayLetter();
                manager.waitAnimColor();
                setAndExecuteState(new StateRound(ui,id)); //todo rename
            }
        };
        executor.submit(r);
    }

    @Override
    public void receiveFromServeurNotifyCurrentPlayerRound(String var) {
        ControllerGameUI ui = this;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("receiveFromServeurNotifyCurrentPlayerRound");
                handlerRound.setCurrentPlayerId(var);
                PlayerData p = handlerPlayerDataTable.getPlayerData(var);
                log("Current player is "+p.namePlayer);
                setAndExecuteState(new StateRound(ui,var)); //todo rename
            }
        };
        executor.submit(r);
    }

    @Override
    public void receiveFromServeurChoiceStep(ChoiceStep var) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("receiveFromServeurChoiceStep");
                log("You need to choice between buy vowel, turn wheel or proposal enigma");
            }
        };
        executor.submit(r);
    }

    @Override
    public void receiveFromServeurEnigmaOfRound(Enigme var) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("receiveFromServeurEnigmaOfRound");
                handlerEnigme.setCurrentEnigme(var);
                manager.resetPanneau(handlerEnigme.getCurrentEnigmeLabel());
                log("SET NEW ENIGME");
            }
        };
        executor.submit(r);
    }

    @Override
    public void receiveFromServeurPlayerMoneyInfo(DataMoneyInfo var) {
        Platform.runLater(() -> {
            handlerPlayerDataTable.updateDataPlayer(var);
        });
    }

    @Override
    public void receiveFromServeurAskForALetter(String var) {
        Platform.runLater(() -> {
            //todo popup coco
            setAndExecuteState(new StateAskALetterWheel(this));
        });
    }

    @Override
    public void receiveFromServeurBadAskForALetter(String id, String var) {
        String idP = id;
        Platform.runLater(() -> {
            PlayerData p = handlerPlayerDataTable.getPlayerData(idP);
            log(var+" : BAD LETTER "+p.namePlayer);
            setAndExecuteState(new StateRound(this, idP)); //todo rename
        });
    }

    @Override
    public void receiveFromServeurGoodAskForALetter(String id, String var) {
        String idP = id;
        Platform.runLater(() -> {
            PlayerData p = handlerPlayerDataTable.getPlayerData(idP);
            log(var+" GOOD LETTER "+p.namePlayer);
            manager.displayLetter(var.charAt(0));
            setAndExecuteState(new StateRound(this,idP)); //todo rename
        });
    }

    @Override
    public void receiveFromServeurCaseOfWheel(CaseInfo var) {
        Platform.runLater(() -> {
            labelCase.setText(var.value+" "+var.type);
        });
    }

    @Override
    public void receiveFromServeurEnigmaConsAllBuy(String var) {
        Platform.runLater(() -> {
            handlerEnigme.notifyHaveNotConson();
        });
    }

    @Override
    public void receiveFromServeurActionFinal(String idPlayer, Enigme e){
        ControllerGameUI controller = this;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                handlerRound.setCurrentPlayerId(idPlayer);
                Thread.currentThread().setName("receiveFromServeurActionFinal");
                handlerEnigme.setCurrentEnigme(e); // set enigma of final round
                manager.resetPanneau(handlerEnigme.getCurrentEnigmeLabel());
                lock.lock();
                try {
                    animLetter.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                loadPreSetLetterOfFinalEnigma();
                log("SET NEW ENIGME FINAL");
                Platform.runLater(()->{
                    setAndExecuteState(new StateFinalRound(controller,idPlayer,e)); //execute final round
                });

            }
        };
        executor.submit(r);
    }

    private void loadPreSetLetterOfFinalEnigma() {
        char[] value = new char[]{'R','S','T','L','N','E'};
        for(char c : value){
            manager.displayLetter(c);
        }
    }

    @Override
    public void receiveFromServeurAskForAFinalLetter(FinalLetters var) {


        ControllerGameUI controller = this;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    ((StateFinalRound)stateUI).askForAFinalLetter(var);
                });
            }
        };
        executor.submit(r);

    }

    @Override
    public void receiveFromServeurNotifyGoodProposalLetterFinal(ProposalLetter var) {
        String idP = var.id;
        Platform.runLater(() -> {
            PlayerData p = handlerPlayerDataTable.getPlayerData(idP);
            log(var+" GOOD LETTER "+p.namePlayer);
            manager.displayLetter(var.letter);
        });
    }

    @Override
    public void receiveFromServeurAskForAFinalProposition(Proposal var) {
        Platform.runLater(() -> {
            Platform.runLater(()->{
                if(stateUI instanceof StateFinalRound){ //HERE HERE
                    ((StateFinalRound)stateUI).switchToPropositionFinal(var);
                }
            });
        });
    }

    @Override
    public void receiveFromServeurFinalResultMoney(Integer var) {
        finish = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gamefinish.fxml"));
        PlayerData p = handlerPlayerDataTable.getPlayerData(handlerRound.getCurrentIdPlayer());
        ControllerGameFinish c = new ControllerGameFinish(main,p,var);
        fxmlLoader.setController(c);
        try {
            Parent pa = fxmlLoader.load();
            Platform.runLater(()->{
                main.switchScene(pa);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void notifyDisconnect() {
        if(!alreadyStop){
            stop();
            if(!finish){
                Platform.runLater(() -> {
                    main.backToMainLobbies();
                });
            }
        }
    }

    public synchronized void stop() {

        if(!alreadyStop) {

            alreadyStop = true;
            waitAnimationQuickRound();
            dataLoad.client.stop();
            dataLoad.clientThread.interrupt();
            try {
                shutdownExecutors();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manager.stopExecutorAnimationRect();
        }
    }

    private void shutdownExecutors() throws InterruptedException {
        executor.shutdownNow();
        executor.awaitTermination(10,TimeUnit.MINUTES);
    //    executorPopup.shutdownNow();
   //     executorPopup.awaitTermination(10,TimeUnit.MINUTES);
    }

    public void actionPopup(){
        executorPopup = Executors.newScheduledThreadPool(1);
        rootpopup.setVisible(true);
        showPopup();
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                rootpopup.setVisible(false);
                executorPopup.shutdownNow();
            }
        };
        executorPopup.schedule(task1, 3, TimeUnit.SECONDS);
    }

    public void setPopup(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("dialogTest.fxml"));

        try {
            // https://stackoverflow.com/questions/12935953/javafx-class-controller-scene-reference
            rootpopup = fxmlLoader.load();
            JFXPopup popup = new JFXPopup(rootpopup);
            popup.show(board, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT,
                    (board.getWidth() - rootpopup.getPrefWidth()) / 2,
                    (board.getHeight() - rootpopup.getPrefHeight()) / 2);

            Label mess = new Label();
            mess.setText("Message popup !");
            mess.setTranslateY(50);
            rootpopup.getChildren().add(mess);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPopup() {


    }



    private void loadWebWheel() {

   /*     URL str = getClass().getClassLoader().getResource("javascript-winwheel-master/examples/wheel_of_fortune/index.html");
        webEngine = browser.getEngine();
        if(str != null) webEngine.load(str.getPath());*/
    }


 /*   public void animEnigmRound() {
        mapRectWithLetter = new HashMap<>();
        nbrRectWithLetter = 0;
        manager.mapRect.forEach((idR, r)->{
            if(manager.mapRect.get(idR).getStat() == StateOfRect.LETTRE_HIDDEN ||
                    manager.mapRect.get(idR).getStat() == StateOfRect.LETTRE_SPECIAL ){
                nbrRectWithLetter++;
                mapRectWithLetter.put(nbrRectWithLetter, r);
            }
        });

    }*/

  /*  public boolean allIsShow;
    public void chekIfEnigmIsShow(){
        allIsShow = true;
        mapRectWithLetter.forEach((rId, r)->{
            if(mapRectWithLetter.get(rId).getStat() == StateOfRect.LETTRE_HIDDEN ||
                    mapRectWithLetter.get(rId).getStat() == StateOfRect.LETTRE_SPECIAL){
                allIsShow = false;
            }
        });
    }*/

}

