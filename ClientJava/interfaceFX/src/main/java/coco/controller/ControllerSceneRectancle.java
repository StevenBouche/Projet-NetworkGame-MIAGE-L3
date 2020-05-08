package coco.controller;

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


public class ControllerSceneRectancle implements Initializable {

    Map<Integer, Rect> mapRect;
    Map<Integer, Label> mapEnigm;
    Map<Integer, Boolean> mapVisibilityEnigm;
    Map<Integer, Label> listCharEnigm;
    String e;
    String[] wordsEnigm;
    List<String> listeLigne = new ArrayList<>();
    int nbrLigne;

    int nbrOccurence;

    @FXML
    public Pane pane;
    public int ifFirstLetter;


    public ControllerSceneRectancle(){
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
    public synchronized void displayLetter(char l){
        List<Integer> posRecurrenceLetter = new ArrayList<>();
        nbrOccurence = 0;

        this.mapRect.forEach((idR, r) ->{
            if(r.getLetter() == l) {
                nbrOccurence++;
                this.mapRect.get(idR).setColor(Color.BLUE);
                this.mapRect.get(idR).setStat(StateOfRect.LETTRE_BLOCKED);
                this.mapRect.get(idR).drawLetter(listCharEnigm);
            }
        });
        if(nbrOccurence == 0){
            this.mapRect.forEach((idR, r) ->{
                if(r.getStat() == StateOfRect.NULL || r.getStat() == StateOfRect.SPACE) {
                    this.mapRect.get(idR).setColor(Color.RED);
                    this.mapRect.get(idR).resetColor();
                }
            });

        }

    }

    /**This function set animation
     * depending on the résult of compare
     * clientProp and enigm
     *
     * @param clientProp s the proposition of player
     */
    public synchronized void compareProp(String clientProp) {
            this.mapRect.forEach((id, r) ->{
                if(r.getStat() == StateOfRect.NULL || r.getStat() == StateOfRect.SPACE) {
                    if (e.equals(clientProp)) {
                        r.setColor(Color.GREEN);
                        r.resetColor();
                    } else {
                        r.setColor(Color.RED);
                        r.resetColor();
                    }
                }

            });
    }

    /**This function show all
     * letters of the enigm
     *
     */
    public synchronized void displayEnigm(){
        this.mapRect.forEach((id, r) ->{
            r.setStat(StateOfRect.LETTRE_SHOW);
            r.drawLetter(listCharEnigm);
        });
    }

    /**This function set new enigm
     *
     * @param enigm
     */
    public synchronized void setEnigm(String enigm){
        this.e = enigm;
        String actualLigneW = "";
        int sizeLine = 0;
        nbrLigne = 1;
        wordsEnigm = this.e.split("\\s");
        /** Create the enigm display lines */
        for(String word : wordsEnigm){

            sizeLine += word.length();
            if(sizeLine > 12 ){
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
    public synchronized void setLigne(String ligne, int numLigne, int nbrLigne){
        /** If == 2 we draw in the 2 center lines */
        if(nbrLigne <= 2) {
            if(numLigne == 0) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(12 + i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(26 + i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
        }
        /** Else draw in all board lines */
        else{
            if(numLigne == 0) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else if(numLigne == 1) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(12 + i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else if(numLigne == 2) {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(26 + i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
            else {
                for (int i = 0; i < ligne.length(); i++) {
                    this.mapRect.get(40 + i).setLetter(ligne.charAt(i), listCharEnigm);
                }
            }
        }
    }

    public synchronized void resetPanneau(){
        this.mapRect.forEach((idR, r)->{
            r.setColor(Color.AQUAMARINE);
            listCharEnigm.clear();
            r.setLetter('~', listCharEnigm);
            //listCharEnigm.get(idR).
        });
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
    public void setRectWithLetter(){
        this.mapRect.forEach((id, rect) ->{
            if(this.mapRect.get(id).getLetter() != ' ' && this.mapRect.get(id).getLetter() != '~'){
                this.mapRect.get(id).setColor(Color.WHITE);
            }
        });
    }

    /**this function return the enigm
     *
     * @return the String of enigm
     */
    public String getEnigme(){
        return this.e;
    }


}
