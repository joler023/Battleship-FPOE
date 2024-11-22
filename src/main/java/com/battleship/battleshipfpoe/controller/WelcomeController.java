package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.PreparationStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for managing the Welcome screen of the Battleship game.
 * This class handles user interactions such as starting a new game, continuing an existing game, and exiting the application.
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
public class WelcomeController {
    private SerializableFileHandler serializableFileHandler;
    private PlaneTextFileHandler planeTextFileHandler;
    private GameController gameController;

    /**
     * Default constructor for the WelcomeController class.
     * Initializes the necessary file handlers for serialization and file operations.
     *
     * @since 1.0
     */
    public WelcomeController() {

    }

    /**
     * Handles the action when the player clicks the "Play" button to start a new game.
     * This method deletes the current instance of the WelcomeStage and loads the PreparationStage to prepare the game.
     *
     * @param event the ActionEvent triggered when the "Play" button is clicked.
     * @since 1.0
     * @see PreparationStage
     * @see WelcomeStage
     */
    @FXML
    public void handleClickPlay(ActionEvent event) {
        WelcomeStage.deleteInstance();
        //GameStage.getInstance();  // Uncomment if GameStage needs to be initialized here
        PreparationStage.getInstance();
    }

    /**
     * Handles the action when the player clicks the "Continue" button to resume an existing game.
     * This method reads the player data from a CSV file, sets the player's nickname and destroyed ships count,
     * and loads the GameStage.
     *
     * @param event the ActionEvent triggered when the "Continue" button is clicked.
     * @since 1.0
     * @see Player
     * @see PlaneTextFileHandler
     * @see GameStage
     * @throws IllegalArgumentException if the player data is not correctly formatted or missing.
     */
    @FXML
    public void handleClickContinue(ActionEvent event) {
//        Player player = (Player) serializableFileHandler.deserialize("player_data.ser");
        // Read player data from a CSV file
        String[] data = planeTextFileHandler.readFromFile("player_data.csv");
        String nickname = data[0];
        int warshipDestroyed = Integer.parseInt(data[1]);
        Player player = new Player();
        player.setNickname(nickname);
        System.out.println("Nombre al darle continue: "+player.getNickname());

        //MachineBoard machineBoard = (MachineBoard) serializableFileHandler.deserialize("machineBoard_data.ser");
        // Load the GameStage and pass the player information to it
        WelcomeStage.deleteInstance();
        GameStage.getInstance().getGameController().setPlayer(player);
    }

    /**
     * Handles the action when the player clicks the "Exit" button to close the application.
     * This method deletes the instance of the WelcomeStage and exits the application.
     *
     * @param event the ActionEvent triggered when the "Exit" button is clicked.
     * @since 1.0
     * @see WelcomeStage
     */
    @FXML
    public void handleClickExit(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }

}
