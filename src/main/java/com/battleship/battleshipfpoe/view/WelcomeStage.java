package com.battleship.battleshipfpoe.view;

import com.battleship.battleshipfpoe.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WelcomeStage extends Stage {
    private WelcomeController welcomeController;
    private Parent root;

    public WelcomeStage() {
        super();
        // Obtener los límites visibles de la pantalla
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/battleship/battleshipfpoe/welcome-view.fxml"));
        try{
            root = loader.load();
            welcomeController = loader.getController();
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

        setResizable(false);
        show();
    }
    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    public static WelcomeStage getInstance() {
        WelcomeStageHolder.INSTANCE = (WelcomeStageHolder.INSTANCE != null ? WelcomeStageHolder.INSTANCE : new WelcomeStage());
        return WelcomeStageHolder.INSTANCE;
    }

    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }
}
