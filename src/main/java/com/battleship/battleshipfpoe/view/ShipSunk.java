package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ShipSunk {

    public Group getShipSunk() {
        Group group = new Group();
        // Crear el primer polígono rojo
        Polygon polygon1 = new Polygon();
        polygon1.getPoints().addAll(15.0, 0.0, 10.0, 8.0, 8.0, 12.0, 10.0, 20.0, 5.0, 24.0, 8.0, 28.0, 15.0, 30.0, 22.0, 28.0, 25.0, 24.0, 20.0, 20.0, 22.0, 12.0, 20.0, 8.0);
        polygon1.setFill(Color.RED);

        // Crear el segundo polígono naranja
        Polygon polygon2 = new Polygon();
        polygon2.getPoints().addAll(15.0, 3.0, 12.0, 10.0, 10.0, 14.0, 12.0, 20.0, 8.0, 24.0, 12.0, 26.0, 15.0, 27.0, 18.0, 26.0, 22.0, 24.0, 18.0, 20.0, 20.0, 14.0, 18.0, 10.0);
        polygon2.setFill(Color.ORANGE);

        // Crear el tercer polígono amarillo
        Polygon polygon3 = new Polygon();
        polygon3.getPoints().addAll(15.0, 6.0, 13.0, 12.0, 12.0, 18.0, 15.0, 22.0, 18.0, 18.0, 17.0, 12.0);
        polygon3.setFill(Color.YELLOW);

        // Crear los círculos amarillos
        Circle circle1 = new Circle(5.0, 10.0, 1.5, Color.YELLOW);
        Circle circle2 = new Circle(25.0, 10.0, 1.5, Color.YELLOW);

        // Crear los círculos naranjas
        Circle circle3 = new Circle(4.0, 15.0, 1.0, Color.ORANGE);
        Circle circle4 = new Circle(26.0, 15.0, 1.0, Color.ORANGE);

        // Crear los círculos rojos
        Circle circle5 = new Circle(6.0, 20.0, 0.8, Color.RED);
        Circle circle6 = new Circle(24.0, 20.0, 0.8, Color.RED);

        group.getChildren().addAll(polygon1, polygon2, polygon3, circle1, circle2, circle3, circle4, circle5, circle6);
        return group;
    }
}
