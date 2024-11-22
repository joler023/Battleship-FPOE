package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.BombTouch;
import com.battleship.battleshipfpoe.view.ShipSunk;
import com.battleship.battleshipfpoe.view.WaterShot;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;


import java.util.List;
import java.util.Random;

public class Game {
    private final Button[][] matrix;
    private WaterShot waterShot;
    private BombTouch bombTouch;
    private ShipSunk shipSunk;
    private Boat boat;
    private int[][] matrixPlayer;

    public Game() {
        matrix = new Button[10][10];
        matrixPlayer = new int[10][10];
        waterShot = new WaterShot();
        bombTouch = new BombTouch();
        shipSunk = new ShipSunk();
        fillMatrixPlayer();
    }

    public void fillMatrixPlayer(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                matrixPlayer[i][j] = 0;
            }
        }
    }

    public void shootingMachine(List<Boat> boats, PlayerBoard playerBoard) {
        System.out.println("Turno de la máquina");
        Random random = new Random();

        while (true) {
            int row = random.nextInt(10); // Índice aleatorio para fila
            int col = random.nextInt(10); // Índice aleatorio para columna

            int playerBoardShotValue = playerBoard.getMatrixPlayer().get(row).get(col);

            // Salta si la celda ya fue atacada
            if (playerBoardShotValue == 2 || playerBoardShotValue == 3 || playerBoardShotValue == -1) {
                continue;
            }

            // Realiza el disparo
            boolean hit = handleShot(row, col, playerBoard);

            // Actualiza la interfaz gráfica
            updateCellGraphic(row, col, hit);

            // Si se golpeó un barco, verifica si alguno está completamente destruido
            if (hit) {
                checkAndMarkDestroyedBoats(boats, playerBoard);
            }

            // Si no golpea un barco, termina el turno
            if (!hit) {
                playerBoard.getMatrixPlayer().get(row).set(col, -1);
                break;
            }
        }
    }

    private boolean handleShot(int row, int col, PlayerBoard playerBoard) {
        List<List<Integer>> matrix = playerBoard.getMatrixPlayer();
        boolean isABoatThere = (matrix.get(row).get(col) == 1);
        matrix.get(row).set(col, 2); // Marca la celda como disparada
        return isABoatThere;
    }

    private void updateCellGraphic(int row, int col, boolean hit) {
        Button btn = matrix[row][col];
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Group graphic = hit ? bombTouch.getBombTouch() : waterShot.getWaterShot();
        btn.setGraphic(graphic);
    }

    private void checkAndMarkDestroyedBoats(List<Boat> boats, PlayerBoard playerBoard) {
        List<List<Integer>> matrix = playerBoard.getMatrixPlayer();

        for (Boat boat : boats) {
            int[] position = boat.getPosition();
            if (position[0] == -1 || position[1] == -1) continue; // Ignorar barcos no colocados

            boolean isDestroyed = isBoatDestroyed(boat, matrix);

            if (isDestroyed) {
                markDestroyedBoat(boat, matrix);
                System.out.println("¡Barco destruido: " + boat.getName() + "!");
            }
        }
    }

    private boolean isBoatDestroyed(Boat boat, List<List<Integer>> matrix) {
        int row = boat.getPosition()[0];
        int col = boat.getPosition()[1];

        for (int i = 0; i < boat.getLength(); i++) {
            int cellValue = boat.isHorizontal()
                    ? matrix.get(row).get(col + i)
                    : matrix.get(row + i).get(col);

            if (cellValue != 2) return false;
        }

        return true;
    }

    private void markDestroyedBoat(Boat boat, List<List<Integer>> matrixPlayer) {
        int row = boat.getPosition()[0];
        int col = boat.getPosition()[1];

        for (int i = 0; i < boat.getLength(); i++) {
            if (boat.isHorizontal()) {
                if (matrixPlayer.get(row).get(col + i) == 2) { // Verificar si la celda ya fue disparada
                    matrixPlayer.get(row).set(col + i, 3); // Cambiar a estado de barco hundido
                    matrix[row][col + i].setGraphic(shipSunk.getShipSunk());
                }
            } else {
                if (matrixPlayer.get(row + i).get(col) == 2) {
                    matrixPlayer.get(row + i).set(col, 3);
                    matrix[row + i][col].setGraphic(shipSunk.getShipSunk());
                }
            }
        }
    }



    public void setMatrix(int i, int j, Button btn){
        matrix[i][j]=btn;
    }

    public void setMatrixPlayer(int i, int j, int num){
        this.matrixPlayer[i][j]=num;
    }

    public void imprimirMatrizJugador(){
        System.out.println("MATRIZ DE JUGADOR EN EL GAME");
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(matrixPlayer[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
