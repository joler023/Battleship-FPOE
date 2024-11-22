package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    private List<Boat> boats;
    private Player player;
    private DraggableMaker draggableMaker;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private BombTouch bombTouch;
    private WaterShot waterShot;
    private ShipSunk shipSunk;
    private PlayerBoard playerBoard;
    private MachineBoard machineBoard;
    private Game game;

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
        game = new Game();
    }

    @FXML
    public void initialize(){
        //createTablePlayer();
        positionShips();
        positionShapes();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.machineBoard = new MachineBoard();
        startGame();
    }

//    public void positionShipsMachine(){
//
//    }


    public void placeShipsOnGrid(GridPane gridPane) throws PlacementException {
        List<String[]> shipsInfo = machineBoard.getShipsInfo(); // Obtén la información de los barcos

        for (String[] ship : shipsInfo) {
            String shipName = ship[0];
            String start = ship[1]; // Ejemplo: "0,0"
            String end = ship[2];   // Ejemplo: "0,3"

            // Parsear coordenadas
            String[] startCoords = start.replace("(", "").replace(")", "").trim().split(",");
            int startRow = Integer.parseInt(startCoords[0].trim());
            int startCol = Integer.parseInt(startCoords[1].trim());

            String[] endCoords = end.replace("(", "").replace(")", "").trim().split(",");
            int endRow = Integer.parseInt(endCoords[0].trim());
            int endCol = Integer.parseInt(endCoords[1].trim());


            // Determinar orientación y longitud
            boolean isHorizontal = startRow == endRow;
            int length = isHorizontal ? (endCol - startCol + 1) : (endRow - startRow + 1);

            // Colocar el barco en el GridPane
            placeShipOnGrid(gridPane, startRow, startCol, length, isHorizontal);
        }
    }

    private void placeShipOnGrid(GridPane gridPane, int startRow, int startCol, int length, boolean isHorizontal) throws PlacementException {
        // Validar índices
        if (startRow < 0 || startCol < 0 || startRow >= gridPane.getRowCount() || startCol >= gridPane.getColumnCount()) {
            throw new IllegalArgumentException("Los índices iniciales están fuera de los límites del GridPane.");
        }

        // Validar si cabe el barco
        if ((isHorizontal && startCol + length > gridPane.getColumnCount()) ||
                (!isHorizontal && startRow + length > gridPane.getRowCount())) {
            throw new PlacementException("El barco no cabe en la posición especificada.");
        }

        // Crear el barco y configurarlo
        Rectangle ship = new Rectangle();
        ship.setFill(getColorByLength(length));
        ship.setStroke(Color.BLACK);

        if (isHorizontal) {
            ship.setWidth(length * 40);
            ship.setHeight(40);
        } else {
            ship.setWidth(40);
            ship.setHeight(length * 40);
        }

        gridPane.add(ship, startCol + 1, startRow + 1);
        GridPane.setRowSpan(ship, isHorizontal ? 1 : length);
        GridPane.setColumnSpan(ship, isHorizontal ? length : 1);
    }



    // Metodo auxiliar para obtener un color según la longitud del barco
    private Color getColorByLength(int length) {
        switch (length) {
            case 1:
                return Color.GREEN; // Fragata (1 casilla)
            case 2:
                return Color.YELLOW; // Destructor (2 casillas)
            case 3:
                return Color.ORANGE; // Submarino (3 casillas)
            case 4:
                return Color.RED; // Portaaviones (4 casillas)
            default:
                return Color.GRAY; // Color por defecto para barcos desconocidos
        }
    }


    private void removeAllShipsFromGrid(GridPane gridPane) {
        // Crear una lista temporal para almacenar los nodos a eliminar
        List<Node> nodesToRemove = new ArrayList<>();

        // Recorrer todos los nodos del GridPane
        for (Node node : gridPane.getChildren()) {
            // Verificar si el nodo representa un barco
            if (node instanceof Rectangle || (node.getId() != null && isShipId(node.getId()))) {
                nodesToRemove.add(node); // Agregar a la lista de nodos a eliminar
            }
        }

        // Eliminar todos los nodos marcados
        gridPane.getChildren().removeAll(nodesToRemove);
    }

    // Metodo auxiliar para identificar nodos de barcos por ID (opcional)
    private boolean isShipId(String id) {
        // Define una lista de IDs válidos para los barcos
        List<String> validShipIds = List.of("Portaviones", "Submarino", "Destructor", "Fragata");
        return validShipIds.contains(id.toLowerCase());
    }


    public void startGame(){
        textFieldName.setText(player.getNickname());
        createTableMachine();
        game.imprimirMatrizJugador();
    }

    public void setBoatsList(List<Boat> boatsList) {
        // Iterate over each boat in the list
        for (Boat boat : boatsList) {
            // Obtener posición y orientación
            int[] position = boat.getPosition();
            int row = position[0];
            int col = position[1];
            boolean isHorizontal = boat.isHorizontal();

            // Colocar el barco en el tablero
            System.out.println("Colocando barco " +  boat + " en fila: " + row + ", columna: " + col);
            playerBoard.placeShip(row, col, boat.getLength(), isHorizontal);
        }
        boats = boatsList;

        playerBoard.printMatrix();
        createTablePlayer();
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
                Integer value = machineBoard.getMatrix().get(i-1).get(j-1);
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

    public void createTablePlayer() {
        // Creamos un Pane para los barcos que se renderizará detrás del grid
        Pane boatsPane = new Pane();
        gridPanePlayer.getChildren().add(boatsPane); // Añadimos el Pane al GridPane del jugador

        // Recorremos la matriz del tablero de jugadores
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button btn = new Button();
                String text = "";
                btn.setText(text);
                btn.setPrefHeight(40);
                btn.setPrefWidth(40);
                btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/index.css")));
                btn.getStyleClass().add("button-gridPane-show");

                game.setMatrix(i - 1, j - 1, btn);

                // Añadir el botón al GridPane
                gridPanePlayer.add(btn, j, i);

                // Recorremos la lista de barcos para pintar en el grid
                for (Boat boat : boats) {
                    int[] position = boat.getPosition();
                    int row = position[0];
                    int col = position[1];
                    boolean isHorizontal = boat.isHorizontal();
                    int length = boat.getLength();

                    // Iterar sobre las posiciones que ocupa el barco
                    for (int k = 0; k < length; k++) {
                        int currentRow = isHorizontal ? row : row + k;
                        int currentCol = isHorizontal ? col + k : col;

                        // Verificar si estamos en la celda correspondiente
                        if (currentRow == i - 1 && currentCol == j - 1) {
                            // Crear una copia visual del estilo del barco
                            Group boatPartStyle = new Group(boat.getChildren()); // Copiar el estilo
                            boatPartStyle.setScaleX(0.7); // Ajustar escala si es necesario
                            boatPartStyle.setScaleY(0.7);

                            // Establecer el estilo visual en el Pane de barcos (no en el botón)
                            boatPartStyle.setTranslateX(j * 40); // Posicionar basado en la celda
                            boatPartStyle.setTranslateY(i * 40);

                            if(!boat.isHorizontal()) {
                                boatPartStyle.setLayoutY(boatPartStyle.getLayoutY() + 50);
                                boatPartStyle.setLayoutX(boatPartStyle.getLayoutX() - 50);
                            }// Posicionar basado en la celda

                            // Añadir el barco al Pane
                            boatsPane.getChildren().add(boatPartStyle);
                        }
                    }
                }
            }
        }
    }



    public void handleButtonValue(Button btn) {
        btn.setOnMouseClicked(event -> {
            int row = GridPane.getRowIndex(btn) - 1;
            int col = GridPane.getColumnIndex(btn) - 1;

            // Verificar impacto y estado de los barcos
            checkShipHitAndSunk(btn, row, col);

            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            btn.setOnMouseClicked(null); // Desactiva el evento después de ejecutarse
            btn.setOnMouseEntered(null);

            // Turno del jugador o de la máquina según el resultado
            if (Objects.equals(btn.getText(), "0")) {
                game.shootingMachine(boats, playerBoard);
            }

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


    public void checkShipHitAndSunk(Button btn, int row, int col) {
        for (Boat boat : machineBoard.getBoats()) {
            System.out.println("Boat: "+ boat.getName() + " "+ Arrays.toString(boat.getPosition()));
            // Obtener posición y orientación del barco
            int[] position = boat.getPosition();
            int boatRow = position[0];
            int boatCol = position[1];
            boolean isHorizontal = boat.isHorizontal();
            int length = boat.getLength();

            // Verificar si el disparo impacta en el barco
            for (int i = 0; i < length; i++) {
                int currentRow = isHorizontal ? boatRow : boatRow + i;
                int currentCol = isHorizontal ? boatCol + i : boatCol;

                if (currentRow == row && currentCol == col) {
                    // Registrar impacto en el barco
                    boat.markHit(i);

                    // Cambiar el gráfico del botón al impacto
                    btn.setGraphic(bombTouch.getBombTouch());

                    // Verificar si el barco está completamente destruido
                    if (boat.isSunk()) {
                        for (int j = 0; j < length; j++) {
                            int sunkRow = isHorizontal ? boatRow : boatRow + j;
                            int sunkCol = isHorizontal ? boatCol + j : boatCol;

                            Button sunkBtn = matrixButtons[sunkRow][sunkCol];
                            sunkBtn.setGraphic(shipSunk.getShipSunk());
                        }
                    }
                    return; // No es necesario verificar más barcos
                }
            }
        }

        // Si no impactó en ningún barco, mostrar agua
        btn.setGraphic(waterShot.getWaterShot());
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
    // Manejo del cambio de visibilidad del tablero de la máquina.
    @FXML
    public void showMachineBoard(ActionEvent event) {
        if (!buttonShowPressed) {
            try {
                setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-hide.png", "OCULTAR");
                showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css", "button-gridPane-hide", "button-gridPane-show");
                buttonShowPressed = true;
            } catch (RuntimeException e) {
                // EXCEPCIÓN NO MARCADA: Maneja errores al cambiar visibilidad.
                System.err.println("Error cambiando la visibilidad del tablero: " + e.getMessage());
            }
        } else {
            setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-show.png", "MOSTRAR");
            showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css", "button-gridPane-show", "button-gridPane-hide");
            buttonShowPressed = false;
        }
    }

    public void setImageButtonShow(String url, String message) {
        try {
            // EXCEPCIÓN MARCADA: Manejo de recursos gráficos (como imágenes).
            Image image = new Image(getClass().getResource(url).toExternalForm());
            imageShow.setImage(image);
            labelShow.setText(message);
        } catch (Exception e) {
            // Registra el error pero no interrumpe el juego.
            System.err.println("Error cambiando la imagen del botón: " + e.getMessage());
        }
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
    public void handleClickExit(){
        player.setNickname(textFieldName.getText());
        PlaneTextFileHandler planeTextFileHandler = new PlaneTextFileHandler();
        planeTextFileHandler.writeToFile("player_data.csv", player.getNickname() + ","+player.getShipsDestroyed());

        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }
}
