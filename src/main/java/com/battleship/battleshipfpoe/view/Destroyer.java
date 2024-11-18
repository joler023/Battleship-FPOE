package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class Destroyer {
    private Group destroyer;

    public Destroyer() {
        // Crear el Polygon
        Polygon polygon = new Polygon(0.0, 20.0, 10.0, 15.0, 60.0, 15.0, 70.0, 20.0, 65.0, 25.0, 5.0, 25.0);
        polygon.setFill(Color.web("#2F2F37"));
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(0.5);

        // Crear los Rectángulos
        Rectangle rect1 = new Rectangle(50.0, 5.0, Color.web("#555661"));
        rect1.setLayoutX(10.0);
        rect1.setLayoutY(10.0);
        rect1.setStyle("-fx-stroke: black;");

        Rectangle rect2 = new Rectangle(15.0, 8.0, Color.web("#3C3F48"));
        rect2.setLayoutX(30.0);
        rect2.setLayoutY(5.0);

        Rectangle rect3 = new Rectangle(5.0, 5.0, Color.web("#2F2F37"));
        rect3.setLayoutX(35.0);
        rect3.setLayoutY(2.0);

        Rectangle rect4 = new Rectangle(3.0, 10.0, Color.web("#3C3F48"));
        rect4.setLayoutX(40.0);
        rect4.setLayoutY(-5.0);

        Rectangle rect5 = new Rectangle(5.0, 2.0, Color.web("#555661"));
        rect5.setLayoutX(39.0);
        rect5.setLayoutY(-6.5);

        Rectangle rect6 = new Rectangle(1.0, 6.0, Color.web("#2F2F37"));
        rect6.setLayoutX(43.0);
        rect6.setLayoutY(-10.0);

        Rectangle rect7 = new Rectangle(6.0, 1.0, Color.web("#2F2F37"));
        rect7.setLayoutX(40.5);
        rect7.setLayoutY(-10.0);

        // Crear los Círculos
        Circle circle1 = new Circle(1.0, Color.BLACK);
        circle1.setLayoutX(40.0);
        circle1.setLayoutY(5.0);

        Circle circle2 = new Circle(1.0, Color.BLACK);
        circle2.setLayoutX(45.0);
        circle2.setLayoutY(5.0);

        // Crear el Polyline
        Polyline polyline = new Polyline(
                27.10357666015625, 18.25,
                16.39999771118164, 24.749990463256836,
                -32.39999008178711, 24.749990463256836,
                -45.10357666015625, 18.25
        );
        polyline.setFill(Color.RED);
        polyline.setLayoutX(43.0);
        polyline.setLayoutY(4.0);
        polyline.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);


        destroyer = new Group(polygon, rect1, rect2, rect3, rect4, rect5, rect6, rect7, circle1, circle2, polyline);
    }

    public Group getDestroyer() {
        return destroyer;
    }
}
