package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;

public class GameController {

    public void handleClickExit(){
        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }
}
