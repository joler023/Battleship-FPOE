package com.battleship.battleshipfpoe.model;

/**
 * Represents a submarine in the Battleship game.
 * This class stores the initial and final positions of the submarine on the game board.
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
public class Submarino {
    private int[][] posicionInicial;
    private int[][] posicionFinal;

    /**
     * Constructs a new {@link Submarino} instance with specified initial and final positions.
     *
     * @param posicionInicial a 2D array representing the initial coordinates of the submarine.
     * @param posicionFinal a 2D array representing the final coordinates of the submarine.
     * @since 1.0
     */
    public Submarino(int[][] posicionInicial, int[][] posicionFinal) {
        this.posicionInicial = posicionInicial;
        this.posicionFinal = posicionFinal;
    }
}
