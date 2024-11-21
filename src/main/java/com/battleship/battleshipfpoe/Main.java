package com.battleship.battleshipfpoe;

import com.battleship.battleshipfpoe.view.GameStage;
import com.battleship.battleshipfpoe.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //GameStage.getInstance();
        WelcomeStage.getInstance();
    }
}