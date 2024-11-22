package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.WaterShot;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

import java.util.Random;

public class Game {
    private Button[][] matrix;
    private WaterShot waterShot;

    public Game() {
        matrix = new Button[10][10];
        waterShot = new WaterShot();
    }
    public void shootingMachine(){
        Random random = new Random();
        int row = random.nextInt(10); // Genera un índice entre 0 y 9 para la fila
        int col = random.nextInt(10); // Genera un índice entre 0 y 9 para la columna

        // Ahora puedes usar matrix[row][col]
        Button btn = matrix[row][col];
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Group water = waterShot.getWaterShot(); // Por cada evento se crea una nueva instancia del Group
        btn.setGraphic(water);
    }

    public void setMatrix(int i, int j, Button btn){
        matrix[i][j]=btn;
    }

}
