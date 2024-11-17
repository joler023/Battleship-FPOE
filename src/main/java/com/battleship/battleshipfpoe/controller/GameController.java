package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.DraggableMaker;
import com.battleship.battleshipfpoe.model.MachineBoard;
import com.battleship.battleshipfpoe.model.PlayerBoard;
import com.battleship.battleshipfpoe.view.AircraftCarrier;
import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController {

    @FXML
    private Button buttonCarrier;
    private Group airCraftCarrier;

    @FXML
    private Button buttonSubmarine;

    @FXML
    private GridPane gridPaneMachine;
    @FXML
    private GridPane gridPanePlayer;

    private DraggableMaker draggableMaker;
    private AircraftCarrier aircraftCarrier;
    private PlayerBoard playerBoard;
    private MachineBoard machineBoard;

    public GameController() {
        playerBoard = new PlayerBoard();
        machineBoard = new MachineBoard();
        draggableMaker = new DraggableMaker();
        aircraftCarrier = new AircraftCarrier();
    }

    @FXML
    public void initialize(){
        createTableMachine();
        createTablePlayer();
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

    public void createTableMachine(){
        for(int i=1; i<11; i++){
            for(int j=1; j<11; j++){
                Button btn = new Button();
                Integer value = machineBoard.getMatrixMachine().get(i-1).get(j-1);
                String text = String.valueOf(value);
                btn.setText(text);
                btn.setPrefHeight(40);
                btn.setPrefWidth(40);
                btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/index.css")));
                btn.getStyleClass().add("button-gridPane-show");
                gridPaneMachine.add(btn, j, i);
                machineBoard.setButtonList(btn);
            }
        }
    }

    public void createTablePlayer(){
        for(int i=1; i<11; i++){
            for(int j=1; j<11; j++){
                Button btn = new Button();
                Integer value = playerBoard.getMatrixPlayer().get(i-1).get(j-1);
                String text = String.valueOf(value);
                btn.setText(text);
                btn.setPrefHeight(40);
                btn.setPrefWidth(40);
                btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/index.css")));
                btn.getStyleClass().add("button-gridPane-show");
                gridPanePlayer.add(btn, j, i);
                handleButtonValue(btn,text);
            }
        }
    }

    public void activateMachineEvents(){
        for(int i=0; i<100; i++){
            Button btn = machineBoard.getButtonList().get(i);
            handleButtonValue(btn,btn.getText());
        }
    }

    public void handleButtonValue(Button btn, String text){
        btn.setOnMouseEntered(event -> {
            btn.setText("1");
            btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/styles-game.css")));
            btn.getStyleClass().add("button-Entered");
        });
        btn.setOnMouseExited(event -> {
            btn.getStyleClass().remove("button-Entered");
        });
    }

    @FXML
    void handleClickStart(ActionEvent event) {
        activateMachineEvents();
    }

    @FXML
    public void handleClickExit(){
        GameStage.deleteInstance();
        //WelcomeStage.getInstance();
    }
}
