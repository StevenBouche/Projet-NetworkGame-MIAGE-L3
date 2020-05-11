package coco;

import coco.controller.ControllerGameUI;
import coco.controller.PlayerData;
import coco.state.StateFinalRound;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.message.obj.Category;
import network.message.obj.Enigme;
import network.message.obj.FinalLetters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    ControllerGameUI managerGameUI;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{

        loadPrimaryScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();

        //test

        Enigme var = new Enigme();
        var.label = "OFFRIR DES CHOCOLATS A NOEL";
        var.category = Category.CELEBRITES;
        var.order = new ArrayList<>();
        var.order.add('E');
        var.order.add('N');
        var.order.add('I');
        var.order.add('G');
        var.order.add('M');
        var.order.add('R');
        var.order.add('A');
        var.order.add('P');
        var.order.add('I');
        var.order.add('D');
        var.order.add('O');
        managerGameUI.receiveFromServeurEnigmaOfRound(var);
        managerGameUI.startActionEnigmeRapide(var);
        managerGameUI.receiveFromServeurBadProposalResponse("1", "Hello test c'est la mauvaise reponse");
     //   Thread.sleep(3000);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
          /*      try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                managerGameUI.receiveFromServeurGoodProposalResponse("1","ENIGME RAPIDO !");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

          //      managerGameUI.receiveFromServeurEnigmaOfRound(var);

                managerGameUI.receiveFromServeurActionFinal("1", var);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FinalLetters f=new FinalLetters();
                managerGameUI.receiveFromServeurAskForAFinalLetter(f);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

             //   managerGameUI.notifyDisconnect();


           /*     managerGameUI.receiveFromServeurNotifyCurrentPlayerRound("1");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
        });
        t.start();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void loadPrimaryScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gameUI.fxml"));

        List<PlayerData> l = new ArrayList<>();
        PlayerData p1 = new PlayerData();
        PlayerData p2 = new PlayerData();
        PlayerData p3 = new PlayerData();
        p1.namePlayer = "coco";
        p1.id = "1";
        p1.cashRound = 0;
        p1.cashTotal = 0;
        p2.namePlayer = "pierre";
        p2.id = "2";
        p2.cashRound = 0;
        p2.cashTotal = 0;
        p3.namePlayer = "armand";
        p3.id = "3";
        l.add(p1);
        l.add(p2);
        l.add(p3);
        p3.cashRound = 0;
        p3.cashTotal = 0;
        managerGameUI = new ControllerGameUI(null,null, l, "1",null);

        fxmlLoader.setController(managerGameUI);
        root = fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
