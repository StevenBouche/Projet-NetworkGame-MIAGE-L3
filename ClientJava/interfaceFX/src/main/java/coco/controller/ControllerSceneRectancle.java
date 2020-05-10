package coco.controller;

import coco.controller.handle.HandlerEnigma;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;


public class ControllerSceneRectancle implements Initializable {

    Map<Integer, Rect> mapRect;
    Map<Integer, Label> mapEnigm;
    Map<Integer, Boolean> mapVisibilityEnigm;
    Map<Integer, Label> listCharEnigm;

    String[] wordsEnigm;
    List<String> listeLigne;
    int nbrLigne;

    int nbrOccurence;

    @FXML
    public Pane pane;
    public int ifFirstLetter;

    private HandlerEnigma handlerEnigma;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(15);
    public Future tempoDisplayLetters;
    public Future tempoAnimColor;

    public ControllerSceneRectancle(HandlerEnigma handlerEnigma){
        initScene();
        this.mapRect = new HashMap<>();
        mapEnigm = new HashMap<>();
        mapVisibilityEnigm = new HashMap<>();
        listCharEnigm = new HashMap<>();
        this.handlerEnigma = handlerEnigma;
    }

    private void initScene() {
        listeLigne = new ArrayList<>();
        this.mapRect = new HashMap<>();
        mapEnigm = new HashMap<>();
        mapVisibilityEnigm = new HashMap<>();
        listCharEnigm = new HashMap<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPanneau(0, 51);
    }

    /**Method displayLetter
     * display choiced letter
     * @param l is the letter to display
     */
    public void displayLetter(char l){
        List<Integer> posRecurrenceLetter = new ArrayList<>();
        nbrOccurence = 0;
    //    List<Rect> rectUpdate = new ArrayList<>();
        List<Rect> listDrawLetter = new ArrayList<>();

        this.mapRect.forEach((idR, r) ->{
            if(r.getLetter() == l) {
                this.mapRect.get(idR).setColor(Color.BLUE);
                this.mapRect.get(idR).setStat(StateOfRect.LETTRE_BLOCKED);
                listDrawLetter.add(this.mapRect.get(idR));
            }
        });

        drawLetter(listDrawLetter);
  //      drawLetters(listDrawLetter);
       /* if(drawLetter.isEmpty()){
            this.mapRect.forEach((idR, r) ->{
                if(r.getStat() == StateOfRect.NULL || r.getStat() == StateOfRect.SPACE) {
                    this.mapRect.get(idR).setColor(Color.RED);
                    rectUpdate.add(this.mapRect.get(idR));
                }
            });
        }*/

    //    triggerThreadColorRect(rectUpdate);
    }

