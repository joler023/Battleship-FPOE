package com.battleship.battleshipfpoe.model;

import javafx.scene.Group;

public interface BoatInterface {
    void placeBoat(double startX, double startY, int length, boolean isHorizontal);
    int getLength();
    double getStartX();
    double getStartY();
    boolean isHorizontal();
}

