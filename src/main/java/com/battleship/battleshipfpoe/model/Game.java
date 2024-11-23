package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.BombTouch;
import com.battleship.battleshipfpoe.view.ShipSunk;
import com.battleship.battleshipfpoe.view.WaterShot;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;


import java.util.List;
import java.util.Random;

/**
 * Manages the gameplay logic for the Battleship game, including handling the game board,
 * shooting actions, detecting hit or sunk ships, and updating the player's and machine's state.
 * This class interacts with various views and models to update the game state and interface.
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
public class Game implements IGame{
    private final Button[][] matrix;
    private WaterShot waterShot;
    private BombTouch bombTouch;
    private ShipSunk shipSunk;
    private Boat boat;
    private int[][] matrixPlayer;

    /**
     * Constructor de la clase {@link Game}. Inicializa las matrices para la representación del tablero de juego,
     * así como los elementos gráficos relacionados con el agua, los impactos y los barcos hundidos.
     * También llama a {@link #fillMatrixPlayer()} para inicializar el tablero del jugador.
     *
     * @since 1.0
     * @see WaterShot
     * @see BombTouch
     * @see ShipSunk
     */
    public Game() {
        matrix = new Button[10][10];
        matrixPlayer = new int[10][10];
        waterShot = new WaterShot();
        bombTouch = new BombTouch();
        shipSunk = new ShipSunk();
        fillMatrixPlayer();
    }

    /**
     * Fills the player's matrix with initial values (0 represents water).
     * This method sets all the values of the player's matrix to 0, indicating that all cells are water,
     * with no ships placed initially.
     *
     * @since 1.0
     */
    public void fillMatrixPlayer(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                matrixPlayer[i][j] = 0;
            }
        }
    }

    /**
     * Gets the count of destroyed boats by checking each boat's position and verifying if all parts of it are hit.
     *
     * @param boats the list of boats in the game.
     * @param playerBoard the player's board containing the game matrix.
     * @return the number of destroyed boats.
     * @since 1.0
     */
    public int getDestroyedBoatsCount(List<Boat> boats, PlayerBoard playerBoard) {
        int destroyedCount = 0;

        List<List<Integer>> matrix = playerBoard.getMatrixPlayer();

        for (Boat boat : boats) {
            int[] position = boat.getPosition();
            if (position[0] == -1 || position[1] == -1) continue; // Skip unplaced boats

            // Check if all cells of the boat are marked as sunk (3)
            boolean isDestroyed = true;
            for (int i = 0; i < boat.getLength(); i++) {
                int cellValue = boat.isHorizontal()
                        ? matrix.get(position[0]).get(position[1] + i)
                        : matrix.get(position[0] + i).get(position[1]);

                if (cellValue != 3) {
                    isDestroyed = false;
                    break;
                }
            }

            if (isDestroyed) {
                destroyedCount++; // Increment the count if the boat is sunk
            }
        }

        return destroyedCount; // Return the number of destroyed boats
    }

    /**
     * Simulates the machine's shooting action. The machine randomly selects a cell to shoot at.
     * If a ship is hit, it updates the state and checks if the boat is destroyed.
     *
     * @param boats the list of boats in the game.
     * @param playerBoard the player's board containing the game matrix.
     * @since 1.0
     * @see InvalidShotException
     */
    public void shootingMachine(List<Boat> boats, PlayerBoard playerBoard) {
        System.out.println("Turno de la máquina");
        Random random = new Random();

        while (true) {
            int row = random.nextInt(10); // Random index for row
            int col = random.nextInt(10); // Random index for column

            try {
                // Call the checkIfShotIsValid method to check if the shot is valid
                checkIfShotIsValid(row, col, playerBoard);

                int playerBoardShotValue = playerBoard.getMatrixPlayer().get(row).get(col);

                // Perform the shot
                boolean hit = handleShot(row, col, playerBoard);

                // Update the graphical interface
                updateCellGraphic(row, col, hit);

                // If a boat is hit, check if it is completely destroyed
                if (hit) {
                    checkAndMarkDestroyedBoats(boats, playerBoard);
                }

                // If no boat is hit, end the turn
                if (!hit) {
                    playerBoard.getMatrixPlayer().get(row).set(col, -1);
                    break;
                }

            } catch (InvalidShotException e) {
                // If the shot is invalid, try again with a new shot
                System.err.println(e.getMessage());
            }
        }
    }

    // OWN EXCEPTION: To handle invalid shots
    /**
     * Custom exception used to handle invalid shots in the game.
     * It is thrown when a shot is made on a cell that has already been targeted or if it is out of bounds.
     *
     * @since 1.0
     */
    public static class InvalidShotException extends Exception {
        /**
         * Constructor for the {@link InvalidShotException}.
         * Initializes the exception with a specific error message.
         *
         * @param message the error message describing the issue.
         * @since 1.0
         */
        public InvalidShotException(String message) {
            super(message);
        }
    }

    // Method to verify if the shot is valid
    /**
     * Verifies if the shot at the specified row and column is valid.
     * A shot is invalid if it has already been made at that cell or if it's out of bounds.
     *
     * @param row the row of the shot.
     * @param col the column of the shot.
     * @param playerBoard the player's board containing the game matrix.
     * @throws InvalidShotException if the shot is invalid.
     * @since 1.0
     */
    public void checkIfShotIsValid(int row, int col, PlayerBoard playerBoard) throws InvalidShotException {
        int playerBoardShotValue = playerBoard.getMatrixPlayer().get(row).get(col);

        // If the cell has already been shot at, throw an exception
        if (playerBoardShotValue == 2 || playerBoardShotValue == 3 || playerBoardShotValue == -1) {
            throw new InvalidShotException("Disparo en una celda ya atacada o fuera de límites.");
        }
    }

    /**
     * Handles the shot at the specified row and column. It marks the cell as shot and checks if a boat is hit.
     *
     * @param row the row of the shot.
     * @param col the column of the shot.
     * @param playerBoard the player's board containing the game matrix.
     * @return true if a boat is hit, false otherwise.
     * @since 1.0
     */
    public boolean handleShot(int row, int col, PlayerBoard playerBoard) {
        List<List<Integer>> matrix = playerBoard.getMatrixPlayer();
        boolean isABoatThere = (matrix.get(row).get(col) == 1);
        matrix.get(row).set(col, 2); // Marca la celda como disparada
        return isABoatThere;
    }

    /**
     * Updates the graphical representation of a cell after a shot has been made.
     * If the shot hit a boat, it displays a bomb graphic; otherwise, it shows water.
     *
     * @param row the row of the shot.
     * @param col the column of the shot.
     * @param hit true if the shot hit a boat, false otherwise.
     * @since 1.0
     * @see BombTouch
     * @see WaterShot
     */
    public void updateCellGraphic(int row, int col, boolean hit) {
        Button btn = matrix[row][col];
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Group graphic = hit ? bombTouch.getBombTouch() : waterShot.getWaterShot();
        btn.setGraphic(graphic);
    }

    /**
     * Checks if any boats are destroyed after a hit and marks them as destroyed if necessary.
     *
     * @param boats the list of boats in the game.
     * @param playerBoard the player's board containing the game matrix.
     * @since 1.0
     */
    public void checkAndMarkDestroyedBoats(List<Boat> boats, PlayerBoard playerBoard) {
        List<List<Integer>> matrix = playerBoard.getMatrixPlayer();

        for (Boat boat : boats) {
            int[] position = boat.getPosition();
            if (position[0] == -1 || position[1] == -1) continue; // Ignorar barcos no colocados

            boolean isDestroyed = isBoatDestroyed(boat, matrix);

            if (isDestroyed) {
                markDestroyedBoat(boat, matrix);
                System.out.println("¡Barco destruido: " + boat.getName() + "!");
            }
        }
    }

    /**
     * Checks if a specific boat is destroyed by verifying that all its cells are marked as hit.
     *
     * @param boat the boat to check.
     * @param matrix the player's game matrix.
     * @return true if the boat is destroyed, false otherwise.
     * @since 1.0
     */
    public boolean isBoatDestroyed(Boat boat, List<List<Integer>> matrix) {
        int row = boat.getPosition()[0];
        int col = boat.getPosition()[1];

        for (int i = 0; i < boat.getLength(); i++) {
            int cellValue = boat.isHorizontal()
                    ? matrix.get(row).get(col + i)
                    : matrix.get(row + i).get(col);

            if (cellValue != 2) return false;
        }

        return true;
    }

    /**
     * Marks the boat as destroyed on the board and updates the graphical representation.
     * The boat is visually marked as sunk and its cells are updated in the player's matrix.
     *
     * @param boat the boat to mark as destroyed.
     * @param matrixPlayer the player's game matrix.
     * @since 1.0
     * @see ShipSunk
     */
    public void markDestroyedBoat(Boat boat, List<List<Integer>> matrixPlayer) {
        int row = boat.getPosition()[0];
        int col = boat.getPosition()[1];

        for (int i = 0; i < boat.getLength(); i++) {
            if (boat.isHorizontal()) {
                if (matrixPlayer.get(row).get(col + i) == 2) { // Verificar si la celda ya fue disparada
                    matrixPlayer.get(row).set(col + i, 3); // Cambiar a estado de barco hundido
                    matrix[row][col + i].setGraphic(shipSunk.getShipSunk());
                }
            } else {
                if (matrixPlayer.get(row + i).get(col) == 2) {
                    matrixPlayer.get(row + i).set(col, 3);
                    matrix[row + i][col].setGraphic(shipSunk.getShipSunk());
                }
            }
        }
    }


    /**
     * Sets the matrix value for the specified cell in the game matrix.
     *
     * @param i the row index.
     * @param j the column index.
     * @param btn the button associated with the cell.
     * @since 1.0
     */
    public void setMatrix(int i, int j, Button btn){
        matrix[i][j]=btn;
    }

    /**
     * Sets the value in the player's matrix for the specified cell.
     *
     * @param i the row index.
     * @param j the column index.
     * @param num the value to set.
     * @since 1.0
     */
    public void setMatrixPlayer(int i, int j, int num){
        this.matrixPlayer[i][j]=num;
    }

    /**
     * Prints the current state of the player's matrix to the console for debugging purposes.
     * This method loops through the player's matrix and displays the state of each cell, where:
     * 0 represents water, 1 represents a ship, 2 represents a successful shot, and 3 represents a sunk ship.
     *
     * @since 1.0
     */
    public void imprimirMatrizJugador(){
        System.out.println("MATRIZ DE JUGADOR EN EL GAME");
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(matrixPlayer[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
