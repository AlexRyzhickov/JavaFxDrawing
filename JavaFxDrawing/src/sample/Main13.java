package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sample.MyShape.MyNode;

import java.util.ArrayList;

// добавлена перерисовка вершины

public class Main13 extends Application {


    Group group_for_shapes = new Group();


    enum PROGRAM_STATE {ADD, DRAG, DELETE, ADD_LINES}

    PROGRAM_STATE state = PROGRAM_STATE.ADD;

    MyNode dragNode = null;

    ArrayList<Line> listLines = new ArrayList<>();
    Line currentLine = null;

    boolean isChouseNodeFirstForAddLines = false;
    boolean isDragState = false;

    ArrayList<MyNode> lisNodes = new ArrayList<>();


    void updateLineEndPoint(double end_x,
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

        group_for_shapes.getChildren().addAll(button, button2, button3, button4);


        Scene scene = new Scene(group_for_shapes, 800, 600);


        scene.setFill(Color.BEIGE);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            System.out.println("press mouse");
            if (state == PROGRAM_STATE.ADD) {
                MyNode node = new MyNode(event.getSceneX(), event.getSceneY());
                group_for_shapes.getChildren().add(node.getEllipse());
                lisNodes.add(node);
            }
            if (state == PROGRAM_STATE.DELETE) {
                if (lisNodes.size() > 0) {
                    MyNode node = findDragEllipse(event.getSceneX(), event.getSceneY());
                    if (node != null) {
                        System.out.println("delete");
                        node.deleteNode(group_for_shapes);
                        lisNodes.remove(node);
                    }
                }
            }
            if (state == PROGRAM_STATE.ADD_LINES) {
                if (!isChouseNodeFirstForAddLines) {
                    MyNode node = findDragEllipse(event.getSceneX(), event.getSceneY());
                    System.out.println("helloddd");
                    if (node != null) {
                        Line line = new Line(node.getEllipse().getCenterX(), node.getEllipse().getCenterY(), node.getEllipse().getCenterX(), node.getEllipse().getCenterY());
                        node.addLineStartPoint(line);
                        listLines.add(line);
                        group_for_shapes.getChildren().add(line);
                        currentLine = line;
                        isChouseNodeFirstForAddLines = true;
                        node.getEllipse().toFront();
                    }
                }
            }
            if (state == PROGRAM_STATE.DRAG) {
                if (!isDragState) {
                    if (lisNodes.size() > 0) {
                        MyNode node = findDragEllipse(event.getSceneX(), event.getSceneY());
                        if (node != null) {
                            dragNode = node;
                            isDragState = true;
                        }
                    }
                }
            }

        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            if (state == PROGRAM_STATE.DRAG) {
                System.out.println("drag");

                if (isDragState && dragNode!=null) {
                    dragNode.updatePosition(event.getSceneX(), event.getSceneY());
                }
            }
            if (state == PROGRAM_STATE.ADD_LINES) {
                if (isChouseNodeFirstForAddLines && currentLine != null) {
                    updateLineEndPoint(event.getSceneX(), event.getSceneY(), currentLine);
                }
            }
        });

        scene.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("released");
            if (state == PROGRAM_STATE.ADD_LINES) {
                if (isChouseNodeFirstForAddLines) {
                    if (lisNodes.size() > 0) {
                        MyNode node = findDragEllipse(event.getSceneX(), event.getSceneY());
                        if (node != null) {
                            node.addLineEndPoint(currentLine);
                            updateLineEndPoint(node.getEllipse().getCenterX(), node.getEllipse().getCenterY(), currentLine);
                            currentLine = null;
                            isChouseNodeFirstForAddLines = false;
                            node.getEllipse().toFront();
                        } else {
                            group_for_shapes.getChildren().remove(currentLine);
                            currentLine = null;
                            isChouseNodeFirstForAddLines = false;
                        }
                    }
                }
            }
            if (state == PROGRAM_STATE.DRAG) {
                if (isDragState) {
                    dragNode = null;
                    isDragState = false;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private MyNode findDragEllipse(double x, double y) {
        MyNode node = null;
        double min = 1000000;
        for (MyNode el : lisNodes) {
            double localMin = (Math.abs(el.getEllipse().getCenterX() - x) + Math.abs(el.getEllipse().getCenterY() - y));
            if (localMin < min) {
                min = localMin;
                node = el;
            }
        }
        if (min < 30) {
            return node;
        } else {
            return null;
        }
    }


    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}
