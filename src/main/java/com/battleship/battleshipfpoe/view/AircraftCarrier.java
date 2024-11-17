package com.battleship.battleshipfpoe.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class AircraftCarrier {
    private Group aircraftCarrier;
    public AircraftCarrier() {
        // Crear el Polyline
        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(163.625, 20.125, 146.25, 33.375, 28.5, 33.375, 18.75, 20.125);
        polyline.setFill(Color.web("#2f312d"));
        polyline.setStroke(Color.WHITE);
        polyline.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        polyline.setStrokeWidth(0.0);
        polyline.setLayoutX(-13.0);
        polyline.setLayoutY(1.0);

        // Crear los Rectángulos
        Rectangle rect1 = new Rectangle(11.0, 18.0, Color.DODGERBLUE);
        rect1.setArcHeight(5.0);
        rect1.setArcWidth(5.0);
        rect1.setLayoutX(18.0);
        rect1.setLayoutY(4.0);
        rect1.setStroke(Color.BLACK);
        rect1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect1.setStrokeWidth(0.0);

        Rectangle rect2 = new Rectangle(11.0, 18.0, Color.DODGERBLUE);
        rect2.setArcHeight(5.0);
        rect2.setArcWidth(5.0);
        rect2.setLayoutX(35.0);
        rect2.setLayoutY(4.0);
        rect2.setStroke(Color.BLACK);
        rect2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect2.setStrokeWidth(0.0);

        Rectangle rect3 = new Rectangle(18.0, 9.0, Color.web("#848a90"));
        rect3.setArcHeight(5.0);
        rect3.setArcWidth(5.0);
        rect3.setLayoutX(73.0);
        rect3.setLayoutY(13.0);
        rect3.setStroke(Color.BLACK);
        rect3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect3.setStrokeWidth(0.0);

        Rectangle rect4 = new Rectangle(18.0, 9.0, Color.web("#898c8f"));
        rect4.setArcHeight(5.0);
        rect4.setArcWidth(5.0);
        rect4.setLayoutX(114.0);
        rect4.setLayoutY(13.0);
        rect4.setStroke(Color.BLACK);
        rect4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect4.setStrokeWidth(0.0);

        Rectangle rect5 = new Rectangle(20.0, 4.0, Color.DODGERBLUE);
        rect5.setArcHeight(5.0);
        rect5.setArcWidth(5.0);
        rect5.setLayoutX(88.0);
        rect5.setLayoutY(13.0);
        rect5.setStroke(Color.BLACK);
        rect5.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect5.setStrokeWidth(0.0);

        Rectangle rect6 = new Rectangle(20.0, 4.0, Color.DODGERBLUE);
        rect6.setArcHeight(5.0);
        rect6.setArcWidth(5.0);
        rect6.setLayoutX(129.0);
        rect6.setLayoutY(13.0);
        rect6.setStroke(Color.BLACK);
        rect6.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        rect6.setStrokeWidth(0.0);

        aircraftCarrier = new Group(polyline, rect1, rect2, rect3, rect4, rect5, rect6);
    }

    public Group getAircraftCarrier() {
        return aircraftCarrier;
    }
}
