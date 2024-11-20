package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class BombTouch {
    private Group bombTouch;

    public Group getBombTouch() {
        Group group = new Group();
        // Crear el primer círculo negro
        Circle circle1 = new Circle(15.0, 15.5, 12.0, Color.BLACK);

        // Crear el círculo gris claro
        Circle circle2 = new Circle(15.0, 20.0, 6.0, Color.LIGHTGRAY);

        // Crear el primer círculo negro pequeño
        Circle circle3 = new Circle(13.0, 19.0, 1.0, Color.BLACK);

        // Crear el segundo círculo negro pequeño
        Circle circle4 = new Circle(17.0, 19.0, 1.0, Color.BLACK);

        // Crear el triángulo negro
        Polygon triangle1 = new Polygon();
        triangle1.getPoints().addAll(15.0, 20.0, 14.5, 21.0, 15.5, 21.0);
        triangle1.setFill(Color.BLACK);

        // Crear la línea negra
        Line line1 = new Line(13.5, 23.0, 16.5, 23.0);
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(0.5);

        // Crear la primera línea gris claro
        Line line2 = new Line(8.0, 12.0, 22.0, 28.0);
        line2.setStroke(Color.LIGHTGRAY);
        line2.setStrokeWidth(2.0);

        // Crear la segunda línea gris claro
        Line line3 = new Line(22.0, 12.0, 8.0, 28.0);
        line3.setStroke(Color.LIGHTGRAY);
        line3.setStrokeWidth(2.0);

        // Crear la línea negra vertical
        Line line4 = new Line(15.0, 7.0, 15.0, 0.0);
        line4.setStroke(Color.BLACK);
        line4.setStrokeWidth(1.0);

        // Crear el primer triángulo naranja
        Polygon triangle2 = new Polygon();
        triangle2.getPoints().addAll(15.0, 0.0, 13.0, 2.0, 15.0, 4.0, 17.0, 2.0);
        triangle2.setFill(Color.ORANGE);

        // Crear el segundo triángulo amarillo
        Polygon triangle3 = new Polygon();
        triangle3.getPoints().addAll(15.0, -1.0, 14.0, 1.5, 15.0, 3.0, 16.0, 1.5);
        triangle3.setFill(Color.YELLOW);

        // Agregar todos los elementos al grupo
        group.getChildren().addAll(circle1, circle2, circle3, circle4, triangle1, line1, line2, line3, line4, triangle2, triangle3);
        return group;
    }
}
