package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.PreparationStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {
    private SerializableFileHandler serializableFileHandler;
    private PlaneTextFileHandler planeTextFileHandler;
    private GameController gameController;

    public WelcomeController() {

    }

    @FXML
    public void handleClickPlay(ActionEvent event) {
        WelcomeStage.deleteInstance();
        PreparationStage.getInstance();
    }

    @FXML
    public void initialize() {
        planeTextFileHandler = new PlaneTextFileHandler();
    }

    @FXML
    public void handleClickContinue(ActionEvent event) {
//        Player player = (Player) serializableFileHandler.deserialize("player_data.ser");

        String[] data = planeTextFileHandler.readFromFile("player_data.csv");
        String nickname = data[0];
        String sunkenPlayer = data[1];
        String sunkenMachine = data[2];
        System.out.println("SunkenPlayer: " + sunkenPlayer + " SunkenMachine: " + sunkenMachine);

        Player player = new Player();
        player.setNickname(nickname);
        player.setSunkenPlayer(sunkenPlayer);
        player.setMachineSunken(sunkenMachine);

        System.out.println("Nombre al darle continue: "+player.getNickname());

        //MachineBoard machineBoard = (MachineBoard) serializableFileHandler.deserialize("machineBoard_data.ser");

        WelcomeStage.deleteInstance();
        GameStage.getInstance().getGameController().setPlayer(player);
    }

    @FXML
    public void handleClickExit(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }

}
