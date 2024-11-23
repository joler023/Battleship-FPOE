package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.Submarine;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
/**
 * Represents a boat in the Battleship game. This class handles the boat's position,
 * orientation (horizontal/vertical), and interactions on the game board, as well as
 * visual representation and tracking hits.
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
public class Boat extends Group implements BoatInterface {

    private final String name;
    private static final int SQUARE_SIZE = 60;
    private int length;
    private boolean isHorizontal;
    private double startX, startY;
    private int currentRow = -1;
    private int currentCol = -1;
    private BoardHandler boardHandler;
    private boolean rotated = false;
    private boolean wasFirstMove = true;
    private final Group boatStyle; // Grupo que define el estilo visual del barco
    private Game game;
    private boolean[] hits; // Array para rastrear impactos

    /**
     * Constructs a Boat object with the specified parameters.
     * Initializes the boat's visual style, position, and size.
     *
     * @param name       the name of the boat (e.g., "Frigate", "Destroyer").
     * @param startX     the starting X position of the boat on the board.
     * @param startY     the starting Y position of the boat on the board.
     * @param length     the length of the boat (number of tiles it occupies).
     * @param isHorizontal boolean indicating whether the boat is placed horizontally.
     * @param boatStyle  the visual style (Group) of the boat.
     * @since 1.0
     */
    public Boat(String name, double startX, double startY, int length, boolean isHorizontal, Group boatStyle) {
        this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.boatStyle = boatStyle;
        game = new Game();

        this.hits = new boolean[length]; // Initialize the hits array


        // Initialize the boat with its visual style and starting position
        placeBoat(startX, startY, length, isHorizontal);

        setupInteractions();
    }

    /**
     * Marks a specific section of the boat as hit.
     *
     * @param index the index of the section that was hit.
     * @since 1.0
     */
    public void markHit(int index) {
        hits[index] = true; // Mark section as impacted
    }

    /**
     * Checks if the boat is sunk, i.e., all sections have been hit.
     *
     * @return true if all sections of the boat are hit, false otherwise.
     * @since 1.0
     */
    public boolean isSunk() {
        // Check if all sections have been hit
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }

    /**
     * Places the boat on the board at the specified position.
     * Adjusts the boat's rotation and scale based on its orientation.
     *
     * @param startX     the starting X position on the board.
     * @param startY     the starting Y position on the board.
     * @param length     the length of the boat.
     * @param isHorizontal boolean indicating whether the boat is placed horizontally.
     * @since 1.0
     */
    @Override
    public void placeBoat(double startX, double startY, int length, boolean isHorizontal) {
        // Adjust rotation and scale based on orientation
        boatStyle.setScaleX(1.5);
        boatStyle.setScaleY(2);
        if (!isHorizontal) {
            boatStyle.setRotate(90);
        }

        // Add the boat's style to the Group
        getChildren().clear(); // Clear previous contents
        getChildren().add(boatStyle);

        // Position the boat on the board
        setLayoutX(startX);
        setLayoutY(startY);
        toFront(); // Ensure the boat is in front
    }

    /**
     * Stores the boat's current position on the board.
     *
     * @param row the current row position.
     * @param col the current column position.
     * @since 1.0
     */
    public void storePosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
        System.out.println("Snapped at row: " + currentRow + ", col: " + currentCol);
    }

    /**
     * Clears the boat's position on the board based on its previous orientation.
     * This method is used when the boat is dragged or repositioned on the board.
     * It clears the cells where the boat was previously placed, according to the boat's previous orientation.
     *
     * @param boardHandler      the {@link BoardHandler} responsible for managing the board and updating the cells.
     * @param previousOrientation the previous orientation of the boat (horizontal or vertical) before it was moved.
     * @since 1.0
     * @see BoardHandler
     */
    public void clearBoatPosition(BoardHandler boardHandler, boolean previousOrientation) {
        if (currentRow == -1 || currentCol == -1) {
            return;
        }

        // Clear based on previous orientation
        if (!previousOrientation) { // Horizontal
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow, currentCol + i, 0);
            }
        } else { // Vertical
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow + i, currentCol, 0);
            }
        }
    }

    /**
     * Updates the boat's position on the board after it has been moved.
     * This method sets the value of the cells occupied by the boat to indicate that the boat is present at its new position.
     * If the boat is placed horizontally, it will fill the cells in the respective row. If the boat is placed vertically,
     * it will fill the cells in the respective column.
     *
     * @param boardHandler the {@link BoardHandler} responsible for managing the board state and updating the cells.
     * @since 1.0
     * @see BoardHandler
     */
    public void updateBoatPosition(BoardHandler boardHandler) {
        for (int i = 0; i < length; i++) {

            if (isHorizontal) {
                boardHandler.setCell(currentRow, currentCol + i, 1);
            } else {
                System.out.println("Row inicio: "+ currentRow + i + " Row fin: "  + (currentRow + i + length));
                boardHandler.setCell(currentRow + i, currentCol, 1);
            }
        }
    }

    /**
     * Gets the length of the boat.
     *
     * @return the length of the boat in tiles.
     * @since 1.0
     */
    @Override
    public int getLength() {
        return length;
    }

    /**
     * Gets the starting X position of the boat.
     *
     * @return the starting X position.
     * @since 1.0
     */
    @Override
    public double getStartX() {
        return startX;
    }

    /**
     * Gets the starting Y position of the boat.
     *
     * @return the starting Y position.
     * @since 1.0
     */
    @Override
    public double getStartY() {
        return startY;
    }

    /**
     * Checks if the boat is placed horizontally.
     *
     * @return true if the boat is horizontal, false if vertical.
     * @since 1.0
     */
    @Override
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Sets the orientation of the boat (horizontal or vertical).
     *
     * @param horizontal true to place the boat horizontally, false for vertical.
     * @since 1.0
     */
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    /**
     * Gets the current position of the boat on the board.
     *
     * @return an array containing the current row and column positions of the boat.
     * @since 1.0
     */
    public int[] getPosition() {
        return new int[]{currentRow, currentCol};
    }

    /**
     * Gets the visual style of the boat.
     *
     * @return the Group containing the boat's visual elements.
     * @since 1.0
     */
    public Group getBoatStyle(){
        return boatStyle;
    }

    /**
     * Sets the board handler to update the board state when necessary.
     *
     * @param boardHandler the board handler to associate with the boat.
     * @since 1.0
     */
    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    /**
     * Sets the position of the boat on the board.
     *
     * @param row the row position of the boat.
     * @param col the column position of the boat.
     * @since 1.0
     */
    public void setPosition(int row, int col){
        currentRow = row;
        currentCol = col;
    }

    /**
     * Sets up the interactions for moving the boat (dragging and rotating).
     * The boat can be dragged around the board, and the user can rotate it.
     *
     * @since 1.0
     */
    private void setupInteractions() {
        this.setOnMousePressed(event -> {
            this.setUserData(new double[]{
                    event.getSceneX() - this.getLayoutX(),
                    event.getSceneY() - this.getLayoutY()
            });
            this.requestFocus();
        });

        this.setOnMouseDragged(event -> {
            double[] offsets = (double[]) this.getUserData();
            double newX = event.getSceneX() - offsets[0];
            double newY = event.getSceneY() - offsets[1];

            this.setLayoutX(newX);
            this.setLayoutY(newY);
        });
    }

    /**
     * Rotates the boat by 90 degrees, switching between horizontal and vertical orientations.
     *
     * @since 1.0
     */
    public void rotate() {
        rotated = !rotated;
        isHorizontal = !isHorizontal;

        boatStyle.setRotate(rotated ? -90 : 0);

        placeBoat(getLayoutX(), getLayoutY(), length, isHorizontal);
    }

    /**
     * Gets the name of the boat.
     *
     * @return the name of the boat (e.g., "Frigate", "Destroyer").
     * @since 1.0
     */
    public String getName(){
        return name;
    }

    /**
     * Checks if the boat is at its first move.
     *
     * @return true if the boat is at its first move, false otherwise.
     * @since 1.0
     */
    public boolean isWasFirstMove() {
        return wasFirstMove;
    }

    /**
     * Sets whether the boat is at its first move.
     *
     * @param wasFirstMove true if this is the boat's first move, false otherwise.
     * @since 1.0
     */
    public void setWasFirstMove(boolean wasFirstMove) {
        this.wasFirstMove = wasFirstMove;
    }

    /**
     * Checks if the boat has been rotated.
     *
     * @return true if the boat is rotated, false otherwise.
     * @since 1.0
     */
    public boolean isRotated() {
        return rotated;
    }
    /**
     * Sets whether the boat has been rotated.
     *
     * @param wasRotated true if the boat has been rotated, false otherwise.
     * @since 1.0
     */
    public void setRotated(boolean wasRotated) {
        rotated = wasRotated;
    }
}
