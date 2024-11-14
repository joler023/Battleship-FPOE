package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {

    @FXML
    public void handleClickPlay(ActionEvent event) {
        WelcomeStage.deleteInstance();
        GameStage.getInstance();
    }

    @FXML
    public void handleClickExit(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }
}
