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
import network.message.obj.ChoiceStep;
import network.message.obj.DataMoneyInfo;
import network.message.obj.Enigme;
import network.message.obj.PlayerMoneyInfo;

import javafx.scene.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

 //   final WebView browser = new WebView();
   // public WebEngine webEngine;

    public Boolean switchActive; //switchActive = true -> voyelle switchActive = false -> consonne

    public Label displayManche;

    public int nbrRectWithLetter; // ?

    public StackPane rootpopup;

    /** Sub Scene Element **/
    public ControllerSceneRectancle manager;
    public Parent root;

  //  Timer timerAnimLetter;
 //   TimerTask taskTimer;
    ScheduledExecutorService executor;
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
            setPopup();
            setAndExecuteState(new StateStartGame(this)); // todo tous doit etre desactiver et le panneau refresh
            loadWebWheel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHandler() {
        handlerEnigme = new HandlerEnigma(displayTheme);
        handlerEnigme.setCallbackForEnigmaChange(manager);
        handlerPlayerDataTable = new HandlerPlayerDataTable(tableView,dataLoad.listPlayerData);
        handlerIdentity = new HandlerMyIdentity(dataLoad.myId);
        handlerRound = new HandlerRound(displayManche);
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
        manager = new ControllerSceneRectancle(handlerEnigme);
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
        }
        else{
            cV.setVisible(false);
            cC.setVisible(true);
        }
    }

    private void log(String str){
        String str2 = log.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String newString = formatter.format(date)+" : "+str+"\n" + str2;
        log.clear();
        log.appendText(newString);
    }

    public void preSetEnigm() {
        manager.resetPanneau();
        manager.setEnigm(handlerEnigme.getCurrentEnigmeLabel());
        manager.setRectWithLetter();
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

        executor = Executors.newScheduledThreadPool(1);

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                tryshowRandomLetter();
            }
        };

        executor.scheduleAtFixedRate(task1, 0, 2, TimeUnit.SECONDS);

    }

    public void tryshowRandomLetter(){
        System.out.println("TASK");
        Enigme e = handlerEnigme.getCurrentEnigme();
        if(e == null) return; //todo bug quand on recois l'enigme d'apres currentEnigme = null a test
        if(!e.order.isEmpty()){
            char c = e.order.remove(0);
            manager.displayLetter(c);
         //   chekIfEnigmIsShow();
        } else {
            executor.shutdownNow();
        }
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
    public synchronized void startActionEnigmeRapide(Enigme varE) {
        Platform.runLater(() -> {
            handlerEnigme.setCurrentEnigme(varE);
            // todo change state of UI on rapid enigma
            setAndExecuteState(new StateEnigmeRapide(this)); //todo handle enigme => panneau enigme
        });
    }

    int cpt;
    @Override
    public synchronized void receiveFromServeurBadProposalResponse(String var, String badReponse) {
        Platform.runLater(() -> {
            manager.compareProp(badReponse);
            handlerRound.setIdPlayerHaveProposal(var);
            PlayerData p = handlerPlayerDataTable.getPlayerData(var);
            log("Bad response from "+p.namePlayer+" : "+badReponse);
        });
    }

    @Override
    public synchronized void receiveFromServeurGoodProposalResponse(String id, String proposal) {
        String idLocal = id;
        //   cancelTimerTask();

        Platform.runLater(() -> {
            executor.shutdownNow();
            //    log("State timer : "+taskTimer.cancel());
            handlerRound.setIdPlayerHaveProposal(idLocal);
            manager.compareProp(proposal); // couleur vert
            manager.displayEnigm(); // montre toute l'enigme
            PlayerData p = handlerPlayerDataTable.getPlayerData(id);
            log("Good response from "+p.namePlayer+" : "+proposal);
            actionPopup();
        });
    }

    public void actionPopup(){

        executorPopup = Executors.newScheduledThreadPool(1);
        rootpopup.setVisible(true);
        showPopup();
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                rootpopup.setVisible(false);
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


    @Override
    public synchronized void receiveFromServeurNotifyCurrentPlayerRound(String var) {
        Platform.runLater(() -> {
            handlerRound.setCurrentPlayerId(var);
            PlayerData p = handlerPlayerDataTable.getPlayerData(var);
            log("Current player is "+p.namePlayer);
            setAndExecuteState(new StateRound(this,var)); //todo rename
        });
    }

    @Override
    public synchronized void receiveFromServeurChoiceStep(ChoiceStep var) {
        Platform.runLater(() -> {
            log("You need to choice between buy vowel, turn wheel or proposal enigma");
        });
    }

    @Override
    public synchronized void receiveFromServeurEnigmaOfRound(Enigme var) {
        Platform.runLater(() -> {
            handlerEnigme.setCurrentEnigme(var);
            preSetEnigm();
            log("SET NEW ENIGME");
        });
    }

    @Override
    public synchronized void receiveFromServeurPlayerMoneyInfo(DataMoneyInfo var) {
        Platform.runLater(() -> {
            handlerPlayerDataTable.updateDataPlayer(var);
        });
    }

    @Override
    public synchronized void notifyDisconnect() {
        stop();
        Platform.runLater(() -> {
            main.backToMainLobbies();
        });
    }

    @Override
    public synchronized void receiveFromServeurAskForALetter(String var) {
        Platform.runLater(() -> {
            //todo popup coco
            setAndExecuteState(new StateAskALetterWheel(this,false,false));
        });
    }

    @Override
    public synchronized void receiveFromServeurBadAskForALetter(String id, String var) {
        String idP = id;
        Platform.runLater(() -> {
            PlayerData p = handlerPlayerDataTable.getPlayerData(idP);
            log(var+" : BAD LETTER "+p.namePlayer);
            setAndExecuteState(new StateRound(this, idP)); //todo rename
        });
    }

    @Override
    public synchronized void receiveFromServeurGoodAskForALetter(String id, String var) {
        String idP = id;
        Platform.runLater(() -> {
            PlayerData p = handlerPlayerDataTable.getPlayerData(idP);
            log(var+" GOOD LETTER "+p.namePlayer);
            manager.displayLetter(var.charAt(0));
            setAndExecuteState(new StateRound(this,idP)); //todo rename
        });
    }

    public synchronized void stop() {
        if(!alreadyStop) {
            manager.stopExecutorAnimationRect();
            executor.shutdownNow();
            dataLoad.client.stop();
            dataLoad.clientThread.interrupt();
            try {
                dataLoad.clientThread.join();
                executor.awaitTermination(10,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            alreadyStop = true;
        }
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

