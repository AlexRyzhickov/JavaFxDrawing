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


public class Main4 extends Application {
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
        Button button2 = new Button("delete");
        button.setLayoutX(50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("add press");
                addOrDragMode = true;
            }
        });
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("delete press");
                addOrDragMode = false;
            }
        });

        group_for_rectangles.getChildren().addAll(button,button2);


        Scene scene = new Scene(group_for_rectangles, 800, 600);


        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            if(addOrDragMode) {
                starting_point_x = event.getSceneX();
                starting_point_y = event.getSceneY();

                Ellipse new_rectangle = new Ellipse();
                list.add(new_rectangle);
//                new_rectangle.setId(String.valueOf(count));
//                count++;


                new_rectangle.setFill(Color.SNOW);
                new_rectangle.setStroke(Color.BLACK);

                group_for_rectangles.getChildren().add(new_rectangle);
                adjust_rectangle_properties(starting_point_x, starting_point_y, new_rectangle);

            }
            if(!addOrDragMode){
                System.out.println("delete");
                if (list.size()>0) {
                    group_for_rectangles.getChildren().remove(list.get(list.size() - 1));
                    list.remove(list.size() - 1);
                }
//                Ellipse ellipse = (Ellipse) scene.lookup(String.valueOf(count));
//                group_for_shapes.getChildren().remove(ellipse);
            }
        });

        scene.setOnMouseReleased((MouseEvent)->{
        });

        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
