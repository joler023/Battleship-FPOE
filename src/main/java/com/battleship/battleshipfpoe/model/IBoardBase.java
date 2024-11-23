package com.battleship.battleshipfpoe.model;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public interface IBoardBase {
    int getCell(int row, int col);
    void setCell(int row, int col, int value);
    void resetBoard();
    double getPlaneWidth();
    double getPlaneHeight();
    int getTilesAcross();
    int getTileAmount();
    int getGridSize();
    int getTilesDown();
    AnchorPane getAnchorPane();
    ArrayList<ArrayList<Integer>> getBoard();
}
