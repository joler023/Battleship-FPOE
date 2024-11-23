package com.battleship.battleshipfpoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the player's board in the Battleship game.
 * This class manages the board's state, including the placement of ships, tracking water shots,
 * touched ships, and sunken ships. It provides methods to initialize, manipulate, and display the board.
 *
 * @author Maycol Andres Taquez Carlosama
 * @code 2375000
 * @author Santiago Valencia Aguiño
 * @code 2343334
 * @author Joel Andres Ochoa Sará
 * @code 2341100
 * @version 1.0
 * @since 1.0
 */
public class PlayerBoard {
    private List<List<Integer>> matrixPlayer;
    private List<Integer> shipsTouched;
    private List<Integer> sunkenShips;
    private List<Integer> waterShots;
    private Game game;

    /**
     * Constructs a new {@link PlayerBoard} instance and initializes the board.
     * The board is represented as a 10x10 matrix, with all cells initially set to 0 (water).
     *
     * @since 1.0
     * @see #generateBoardPlayer()
     */
    public PlayerBoard() {
        matrixPlayer = new ArrayList<>();
        game = new Game();
        generateBoardPlayer();
    }

    /**
     * Generates a 10x10 board matrix for the player.
     * All cells are initialized to 0, representing water.
     *
     * @see #generateBoardPlayer()
     * @since 1.0
     */
    public void generateBoardPlayer() {
        for (int i = 0; i < 10; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(0);
            }
            matrixPlayer.add(row);
        }
    }

    /**
     * Retrieves the player's board matrix.
     *
     * @return the player's board matrix as a list of lists.
     * @since 1.0
     */
    public List<List<Integer>> getMatrixPlayer() {
        return matrixPlayer;
    }

    public void printMatrix(){
        for (int i = 0; i < matrixPlayer.size(); i++) {
            for (int j = 0; j < matrixPlayer.get(1).size(); j++) {
                System.out.print(matrixPlayer.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Places a ship on the player's board at the specified position and orientation.
     * The ship's cells are marked with the value 1.
     *
     * @param row the starting row index for the ship.
     * @param col the starting column index for the ship.
     * @param length the length of the ship.
     * @param isHorizontal true if the ship is placed horizontally, false if placed vertically.
     * @since 1.0
     */
    public void placeShip(int row, int col, int length, boolean isHorizontal) {
        System.out.println("Length: " + length);
        for (int i = 0; i < length; i++) {
            if (isHorizontal) {
                matrixPlayer.get(row).set(col + i, 1); // Replace the value instead of shifting it

            } else {
                matrixPlayer.get(row + i).set(col, 1); // Replace the value instead of shifting it
            }
        }
    }
}
