package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Frigate {

    public Group getFrigate() {
        Group group = new Group();
        // Crear el Polygon
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(5.0, 25.0, 25.0, 25.0, 20.0, 30.0, 10.0, 30.0);
        polygon.setFill(Color.web("#006400"));
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        // Crear Rectángulos principales
        Rectangle rect1 = new Rectangle(6.0, 15.0, 18.0, 5.0);
        rect1.setFill(Color.web("#B0C4DE"));

        Rectangle rect2 = new Rectangle(7.0, 16.0, 3.0, 3.0);
        rect2.setFill(Color.web("#4682B4"));

        Rectangle rect3 = new Rectangle(11.0, 16.0, 3.0, 3.0);
        rect3.setFill(Color.web("#4682B4"));

        Rectangle rect4 = new Rectangle(15.0, 16.0, 3.0, 3.0);
        rect4.setFill(Color.web("#4682B4"));

        // Crear la línea entre los rectángulos
        Line line1 = new Line(7.0, 16.0, 18.0, 16.0);
        line1.setStroke(Color.BLACK);

        // Crear más rectángulos
        Rectangle rect5 = new Rectangle(16.0, 10.0, 3.0, 3.0);
        rect5.setFill(Color.web("#B0C4DE"));

        Rectangle rect6 = new Rectangle(5.0, 15.0, 4.0, 2.0);
        rect6.setFill(Color.web("#B0C4DE"));

        Rectangle rect7 = new Rectangle(16.0, 13.0, 3.0, 3.0);
        rect7.setFill(Color.web("#B0C4DE"));

        // Crear línea adicional
        Line line2 = new Line(17.5, 10.0, 17.5, 6.0);
        line2.setStroke(Color.BLACK);

        Line line3 = new Line(4.0, 15.0, 2.0, 14.0);
        line3.setStroke(Color.BLACK);
        line3.setStrokeWidth(0.5);

        // Crear el círculo
        Circle circle = new Circle(17.5, 9.0, 0.8);
        circle.setFill(Color.GRAY);

        group.getChildren().addAll(polygon, rect1, rect2, rect3, rect4, line1, rect5, rect6, rect7, line2, circle, line3);
        return group;
    }
}
