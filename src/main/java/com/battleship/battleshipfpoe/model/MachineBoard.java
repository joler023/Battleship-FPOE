package com.battleship.battleshipfpoe.model;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MachineBoard {
    private List<List<Integer>> matrixMachine;
    private List<Button> buttonList;
    private Button[][] matrixButtons;
    private Random rand;

    public MachineBoard() {
        matrixMachine = new ArrayList<List<Integer>>();
        matrixButtons = new Button[10][10];
        buttonList = new ArrayList<>();
        rand = new Random();
        generateBoardMachine();
        placeShips();
    }

    public void generateBoardMachine() {
        for (int i = 0; i < 10; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(0);
            }
            matrixMachine.add(row);
        }
    }

    // Verifica si una casilla esta vacia
    private boolean isValidPosition(int x, int y, int size, boolean isHorizontal) {
        // Verificar si la posición está dentro de los límites de la matriz
        if (isHorizontal) {
            if (y + size > 10) return false; // Excede el borde derecho
            for (int j = y; j < y + size; j++) {
                if (matrixMachine.get(x).get(j) == 1) return false; // Casilla ocupada
            }
        } else {
            if (x + size > 10) return false; // Excede el borde inferior
            for (int i = x; i < x + size; i++) {
                if (matrixMachine.get(i).get(y) == 1) return false; // Casilla ocupada
            }
        }
        return true;
    }

    // Se posiciona un barco, según el tamaño del argumento
    private void placeShip(int size) {
        boolean placed = false;
        while (!placed) {
            // Seleccionar una posición aleatoria (x, y) y una dirección (horizontal o vertical)
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            boolean isHorizontal = rand.nextBoolean();

            // Verificar si el barco se puede colocar en la posición seleccionada
            if (isValidPosition(x, y, size, isHorizontal)) {
                // Colocar el barco (marcar las casillas con 1)
                if (isHorizontal) {
                    for (int j = y; j < y + size; j++) {
                        matrixMachine.get(x).set(j, 1);
                    }
                } else {
                    for (int i = x; i < x + size; i++) {
                        matrixMachine.get(i).set(y, 1);
                    }
                }
                placed = true;
            }
        }
    }

    // Se coloca los barcos en la matriz
    public void placeShips() {
        // Portaviones
        placeShip(4); // Coloca el primer portaviones (4 casillas)

        // Submarino
        placeShip(3);
        placeShip(3);

        // Destructor
        placeShip(2); // Coloca el segundo submarino (3 casillas)
        placeShip(2); // Coloca el segundo submarino (3 casillas)
        placeShip(2); // Coloca el segundo submarino (3 casillas)

        //Fragata
        placeShip(1); // Coloca el segundo submarino (3 casillas)
        placeShip(1); // Coloca el segundo submarino (3 casillas)
        placeShip(1); // Coloca el segundo submarino (3 casillas)
        placeShip(1); // Coloca el segundo submarino (3 casillas)
    }

    public List<List<Integer>> getMatrixMachine() {
        return matrixMachine;
    }

    public void setButtonList(Button btn) {
        buttonList.add(btn);
    }
    public List<Button> getButtonList() {
        return buttonList;
    }

    public Button[][] getMatrixButtons() {
        return matrixButtons;
    }

    public void setMatrixButtons(Button btn, int i, int j) {
        matrixButtons[i][j] = btn;
    }
}
