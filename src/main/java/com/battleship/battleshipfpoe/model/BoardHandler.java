package com.battleship.battleshipfpoe.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class responsible for handling the visual and logical aspects of the game board in the Battleship game.
 * It extends the {@link BoardBase} class to manage the board's state and rendering.
 * It also determines the colors of the tiles based on their state (water, ship, hit, miss).
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

public class BoardHandler extends BoardBase {

    private static final Color BACKGROUND_COLOR_1 = Color.rgb(127, 205, 255);
    private static final Color BACKGROUND_COLOR_2 = Color.rgb(6, 66, 115);
    private static final Color SHIP_COLOR = Color.GRAY;
    private static final Color HIT_COLOR = Color.RED;
    private static final Color MISS_COLOR = Color.BLUE;

    private boolean initializeEmpty;

    /**
     * Constructor for BoardHandler.
     * Initializes the board handler with the specified parameters and sets up the board grid.
     *
     * @param planeWidth  the width of the board in pixels.
     * @param planeHeight the height of the board in pixels.
     * @param gridSize    the size of each grid square (number of tiles per row/column).
     * @param anchorPane  the JavaFX AnchorPane where the board will be rendered.
     * @since 1.0
     */
    public BoardHandler(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);

    }



    /**
     * Updates the grid visually based on the current board state.
     * It draws each cell with a color corresponding to its value (water, ship, hit, or miss).
     * If the board is hidden, ships are not displayed.
     *
     * @param isBoardHidden boolean flag indicating whether the board should be hidden (true means hide).
     * @since 1.0
     * @see AnchorPane
     * @see Pane
     */
    public void updateGrid(boolean isBoardHidden) {
        getAnchorPane().getChildren().clear(); // Clear the board before redrawing it

        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                // Create a new cell
                Pane cell = new Pane();
                cell.setPrefSize(getTilesAcross(), getTilesDown());
                cell.setLayoutX(col * getTilesAcross());
                cell.setLayoutY(row * getTilesDown());
                cell.setStyle("-fx-border-color: black; -fx-background-color: transparent;"); // Fondo transparente

                // Determine the color of the cell based on its value
                int tileValue = getCell(row, col);
                Color tileColor;

                // If it's an enemy board, don't show ships
                if (isBoardHidden&& tileValue == 1) {
                    tileColor = BACKGROUND_COLOR_1; // Water (hide the ship)
                } else {
                    tileColor = determineTileColor(tileValue);
                }

                cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");

                // Add identifiers for the cell
                cell.setUserData(new int[]{row, col});
                getAnchorPane().getChildren().add(cell);
            }
        }
    }

    /**
     * Checks if a specific position is within the bounds of the grid.
     *
     * @param row the row index to check.
     * @param col the column index to check.
     * @return true if the position is within bounds, false otherwise.
     * @since 1.0
     */
    public boolean isWithinBounds(int row, int col) {
        int gridSize = getGridSize();
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    /**
     * Converts a JavaFX color to RGB format for use in CSS styling.
     *
     * @param color the color to convert.
     * @return the color in RGB format as a string (e.g., "rgb(255, 0, 0)").
     * @since 1.0
     */
    private String toRgbString(Color color) {
        return "rgb(" + (int)(color.getRed() * 255) + "," +
                (int)(color.getGreen() * 255) + "," +
                (int)(color.getBlue() * 255) + ")";
    }



    /**
     * Determines the color for a given tile value based on its state (water, ship, hit, or miss).
     *
     * @param tileValue the value of the tile (e.g., 0 for water, 1 for ship, 2 for hit, -1 for miss).
     * @return the corresponding color for the tile.
     * @since 1.0
     */
    private Color determineTileColor(int tileValue) {
        switch (tileValue) {
            case 1:  // Ship
                return SHIP_COLOR;
            case 2:  // Hit
                return HIT_COLOR;
            case -1: // Miss
                return MISS_COLOR;
            default: // Water
                return BACKGROUND_COLOR_1;  // Alternating pattern can be added if needed
        }
    }

    /**
     * Places a ship on the board at the specified position.
     *
     * @param row the row index where the ship will be placed.
     * @param col the column index where the ship will be placed.
     * @since 1.0
     */
    public void placeShip(int row, int col) {
        setCell(row, col, 1);  // 1 represents a ship
    }

    /**
     * Registers a hit on the board at the specified position.
     *
     * @param row the row index where the hit occurred.
     * @param col the column index where the hit occurred.
     * @since 1.0
     */
    public void registerHit(int row, int col) {
        setCell(row, col, 2);  // 2 represents a hit
    }

    /**
     * Registers a miss on the board at the specified position.
     *
     * @param row the row index where the miss occurred.
     * @param col the column index where the miss occurred.
     * @since 1.0
     */
    public void registerMiss(int row, int col) {
        setCell(row, col, -1);  // -1 represents a miss
    }
}
