package sample.MyShape;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MyNode {

    private Ellipse ellipse;
    private ArrayList<Line> linesStartPoint;
    private ArrayList<Line> linesEndPoint;
    private Text text;

    public MyNode(double centerX, double centerY) {
        ellipse = new Ellipse();
        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRadiusX(10);
        ellipse.setRadiusY(10);
        ellipse.setFill(Color.SNOW);
        ellipse.setStroke(Color.BLACK);
        text = new Text(centerX,centerY,"A");
        text.setX(centerX-4);
        text.setY(centerY+3);
        linesStartPoint = new ArrayList<>();
        linesEndPoint = new ArrayList<>();
    }

    public void updatePosition(double centerX, double centerY){
        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        text.setX(centerX-4);
        text.setY(centerY+3);
        for (Line line : linesStartPoint) {
            line.setStartX(centerX);
            line.setStartY(centerY);
        }
        for (Line line : linesEndPoint) {
            line.setEndX(centerX);
            line.setEndY(centerY);
        }
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public Text getText() {
        return text;
    }

    public ArrayList<Line> getLinesStartPoint() {
        return linesStartPoint;
    }

    public ArrayList<Line> getLinesEndPoint() {
        return linesEndPoint;
    }

    public void addLineStartPoint(Line line){
        linesStartPoint.add(line);
    }

    public void addLineEndPoint(Line line){
        linesEndPoint.add(line);
    }

    public void popLineStartPoint(Line line) {
        if (line!=null) {
            linesStartPoint.remove(line);
        }
    }

    public void popLineEndPoint(Line line){
        if (line!=null) {
            linesEndPoint.remove(line);
        }
    }

    public void deleteNode(Group group){
        group.getChildren().remove(ellipse);
        group.getChildren().remove(text);
        for (Line line : linesStartPoint) {
            group.getChildren().remove(line);
        }
        for (Line line : linesEndPoint) {
            group.getChildren().remove(line);
        }
    }

    public void setName(String name){
        text.setText(name);
    }

    public void drawFront(){
        ellipse.toFront();
        text.toFront();
    }


}
