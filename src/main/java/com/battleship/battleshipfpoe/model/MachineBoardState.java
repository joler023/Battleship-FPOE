package com.battleship.battleshipfpoe.model;

import java.util.Map;
/**
 * Represents the state of the machine's board during gameplay.
 * This class tracks missed shots, ships that have been touched, and ships that have been destroyed.
 * It provides a structured way to manage and monitor the current state of the machine's board.
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
public class MachineBoardState {
    private Map<String, Boolean> missedShots;
    private Map<String, Boolean> shipsTouched;
    private Map<String, Boolean> shipsDestroyed;

    /**
     * Constructs a new {@link MachineBoardState} instance.
     * Initializes the state without pre-defined values.
     *
     * @since 1.0
     */
    public MachineBoardState() {

    }


}
