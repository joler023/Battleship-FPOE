package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.DraggableMaker;
import com.battleship.battleshipfpoe.model.MachineBoard;
import com.battleship.battleshipfpoe.model.PlayerBoard;
import com.battleship.battleshipfpoe.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameController {

    @FXML
    private Button buttonCarrier;
    private Group airCraftCarrier;

    @FXML
    private Button buttonSubmarine;

    @FXML
    private ImageView imageShow;

    @FXML
    private Pane water;
    @FXML
    private Pane bomb;
    @FXML
    private Pane fire;

    @FXML
    private Label labelShow; // Show text: hide/show

    @FXML
    private GridPane gridPaneMachine;
    @FXML
    private GridPane gridPanePlayer;

    private DraggableMaker draggableMaker;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private BombTouch bombTouch;
    private WaterShot waterShot;
    private ShipSunk shipSunk;
    private PlayerBoard playerBoard;
    private MachineBoard machineBoard;

    private boolean buttonShowPressed;

    public GameController() {
        playerBoard = new PlayerBoard();
        machineBoard = new MachineBoard();
        draggableMaker = new DraggableMaker();
        aircraftCarrier = new AircraftCarrier();
        shipSunk = new ShipSunk();
        frigate = new Frigate();
        bombTouch = new BombTouch();
        waterShot = new WaterShot();
        buttonShowPressed = false;
    }

    @FXML
    public void initialize(){
        createTableMachine();
        createTablePlayer();
        positionShips();
        positionShapes();
    }

    public void positionShips(){
        positionAirCraftCarrier();
        positionSubmarine();
    }

    public void positionShapes(){
        Group group = waterShot.getWaterShot();
        water.getChildren().add(group);
        group = bombTouch.getBombTouch();
        bomb.getChildren().add(group);
        group = shipSunk.getShipSunk();
        fire.getChildren().add(group);
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
                btn.getStyleClass().add("button-gridPane-hide");
                gridPaneMachine.add(btn, j, i); // Agrega los botones creados al GridPane Machine
                machineBoard.setButtonList(btn); // Matriz para establecer eventos a cada boton.
                machineBoard.setMatrixButtons(btn, i-1, j-1); // Matriz para agregar los botones creados
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
        btn.setOnMouseClicked(event -> {
            pressedCell(btn);
            btn.setText("1");
            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Muestra solo el contenido grafico
            //System.out.println(btn.getText());
            btn.setOnMouseClicked(null); // Desactiva el evento después de ejecutarse una vez
            btn.setOnMouseEntered(null);
        });
        btn.setOnMouseEntered(event -> {
            btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/styles-game.css")));
            btn.getStyleClass().add("button-Entered");
        });
        btn.setOnMouseExited(event -> {
            btn.getStyleClass().remove("button-Entered");
        });
    }

    public void pressedCell(Button btn){
        String value = btn.getText();
        if(value.equals("0")){
            Group water = waterShot.getWaterShot(); // Por cada evento se crea una nueva instancia del Group
            btn.setGraphic(water);
        }
        else if(value.equals("1")){
            Group bomb = bombTouch.getBombTouch(); // Por cada evento se crea una nueva instancia del Group
            btn.setGraphic(bomb);
        }
    }

    // Función que oculta o muestra las casillas del GridPane de la maquina
    @FXML
    public void showMachineBoard(ActionEvent event) {
        if(!buttonShowPressed){
            setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-hide.png", "OCULTAR");
            showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css","button-gridPane-hide","button-gridPane-show");
            buttonShowPressed = true;
        }
        else{
            setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-show.png", "MOSTRAR");
            showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css", "button-gridPane-show", "button-gridPane-hide");
            buttonShowPressed = false;
        }
    }

    public void setImageButtonShow(String url, String message){
        Image image = new Image(getClass().getResource(url).toExternalForm());
        imageShow.setImage(image);
        labelShow.setText(message);
    }

    public void showHideMachineGridPane(String url, String css1, String css2){
        Button[][] matrixButtons = machineBoard.getMatrixButtons();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                Button btn = matrixButtons[i][j];
                btn.getStylesheets().add(String.valueOf(getClass().getResource(url)));
                btn.getStyleClass().remove(css1);
                btn.getStyleClass().add(css2);
            }
        }
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
