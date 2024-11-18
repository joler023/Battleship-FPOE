package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class Submarine {
    private Group submarine;

    public Submarine() {
        // Crear el primer rectángulo negro
        Rectangle rect1 = new Rectangle(90.0, 16.0, Color.BLACK);
        rect1.setArcHeight(10.0);
        rect1.setArcWidth(10.0);
        rect1.setLayoutX(9.0);
        rect1.setLayoutY(10.0);

        // Crear el rectángulo rojo
        Rectangle rect2 = new Rectangle(90.0, 10.0, Color.RED);
        rect2.setArcHeight(30.0);
        rect2.setArcWidth(30.0);
        rect2.setLayoutX(8.0);
        rect2.setLayoutY(18.0);

        // Crear el arco negro
        Arc arc = new Arc(10.0, 20.0, 15.0, 8.0, 90.0, 180.0);
        arc.setFill(Color.BLACK);
        arc.setLayoutX(7.0);
        arc.setLayoutY(-2.0);

        // Crear el rectángulo negro superior
        Rectangle rect3 = new Rectangle(20.0, 10.0, Color.BLACK);
        rect3.setLayoutX(30.0);
        rect3.setLayoutY(0.0);

        // Crear las ventanas blancas en la parte superior
        Rectangle rect4 = new Rectangle(10.0, 2.0, Color.WHITE);
        rect4.setLayoutX(35.0);
        rect4.setLayoutY(2.0);

        Rectangle rect5 = new Rectangle(10.0, 2.0, Color.WHITE);
        rect5.setLayoutX(35.0);
        rect5.setLayoutY(5.0);

        // Crear la línea negra
        Line line = new Line(40.0, 0.0, 40.0, -5.0);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2.0);

        // Crear las ventanas blancas inferiores
        Rectangle[] windows = new Rectangle[7];
        for (int i = 0; i < 7; i++) {
            windows[i] = new Rectangle(5.0, 3.0, Color.WHITE);
            windows[i].setLayoutX(20.0 + (10.0 * i));
            windows[i].setLayoutY(15.0);
        }

        // Crear el Polyline
        Polyline polyline = new Polyline(-12.8, 18.4, 0.0, 10.4, 0.0, 25.0, -12.8, 18.4);
        polyline.setFill(Color.web("#736969"));
        polyline.setStroke(Color.web("#00000097"));
        polyline.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        polyline.setLayoutX(105.0);

        // Crear el último rectángulo gris
        Rectangle rect6 = new Rectangle(3.0, 10.0, Color.web("#3C3F48"));
        rect6.setLayoutX(53.0);
        rect6.setLayoutY(3.0);


        submarine = new Group(rect1, rect2, arc, rect3, rect4, rect5, line, polyline, rect6);
        submarine.getChildren().addAll(windows); // Añadir las ventanas
    }

    public Group getSubmarine() {
        return submarine;
    }
}
