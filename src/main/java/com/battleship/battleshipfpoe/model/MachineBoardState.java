package com.battleship.battleshipfpoe.model;

import java.util.Map;

public class MachineBoardState {
    private Map<String, Boolean> missedShots;
    private Map<String, Boolean> shipsTouched;
    private Map<String, Boolean> shipsDestroyed;


    public MachineBoardState() {

    }


}
