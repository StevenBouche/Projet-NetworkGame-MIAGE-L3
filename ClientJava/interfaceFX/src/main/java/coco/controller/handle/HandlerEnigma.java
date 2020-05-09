package coco.controller.handle;

import coco.controller.ControllerSceneRectancle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import network.message.obj.Enigme;

public class HandlerEnigma {

    private Label displayTheme;
    private Enigme currentEnigme;
    private boolean haveConson;
    ControllerSceneRectancle controllerPan;
    Button wheel;

    public HandlerEnigma(Label displayTheme, Button wheel){
        this.displayTheme = displayTheme;
        this.wheel = wheel;
    }

    public void setCallbackForEnigmaChange(ControllerSceneRectancle controllerPan){
        this.controllerPan = controllerPan;
    }

    public Enigme getCurrentEnigme(){
        return currentEnigme;
    }

    public void setCurrentEnigme(Enigme e){
        haveConson = true;
        wheel.setText("Turn wheel");
        this.currentEnigme = e;
        updateThemeEnigmaUI();
    }

    public Boolean getHaveConson(){
        return haveConson;
    }

    public void notifyHaveNotConson(){
        this.haveConson = false;
        wheel.setText("Turn wheel - plus de consonne");
    }

    public String getCurrentEnigmeLabel(){
      return currentEnigme.label;
    }

    private void updateThemeEnigmaUI(){
        if(currentEnigme.category != null) {
            displayTheme.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            displayTheme.setText(currentEnigme.category.name());
        }
    }

}
