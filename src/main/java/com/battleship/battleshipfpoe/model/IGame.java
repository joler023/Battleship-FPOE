package com.battleship.battleshipfpoe.model;

import javafx.scene.control.Button;

import java.util.List;

public interface IGame {
    void fillMatrixPlayer();
    int getDestroyedBoatsCount(List<Boat> boats, PlayerBoard playerBoard);
    void shootingMachine(List<Boat> boats, PlayerBoard playerBoard);
    boolean handleShot(int row, int col, PlayerBoard playerBoard);
    void updateCellGraphic(int row, int col, boolean hit);
    void checkAndMarkDestroyedBoats(List<Boat> boats, PlayerBoard playerBoard);
    boolean isBoatDestroyed(Boat boat, List<List<Integer>> matrix);
    void markDestroyedBoat(Boat boat, List<List<Integer>> matrixPlayer);
}
