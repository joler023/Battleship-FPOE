package com.battleship.battleshipfpoe.model;

import com.battleship.battleshipfpoe.view.Submarine;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class Boat extends Group implements BoatInterface {

    private final String name;
    private static final int SQUARE_SIZE = 60;
    private int length;
    private boolean isHorizontal;
    private double startX, startY;
    private int currentRow = -1;
    private int currentCol = -1;
    private BoardHandler boardHandler;
    private boolean rotated = false;
    private boolean wasFirstMove = true;
    private final Group boatStyle; // Grupo que define el estilo visual del barco

    public Boat(String name, double startX, double startY, int length, boolean isHorizontal, Group boatStyle) {
        this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.boatStyle = boatStyle;

        // Inicializa el barco con el estilo y posición inicial
        placeBoat(startX, startY, length, isHorizontal);

        setupInteractions();
    }

    @Override
    public void placeBoat(double startX, double startY, int length, boolean isHorizontal) {
        // Ajustar rotación y escala del estilo según orientación
        boatStyle.setScaleX(1.5);
        boatStyle.setScaleY(2);
        if (!isHorizontal) {
            boatStyle.setRotate(90);
        }

        // Añadir el estilo del barco al Group
        getChildren().clear(); // Limpia cualquier contenido previo
        getChildren().add(boatStyle);

        // Posicionar el barco en el tablero
        setLayoutX(startX);
        setLayoutY(startY);
        toFront(); // Asegura que el barco está al frente
    }

    public void storePosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
        System.out.println("Snapped at row: " + currentRow + ", col: " + currentCol);
    }

    public void clearBoatPosition(BoardHandler boardHandler, boolean previousOrientation) {
        if (currentRow == -1 || currentCol == -1) {
            return;
        }

        // Limpiar según la orientación previa
        if (!previousOrientation) { // Horizontal
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow, currentCol + i, 0);
            }
        } else { // Vertical
            for (int i = 0; i < length; i++) {
                boardHandler.setCell(currentRow + i, currentCol, 0);
            }
        }
    }

    public void updateBoatPosition(BoardHandler boardHandler) {
        for (int i = 0; i < length; i++) {

            if (isHorizontal) {
                boardHandler.setCell(currentRow, currentCol + i, 1);
            } else {
                System.out.println("Row inicio: "+ currentRow + i + " Row fin: "  + (currentRow + i + length));
                boardHandler.setCell(currentRow + i, currentCol, 1);
            }
        }
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public int[] getPosition() {
        return new int[]{currentRow, currentCol};
    }

    public Group getBoatStyle(){
        return boatStyle;
    }

    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    private void setupInteractions() {
        this.setOnMousePressed(event -> {
            this.setUserData(new double[]{
                    event.getSceneX() - this.getLayoutX(),
                    event.getSceneY() - this.getLayoutY()
            });
            this.requestFocus();
        });

        this.setOnMouseDragged(event -> {
            double[] offsets = (double[]) this.getUserData();
            double newX = event.getSceneX() - offsets[0];
            double newY = event.getSceneY() - offsets[1];

            this.setLayoutX(newX);
            this.setLayoutY(newY);
        });
    }

    public void rotate() {
        rotated = !rotated;
        isHorizontal = !isHorizontal;

        boatStyle.setRotate(rotated ? -90 : 0);

        placeBoat(getLayoutX(), getLayoutY(), length, isHorizontal);

        System.out.println("--------");
        boardHandler.printBoard();
        System.out.println("Boat rotated.");
        System.out.println("Placing boat at X: " + getLayoutX() + ", Y: " + getLayoutY() + ", Horizontal: " + isHorizontal);
        boardHandler.printBoard();
        System.out.println("--------");
    }

    public boolean isWasFirstMove() {
        return wasFirstMove;
    }

    public void setWasFirstMove(boolean wasFirstMove) {
        this.wasFirstMove = wasFirstMove;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean wasRotated) {
        rotated = wasRotated;
    }
}
