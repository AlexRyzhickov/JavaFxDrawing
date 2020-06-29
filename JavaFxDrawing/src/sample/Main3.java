package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;


public class Main3 extends Application {
    double starting_point_x, starting_point_y;

    Group group_for_rectangles = new Group();

    Ellipse new_rectangle = null;

    boolean new_rectangle_is_being_drawn = false;

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

        Scene scene = new Scene(group_for_rectangles, 800, 600);

        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            starting_point_x = event.getSceneX();
            starting_point_y = event.getSceneY();

            new_rectangle = new Ellipse();


            new_rectangle.setFill(Color.SNOW);
            new_rectangle.setStroke(Color.BLACK);

            group_for_rectangles.getChildren().add(new_rectangle);
            adjust_rectangle_properties(starting_point_x, starting_point_y, new_rectangle);


            new_rectangle_is_being_drawn = true;

        });

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
