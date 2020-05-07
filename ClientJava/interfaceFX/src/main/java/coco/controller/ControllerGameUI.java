package coco.controller;

import coco.state.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import network.client.ClientTCP;
import network.client.INotifyPlayersGame;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGameUI implements Initializable, INotifyPlayersGame {

    /** FXML elements */
    @FXML
    public SubScene subScene;
    @FXML
    public Pane zoneButtons;
    @FXML
    public Pane clientChoic;
    @FXML
    public Pane scoreBoard;
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

    /** other declaration */
    //for loadButtons
    public Button buttonSetEnigm;
    public Button buttonShowEnigm;
    public Button buttonReset;
    public Button validLetter;
    public int posFound;
    //for loadCBD()
    public ChoiceBox cbdV;
    public ChoiceBox cbdC;
    //for loadScoreBoard()
    public Label displayTheme;
    public Label displayManche;
    int manche;

    /**
     * switchActive = true -> voyelle
     * switchActive = false -> consonne
     */
    public Boolean switchActive;
    //for loadSubScene()
    public ControllerSceneRectancle manager;
    public Parent root;

    public List<PlayerData> listPlayerData;

    ClientTCP client;
    Thread clientThread;
    StateGameUI stateUI;

    public ControllerGameUI(ClientTCP client, Thread clientThread) {
        this.client = client;
        this.clientThread = clientThread;
        if(this.client != null) this.client.setNotifierGame(this);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchActive = false;
        try {
            loadSubScene();
            loadButtons();
            loadCBD();
            loadSwitch();
            loadScoreBoard();
            setAndExecuteState(new StateStartGame(this));
            setAndExecuteState(new StateEnigme(this));
            setAndExecuteState(new StateNotCurrentPlayerRound(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        });

        /** set Event to propose the enigm chosen */
        validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                /** If player propose enigme */
                manager.compareProp(proposEnigm.getText());
                System.out.println("client submit enigm");
            }
        });
        /** Set visibility of ChoiceBoxs */
        changeChoiceB(cbdV, cbdC);
    }

    /**
     * This function set up the score Board.
     * There are 3 player and the number of the round
     * in the score Board.
     */
    public void loadScoreBoard(){
        /** create the display round */
        displayManche = new Label();
        displayManche.setTranslateX(450);
        displayManche.setTranslateY(15);
        displayManche.setAlignment(Pos.CENTER);
        setManche(1);
        scoreBoard.getChildren().add(displayManche);

        displayTheme = new Label();
        //displayTheme.setTranslateX(25);
        displayTheme.setTranslateY(15);
        displayTheme.setPrefWidth(380);
        displayTheme.setAlignment(Pos.CENTER);
        displayTheme.setText("");
        scoreBoard.getChildren().add(displayTheme);
        updateThemeEnigm("Mon theme");

        initTable();
    }

    public void initTable(){
        tableView.setEditable(false);

        TableColumn<PlayerData,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("namePlayer"));

        TableColumn<PlayerData,Integer> cashColumn = new TableColumn<>("Cash");
        cashColumn.setCellValueFactory(new PropertyValueFactory<>("cashPlayer"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(cashColumn);

        listPlayerData = new ArrayList<>();
        PlayerData p1 = new PlayerData();
        p1.namePlayer = "coco";
        p1.cashPlayer = 0;
        listPlayerData.add(p1);
        PlayerData p2 = new PlayerData();
        p2.namePlayer = "steven";
        p2.cashPlayer = 0;
        listPlayerData.add(p2);
        PlayerData p3 = new PlayerData();
        p3.namePlayer = "pierre";
        //p3.cashPlayer = 0;
        listPlayerData.add(p3);
        resetCashPlayer(2, 0);
        updateNamePlayer(0, "Aramand");
        addCashPlayer(1, 150);
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

    public void updateThemeEnigm(String theme){
        displayTheme.setText(theme);
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
    public void receiveLetterFromServer(String str) {

    }

}
