package coco.controller;

import coco.controller.handle.HandlerEnigma;
import coco.controller.handle.HandlerMyIdentity;
import coco.controller.handle.HandlerPlayerDataTable;
import coco.controller.handle.HandlerRound;
import coco.state.*;
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
import network.client.ClientTCP;
import network.client.INotifyPlayersGame;
import network.message.obj.ChoiceStep;
import network.message.obj.Enigme;
import network.message.obj.PlayerMoneyInfo;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class ControllerGameUI implements Initializable, INotifyPlayersGame {

    /** FXML elements */
    @FXML
    public SubScene subScene;
    @FXML
    public Pane zoneButtons;
    @FXML
    public Pane clientChoic;
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

    /** Element dynamic with code */
    public Button buttonSetEnigm; //for loadButtons
    public Button buttonShowEnigm;
    public Button buttonReset;
    public Button validLetter;
    public Boolean switchActive; //switchActive = true -> voyelle switchActive = false -> consonne
    public ChoiceBox<String> cbdV; //for loadCBD()
    public ChoiceBox<String> cbdC;
    public Label displayManche;

    public int nbrRectWithLetter; // ?

    /** Sub Scene Element **/
    public ControllerSceneRectancle manager;
    public Parent root;

    Timer timerAnimLetter;
    TimerTask taskTimer;

    /** Data comming from lobby**/
    public DataLoadGame dataLoad;

    /** Handler UI **/
    public HandlerEnigma handlerEnigme ;
    public HandlerPlayerDataTable handlerPlayerDataTable;
    public HandlerMyIdentity handlerIdentity;
    public HandlerRound handlerRound;

    /** My current state **/
    StateGameUI stateUI;

    public ControllerGameUI(ClientTCP client, Thread clientThread, List<PlayerData> data, String myId) {
        buildDataLoad(client,clientThread,data,myId);
        timerAnimLetter = new Timer();
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
            loadTextField();
            loadSubScene();
            loadButtons();
            loadCBD();
            loadSwitch();
            loadBackGround();
            createHandler();
            setAndExecuteState(new StateStartGame(this)); // todo tous doit etre desactiver et le panneau refresh
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHandler() {
        handlerEnigme = new HandlerEnigma(displayTheme);
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
        manager = new ControllerSceneRectancle();
        fxmlLoader.setController(manager);
        root = fxmlLoader.load();
        subScene.setRoot(root);
    }

    /**
     * This function set 3 buttons
     * 1st to set the enigm
     * 2nd to rddisplay enigm
     * 3rd to propose a letter (chosen in list) to show
     *
     * @throws IOException
     */
    private void loadButtons() throws IOException {
        //1ST BUTTON
        buttonSetEnigm = new Button();
        buttonSetEnigm.setText("Set Enigm");
        buttonSetEnigm.setTranslateY(30);
        zoneButtons.getChildren().add(buttonSetEnigm);
        buttonSetEnigm.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){

                //manager.setEnigm("PETIT OISEAU SUR L'EAU");
                //manager.setRectWithLetter();

                System.out.println("b1 pressed");
                //clientChoic.setVisible(true);
            }
        });

        //2ND BUTTON
        buttonShowEnigm = new Button();
        buttonShowEnigm.setText("Show enigm");
        buttonShowEnigm.setTranslateX(95);
        buttonShowEnigm.setTranslateY(30);
        zoneButtons.getChildren().add(buttonShowEnigm);
        buttonShowEnigm.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                manager.displayEnigm();
                System.out.println("b2 pressed");
            }
        });

        //3RD BUTTON
        buttonReset = new Button();
        buttonReset.setText("Reset");
        buttonReset.setTranslateX(200);
        buttonReset.setTranslateY(30);
        zoneButtons.getChildren().add(buttonReset);
        buttonReset.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                manager.resetPanneau();
                System.out.println("b3 pressed");
            }
        });
    }

    /**
     * This function set Event switch button voyelle / consonne
     * and set default letter chosen
     */
    public void loadSwitch(){
        switchVoyCons.setTranslateX(200);
        switchVoyCons.setTranslateY(-60);
        switchVoyCons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchActive = !switchActive;
                changeChoiceB(cbdV, cbdC);
                cbdC.setValue(" ");
                cbdV.setValue(" ");
            }
        });
    }

    /**
     * This function create 2 CheckChoice
     * 1st to consone
     * 2nd to voyelle
     */
    public void loadCBD(){
        cbdC = new ChoiceBox();
        cbdV = new ChoiceBox();

        cbdC.setTranslateX(220);
        cbdV.setTranslateX(220);
        cbdC.setPrefWidth(75);
        cbdV.setPrefHeight(50);
        cbdC.setPrefHeight(50);
        cbdV.setPrefWidth(75);
        proposEnigm.setTranslateY(-25);
        proposEnigm.setPrefWidth(200);
        proposEnigm.setPrefHeight(50);

        cbdV.getItems().addAll(" ", "A", "E", "I", "O", "U", "Y");

        cbdC.getItems().addAll(" ", "B", "C", "D", "F", "G", "H", "J", "K","L", "M", "N", "P", "Q", "R", "S", "T", "V", "W","X", "Z");

        clientChoic.getChildren().add(cbdC);
        clientChoic.getChildren().add(cbdV);

        validLetter = new Button();
        validLetter.setText("LETTRE");
        validLetter.setTranslateX(220);
        validLetter.setTranslateY(58);
        validLetter.setPrefWidth(75);
        clientChoic.getChildren().add(validLetter);

        validChoice.setText("ENIGME");
        validChoice.setPrefWidth(200);

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

    @Override
    public void startActionEnigmeRapide(Enigme varE) {
        Platform.runLater(() -> {
            handlerEnigme.setCurrentEnigme(varE);
            // todo change state of UI on rapid enigma
            setAndExecuteState(new StateEnigmeRapide(this)); //todo handle enigme => panneau enigme
        });
    }

    int cpt;
    @Override
    public void receiveFromServeurBadProposalResponse(String var, String badReponse) {
        Platform.runLater(() -> {
            manager.compareProp(badReponse);
            handlerRound.setIdPlayerHaveProposal(var);
            PlayerData p = handlerPlayerDataTable.getPlayerData(var);
            log("Bad response from "+p.namePlayer+" : "+badReponse);
        });
    }

    @Override
    public void receiveFromServeurGoodProposalResponse(String id, String proposal) {
        String idLocal = id;
        Platform.runLater(() -> {
            timerAnimLetter.cancel();
            boolean b = taskTimer.cancel();
        /*    while(!b){
                b = taskTimer.cancel();
            }*/
            log("State timer : "+taskTimer.cancel());
            handlerRound.setIdPlayerHaveProposal(idLocal);
            manager.compareProp(proposal); // couleur vert
            manager.displayEnigm(); // montre toute l'enigme
            PlayerData p = handlerPlayerDataTable.getPlayerData(id);
            log("Good response from "+p.namePlayer+" : "+proposal);
        });
    }

    private void log(String str){
        String str2 = log.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String newString = formatter.format(date)+" : "+str+"\n" + str2;
        log.clear();
        log.appendText(newString);
    }

    @Override
    public void receiveFromServeurNotifyCurrentPlayerRound(String var) {
        Platform.runLater(() -> {
            handlerRound.setCurrentPlayerId(var);
            PlayerData p = handlerPlayerDataTable.getPlayerData(var);
            log("Current player is "+p.namePlayer);
            setAndExecuteState(new StateStartRound(this,var)); //todo rename
        });
    }

    @Override
    public void receiveFromServeurChoiceStep(ChoiceStep var) {
        Platform.runLater(() -> {
          log("You need to choice between buy vowel, turn wheel or proposal enigma");
        });
    }

    @Override
    public void receiveFromServeurEnigmaOfRound(Enigme var) {
        Platform.runLater(() -> {
            handlerEnigme.setCurrentEnigme(var);
            log("SET NEW ENIGME");
            preSetEnigm();
        });
    }

    @Override
    public void receiveFromServeurPlayerMoneyInfo(PlayerMoneyInfo var) {
        Platform.runLater(() -> {
            handlerPlayerDataTable.updateDataPlayer(var);
        });
    }

    public void preSetEnigm() {
       // manager.getEnigme();
        manager.resetPanneau();
        manager.setEnigm(handlerEnigme.getCurrentEnigmeLabel());//"PETIT OISEAU SUR L'EAU"
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

  /*      mapRectWithLetter.forEach((id, r)->{
            System.out.println("id : " + id + ", letter : " + r.getLetter());
        });*/
        taskTimer = new TimerTask() {
            @Override
            public void run() {
                if(allIsShow) cancel();
                else tryshowRandomLetter(timerAnimLetter);
            }
        };
        timerAnimLetter.schedule(taskTimer, 100, 2000);

    }

    public void tryshowRandomLetter(Timer t){
        System.out.println("TASK");
        Enigme e = handlerEnigme.getCurrentEnigme();
        if(e == null) return; //todo bug quand on recois l'enigme d'apres currentEnigme = null a test
        if(!e.order.isEmpty()){
            char c = e.order.remove(0);
            manager.displayLetter(c);
            chekIfEnigmIsShow(t);
        }
    }

    public void displayLetter(int idL){
        Platform.runLater(() -> {
            // OBLIGATOIRE LORS DE MODIFICATION DE QUELQUE CHOSE DE GRAPHIQUE CAR CELA VIENT DU RESEAU
            manager.displayLetter(mapRectWithLetter.get(idL).getLetter());
        });
    }

    public boolean allIsShow;
    public void chekIfEnigmIsShow(Timer t){
        allIsShow = true;
        mapRectWithLetter.forEach((rId, r)->{
            if(mapRectWithLetter.get(rId).getStat() == StateOfRect.LETTRE_HIDDEN ||
                    mapRectWithLetter.get(rId).getStat() == StateOfRect.LETTRE_SPECIAL){
                allIsShow = false;
            }
        });
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

    public void stop() {
        dataLoad.client.stop();
    }
}

