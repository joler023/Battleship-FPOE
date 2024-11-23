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
public class Player implements IPlayer{
    private String nickname;
    private String playerSunken;
    private String machineSunken;

    /**
     * Constructs a new {@link Player} instance with default values.
     * The default nickname is "ANONIMO" and the number of ships destroyed is set to 0.
     *
     * @since 1.0
     */
    public Player() {
        nickname = "ANONIMO";
        playerSunken = "0";
        machineSunken = "0";
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
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the player's nickname.
     *
     * @return the player's nickname.
     * @since 1.0
     */
    public String getPlayerSunken() {
        return playerSunken;
    }

    /**
     * Sets the player who has been sunk in the game.
     * This method is used to track the player whose ship or unit has been defeated.
     *
     * @param playerSunken the name or identifier of the player who has been sunk.
     * @since 1.0
     * @see #setMachineSunken(String)
     * @see #getMachineSunken()
     */
    public void setSunkenPlayer(String playerSunken) {
        this.playerSunken = playerSunken;
    }

    /**
     * Retrieves the identifier or description of the machine's sunken element.
     *
     * @return a {@code String} representing the machine's sunken element.
     * @since 1.0
     * @see #setMachineSunken(String)
     * @see #setSunkenPlayer(String)
     */
    public String getMachineSunken() {
        return machineSunken;
    }

    /**
     * Sets the identifier or description of the machine's sunken element.
     * This method is used to update the state of the machine's sunken status.
     *
     * @param machineSunken a {@code String} representing the identifier or description of the sunken machine element.
     * @since 1.0
     * @see #getMachineSunken()
     * @see #setSunkenPlayer(String)
     */
    public void setMachineSunken(String machineSunken) {
        this.machineSunken = machineSunken;
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
                ", playerSunken='" + playerSunken + '\'' +
                ", machineSunken='" + machineSunken + '\'' +
                '}';
    }
}
