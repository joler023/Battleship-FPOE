package com.battleship.battleshipfpoe.view;

import com.battleship.battleshipfpoe.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameStage extends Stage {
    private GameController gameController;
    private Parent root;
    public GameStage(){
        super();
        // Obtener los límites visibles de la pantalla
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/battleship/battleshipfpoe/game-view.fxml"));
        try{
            root = loader.load();
            gameController = loader.getController();
        } catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        setX(visualBounds.getMinX());
        setY(visualBounds.getMinY());
        setWidth(visualBounds.getWidth());
        setHeight(visualBounds.getHeight());

        setScene(scene);

        setTitle("Batalla Naval");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/battleship/battleshipfpoe/images/favicon.png"))));

        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        show();
    }

    public GameController getGameController() {
        return gameController;
    }

    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    public static GameStage getInstance() {
        GameStageHolder.INSTANCE = (GameStageHolder.INSTANCE != null ? GameStageHolder.INSTANCE : new GameStage());
        return GameStageHolder.INSTANCE;
    }

    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
