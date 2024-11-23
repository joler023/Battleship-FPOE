package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class WaterShot {

    public Group getWaterShot() {
        Group group = new Group();
        // Crear el primer polígono rojo
        Polygon polygon1 = new Polygon();
        polygon1.getPoints().addAll(5.0, 4.0, 8.0, 2.0, 27.0, 23.0, 24.0, 25.0);
        polygon1.setFill(Color.RED);

        // Crear el segundo polígono rojo
        Polygon polygon2 = new Polygon();
        polygon2.getPoints().addAll(27.0, 4.0, 24.0, 2.0, 5.0, 23.0, 8.0, 25.0);
        polygon2.setFill(Color.RED);

        // Crear el primer polígono rojo oscuro
        Polygon polygon3 = new Polygon();
        polygon3.getPoints().addAll(5.0, 4.0, 7.5, 5.5, 26.0, 24.0, 24.0, 23.0);
        polygon3.setFill(Color.DARKRED);

        // Crear el segundo polígono rojo oscuro
        Polygon polygon4 = new Polygon();
        polygon4.getPoints().addAll(27.0, 4.0, 25.5, 5.5, 6.0, 24.0, 8.0, 23.0);
        polygon4.setFill(Color.DARKRED);

        group.getChildren().addAll(polygon1, polygon2, polygon3, polygon4);
        // Agregar todos los polígonos al grupo
        return group;
    }

}
