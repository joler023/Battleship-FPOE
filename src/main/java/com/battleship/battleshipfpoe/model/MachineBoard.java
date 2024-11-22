package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.AircraftCarrier;
import com.battleship.battleshipfpoe.view.Destroyer;
import com.battleship.battleshipfpoe.view.Frigate;
import com.battleship.battleshipfpoe.view.Submarine;
import javafx.scene.Group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MachineBoard implements Serializable {
    private List<List<Integer>> matrixMachine;
    private Random rand;
    private List<String[]> shipsInfo; // Lista para almacenar información de los barcos
    private List<Boat> boats; // Lista para almacenar objetos Boat

    public MachineBoard() {
        matrixMachine = new ArrayList<>();
        rand = new Random();
        shipsInfo = new ArrayList<>();
        boats = new ArrayList<>();
        generateBoardMachine();
        placeShips();
        printShipsInfo();
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

    // Verifica si una casilla está vacía
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

    // Se posiciona un barco, según el tamaño del argumento y su nombre
    private void placeShip(int size, String shipName, Group boatStyle) {
        boolean placed = false;
        while (!placed) {
            // Seleccionar una posición aleatoria (x, y) y una dirección (horizontal o vertical)
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            boolean isHorizontal = rand.nextBoolean();

            // Verificar si el barco se puede colocar en la posición seleccionada
            if (isValidPosition(x, y, size, isHorizontal)) {
                Boat boat = new Boat(shipName, x, y, size, isHorizontal, boatStyle);

                boat.setPosition(x,y);

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

                // Registrar información del barco (posición inicial y final)
                String[] shipInfo = new String[3];
                shipInfo[0] = shipName; // Nombre del barco
                shipInfo[1] = "(" + x + "," + y + ")"; // Posición inicial
                shipInfo[2] = "(" + (x + size - 1) + "," + y + ")"; // Posición final
                shipsInfo.add(shipInfo); // Agregar información a la lista

                // Agregar el objeto Boat a la lista de barcos
                boats.add(boat);
                placed = true; // Marcar que el barco ha sido colocado
            }
        }
    }

    private Group createBoatStyle(String shipName) {
        // Aquí puedes crear el estilo visual del barco según el nombre.
        // Por ejemplo, dependiendo del tipo de barco (Portaviones, Submarino, etc.), puedes
        // devolver un estilo diferente. Aquí se asume que es una implementación básica.
        return new Group(); // Placeholder, debes definir cómo crear el estilo
    }

    // Se coloca los barcos en la matriz
    public void placeShips() {
        placeShip(4, "Portaviones", new AircraftCarrier().getAircraftCarrier()); // Coloca el primer portaviones (4 casillas)
        placeShip( 3, "Submarino", new Submarine().getSubmarine()); // Coloca el primer submarino (3 casillas)
        placeShip(3, "Submarino", new Submarine().getSubmarine()); // Coloca el segundo submarino (3 casillas)
        placeShip(2, "Destructor", new Destroyer().getDestroyer()); // Coloca el primer destructor (2 casillas)
        placeShip(2, "Destructor", new Destroyer().getDestroyer()); // Coloca el segundo destructor (2 casillas)
        placeShip(2, "Destructor", new Destroyer().getDestroyer()); // Coloca el tercer destructor (2 casillas)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Coloca el primer fragata (1 casilla)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Coloca el segundo fragata (1 casilla)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Coloca el tercer fragata (1 casilla)
        placeShip(1, "Fragata", new Frigate().getFrigate()); // Coloca el cuarto fragata (1 casilla)
    }

    // Metodo para imprimir la información de los barcos colocados
    public void printShipsInfo() {
        // SERIALIZAR POSICIONES DE LOS MATRIZ CON LAS POCISIONES DE LOS BARCOS
        for (String[] ship : shipsInfo) {
            System.out.println("Barco: " + ship[0] + ", Inicio: " + ship[1] + ", Fin: " + ship[2]); // Imprimir información del barco
        }
    }

    public List<List<Integer>> getMatrix() {
        return matrixMachine;
    }

    public List<Boat> getBoats() {
        return boats;
    }
}
