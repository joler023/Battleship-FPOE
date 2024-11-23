package com.battleship.battleshipfpoe.model;

import java.util.List;

public interface IPlayerBoard {
    void generateBoardPlayer();
    List<List<Integer>> getMatrixPlayer();
    void printMatrix();
    void placeShip(int row, int col, int length, boolean isHorizontal);
}
