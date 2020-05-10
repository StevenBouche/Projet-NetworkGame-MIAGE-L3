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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public char ch;



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

    /**This method reste the color of rectangle t blue
     *
     */
/*    public void resetColor(){
        Timer t = new Timer();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1500),
                ae -> setColor(Color.AQUAMARINE)));
        timeline.play();

    }*/

/**------------------------- Getter and Setter : -------------*/

    public void setLetter(char c, Map<Integer, Label> listCharEnigm){
        ch = c;

        if(ch != '~') {
            state = StateOfRect.SPACE;
            if (ch == ' ') state = StateOfRect.SPACE;
            else if (ch == '!' || ch == '?' || ch == '\'' || ch == '-') {
                this.l.setText("" + ch);
                state = StateOfRect.LETTRE_SPECIAL;
            }
            else{
                state = StateOfRect.LETTRE_HIDDEN;
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