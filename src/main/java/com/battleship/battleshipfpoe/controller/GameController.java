package com.battleship.battleshipfpoe.controller;

import com.battleship.battleshipfpoe.model.*;
import com.battleship.battleshipfpoe.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameController {

    public Label playerShipsSunkenLabel;
    public Label machineShipsSunkenLabel;
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
    private int playerShipsSunken  = 0;
    private int machineShipsSunken = 0;

    private boolean buttonShowPressed;
    private List<Button> buttonList;
    private Button[][] matrixButtons;

    /**
     * Controller class for managing the Battleship game.
     * This class handles the interaction between the player's board, the machine's board,
     * and the game logic, enabling gameplay functionality such as ship placement,
     * attack management, and game status updates.
     *
     * @author Maycol Andres Taquez Carlosama
     * @code 2375000
     * @author Santiago Valencia Aguiño
     * @code 2343334
     * @author Joel Andres Ochoa Sará
     * @code 2341100
     * @version 1.0
     * @since 1.0
     */

    /**
     * Constructs a GameController instance and initializes the necessary game components.
     *
     * @since 1.0
     */
    public GameController() {

        // Initializing player board
        playerBoard = new PlayerBoard();
        //machineBoard = new MachineBoard();

        // Initializing draggable maker to enable dragging functionality
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

    /**
     * Initializes the game controller by setting up ship positions and visual elements.
     *
     * @since 1.0
     */
    @FXML
    public void initialize(){
        //createTablePlayer();

        // Position ships on the player's board
        positionShips();
        // Add visual effects for water, bomb, and fire
        positionShapes();
    }

    /**
     * Sets the player instance for the game and starts the game.
     *
     * @param player the Player instance to be set.
     * @since 1.0
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.machineBoard = new MachineBoard();
        startGame();
    }

//    public void positionShipsMachine(){
//
//    }

    /**
     * Places ships on the grid for the machine's board based on predefined positions.
     *
     * @param gridPane the GridPane where ships are to be placed.
     * @throws PlacementException if a ship cannot be placed due to invalid coordinates.
     * @since 1.0
     * @see MachineBoard
     */
    public void placeShipsOnGrid(GridPane gridPane) throws PlacementException {
        List<String[]> shipsInfo = machineBoard.getShipsInfo(); // Obtén la información de los barcos

        for (String[] ship : shipsInfo) {
            String shipName = ship[0];
            String start = ship[1]; // Ejemplo: "0,0"
            String end = ship[2];   // Ejemplo: "0,3"

            // Parse ship's starting coordinates
            String[] startCoords = start.replace("(", "").replace(")", "").trim().split(",");
            int startRow = Integer.parseInt(startCoords[0].trim());
            int startCol = Integer.parseInt(startCoords[1].trim());

            // Parse ship's ending coordinates
            String[] endCoords = end.replace("(", "").replace(")", "").trim().split(",");
            int endRow = Integer.parseInt(endCoords[0].trim());
            int endCol = Integer.parseInt(endCoords[1].trim());


            // Determine orientation and length
            boolean isHorizontal = startRow == endRow;
            int length = isHorizontal ? (endCol - startCol + 1) : (endRow - startRow + 1);

            // Place the ship on the GridPane
            placeShipOnGrid(gridPane, startRow, startCol, length, isHorizontal);
        }
    }
    /**
     * Places a single ship on the grid based on its starting position, length, and orientation.
     *
     * @param gridPane the GridPane where the ship will be placed.
     * @param startRow the starting row index of the ship.
     * @param startCol the starting column index of the ship.
     * @param length the length of the ship.
     * @param isHorizontal true if the ship is placed horizontally, false otherwise.
     * @throws PlacementException if the ship cannot fit in the specified location.
     * @since 1.0
     */
    private void placeShipOnGrid(GridPane gridPane, int startRow, int startCol, int length, boolean isHorizontal) throws PlacementException {
        // Validate starting indices are within bounds
        if (startRow < 0 || startCol < 0 || startRow >= gridPane.getRowCount() || startCol >= gridPane.getColumnCount()) {
            throw new IllegalArgumentException("Los índices iniciales están fuera de los límites del GridPane.");
        }

        // Check if the ship fits in the specified position
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


    /**
     * Gets the color corresponding to the length of a ship.
     *
     * @param length the length of the ship.
     * @return a Color object representing the color of the ship.
     * @since 1.0
     */

    private Color getColorByLength(int length) {
        switch (length) {
            case 1:
                return Color.GREEN; // Frigate (1 cell)
            case 2:
                return Color.YELLOW;  // Destroyer (2 cells)
            case 3:
                return Color.ORANGE;  // Submarine (3 cells)
            case 4:
                return Color.RED; // Aircraft carrier (4 cells)
            default:
                return Color.GRAY; // Default color for unknown ships
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

    /**
     * Starts the game by initializing the player's nickname and setting up the machine's board.
     * Also establishes listeners for updating the state of the game.
     *
     * @since 1.0
     * @see PlayerBoard
     * @see MachineBoard
     * @throws IllegalStateException if the game cannot start due to invalid player or machine board state.
     */
    public void startGame(){
        // Set the player's nickname in the text field
        textFieldName.setText(player.getNickname());
        // Create the machine's table/grid
        createTableMachine();
        // Print the player's matrix for debugging
        game.imprimirMatrizJugador();
        // Update the label showing the number of sunken machine ships
        machineShipsSunkenLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            machineShipsSunkenLabel.setText(String.valueOf(machineShipsSunken));
        });
    }

    /**
     * Sets the list of boats for the player's board and places them on the grid.
     *
     * @param boatsList the list of Boat objects to be placed on the player's board.
     * @since 1.0
     * @see Boat
     * @see PlayerBoard#placeShip(int, int, int, boolean)
     * @throws IllegalArgumentException if the boat positions are invalid.
     */
    public void setBoatsList(List<Boat> boatsList) {
        // Iterate over each boat in the list
        for (Boat boat : boatsList) {
            // Get position and orientation
            int[] position = boat.getPosition();
            int row = position[0];
            int col = position[1];
            boolean isHorizontal = boat.isHorizontal();

            // Place the boat on the player's board
            System.out.println("Colocando barco " +  boat + " en fila: " + row + ", columna: " + col);
            playerBoard.placeShip(row, col, boat.getLength(), isHorizontal);
        }
        // Store the boats list and print the player's board matrix
        boats = boatsList;

        playerBoard.printMatrix();
        // Create the player's grid/table
        createTablePlayer();
    }

    /**
     * Positions the player's aircraft carrier and submarine on the board.
     *
     * @since 1.0
     */

    public void positionShips(){
        positionAirCraftCarrier();
        positionSubmarine();
    }
    /**
     * Adds visual elements (water, bomb, and fire effects) to the player's board.
     *
     * @since 1.0
     * @see WaterShot
     * @see BombTouch
     * @see ShipSunk
     */

    public void positionShapes(){
        // Add water effect to the player's board
        Group group = waterShot.getWaterShot();
        water.getChildren().add(group);
        // Add bomb effect to the player's board
        group = bombTouch.getBombTouch();
        bomb.getChildren().add(group);
        // Add fire effect to the player's board
        group = shipSunk.getShipSunk();
        fire.getChildren().add(group);
    }

    /**
     * Positions the aircraft carrier on the board.
     *
     * @since 1.0
     */
    public void positionAirCraftCarrier(){

    }
    /**
     * Positions the submarine on the board.
     *
     * @since 1.0
     */
    public void positionSubmarine(){
    }

    /**
     * Configures a button to handle focus and rotation events when the space key is pressed.
     *
     * @param btn the Button to be configured with focus and rotation events.
     * @since 1.0
     * @throws IllegalArgumentException if the button is null or has invalid properties.
     */
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

    /**
     * Creates the machine's game grid by generating buttons with appropriate styles and functionalities.
     * The machine's board state is serialized for persistence.
     *
     * @since 1.0
     * @see MachineBoard
     * @serial Serializes the MachineBoard object to save its state.
     */

    public void createTableMachine(){
        for(int i=1; i<11; i++){
            for(int j=1; j<11; j++){
                // Create a new button for the grid
                Button btn = new Button();
                Integer value = machineBoard.getMatrix().get(i-1).get(j-1);
                String text = String.valueOf(value);
                btn.setText(text);

                // Set dimensions and styles for the button
                btn.setPrefHeight(40);
                btn.setPrefWidth(40);
                btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/index.css")));
                btn.getStyleClass().add("button-gridPane-hide");
                gridPaneMachine.add(btn, j, i); // Add the button to the machine's grid
                buttonList.add(btn); // Store the button in the list and matrix for event handling
                matrixButtons[i-1][j-1] = btn; // Add event handling for button actions
                handleButtonValue(btn);
            }
        }
        // Serialize the MachineBoard object
        SerializableFileHandler serializableFileHandler = new SerializableFileHandler();
        serializableFileHandler.serialize("machineBoard_data.ser", machineBoard);
    }

    /**
     * Creates the player's game grid by initializing buttons and rendering ships visually on the board.
     *
     * @since 1.0
     * @see Boat
     */

    public void createTablePlayer() {
        // Create a Pane for the boats that will render behind the grid
        Pane boatsPane = new Pane();
        gridPanePlayer.getChildren().add(boatsPane); // Add the Pane to the player's GridPane

        // Iterate through the player's board matrix
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

                // Add the button to the GridPane
                gridPanePlayer.add(btn, j, i);

                // Iterate through the list of boats to render on the grid
                for (Boat boat : boats) {
                    int[] position = boat.getPosition();
                    int row = position[0];
                    int col = position[1];
                    boolean isHorizontal = boat.isHorizontal();
                    int length = boat.getLength();

                    // Iterate over the positions occupied by the boat
                    for (int k = 0; k < length; k++) {
                        int currentRow = isHorizontal ? row : row + k;
                        int currentCol = isHorizontal ? col + k : col;

                        // Check if we're on the corresponding cell
                        if (currentRow == i - 1 && currentCol == j - 1) {
                            // Create a visual copy of the boat's style
                            Group boatPartStyle = new Group(boat.getChildren());// Copy the style
                            boatPartStyle.setScaleX(0.7);
                            boatPartStyle.setScaleY(0.7);

                            // Set the visual style on the Ship Pane (not the button)
                            boatPartStyle.setTranslateX(j * 40); // Posicionar basado en la celda
                            boatPartStyle.setTranslateY(i * 40);

                            if(!boat.isHorizontal()) {
                                boatPartStyle.setLayoutY(boatPartStyle.getLayoutY() + 50);
                                boatPartStyle.setLayoutX(boatPartStyle.getLayoutX() - 50);
                            }// Position based on cell

                            // Add the boat to the Pane
                            boatsPane.getChildren().add(boatPartStyle);
                        }
                    }
                }
            }
        }
    }


    /**
     * Handles the functionality of a button when clicked or hovered over.
     *
     * @param btn the Button to be configured with click and hover events.
     * @since 1.0
     * @see BombTouch#getBombTouch()
     * @see WaterShot#getWaterShot()
     */
    public void handleButtonValue(Button btn) {
        btn.setOnMouseClicked(event -> {
            int row = GridPane.getRowIndex(btn) - 1;
            int col = GridPane.getColumnIndex(btn) - 1;

            // Check for a hit or sunk ship
            checkShipHitAndSunk(btn, row, col);

            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            btn.setOnMouseClicked(null); // Disable the event after execution
            btn.setOnMouseEntered(null);

            // Player's or machine's turn based on the result
            if (Objects.equals(btn.getText(), "0")) {
                game.shootingMachine(boats, playerBoard);
                machineShipsSunken = game.getDestroyedBoatsCount(boats, playerBoard);
                machineShipsSunkenLabel.setText(String.valueOf(machineShipsSunken));
            }

            // IF THE TEXT OF THE BUTTON PRESSED IS "0" THEN
            // -> SHOOT MACHINE RANDOMLY
            // ELSE THE PLAYER THROWS AGAIN AND ALSO A METHOD IS EXECUTED TO VERIFY IF A BOAT HAS BEEN
            // DESTROYED AND ANOTHER METHOD THAT CHECKS IF YOU HAVE SHOT DOWN ALL THE SHIPS "RIDE THE ARRANGEMENT
            // AND IF THERE ARE NO BUTTONS WITH TEXT 1 THEN YOU HAVE WON THE GAME"
        });
        btn.setOnMouseEntered(event -> {
            btn.getStylesheets().add(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/css/styles-game.css")));
            btn.getStyleClass().add("button-Entered");
        });
        btn.setOnMouseExited(event -> {
            btn.getStyleClass().remove("button-Entered");
        });
    }

    /**
     * Checks if a shot hits a ship and handles the ship sinking logic.
     * If the shot hits a ship, the ship is marked as hit, and if the ship is sunk,
     * the respective cells are updated visually to show the damage.
     * The method also checks if all of the player's ships have been sunk,
     * triggering the game over sequence if all ships are destroyed.
     *
     * @param btn the Button representing the shot taken by the player.
     * @param row the row index of the shot on the grid.
     * @param col the column index of the shot on the grid.
     * @since 1.0
     * @see Boat
     * @see MachineBoard
     * @see BombTouch#getBombTouch()
     * @see WaterShot#getWaterShot()
     * @throws IllegalArgumentException if the shot coordinates are outside of the valid grid bounds.
     * @deprecated This method is deprecated due to a planned update in the game's event handling system.
     */
    public void checkShipHitAndSunk(Button btn, int row, int col) {
        for (Boat boat : machineBoard.getBoats()) {
            System.out.println("Boat: "+ boat.getName() + " "+ Arrays.toString(boat.getPosition()));
            // Get boat's position and orientation
            int[] position = boat.getPosition();
            int boatRow = position[0];
            int boatCol = position[1];
            boolean isHorizontal = boat.isHorizontal();
            int length = boat.getLength();

            // Check if the shot hits the boat
            for (int i = 0; i < length; i++) {
                int currentRow = isHorizontal ? boatRow : boatRow + i;
                int currentCol = isHorizontal ? boatCol + i : boatCol;

                if (currentRow == row && currentCol == col) {
                    // Register the impact on the boat
                    boat.markHit(i);

                    // Change the button graphic to show the hit
                    btn.setGraphic(bombTouch.getBombTouch());

                    // Check if the boat is completely destroyed (sunk)
                    if (boat.isSunk()) {
                        for (int j = 0; j < length; j++) {
                            int sunkRow = isHorizontal ? boatRow : boatRow + j;
                            int sunkCol = isHorizontal ? boatCol + j : boatCol;

                            Button sunkBtn = matrixButtons[sunkRow][sunkCol];
                            sunkBtn.setGraphic(shipSunk.getShipSunk());

                        }
                        playerShipsSunken++;
                        playerShipsSunkenLabel.setText(String.valueOf(playerShipsSunken));

                        if (playerShipsSunken == 10) {
                            System.out.println("Se llamo");
                            showGameOverMessage("¡Has ganado!");
                            blockButtons();

                            GameStage.deleteInstance();
                            WelcomeStage.getInstance();
                        }

                    }
                    return; // No need to check more boats
                }
            }
        }

        // If no ship is hit, show water effect
        btn.setGraphic(waterShot.getWaterShot());
        // Check if the machine has won
        if (machineShipsSunken == 10) {
            showGameOverMessage("¡Has perdido!");
            blockButtons(); // Disable all buttons
            GameStage.deleteInstance();
            WelcomeStage.getInstance();
        }
    }

    /**
     * Displays a "Game Over" message dialog with the provided text.
     * This method creates and displays a custom dialog that shows the result of the game (win/lose).
     * The dialog includes a styled message and a "Close" button to end the game session.
     *
     * @param message the message to display in the "Game Over" dialog, typically indicating win/lose status.
     * @since 1.0
     * @see Dialog
     * @see VBox
     * @see Button
     * @throws NullPointerException if the message parameter is null.
     * @serial Updates the game’s final state, including displaying the game over message in the dialog.
     * @deprecated This method is deprecated as the game’s user interface may transition to a different form of messaging.
     */
    private void showGameOverMessage(String message) {
        // Set the style of the dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Fin del Juego");

        // Create a container for the message
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/com/battleship/battleshipfpoe/css/styles-game-over.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("game-over-dialog");

        // Create the Label with the message
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setStyle("-fx-alignment: center;");

        // Add the container to the dialog
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ff0000; -fx-alignment: center;");


        vbox.getChildren().add(messageLabel);

        // Add the container to the dialog
        dialog.getDialogPane().setContent(vbox);

        // Create and style the "Close" button
        Button closeButton = new Button("Cerrar");
        closeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        closeButton.setOnAction(event -> dialog.close());
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog
        dialog.showAndWait();
    }

    /**
     * Disables all buttons on the game board to prevent further actions after the game is over or during certain game states.
     * This method iterates through all buttons stored in the `buttonList` and disables them to block user interaction.
     *
     * @since 1.0
     * @see Button
     * @see GameStage
     * @see WelcomeStage
     * @throws IllegalStateException if the buttons cannot be disabled due to an invalid state.
     * @deprecated This method is deprecated as the game now uses a more dynamic approach to manage button states.
     * @serial Updates the button state in the game to reflect whether interaction is allowed.
     */
    private void blockButtons() {
        for (Button btn : buttonList) {
            btn.setDisable(true);  // Disable the button
        }
    }



    /**
     * Handles the action when a cell (button) is pressed, showing water or bomb effects based on the button value.
     *
     * @param btn the Button pressed by the user.
     * @since 1.0
     * @see WaterShot
     * @see BombTouch
     */
    public void pressedCell(Button btn){
        String value = btn.getText();
        if(value.equals("0")){
            Group water = waterShot.getWaterShot(); // Creates a new instance of the water effect for each event
            btn.setGraphic(water);
        }
        else if(value.equals("1")){
            Group bomb = bombTouch.getBombTouch(); // Creates a new instance of the bomb effect for each event
            btn.setGraphic(bomb);
        }
    }

    /**
     * Toggles the visibility of the machine's game board based on user interaction.
     * Changes the button's image and grid visibility between show and hide.
     *
     * @param event the ActionEvent triggered by the user.
     * @since 1.0
     * @see Button
     */
    @FXML
    public void showMachineBoard(ActionEvent event) {
        if (!buttonShowPressed) {
            try {
                setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-hide.png", "OCULTAR");
                showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css", "button-gridPane-hide", "button-gridPane-show");
                buttonShowPressed = true;
            } catch (RuntimeException e) {
                // UNCHECKED EXCEPTION: Handle errors when changing visibility.
                System.err.println("Error cambiando la visibilidad del tablero: " + e.getMessage());
            }
        } else {
            setImageButtonShow("/com/battleship/battleshipfpoe/images/icon-show.png", "MOSTRAR");
            showHideMachineGridPane("/com/battleship/battleshipfpoe/css/index.css", "button-gridPane-show", "button-gridPane-hide");
            buttonShowPressed = false;
        }
    }

    /**
     * Sets the image on the show/hide button and updates its label text.
     *
     * @param url the URL to the image to be set.
     * @param message the message to be shown on the label.
     * @since 1.0
     * @throws IllegalArgumentException if the image resource is not found.
     */
    public void setImageButtonShow(String url, String message) {
        try {
            // CHECKED EXCEPTION: Handling of graphic resources (such as images).
            Image image = new Image(getClass().getResource(url).toExternalForm());
            imageShow.setImage(image);
            labelShow.setText(message);
        } catch (Exception e) {
            // Logs the error but does not interrupt the game.
            System.err.println("Error cambiando la imagen del botón: " + e.getMessage());
        }
    }

    /**
     * Changes the visibility of the machine's grid and updates button styles accordingly.
     *
     * @param url the URL to the CSS stylesheet for the grid.
     * @param css1 the first CSS class to apply.
     * @param css2 the second CSS class to apply.
     * @since 1.0
     * @see Button
     */
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

    /**
     * Handles the key typed event to allow only valid characters in the text field.
     *
     * @param event the KeyEvent triggered when a key is typed.
     * @since 1.0
     * @see TextField
     */

    private StringBuilder inputText = new StringBuilder();
    @FXML
    public void onKeyTyped(KeyEvent event) {
        String character = event.getCharacter();

        // Concatenate the character to the StringBuilder if valid
        if (character.matches("[a-zA-Z0-9 ]")) { // Allow letters, numbers and spaces
            inputText.setLength(0); // Clear previous content
            inputText.append(textFieldName.getText());
        } else {
            event.consume(); // Ignore invalid characters
        }
    }

    /**
     * Handles the click event to save the player's nickname and ships destroyed count to a file.
     * Then transitions to the WelcomeStage.
     *
     * @since 1.0
     * @see PlaneTextFileHandler
     * @see WelcomeStage
     */
    @FXML
    public void handleClickExit(){
        player.setNickname(textFieldName.getText());
        PlaneTextFileHandler planeTextFileHandler = new PlaneTextFileHandler();
        planeTextFileHandler.writeToFile("player_data.csv", player.getNickname() + ","+player.getShipsDestroyed());

        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }
}
