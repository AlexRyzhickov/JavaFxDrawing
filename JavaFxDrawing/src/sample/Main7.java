package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main7 extends Application {


    Group group_for_rectangles = new Group();


    enum PROGRAM_STATE { ADD, DRAG, DELETE}
    PROGRAM_STATE state = PROGRAM_STATE.ADD;


    boolean addOrDragMode = true;
    int count = 0;
    ArrayList<Ellipse> list = new ArrayList<>();

    void adjust_rectangle_properties(double starting_point_x,
                                     double starting_point_y,
                                     Ellipse given_rectangle) {
        given_rectangle.setCenterX(starting_point_x);
        given_rectangle.setCenterY(starting_point_y);
        given_rectangle.setRadiusX(10);
        given_rectangle.setRadiusY(10);
    }


    public void start(Stage stage) {
        stage.setTitle("DrawingRectanglesFX.java");

        Button button = new Button("add");
        Button button2 = new Button("drag");
        Button button3 = new Button("delete");

        button.setLayoutX(50);
        button2.setLayoutX(100);

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

        group_for_rectangles.getChildren().addAll(button, button2,button3);


        Scene scene = new Scene(group_for_rectangles, 800, 600);


        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            if (state == PROGRAM_STATE.ADD) {
                //double starting_point_x, starting_point_y;
                double starting_point_x = event.getSceneX();
                double starting_point_y = event.getSceneY();

                Ellipse new_rectangle = new Ellipse();
                list.add(new_rectangle);


                new_rectangle.setFill(Color.SNOW);
                new_rectangle.setStroke(Color.BLACK);

                group_for_rectangles.getChildren().add(new_rectangle);
                adjust_rectangle_properties(starting_point_x, starting_point_y, new_rectangle);

            }
            if (state == PROGRAM_STATE.DELETE){
                if (list.size() > 0) {
                    Ellipse ellipse = findDragEllipse(event.getSceneX(),event.getSceneY());
                    if (ellipse!=null) {
                        System.out.println("delete");
                        group_for_rectangles.getChildren().remove(ellipse);
                        list.remove(ellipse);
                    }
                }
            }
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            if (state == PROGRAM_STATE.DRAG) {
                System.out.println("drag");

                if (list.size() > 0) {
                    Ellipse ellipse = findDragEllipse(event.getSceneX(),event.getSceneY());
                    if (ellipse!=null) {
                        adjust_rectangle_properties(event.getSceneX(), event.getSceneY(), ellipse);
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    Ellipse findDragEllipse(double x, double y){
        Ellipse ellipse = null;
        double min = 1000000;
        for (Ellipse el : list) {
            double localMin = (Math.abs(el.getCenterX()-x) + Math.abs(el.getCenterY()-y));
            if (localMin < min){
                min = localMin;
                ellipse = el;
            }
        }
        if (min<30) {
            return ellipse;
        }else {
            return null;
        }
    }


    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
