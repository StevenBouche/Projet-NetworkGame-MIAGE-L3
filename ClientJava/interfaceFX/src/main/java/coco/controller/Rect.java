package coco.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Rect{
    public int id;
    public int x;
    public int y;
    public Color c;
    //Character l;
    public Rectangle r1;
    public Rectangle r2;
    public StateOfRect state;
    public Pane p;
    public Label l;
    public Map<Integer, Label> listCharEnigm;

    private char ch;


    public Rect(int ID, Pane pane, int coordX, int coordY){
        id = ID;
        x = coordX;
        y = coordY;
        c = Color.AQUAMARINE;
        p = pane;
        state = StateOfRect.NULL;
        ch = '~';
        this.l = new Label();
        this.l.setPrefWidth(19);
        this.l.setPrefHeight(35);
        this.l.setLayoutY((y + 1));
        this.l.setLayoutX((x + 1));
        this.l.setAlignment(Pos.CENTER);
    }

    public void setLabel(Map<Integer, Label> listCharEnigm){
        listCharEnigm.put(this.id, this.l);
        p.getChildren().add(this.l);

    }

    public void addLettreToRect(Character lettre){

    }

    /**This methode draw rectangle of enigm board
     *
     */
    public void drawRect(){
        r1 = new Rectangle(19, 35);
        r1.setX(x+1);
        r1.setY(y+1);
        r1.setFill(Color.BLACK);
        r2 = new Rectangle(17, 33);
        r2.setX(x+2);
        r2.setY(y+2);
        r2.setFill(c);
        p.getChildren().add(r1);
        p.getChildren().add(r2);
    }



    /**
     * Appel la méthode qui affiche les lettres
     */
    public void drawLetter(Map<Integer, Label> listCharEnigm){
        if(state == StateOfRect.LETTRE_BLOCKED){
            Timer t = new Timer();
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(1500),
                    ae -> displayLetter(listCharEnigm)));
            timeline.play();
        }
        else if(state == StateOfRect.LETTRE_SHOW){
            displayLetter(listCharEnigm);
        }
    }

    /**
     * According to the state of the box the letter I display differently
     */
    public void displayLetter(Map<Integer, Label> listCharEnigm){
        if(ch != '~') {
            if (state == StateOfRect.LETTRE_BLOCKED) {
                setColor(Color.WHITE);
                state = StateOfRect.LETTRE_SHOW;
            }
            listCharEnigm.get(this.id).setText("" + ch);
            /*
            Label l = new Label();
            l.setPrefWidth(19);
            l.setPrefHeight(35);
            l.setLayoutY((y + 1));
            l.setLayoutX((x + 1));
            l.setAlignment(Pos.CENTER);
            l.setText("" + ch);
            p.getChildren().add(l);
            listCharEnigm.put(this.id, l);

             */
        }
    }

    /**This method reste the color of rectangle t blue
     *
     */
    public void resetColor(){
        Timer t = new Timer();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1500),
                ae -> setColor(Color.AQUAMARINE)));
        timeline.play();
    }

/**------------------------- Getter and Setter : -------------*/

    public void setLetter(char c, Map<Integer, Label> listCharEnigm){
        ch = c;

        if(ch != '~') {
            state = StateOfRect.LETTRE_HIDDEN;
            if (ch == ' ') state = StateOfRect.SPACE;
            if (ch == '!' || ch == '?' || ch == '\'' || ch == '-') {
                this.l.setText("" + ch);
            }
        }
        else{
            this.l.setText("");
            state = StateOfRect.NULL;
        }

        listCharEnigm.put(this.id, this.l);
    }

    public char getLetter(){
        return ch;
    }
    public void setColor(Color color){
        c = color;
        r2.setFill(c);
    }

    public Color getColor(){
        return c;
    }

    public void setStat(StateOfRect s){
        state = s;
    }

    public StateOfRect getStat(){
        return state;
    }
}