package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

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
    private TextField textFieldName;

    @FXML
    private GridPane gridPaneMachine;
    @FXML
    private GridPane gridPanePlayer;

    private Player player;
    private DraggableMaker draggableMaker;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private BombTouch bombTouch;
    private WaterShot waterShot;
    private ShipSunk shipSunk;
    private PlayerBoard playerBoard;
    private MachineBoard machineBoard;

    private boolean buttonShowPressed;
    private List<Button> buttonList;
    private Button[][] matrixButtons;

    public GameController() {
        playerBoard = new PlayerBoard();
        //machineBoard = new MachineBoard();
        draggableMaker = new DraggableMaker();
        aircraftCarrier = new AircraftCarrier();
        shipSunk = new ShipSunk();
        frigate = new Frigate();
        bombTouch = new BombTouch();
        waterShot = new WaterShot();
        buttonShowPressed = false;
        buttonList = new ArrayList<>();
        matrixButtons = new Button[10][10];
    }

    @FXML
    public void initialize(){
        createTablePlayer();
        positionShips();
        positionShapes();
    }

    public void setPlayer(Player player, MachineBoard machineBoard) {
        this.player = player;
        this.machineBoard = machineBoard;
        startGame();
    }

    public void startGame(){
        textFieldName.setText(player.getNickname());
        createTableMachine();

//        do{
//
//        }while(playerPlay() && machinePlay());
    }

    public boolean playerPlay(){

        return true;
    }
    public boolean machinePlay(){

        return true;
    }

    public void positionShips(){
        positionAirCraftCarrier();
        positionSubmarine();
    }

    public void positionShapes(){
//        // --------------Prueba de serializacion
//        player.setNickname("BELICO");
//        PlaneTextFileHandler planeTextFileHandler = new PlaneTextFileHandler();
//        planeTextFileHandler.writeToFile("player_data.csv", player.getNickname() + ","+player.getShipsDestroyed());

//        SerializableFileHandler serializableFileHandler = new SerializableFileHandler();
//        serializableFileHandler.serialize("player-data.ser", player);

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
                buttonList.add(btn); // Matriz para establecer eventos a cada boton.
                matrixButtons[i-1][j-1] = btn; // Matriz para agregar los botones creados
                handleButtonValue(btn);
            }
        }
        // Serializar el objeto MachineBoard
        SerializableFileHandler serializableFileHandler = new SerializableFileHandler();
        serializableFileHandler.serialize("machineBoard_data.ser", machineBoard);
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
            }
        }
    }


    public void handleButtonValue(Button btn){
        btn.setOnMouseClicked(event -> {
            pressedCell(btn);
            btn.setText("1");
            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Muestra solo el contenido grafico
            //System.out.println(btn.getText());
            btn.setOnMouseClicked(null); // Desactiva el evento después de ejecutarse una vez
            btn.setOnMouseEntered(null);
            // SI EL TEXTO DEL BOTON PRESIONADO ES "0" ENTONCES
            // -> DISPARAR MAQUINA ALEATORIAMENTE
            // SINO VUELVE A TIRAR EL JUGADOR Y ADEMAS SE EJECUTA UN METODO DE VERIFICA SI UN BARCO HA SIDO
            // DESTRUIDO Y OTRO METODO QUE VERIFIQUE SI HA DERRIBADO TODOS LOS BARCOS "RECORRE EL ARREGLO
            // Y SI NO HAY BOTONES CON TEXTO 1 ENTONCES HA GANADO EL JUEGO"
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
        //Button[][] matrixButtons = machineBoard.getMatrixButtons();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                Button btn = matrixButtons[i][j];
                btn.getStylesheets().add(String.valueOf(getClass().getResource(url)));
                btn.getStyleClass().remove(css1);
                btn.getStyleClass().add(css2);
            }
        }
    }
    private StringBuilder inputText = new StringBuilder();
    @FXML
    public void onKeyTyped(KeyEvent event) {
        String character = event.getCharacter();

        // Concatenar el carácter al StringBuilder si es válido
        if (character.matches("[a-zA-Z0-9 ]")) { // Permitir letras, números y espacios
            inputText.setLength(0); // Limpiar el contenido previo
            inputText.append(textFieldName.getText());
        } else {
            event.consume(); // Ignorar caracteres no válidos
        }
    }

    @FXML
    void handleClickStart(ActionEvent event) {
        //activateMachineEvents();
        System.out.println(player.toString());
    }

    @FXML
    public void handleClickExit(){
        player.setNickname(textFieldName.getText());
        PlaneTextFileHandler planeTextFileHandler = new PlaneTextFileHandler();
        planeTextFileHandler.writeToFile("player_data.csv", player.getNickname() + ","+player.getShipsDestroyed());

        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }
}
