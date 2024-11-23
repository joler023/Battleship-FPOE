package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.AircraftCarrier;
import com.battleship.battleshipfpoe.view.Destroyer;
import com.battleship.battleshipfpoe.view.Frigate;
import com.battleship.battleshipfpoe.view.Submarine;
import javafx.scene.Group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the machine's game board, including the placement of ships and the management of the game matrix.
 * This class is responsible for generating the board, placing ships, and managing ship positions.
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
public class MachineBoard implements Serializable {
    private List<List<Integer>> matrixMachine;
    private Random rand;
    private List<String[]> shipsInfo; // Lista para almacenar información de los barcos
    private List<Boat> boats; // Lista para almacenar objetos Boat

    /**
     * Constructor for the {@link MachineBoard} class.
     * Initializes the game board for the machine, including the board matrix,
     * a random number generator, and lists to store ship information and boats.
     * This constructor also generates the initial board, places the ships,
     * and prints the details of the placed ships.
     *
     * @since 1.0
     * @see #generateBoardMachine()
     * @see #placeShips()
     * @see #printShipsInfo()
     */
    public MachineBoard() {
        matrixMachine = new ArrayList<>();
        rand = new Random();
        shipsInfo = new ArrayList<>();
        boats = new ArrayList<>();
        generateBoardMachine();
        placeShips();
        printShipsInfo();
    }

    /**
     * Generates an empty 10x10 matrix to represent the machine's game board.
     * Each cell in the matrix is initialized to 0, which represents water.
     * This method ensures that the machine's board is ready for ship placement.
     *
     * @since 1.0
     * @see #placeShips()
     */
    public void generateBoardMachine() {
        for (int i = 0; i < 10; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(0);
            }
            matrixMachine.add(row);
        }
    }

    // Check if a box is empty
    /**
     * Checks if a position is valid for placing a ship on the board.
     * It verifies whether the ship fits within the bounds of the board and if the cells are empty.
     *
     * @param x the starting x-coordinate (row index).
     * @param y the starting y-coordinate (column index).
     * @param size the size of the ship to place.
     * @param isHorizontal true if the ship is to be placed horizontally, false for vertical.
     * @return true if the position is valid, false otherwise.
     * @since 1.0
     */
    private boolean isValidPosition(int x, int y, int size, boolean isHorizontal) {
        // Check if the position is within the bounds of the matrix
        if (isHorizontal) {
            if (y + size > 10) return false; // Exceeds the right border
            for (int j = y; j < y + size; j++) {
                if (matrixMachine.get(x).get(j) == 1) return false; // Cell occupied
            }
        } else {
            if (x + size > 10) return false; // Exceeds the bottom border
            for (int i = x; i < x + size; i++) {
                if (matrixMachine.get(i).get(y) == 1) return false; // Cell occupied
            }
        }
        return true;
    }

    // A ship is positioned, depending on the size of the argument and its name
    /**
     * Places a ship on the board at a random valid position.
     * The ship is placed horizontally or vertically, depending on the random choice.
     *
     * @param size the size of the ship (number of cells).
     * @param shipName the name of the ship.
     * @param boatStyle the graphical representation of the boat.
     * @since 1.0
     */
    private void placeShip(int size, String shipName, Group boatStyle) {
        boolean placed = false;
        while (!placed) {
            // Select a random position (x, y) and direction (horizontal or vertical)
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            boolean isHorizontal = rand.nextBoolean();

            // Check if the ship can be placed at the selected position
            if (isValidPosition(x, y, size, isHorizontal)) {
                Boat boat = new Boat(shipName, x, y, size, isHorizontal, boatStyle);

                boat.setPosition(x,y);

                // Place the ship (mark the cells with 1)
                if (isHorizontal) {
                    for (int j = y; j < y + size; j++) {
                        matrixMachine.get(x).set(j, 1);
                    }
                } else {
                    for (int i = x; i < x + size; i++) {
                        matrixMachine.get(i).set(y, 1);
                    }
                }

                // Register ship information (initial and final positions)
                String[] shipInfo = new String[3];

                shipInfo[0] = shipName; // Ship name
                shipInfo[1] = "(" + x + "," + y + ")"; // Initial position

                // Determine final position based on orientation
                if (isHorizontal) {
                    shipInfo[2] = "(" + x + "," + (y + size - 1) + ")"; //Increment only the column
                } else {
                    shipInfo[2] = "(" + (x + size - 1) + "," + y + ")"; //Increment only the row

                }
                    shipsInfo.add(shipInfo); // Add information to the list

                // Add the Boat object to the boat list
                boats.add(boat);
                placed = true; // Mark that the ship has been placed
            }
        }
    }

    /**
     * Creates the graphical style for the ship based on its name.
     * Currently, this method returns a placeholder group. The style of the boat can be customized based on the ship type.
     *
     * @param shipName the name of the ship.
     * @return a {@link Group} representing the graphical style of the ship.
     * @since 1.0
     */
    private Group createBoatStyle(String shipName) {
        // Here you can create the visual style of the ship based on the name.
        // For example, depending on the type of ship (Aircraft Carrier, Submarine, etc.), you can
        // return a different style. This is assumed to be a basic implementation.
        return new Group(); // Placeholder, define how to create the style
    }

    /**
     * Places all ships on the machine's board.
     * This method places ships with varying sizes and names at random valid positions on the board.
     *
     * @since 1.0
     * @see #placeShip(int, String, Group)
     */
    public void placeShips() {
        placeShip(4, "Portaviones", new AircraftCarrier().getAircraftCarrier()); // Places the first aircraft carrier (4 cells)
        placeShip( 3, "Submarino", new Submarine().getSubmarine()); // Places the first submarine (3 cells)
        placeShip(3, "Submarino", new Submarine().getSubmarine()); // Places the second submarine (3 cells)
        placeShip(2, "Destructor", new Destroyer().getDestroyer()); // Places the first destroyer (2 cells)
        placeShip(2, "Destructor", new Destroyer().getDestroyer());  // Places the second destroyer (2 cells)
        placeShip(2, "Destructor", new Destroyer().getDestroyer()); // Places the third destroyer (2 cells)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Places the first frigate (1 cell)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Places the second frigate (1 cell)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Places the third frigate (1 cell)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Places the fourth frigate (1 cell)
    }

    // Method to print the information of the placed ships
    /**
     * Prints information about the ships placed on the board.
     * This method prints the name of the ship and its initial and final positions.
     *
     * @since 1.0
     */
    public void printShipsInfo() {
        // SERIALIZE ARRAY POSITIONS WITH SHIP POSITIONS
        for (String[] ship : shipsInfo) {
            System.out.println("Barco: " + ship[0] + ", Inicio: " + ship[1] + ", Fin: " + ship[2]); // Imprimir información del barco
        }
    }

    /**
     * Gets the machine's board matrix.
     *
     * @return a {@link List} representing the 10x10 matrix of the machine's board.
     * @since 1.0
     */
    public List<List<Integer>> getMatrix() {
        return matrixMachine;
    }
    /**
     * Gets the list of ship information on the machine's board.
     *
     * @return a {@link List} of ship information, where each entry contains the ship's name and its positions.
     * @since 1.0
     */
    public List<String[]> getShipsInfo() {
        return shipsInfo; // Retorna la lista con la información de los barcos
    }

    /**
     * Gets the list of boats on the machine's board.
     *
     * @return a {@link List} of {@link Boat} objects representing the boats on the machine's board.
     * @since 1.0
     */
    public List<Boat> getBoats() {
        return boats;
    }
}
