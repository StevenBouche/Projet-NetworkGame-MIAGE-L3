package controllerJavafx.testwheel;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Rotate;

public class CaseWheel {

    double centerX;
    double centerY;
    double rayon;
    double startAngle;
    double valueAngle;
    public int id;
    Arc arc;
    Label l;

    public CaseWheel(int i,double centerX, double centerY, double rayon, double startAngle, double valueAngle){
        this.id = i;
        this.centerX = centerX;
        this.centerY = centerY;
        this.rayon = rayon;
        this.startAngle = startAngle;
        this.valueAngle = valueAngle;
        drawArc();
    }

    public void drawArc(){
        arc = new Arc();
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(rayon);
        arc.setRadiusY(rayon);
        arc.setStartAngle(startAngle);
        arc.setLength(valueAngle);
        arc.setType(ArcType.ROUND);
        l = new Label();
        l.setText("CIRCLE");
        l.setLayoutX(arc.getLayoutX());
        l.setLayoutY(arc.getLayoutY());
    }

    public void rotateAngle(double angle){
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(this.centerX);
        rotate.setPivotY(this.centerY);
        arc.getTransforms().add(rotate);

        if(startAngle+angle > 360) {
            startAngle = (startAngle+ angle)-360;
        } else startAngle += angle;

    }

    public void addTo(Group g){
        g.getChildren().add(this.arc);
        g.getChildren().add(l);
    }

    public Boolean isCurrent(){
        if((startAngle+valueAngle)>360) return true;
        else return false;
    }

}
