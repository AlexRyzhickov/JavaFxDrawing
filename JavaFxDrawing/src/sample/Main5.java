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


public class Main5 extends Application {
    double starting_point_x, starting_point_y;

    Group group_for_rectangles = new Group();

//    Ellipse new_rectangle = null;

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
        button.setLayoutX(50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("add");
                addOrDragMode = true;
            }
        });
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("drag");
                addOrDragMode = false;
            }
        });

        group_for_rectangles.getChildren().addAll(button, button2);


        Scene scene = new Scene(group_for_rectangles, 800, 600);


        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            if (addOrDragMode) {
                starting_point_x = event.getSceneX();
                starting_point_y = event.getSceneY();

                Ellipse new_rectangle = new Ellipse();
                list.add(new_rectangle);


                new_rectangle.setFill(Color.SNOW);
                new_rectangle.setStroke(Color.BLACK);

                group_for_rectangles.getChildren().add(new_rectangle);
                adjust_rectangle_properties(starting_point_x, starting_point_y, new_rectangle);

            }
           /* if (!addOrDragMode) {
                System.out.println("delete");
                if (listEllipses.size() > 0) {
                    group_for_shapes.getChildren().remove(listEllipses.get(listEllipses.size() - 1));
                    listEllipses.remove(listEllipses.size() - 1);
                }
            }*/
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            if (!addOrDragMode) {
                System.out.println("drag");
                Ellipse ellipse;
                if (list.size() > 0) {
                    ellipse = findDragEllipse(event.getSceneX(),event.getSceneY());
                    adjust_rectangle_properties(event.getSceneX(),event.getSceneY(),ellipse);
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

        return ellipse;
    }


    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
