package coco.controller;

import coco.state.*;
import controllerJavafx.LoaderRessource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import network.client.ClientTCP;
import network.client.INotifyPlayersGame;
import network.message.PacketMessage;
import network.message.obj.Enigme;
import network.tcp.ProtocolEventsTCP;

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
    public String currentEnigmeLabel;
    public int nbrRectWithLetter;

    /** My current state **/
    StateGameUI stateUI;

    /** Sub Scene Element **/
    public ControllerSceneRectancle manager;
    public Parent root;

    /** Client Network**/
    ClientTCP client;
    Thread clientThread;

    /** Current Data Game **/
    public List<PlayerData> listPlayerData;
    public List<CurrentResponseData> listResponsePlayerData;
    Enigme currentEnigme;
    public String myId;
    String currentPlayerId;

    int manche;
    String idPlayerHaveProposal; /** quand recu event bad proposal set id player in this variable**/

    Timer timerAnimLetter;

    public ControllerGameUI(ClientTCP client, Thread clientThread, List<PlayerData> data, String myId) {
        this.myId = myId;
        this.client = client;
        this.clientThread = clientThread;
        this.listPlayerData = data;
        this.listResponsePlayerData = new ArrayList<>();
        CurrentResponseData playersResponse = new CurrentResponseData();
        playersResponse.namePlayer = "nobody";
        playersResponse.currentResponse = "-";
        listResponsePlayerData.add(playersResponse);
        timerAnimLetter = new Timer();
        if(this.client != null) this.client.setNotifierGame(this);
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
            initTable();
            loadBackGround();
            displayTheme.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            setAndExecuteState(new StateStartGame(this)); // todo tous doit etre desactiver et le panneau refresh
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        /** set Event to propose the enigm chosen */
        validLetter.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                handlePropositionLetter();
                me.consume();
            }
        });

        /** set Event to propose the enigm chosen */
        validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
               handlePropositionEnigma();
               me.consume();
            }
        });
        /** Set visibility of ChoiceBoxs */
        changeChoiceB(cbdV, cbdC);
    }

    private void handlePropositionEnigma() {

        /** If player propose enigme */
        manager.compareProp(proposEnigm.getText());
        System.out.println("client submit enigm");

        /** Send at server my proposal string **/
        PacketMessage<String> msg = new PacketMessage<>();
        msg.evt = ProtocolEventsTCP.PROPOSALRESPONSE.eventName;
        msg.data = proposEnigm.getText().trim();
        client.sendMsg(msg);
    }

    private void handlePropositionLetter() {
        /** Compare state of switch button v/c */
        if(switchActive){
            char charCb = cbdV.getValue().toString().charAt(0);
            manager.displayLetter(charCb);
        }
        else{
            char charCb = cbdC.getValue().toString().charAt(0);
            manager.displayLetter(charCb);
        }
        /** When the sending has been done */
        cbdC.setValue(" ");
        cbdV.setValue(" ");
        System.out.println("client submit letter");
    }

    public void initTable(){
        tableView.setEditable(false);

        TableColumn<PlayerData,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("namePlayer"));

        TableColumn<PlayerData,Integer> cashColumn = new TableColumn<>("Cash");
        cashColumn.setCellValueFactory(new PropertyValueFactory<>("cashPlayer"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(cashColumn);

        updateTable(listPlayerData);
    }

    public void updateNamePlayer(int id, String nP){
        listPlayerData.get(id).namePlayer = nP;
    }

    public void addCashPlayer(int id, int newCash){
        listPlayerData.get(id).cashPlayer += newCash;
    }

    public void resetCashPlayer(int id, int newCash){
        listPlayerData.get(id).cashPlayer = newCash;
    }

    public void updateTable(List<PlayerData> list){
        Platform.runLater(() -> {
            ObservableList<PlayerData> listO = FXCollections.observableArrayList(list);
            tableView.setItems(listO);
        });
    }

    public void updateThemeEnigm(){
        if(currentEnigme.category != null) displayTheme.setText(currentEnigme.category.name());
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

    /**
     * This function set the number of the round
     *
     * @param numM is the number of the round
     */
    public void setManche(int numM){
        manche = numM;
        displayManche.setText("Manche n°" + manche);
    }

    @Override
    public void startActionEnigmeRapide(Enigme varE) {
        currentEnigme = varE;
        currentEnigmeLabel = currentEnigme.label;
        Platform.runLater(() -> {
            // todo change state of UI on rapid enigma
            setAndExecuteState(new StateEnigmeRapide(this)); //todo handle enigme => panneau enigme
        });
    }

    int cpt;
    @Override
    public void receiveFromServeurBadProposalResponse(String var, String badReponse) {
        idPlayerHaveProposal = var;
        //todo
        Platform.runLater(() -> {
            manager.compareProp(badReponse);
            for(PlayerData p : listPlayerData){
                if(p.id.equals(idPlayerHaveProposal)) {
                    log("Bad response from "+p.namePlayer+" : "+badReponse);
                }
            }
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
    public void receiveFromServeurGoodProposalResponse(String id, String proposal) {
        idPlayerHaveProposal = id;
        timerAnimLetter.cancel();
        currentPlayerId = id;
        Platform.runLater(() -> {
            manager.compareProp(proposal);
            manager.displayEnigm();
            for(PlayerData p : listPlayerData){
                if(p.id.equals(idPlayerHaveProposal)) log("Good response from "+p.namePlayer+" : "+proposal);
            }
        });
    }

    @Override
    public void receiveFromServeurNotifyCurrentPlayerRound(String var) {
        Platform.runLater(() -> {
            for(PlayerData p : listPlayerData){
                if(p.id.equals(var)) log("Current player is "+p.namePlayer);
            }
            setAndExecuteState(new StateStartRound(this,var)); //todo rename
        });
    }

    public void preSetEnigm() {
        manager.getEnigme();
        manager.setEnigm(currentEnigmeLabel);//"PETIT OISEAU SUR L'EAU"
        manager.setRectWithLetter();
    }

    public void animationDisplayLetters() {

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

        mapRectWithLetter.forEach((id, r)->{
            System.out.println("id : " + id + ", letter : " + r.getLetter());
        });

        timerAnimLetter.schedule( new TimerTask() {
                    @Override
                    public void run() {
                        tryshowRandomLetter(timerAnimLetter);
                    }
                }, 0, 2000);

    }

    public void tryshowRandomLetter(Timer t){

   /*     int sizeRectWithLetter = mapRectWithLetter.size();
        Random rdm = new Random();
        int randomLetterId = rdm.nextInt(sizeRectWithLetter);*/

        if(!currentEnigme.order.isEmpty()){
            char c = currentEnigme.order.remove(0);
            manager.displayLetter(c);
       //     displayLetter(c);
            chekIfEnigmIsShow(t);
        }
   /*     if(mapRectWithLetter.get(randomLetterId+1).getStat() == StateOfRect.LETTRE_HIDDEN ||
                mapRectWithLetter.get(randomLetterId+1).getStat() == StateOfRect.LETTRE_SPECIAL){



        }*/




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
        if(allIsShow){
            t.cancel();
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
}

