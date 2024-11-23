package com.battleship.battleshipfpoe.model;


import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Abstract base class for managing the board in the Battleship game.
 * This class defines the properties and methods for initializing, resetting,
 * and interacting with the game board.
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

public abstract class BoardBase implements IBoardBase{

    private final double planeWidth;
    private final double planeHeight;
    private final int tilesAcross;
    private final int tileAmount;
    private final int gridSize;
    private final int tilesDown;
    private final AnchorPane anchorPane;
    private final ArrayList<ArrayList<Integer>> board;
    private Game game;

    /**
     * Constructs a BoardBase object with the given parameters.
     * Initializes the board's grid and the size of its cells based on the given plane width and height.
     *
     * @param planeWidth  the width of the plane (in pixels).
     * @param planeHeight the height of the plane (in pixels).
     * @param gridSize    the size of the grid (number of rows and columns).
     * @param anchorPane  the JavaFX AnchorPane to render the board.
     * @since 1.0
     */
    public BoardBase(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;
        this.gridSize = gridSize;
        this.anchorPane = anchorPane;
        game= new Game();

        tilesAcross = (int) (planeWidth / gridSize);
        tileAmount = (int) ((planeWidth / gridSize) * (planeHeight / gridSize));
        tilesDown = tileAmount / tilesAcross;

        // Initialize the board
        board = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < gridSize; j++) {
                row.add(0); // Default to 0 (water)
            }
            board.add(row);
        }
    }

    /**
     * Retrieves the value at a specific cell on the board.
     *
     * @param row the row index (0 to gridSize-1)
     * @param col the column index (0 to gridSize-1)
     * @return the value at the specified cell
     */
    public int getCell(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * Sets a value at a specific cell on the board.
     *
     * @param row   the row index (0 to gridSize-1)
     * @param col   the column index (0 to gridSize-1)
     * @param value the value to set
     */
    public void setCell(int row, int col, int value) {
        board.get(row).set(col, value);
    }

    /**
     * Resets the board, initializing all cells to 0 (water).
     */
    public void resetBoard() {
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Integer> row = board.get(i);
            for (int j = 0; j < gridSize; j++) {
                row.set(j, 0);
            }
        }
    }

    // Getters
    /**
     * Retrieves the width of the plane.
     *
     * @return the width of the plane.
     * @since 1.0
     */
    public double getPlaneWidth() {
        return planeWidth;
    }

    /**
     * Retrieves the height of the plane.
     *
     * @return the height of the plane.
     * @since 1.0
     */
    public double getPlaneHeight() {
        return planeHeight;
    }

    /**
     * Retrieves the number of tiles across the plane (number of columns).
     *
     * @return the number of tiles across.
     * @since 1.0
     */
    public int getTilesAcross() {
        return tilesAcross;
    }

    /**
     * Retrieves the total number of tiles on the board (total grid size).
     *
     * @return the total number of tiles.
     * @since 1.0
     */
    public int getTileAmount() {
        return tileAmount;
    }

    /**
     * Retrieves the grid size (number of rows and columns).
     *
     * @return the grid size.
     * @since 1.0
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Retrieves the number of tiles down the plane (number of rows).
     *
     * @return the number of tiles down.
     * @since 1.0
     */
    public int getTilesDown() {
        return tilesDown;
    }

    /**
     * Retrieves the AnchorPane associated with this board for rendering.
     *
     * @return the AnchorPane for rendering the board.
     * @since 1.0
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Retrieves the board as a 2D array of integers.
     *
     * @return the 2D array representing the board.
     * @since 1.0
     */
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    /**
     * Prints the current state of the board for debugging purposes.
     * This method outputs the board to the console, where 0 represents water and other numbers represent ships.
     *
     * @since 1.0
     */
    public void printBoard() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(getCell(i, j) + " ");
            }
            System.out.println();
        }
    }
}
