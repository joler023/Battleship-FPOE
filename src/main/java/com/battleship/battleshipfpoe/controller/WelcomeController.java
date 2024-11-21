package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {
    private SerializableFileHandler serializableFileHandler;
    private PlaneTextFileHandler planeTextFileHandler;
    private GameController gameController;

    public WelcomeController() {
        serializableFileHandler = new SerializableFileHandler();
        planeTextFileHandler = new PlaneTextFileHandler();
        gameController = new GameController();
    }

    @FXML
    public void handleClickPlay(ActionEvent event) {
        Player player = new Player();
        MachineBoard machineBoard = new MachineBoard();

        WelcomeStage.deleteInstance();
        GameStage.getInstance().getGameController().setPlayer(player, machineBoard);
    }

    @FXML
    public void handleClickContinue(ActionEvent event) {
//        Player player = (Player) serializableFileHandler.deserialize("player_data.ser");

        String[] data = planeTextFileHandler.readFromFile("player_data.csv");
        String nickname = data[0];
        int warshipDestroyed = Integer.parseInt(data[1]);
        Player player = new Player();
        player.setNickname(nickname);
        System.out.println("Nombre al darle continue: "+player.getNickname());

        MachineBoard machineBoard = (MachineBoard) serializableFileHandler.deserialize("machineBoard_data.ser");

        WelcomeStage.deleteInstance();
        GameStage.getInstance().getGameController().setPlayer(player, machineBoard);
    }

    @FXML
    public void handleClickExit(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }

}
