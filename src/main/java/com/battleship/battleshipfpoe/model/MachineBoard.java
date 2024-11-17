package com.battleship.battleshipfpoe.model;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class MachineBoard {
    private List<List<Integer>> matrixMachine;
    private List<Button> buttonList;

    public MachineBoard() {
        matrixMachine = new ArrayList<List<Integer>>();
        buttonList = new ArrayList<>();
        generateBoardMachine();
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

    public List<List<Integer>> getMatrixMachine() {
        return matrixMachine;
    }

    public void setButtonList(Button btn) {
        buttonList.add(btn);
    }
    public List<Button> getButtonList() {
        return buttonList;
    }
}
