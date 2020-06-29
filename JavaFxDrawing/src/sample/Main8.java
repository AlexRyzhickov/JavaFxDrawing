package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main8 extends Application {


    Group group_for_rectangles = new Group();


    enum PROGRAM_STATE {ADD, DRAG, DELETE, ADD_LINES}

    PROGRAM_STATE state = PROGRAM_STATE.ADD;

    ArrayList<Ellipse> listEllipses = new ArrayList<>();
    ArrayList<Line> listLines = new ArrayList<>();
    Line currentLine = null;


    boolean isChouseNodeFirstForAddLines = false;

    void updateEllipse(double starting_point_x,
                       double starting_point_y,
                       Ellipse given_rectangle) {
        given_rectangle.setCenterX(starting_point_x);
        given_rectangle.setCenterY(starting_point_y);
        given_rectangle.setRadiusX(10);
        given_rectangle.setRadiusY(10);
    }

    void updateLine(double end_x,
                       double end_y,
                       Line line) {
       line.setEndX(end_x);
       line.setEndY(end_y);
    }


    public void start(Stage stage) {
        stage.setTitle("DrawingRectanglesFX.java");

        Button button = new Button("add");
        Button button2 = new Button("drag");
        Button button3 = new Button("delete");
        Button button4 = new Button("add line");


        button.setLayoutX(50);
        button2.setLayoutX(90);
        button4.setLayoutX(138);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("add press");
                state = PROGRAM_STATE.ADD;
            }
        });
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("drag press");
                state = PROGRAM_STATE.DRAG;
            }
        });
        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("delete press");
                state = PROGRAM_STATE.DELETE;
            }
        });
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("add lines press");
                state = PROGRAM_STATE.ADD_LINES;
            }
        });

        group_for_rectangles.getChildren().addAll(button, button2, button3, button4);


        Scene scene = new Scene(group_for_rectangles, 800, 600);


        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            System.out.println("press mouse");
            if (state == PROGRAM_STATE.ADD) {
                //double starting_point_x, starting_point_y;
                double starting_point_x = event.getSceneX();
                double starting_point_y = event.getSceneY();

                Ellipse new_rectangle = new Ellipse();
                listEllipses.add(new_rectangle);


                new_rectangle.setFill(Color.SNOW);
                new_rectangle.setStroke(Color.BLACK);

                group_for_rectangles.getChildren().add(new_rectangle);
                updateEllipse(starting_point_x, starting_point_y, new_rectangle);

            }
            if (state == PROGRAM_STATE.DELETE) {
                if (listEllipses.size() > 0) {
                    Ellipse ellipse = findDragEllipse(event.getSceneX(), event.getSceneY());
                    if (ellipse != null) {
                        System.out.println("delete");
                        group_for_rectangles.getChildren().remove(ellipse);
                        listEllipses.remove(ellipse);
                    }
                }
            }
            if (state == PROGRAM_STATE.ADD_LINES){
                if (!isChouseNodeFirstForAddLines){
                    Ellipse ellipse = findDragEllipse(event.getSceneX(), event.getSceneY());
                    if (ellipse!=null){
                        double centerX = ellipse.getCenterX();
                        double centerY = ellipse.getCenterY();

                        Line line = new Line(centerX,centerY,centerX,centerY);
                        listLines.add(line);
                        group_for_rectangles.getChildren().add(line);
                        currentLine = line;
                        isChouseNodeFirstForAddLines = true;


                    }

                }
            }
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            if (state == PROGRAM_STATE.DRAG) {
                System.out.println("drag");

                if (listEllipses.size() > 0) {
                    Ellipse ellipse = findDragEllipse(event.getSceneX(), event.getSceneY());
                    if (ellipse != null) {
                        updateEllipse(event.getSceneX(), event.getSceneY(), ellipse);
                    }
                }
            }
            if (state==PROGRAM_STATE.ADD_LINES){
                double currentX = event.getSceneX();
                double currentY = event.getSceneY();
                updateLine(currentX,currentY,currentLine);
            }
        });

        scene.setOnMouseReleased((MouseEvent event)->{
            System.out.println("released");
            if(state==PROGRAM_STATE.ADD_LINES){
                if (isChouseNodeFirstForAddLines){
                    if (listEllipses.size() > 0) {
                        Ellipse ellipse = findDragEllipse(event.getSceneX(), event.getSceneY());
                        if (ellipse != null) {
                            double centerX = ellipse.getCenterX();
                            double centerY = ellipse.getCenterY();

                            updateLine(centerX,centerY,currentLine);
                            currentLine = null;
                            isChouseNodeFirstForAddLines = false;
                        }
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    Ellipse findDragEllipse(double x, double y) {
        Ellipse ellipse = null;
        double min = 1000000;
        for (Ellipse el : listEllipses) {
            double localMin = (Math.abs(el.getCenterX() - x) + Math.abs(el.getCenterY() - y));
            if (localMin < min) {
                min = localMin;
                ellipse = el;
            }
        }
        if (min < 30) {
            return ellipse;
        } else {
            return null;
        }
    }


    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
