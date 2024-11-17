package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.DraggableMaker;
import com.battleship.battleshipfpoe.view.AircraftCarrier;
import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class GameController {

    @FXML
    private Button buttonCarrier;
    private Group airCraftCarrier;

    @FXML
    private Button buttonSubmarine;

    private DraggableMaker draggableMaker;
    private AircraftCarrier aircraftCarrier;

    public GameController() {
        draggableMaker = new DraggableMaker();
        aircraftCarrier = new AircraftCarrier();
    }

    @FXML
    public void initialize(){
        positionShips();
    }

    public void positionShips(){
        positionAirCraftCarrier();
        positionSubmarine();
    }

    public void positionAirCraftCarrier(){
        airCraftCarrier = aircraftCarrier.getAircraftCarrier();
        buttonCarrier.setGraphic(airCraftCarrier);
        draggableMaker.makeDraggable(buttonCarrier);

        onFocusedButton(buttonCarrier);
    }

    public void positionSubmarine(){
        draggableMaker.makeDraggable(buttonSubmarine);

        onFocusedButton(buttonSubmarine);
    }

    public void onFocusedButton(Button btn){
        btn.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // EventHandler capture key pressed
                btn.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        // rotates 90 degrees to its center
                        btn.setRotate(btn.getRotate() + 90);
                    }
                });
            }
        });
    }


    public void handleClickExit(){
        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }
}
