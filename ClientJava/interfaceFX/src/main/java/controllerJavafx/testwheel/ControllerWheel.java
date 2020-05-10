package controllerJavafx.testwheel;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ControllerWheel implements Initializable {

    @FXML
    public AnchorPane pane;
    @FXML
    public Slider spinner;

    float angle = 2;
    boolean test;
    long time;
    private Group drawSemiRing() {

        //Creating a Group object
        Group root = new Group();

        List<CaseWheel> arcs = new ArrayList<>();
        double rayon = 100;
        double nbAngle = (360 / 24)-1;

        double startAngle = 8;

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(390.0, 150.0,
                420.0, 160.0,
                420.0, 140.0);

        int cpt=0;
        for(int i = 0; i <24; i++){
            CaseWheel c = new CaseWheel(cpt,300,150,100,startAngle,nbAngle);
            startAngle += nbAngle+1;
            c.addTo(root);
            arcs.add(c);
            cpt++;
        }

        root.getChildren().add(polygon);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        time = System.currentTimeMillis();
        int nbMilliRandom = new Random().nextInt(7000 - 3000 + 1) + 3000;
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(()->{

                    for(CaseWheel n : arcs) {
                        n.rotateAngle(angle);
                        if(n.isCurrent()){
                            System.out.println(n.arc.getStartAngle()+" "+n.valueAngle);
                            System.out.print("Case current id : "+n.id+"\n");
                        }
                    }
                    if(System.currentTimeMillis()-time>nbMilliRandom){
                        angle-=0.01;
                    }
                    if(angle<=0) executor.shutdownNow();
                });
            }
        };

        executor.scheduleAtFixedRate(task1, 0, 16, TimeUnit.MILLISECONDS);

        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getChildren().add(drawSemiRing());
        spinner.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                angle = (int) spinner.getValue();
            }
        });
        spinner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                angle = (int) spinner.getValue();
            }
        });
    }
}
