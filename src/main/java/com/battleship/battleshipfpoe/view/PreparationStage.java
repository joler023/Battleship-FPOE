package com.battleship.battleshipfpoe.view;

import com.battleship.battleshipfpoe.controller.PreparationController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PreparationStage extends Stage {
    private PreparationController controller;
    private Parent root;
    private PreparationStage() {
        super();

        // Configurar límites visibles de la pantalla
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/battleship/battleshipfpoe/preparation-view.fxml"));

        try{
            root = loader.load();
            controller = new PreparationController();
        } catch (IOException e){
            e.printStackTrace();
        }

        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        setScene(scene);

        setX(visualBounds.getMinX());
        setY(visualBounds.getMinY());
        setWidth(visualBounds.getWidth());
        setHeight(visualBounds.getHeight());

        setTitle("Preparación de barcos");
        setResizable(false);
        show();
    }

    public PreparationController getController() {
        return controller;
    }

    private static class PreparationStageHolder {
        private static PreparationStage INSTANCE;
    }

    public static PreparationStage getInstance() {
        if (PreparationStageHolder.INSTANCE == null) {
            PreparationStageHolder.INSTANCE = new PreparationStage();
        }
        return PreparationStageHolder.INSTANCE;
    }

    public static void deleteInstance() {
        if (PreparationStageHolder.INSTANCE != null) {
            PreparationStageHolder.INSTANCE.close();
            PreparationStageHolder.INSTANCE = null;
        }
    }
}