    /**
     * Appel la méthode qui affiche les lettres
     */
    private void drawLetter(List<Rect> listRect){

        List<Rect> listRectBlocked = new ArrayList<>();
        for(Rect rect : listRect){
            if(rect.state == StateOfRect.LETTRE_BLOCKED){
                listRectBlocked.add(rect);
            }
            else if(rect.state == StateOfRect.LETTRE_SHOW){
                displayLetter(rect,listCharEnigm);
            }
        }

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for(Rect rectBlocked : listRectBlocked) displayLetter(rectBlocked,listCharEnigm);
            }
        };

        tempoDisplayLetters = executor.schedule(task1,  1500, TimeUnit.MILLISECONDS);

    }

    public void waitDisplayLetter(){
        try {
            tempoDisplayLetters.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void waitAnimColor(){
        try {
            tempoAnimColor.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * According to the state of the box the letter I display differently
     */
    private void displayLetter(Rect r, Map<Integer, Label> listCharEnigm){
        if(r.ch != '~') {
            if (r.state == StateOfRect.LETTRE_BLOCKED) {
                r.setColor(Color.WHITE);
                r.state = StateOfRect.LETTRE_SHOW;
            }
            Platform.runLater(()->{
                listCharEnigm.get(r.id).setText("" + r.ch);
            });
        }
    }

    /**This function set animation
     * depending on the résult of compare
     * clientProp and enigm
     *
     * @param clientProp s the proposition of player
     */
    public void compareProp(String clientProp) {

        List<Rect> rectUpdate = new ArrayList<>();
            this.mapRect.forEach((id, r) ->{
                System.out.println(r.getLetter() + " : char id -> " + id + " / state : " + r.getStat());
                if(r.getStat() == StateOfRect.NULL || r.getStat() == StateOfRect.SPACE) {
                    if (handlerEnigma.getCurrentEnigmeLabel().equals(clientProp)) {
                        r.setColor(Color.GREEN);

                    } else {
                        r.setColor(Color.RED);
                    }
                    rectUpdate.add(r);
                }
            });
            triggerThreadColorRect(rectUpdate);
    }

    private void triggerThreadColorRect(List<Rect> rlist){

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                for(Rect r : rlist) {
                        r.setColor(Color.AQUAMARINE);
                }
            }
        };
        tempoAnimColor = executor.schedule(task1,  1500, TimeUnit.MILLISECONDS);
    }

    /**This function show all
     * letters of the enigm
     *
     */
    public void displayEnigm(){

        List<Rect> rectToDisplay = new ArrayList<>();

        this.mapRect.forEach((id, r) ->{
            r.setStat(StateOfRect.LETTRE_SHOW);
            rectToDisplay.add(r);
        });

        drawLetter(rectToDisplay);

    }

    /**This function set new enigm
     *
     * @param enigm
     */
    private void setEnigm(String enigm){

        String actualLigneW = "";
        int sizeLine = 0;
        nbrLigne = 1;
        wordsEnigm = enigm.split("\\s");
        /** Create the enigm display lines */
        for(String word : wordsEnigm){

            sizeLine += word.length();
            if(sizeLine > 11 ){
                listeLigne.add(nbrLigne-1, actualLigneW);
                sizeLine = 0;
                nbrLigne++;
                actualLigneW = word;
            }
            else{
                actualLigneW += " " + word;
            }
        }
        listeLigne.add(nbrLigne-1, actualLigneW);
        int cpt = 0;
        /** Browse the created lines */
        for(String Ligne : listeLigne){
            setLigne(Ligne, cpt, nbrLigne);
            cpt++;
        }
    }


    /**This function set up the enigm on the board
     *
     * @param ligne is the actual line to set up
     * @param numLigne is the numbre of the line
     * @param nbrLigne is the number total of line to display
     */
    public void setLigne(String ligne, int numLigne, int nbrLigne){
        /** If == 2 we draw in the 2 center lines */
        int centrageLigne = 0;
        System.out.println(ligne.length());
        if(ligne.length()-1 > 10) centrageLigne = 1;
        else if(ligne.length()-1 <= 10 && ligne.length()-1 > 8) centrageLigne = 1;
        else if(ligne.length()-1 <= 8 && ligne.length()-1 > 6) centrageLigne = 2;
        else if(ligne.length()-1 <= 6 && ligne.length()-1 > 4) centrageLigne = 3;
        else if(ligne.length()-1 <= 4 && ligne.length()-1 > 2) centrageLigne = 4;
        else if(ligne.length()-1 <= 2) centrageLigne = 5;
        if(nbrLigne <= 2) {
            if(numLigne == 0) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(12 + i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(26 + i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
        }
        /** Else draw in all board lines */
        else{
            if(numLigne == 0) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else if(numLigne == 1) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(12 + i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else if(numLigne == 2) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(26 + i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(40 + i + centrageLigne).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
        }
    }

    public void resetPanneau(String str){
        pane.getChildren().clear();
        initScene();
        setPanneau(0, 51);
        setEnigm(str);
        setRectWithLetter();
    }

    /**This function set up the enigm board case
     *
     * @param idStart id of first case
     * @param idFinish id of last case
     */
    private void setPanneau(int idStart, int idFinish) {
        int CoordRectX = 21;
        int CoordRectY = 0;
        for(int cpt = idStart; cpt < (idFinish + 1); cpt++){
            /** if cpt == 12 or 26 or 40 line break */
            if(cpt == 12){
                CoordRectX = 0;
                CoordRectY = 37;
            }
            else if( cpt == 26 ){
                CoordRectX = 0;
                CoordRectY = 74;
            }
            else if(cpt == 40){
                CoordRectX = 21;
                CoordRectY = 111;
            }

            Rect rTest = new Rect(cpt, pane,  CoordRectX, CoordRectY);
            this.mapRect.put(cpt, rTest);
            rTest.drawRect();
            this.mapRect.get(cpt).setLabel(listCharEnigm);
            CoordRectX += 21;
        };
    }

    /**This function set up the case where
     * a letter of enigm is put;
     *
     */
    private void setRectWithLetter(){
        this.mapRect.forEach((id, rect) ->{
            if(this.mapRect.get(id).getLetter() != ' ' && this.mapRect.get(id).getLetter() != '~'){
                this.mapRect.get(id).setColor(Color.WHITE);
            }
        });
    }

    public void stopExecutorAnimationRect(){
        executor.shutdownNow();
        try {
            executor.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
