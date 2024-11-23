package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the preparation phase of the Battleship game.
 * Handles the initialization of the board, positioning of boats, and preparation
 * for transitioning to the game stage.
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

public class PreparationController implements Initializable {

    @FXML
    private AnchorPane BoardPane;
    boolean isSnapped;
    boolean beingDragged;
    final boolean[] initialOrientation = new boolean[1];
    private boolean wasSnapped = false; // Nueva variable

    /**
     * Handler for serializable files.
     * Manages the serialization and deserialization of data in the Battleship game.
     *
     * @serial Handles serialized data storage and retrieval.
     * @since 1.0
     */
    private SerializableFileHandler serializableFileHandler;

    /**
     * Handler for plain text files.
     * Provides support for reading and writing game data in plain text format.
     *
     * @serial Handles plain text data storage.
     * @since 1.0
     */
    private PlaneTextFileHandler planeTextFileHandler;
    private GameController gameController;

    private List<Boat> boats;


    @FXML
    private Pane BoatPane;

    private boolean isPositionValid;

    private BoardHandler boardHandler;

    private final Map<Boat, int[]> boatPositionsMap = new HashMap<Boat, int[]>();

    /**
     * Initializes the preparation controller, setting up the board and positioning the boats.
     *
     * @param url the location of the FXML resource.
     * @param resourceBundle the resources for the FXML.
     * @throws IllegalStateException if the FXML resource is invalid or unavailable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBoard();
        serializableFileHandler = new SerializableFileHandler();
        planeTextFileHandler = new PlaneTextFileHandler();
        gameController = new GameController();

        // Separation between boats
        int horizontalSpacing = 80; // Horizontal spacing between boats
        int verticalSpacing = 100;  // Vertical spacing between rows

        // Create Frigates
        ArrayList<Boat> fragates = new ArrayList<>();

        int firstBoatXPosition = 20;
        int firstBoatYPosition = 50;

        // Creating Frigates with horizontal spacing
        for (int i = 0; i < 4; i++) {
            fragates.add(new Boat( "Fragata", firstBoatXPosition + (i * horizontalSpacing), firstBoatYPosition, 1, true, new Frigate().getFrigate()));
        }

        ArrayList<Boat> destroyers = new ArrayList<>();

        // Creating Destroyers with horizontal spacing
        for (int i = 0; i < 3; i++) {
            destroyers.add(new Boat("Destructor",firstBoatXPosition + (i * (horizontalSpacing + 30)), firstBoatYPosition + verticalSpacing, 2, true, new Destroyer().getDestroyer()));
        }

        ArrayList<Boat> submarines = new ArrayList<>();

        // Creating Submarines with horizontal spacing
        for (int i = 0; i < 2; i++) {
            submarines.add(new Boat("Submarino",firstBoatXPosition + (i * (horizontalSpacing +100)), firstBoatYPosition + (2 * verticalSpacing), 3, true, new Submarine().getSubmarine()));
        }

        // Adding Frigates to the panel
        for (Boat fragate : fragates) {
            addBoatToPane(fragate);
        }

        // Adding Destroyers to the panel
        for (Boat destroyer : destroyers) {
            addBoatToPane(destroyer);
        }

        // Adding Submarines to the panel
        for (Boat submarine : submarines) {
            addBoatToPane(submarine);
        }

        addBoatToPane(new Boat("Portaviones",firstBoatXPosition, firstBoatYPosition + (3 * verticalSpacing), 4, true, new AircraftCarrier().getAircraftCarrier()));
    }

    /**
     * Initializes the board by setting dimensions and updating the grid.
     *
     * @see BoardHandler
     */
    private void initializeBoard() {
        double planeWidth = 600;
        double planeHeight = 600;
        int gridSize = 10;


        boardHandler = new BoardHandler(planeWidth, planeHeight, gridSize, BoardPane);

        boardHandler.updateGrid(false);
    }

    /**
     * Adds a boat to the pane and sets up its drag-and-drop functionality.
     *
     * @param boat the boat object to be added to the pane.
     * @see Boat
     * @since 1.0
     */
    private void addBoatToPane(Boat boat) {
        setupDragAndDrop(boat);
        BoatPane.getChildren().add(boat);
        boat.setBoardHandler(boardHandler);
        boat.requestFocus();
    }

