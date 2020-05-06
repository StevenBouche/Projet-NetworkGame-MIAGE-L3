package coco.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGameUI implements Initializable {




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

    /** other declaration */
    //for loadButtons
    public Button button1;
    public Button button2;
    public Button button3;
    public int posFound;
    //for loadCBD()
    public ChoiceBox cbdV;
    public ChoiceBox cbdC;
    //for loadScoreBoard()
    public List<Label> listPlayersScore;
    public Label textSxoreB;
    public Label displayManche;
    int manche;

    /**
     * switchActive = true -> voyelle
     * switchActive = false -> consonne
     */
    public Boolean switchActive;
    //for loadSubScene()
    ControllerSceneRectancle manager;
    Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hello");
        switchActive = false;
        try {
            loadSubScene();
            loadButtons();
            loadCBD();
            loadSwitch();
            loadScoreBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        button1 = new Button();
        button1.setText("Set Enigm");
        zoneButtons.getChildren().add(button1);
        button1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){

                manager.setEnigm("PETIT OISEAU SUR L'EAU");
                manager.setRectWithLetter();
                System.out.println("b1 pressed");
                clientChoic.setVisible(true);
            }
        });

        //2ND BUTTON
        button2 = new Button();
        button2.setText("Show enigm");
        button2.setTranslateY(30);
        zoneButtons.getChildren().add(button2);
        button2.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){

                manager.displayEnigm();
                System.out.println("b2 pressed");
            }
        });

        //3RD BUTTON
        button3 = new Button();
        button3.setText("Show letter");
        button3.setTranslateX(100);
        zoneButtons.getChildren().add(button3);
        List<Character> listeLettersFound = new ArrayList<>();
        listeLettersFound.add('E');
        listeLettersFound.add('A');
        listeLettersFound.add('I');
        listeLettersFound.add('S');
        posFound = 0;
        button3.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){

                manager.displayLetter(listeLettersFound.get(posFound));
                if(posFound < 3) {
                    posFound++;
                }
                System.out.println("b3 pressed");
            }
        });
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

        cbdC.setTranslateX(250);
        cbdV.setTranslateX(250);

        cbdV.getItems().addAll(" ", "A", "E", "I", "O", "U", "Y");

        cbdC.getItems().addAll(" ", "B", "C", "D", "F", "G", "H", "J", "K","L", "M", "N", "P", "Q", "R", "S", "T", "V", "W","X", "Z");

        clientChoic.getChildren().add(cbdC);
        clientChoic.getChildren().add(cbdV);

        /** set Event to propose the letter chosen */
        validChoice.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                /** If player propose enigme */
                if(proposEnigm.getText().length() > 0){
                    manager.compareProp(proposEnigm.getText());

                }
                else{
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
                }

                System.out.println("client submit");
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
        listPlayersScore = new ArrayList<>();
        displayManche = new Label();
        displayManche.setTranslateX(5);
        displayManche.setTranslateY(15);
        displayManche.setAlignment(Pos.CENTER);
        setManche(1);
        scoreBoard.getChildren().add(displayManche);

        /** Create Label for player's name and for cash players */
        for(int i = 0; i <3; i++){
            /** name */
            textSxoreB = new Label();
            textSxoreB.setTranslateX(5);
            textSxoreB.setTranslateY(30*(i+2));
            textSxoreB.setText("Player " + (i+1));
            scoreBoard.getChildren().add(textSxoreB);
            /** cash */
            textSxoreB = new Label();
            textSxoreB.setTranslateX(50);
            textSxoreB.setTranslateY(30*(i+2)+15);
            listPlayersScore.add(i, textSxoreB);
            addCashPlayer(i+1, 0);
            scoreBoard.getChildren().add(textSxoreB);
        }
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

    /**
     * This function set the cash of player
     *
     * @param numPlayer is the id of the player
     * @param cash is the total cash of player
     */
    public void addCashPlayer(int numPlayer, int cash){
        listPlayersScore.get(numPlayer-1).setText("Cash " + cash);
    }

/*--------------------------------useless
    public void setCheckBox(int i, CheckBox cb){
        if(i < (65+7)){
            cb.setTranslateX(230 + ((i-65)*40));
        }
        else if(i < (65+14) && i > (65+6)){
            cb.setTranslateX(230 + ((i-65-7)*40));
            cb.setTranslateY(30);
        }
        else if(i < (65+21) && i > (65+13)){
            cb.setTranslateX(230 + ((i-65-14)*40));
            cb.setTranslateY(60);
        }
        else if(i < (65+26) && i > (65+20)){
            cb.setTranslateX(230 + ((i-65-21)*40));
            cb.setTranslateY(90);
        }
    }


    public void actionSwitch(){
        listCheckbox.forEach(cbx ->{
            if(switchActive ){
                if(cbx.getText().equals("A") ||
                        cbx.getText().equals("E") || cbx.getText().equals("I") ||
                        cbx.getText().equals("O") || cbx.getText().equals("U") ||
                        cbx.getText().equals("Y")) {
                    cbx.setVisible(true);
                }
                else{
                    cbx.setVisible(false);
                }
            }
            else{
                if(!cbx.getText().equals("A") &&
                        !cbx.getText().equals("E") && !cbx.getText().equals("I") &&
                        !cbx.getText().equals("O") && !cbx.getText().equals("U") &&
                        !cbx.getText().equals("Y")) {
                    cbx.setVisible(true);
                }
                else {
                    cbx.setVisible(false);
                }
            }
        });

    }

    public void loadChoiceClient(){
        listCheckbox = new ArrayList<>();
        for(int i = 65; i< (65+26); i++){
            chB = new CheckBox();
            char c = (char) i;
            chB.setText("" + c);
            chB.setId("" + c);
            chB.setPrefSize(10,10);
            chB.setVisible(false);


            setCheckBox(i, chB);
            clientChoic.getChildren().add(chB);
            listCheckbox.add((i-65), chB);
        }
        listCheckbox.forEach(cb ->{
            cb.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(cb.getText().charAt(0));
                    char charCb = cb.getText().charAt(0);

                    manager.displayLetter(charCb);
                    if(manager.getLetterIsFind()){
                        cb.setTextFill(Color.GREEN);
                        manager.setLetterIsFind(false);
                    }
                    else{
                        cb.setTextFill(Color.RED);
                    }
                }
            });
        });


    }

 */
}
