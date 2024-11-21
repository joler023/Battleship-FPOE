package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.PreparationStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController {

    @FXML
    public void handleClickPlay(ActionEvent event) throws IOException {
        WelcomeStage.deleteInstance();
        //GameStage.getInstance();
        PreparationStage.getInstance();
    }

    @FXML
    public void handleClickExit(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }
}