    /**
     * Snaps a boat to the nearest grid cell based on its current position and orientation.
     * If the position is invalid, the boat reverts to its initial position.
     *
     * @param boat the boat object to be snapped to the grid.
     * @param event the mouse event that triggered the snap action.
     * @param initialPosition the initial position of the boat before snapping.
     * @see BoardHandler
     * @since 1.0
     */
    private void snapToGrid(Boat boat, MouseEvent event, double[] initialPosition) {
        double boardX = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinX();
        double boardY = BoardPane.localToScene(BoardPane.getBoundsInLocal()).getMinY();
        double newX = event.getSceneX() - boardX;
        double newY = event.getSceneY() - boardY;

        double tileWidth = boardHandler.getTilesAcross();
        double tileHeight = boardHandler.getTilesDown();
        int col = (int) (newX / tileWidth);
        int row = (int) (newY / tileHeight);

        // Adjust snap behavior based on boat orientation
        if (boat.isRotated()) {
            col = (int) (newX / tileHeight); // Inverted calculation for vertical orientation
            row = (int) (newY / tileWidth);  // Inverted calculation for vertical orientation
        }


        if (ValidPlacement(boat, col, row)) {
            boat.setWasFirstMove(false);
            isPositionValid = true;
            isSnapped = true;



            // If it was already snapped and changed orientation, clear previous position
            if (wasSnapped && boat.isRotated() != initialOrientation[0]) {
                boat.clearBoatPosition(boardHandler, initialOrientation[0]); // Erase using previous orientation
            }


            switch(boat.getLength()){
                case 1: // Update position after rotation
                    boat.setLayoutX((col * tileWidth) + 10);
                    boat.setLayoutY((row * tileHeight) + 10);
                    break;
                case 3:
                    if(boat.isHorizontal()){
                        boat.setLayoutX((col * tileWidth) + 30);
                        boat.setLayoutY((row * tileHeight));
                    }else{
                        boat.setLayoutX((col * tileWidth) - 10);
                        boat.setLayoutY((row * tileHeight) + 70);
                    }
                    break;
                case 4:
                    if(boat.isHorizontal()){
                        boat.setLayoutX((col * tileWidth) + 30);
                        boat.setLayoutY((row * tileHeight));
                    }else{
                        boat.setLayoutX((col * tileWidth) - 30);
                        boat.setLayoutY((row * tileHeight) + 90);
                    }
                    break;
                default:
                    if(boat.isHorizontal()){
                        boat.setLayoutX((col * tileWidth) + 20);
                        boat.setLayoutY((row * tileHeight));
                        break;
                    }
                    boat.setLayoutX((col * tileWidth) + 20);
                    boat.setLayoutY((row * tileHeight) + 40);
                    break;
            }

            // Update position status
            boat.storePosition(row, col);
            boatPositionsMap.put(boat, new int[]{row, col});
            boat.updateBoatPosition(boardHandler);

            if (!BoardPane.getChildren().contains(boat)) {
                BoardPane.getChildren().add(boat);
            }

            boat.toFront();
            wasSnapped = true;
            return;
        }

        // If position is invalid, revert
        boat.setLayoutX(initialPosition[0]);
        boat.setLayoutY(initialPosition[1]);

        if (boat.isRotated() != initialOrientation[0]) {
            boat.rotate();
        }

        isPositionValid = false;
        isSnapped = false;

        if (!boat.isWasFirstMove()) {
            boat.updateBoatPosition(boardHandler);
        }
    }

