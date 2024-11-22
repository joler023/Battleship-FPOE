package com.battleship.battleshipfpoe.model;

import java.io.Serializable;

/**
 * Represents a player in the Battleship game.
 * This class holds the player's nickname and the number of ships destroyed during gameplay.
 * It provides methods to get and set the player's nickname and to track the number of ships destroyed.
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
public class Player{
    private String nickname;
    private int shipsDestroyed;

    /**
     * Constructs a new {@link Player} instance with default values.
     * The default nickname is "ANONIMO" and the number of ships destroyed is set to 0.
     *
     * @since 1.0
     */
    public Player() {
        nickname = "ANONIMO";
        shipsDestroyed = 0;
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname the nickname to assign to the player.
     * @since 1.0
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retrieves the player's nickname.
     *
     * @return the player's nickname.
     * @since 1.0
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the number of ships destroyed by the player.
     *
     * @return the number of ships destroyed.
     * @since 1.0
     */
    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    /**
     * Returns a string representation of the player, including their nickname and the number of ships destroyed.
     *
     * @return a string describing the player.
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", shipsDestroyed=" + shipsDestroyed +
                '}';
    }
}
