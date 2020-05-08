package coco.controller.handle;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import network.message.obj.Enigme;

public class HandlerEnigma {

    private Label displayTheme;
    private Enigme currentEnigme;

    public HandlerEnigma(Label displayTheme){
        this.displayTheme = displayTheme;
    }

    public Enigme getCurrentEnigme(){
        return currentEnigme;
    }

    public void setCurrentEnigme(Enigme e){
        this.currentEnigme = e;
        updateThemeEnigmaUI();
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