    /**
     * Sets up drag-and-drop functionality for a boat, allowing users to move boats
     * across the board and validate their placement upon release.
     *
     * @param boat the boat object to be configured for drag-and-drop actions.
     * @see Boat
     * @since 1.0
     */
    private void setupDragAndDrop(Boat boat) {
        final double[] initialPosition = new double[2];
        final double[] mouseOffset = new double[2];

        boat.setOnMousePressed(event -> {
            initialPosition[0] = boat.getLayoutX();
            initialPosition[1] = boat.getLayoutY();
            initialOrientation[0] = boat.isRotated();

            mouseOffset[0] = event.getSceneX() - boat.getLayoutX();
            mouseOffset[1] = event.getSceneY() - boat.getLayoutY();
            boat.toFront();
            boat.requestFocus(); // Ensures the boat receives keyboard focus.
            boat.clearBoatPosition(boardHandler, initialOrientation[0]);
            boardHandler.printBoard();
        });

        boat.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseOffset[0];
            double newY = event.getSceneY() - mouseOffset[1];

            // Adjust position for rotated boats.
            if (boat.isRotated()) {
                // Calculate the new position taking into account the rotation
                newX = event.getSceneX() - mouseOffset[0];
                newY = event.getSceneY() - mouseOffset[1];
            }

            boat.setLayoutX(newX);
            boat.setLayoutY(newY);
            boat.toFront();
            beingDragged = true;
        });

        boat.setOnKeyPressed(event -> {
            if (beingDragged && event.getCode() == KeyCode.R) {
                // We save the initial position
                double oldX = boat.getLayoutX();
                double oldY = boat.getLayoutY();

                // Rotates the boat.
                boat.rotate();
                boat.setRotated(boat.isRotated());

                // Adjust position after rotation to maintain mouse alignment.
                double offsetX = oldX - boat.getLayoutX();
                double offsetY = oldY - boat.getLayoutY();

                // After rotation, adjust the position so that the ship is under the click
                boat.setLayoutX(oldX - offsetX); // Maintain position in X
                boat.setLayoutY(oldY - offsetY); // Maintain position in Y

                boat.toFront(); // Brings the boat to the front of the pane.
            }
        });

        boat.setOnMouseReleased(event -> {
            // Event handler for when the mouse is released after dragging.
            snapToGrid(boat, event, initialPosition);
            boat.toFront();
            System.out.println("-------------------");
            System.out.println(isPositionValid);
            boardHandler.printBoard();
            beingDragged = false;
        });

        boat.setFocusTraversable(true); // Habilita el enfoque del barco para recibir eventos de teclado
    }

    /**
     * Validates if the placement of a boat is valid based on its position and orientation.
     *
     * @param boat the boat to be validated.
     * @param col the column where the boat is placed.
     * @param row the row where the boat is placed.
     * @return true if the placement is valid, false otherwise.
     * @since 1.0
     * @see BoardHandler
     */
    private boolean ValidPlacement(Boat boat, int col, int row) {
        int boatSize = boat.getLength();

        if (!boat.isRotated()) {
            return isValidHorizontalPlacement(col, row, boatSize) && areCellsAvailableForHorizontal(col, row, boatSize);
        } else {
            return isValidVerticalPlacement(col, row, boatSize) && areCellsAvailableForVertical(col, row, boatSize);
        }
    }

    /**
     * Adds a boat to the list of boats.
     *
     * @param boat the boat to be added.
     * @since 1.0
     * @see Boat
     */
    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    /**
     * Checks if the horizontal placement of a boat is valid within the board's boundaries.
     *
     * @param col the column where the boat starts.
     * @param row the row where the boat is placed.
     * @param boatSize the size of the boat.
     * @return true if the placement is valid, false otherwise.
     * @since 1.0
     */

    private boolean isValidHorizontalPlacement(int col, int row, int boatSize) {
        // Check that the boat does not overflow in the horizontal direction
        return col >= 0 && col + boatSize <= boardHandler.getGridSize();
    }

    /**
     * Checks if the vertical placement of a boat is valid within the board's boundaries.
     *
     * @param col the column where the boat is placed.
     * @param row the row where the boat starts.
     * @param boatSize the size of the boat.
     * @return true if the placement is valid, false otherwise.
     * @since 1.0
     */
    private boolean isValidVerticalPlacement(int col, int row, int boatSize) {
        // Check that the boat does not overflow in the vertical direction
        return row >= 0 && row + boatSize <= boardHandler.getGridSize();
    }

    /**
     * Verifies if the cells for a horizontal boat placement are free.
     *
     * @param col the starting column of the boat.
     * @param row the row where the boat is placed.
     * @param boatSize the size of the boat.
     * @return true if the cells are available, false otherwise.
     * @since 1.0
     * @see BoardHandler
     */
    private boolean areCellsAvailableForHorizontal(int col, int row, int boatSize) {
        // Check that the cells in the row are free for the horizontal ship
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row, col + i) || boardHandler.getCell(row, col + i) == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if the cells for a vertical boat placement are free.
     *
     * @param col the column where the boat is placed.
     * @param row the starting row of the boat.
     * @param boatSize the size of the boat.
     * @return true if the cells are available, false otherwise.
     * @since 1.0
     * @see BoardHandler
     */
    private boolean areCellsAvailableForVertical(int col, int row, int boatSize) {
        // Check that the cells in the column are free for the vertical ship
        for (int i = 0; i < boatSize; i++) {
            if (!boardHandler.isWithinBounds(row + i, col) || boardHandler.getCell(row + i, col) == 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * Handles the event of clicking the "Next" button, transitioning the game to the main stage.
     *
     * @param event the action event triggered by clicking the button.
     * @throws IOException if there is an issue loading the next stage.
     * @see GameStage
     * @see PreparationStage
     * @since 1.0
     */
    public void handleNextButton(ActionEvent event) {
            Player player = new Player();
            MachineBoard machineBoard = new MachineBoard();

            // Pass the list of Boat objects to the GameController
            List<Boat> boatsList = new ArrayList<>(boatPositionsMap.keySet());


          GameController gameController1 = GameStage.getInstance().getGameController();
            PreparationStage.deleteInstance();

            // Pass the list of boats to the GameController
            gameController1.setPlayer(player);
            gameController1.setBoatsList(boatsList);
    }

}
