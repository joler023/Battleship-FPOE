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

/**
 * Represents the welcome stage (main menu) of the Battleship game.
 * This class initializes the stage with a predefined FXML view and handles
 * its singleton instance to ensure a single welcome stage exists.
 *
 * @author Maycol Andres Taquez Carlosama
 * @code 2375000
 * @author Santiago Valencia Aguiño
 * @code 2343334
 * @author Joel Andres Ochoa Sará
 * @code 2341100
 * @version 1.0
 * @since 1.0
 */
public class WelcomeStage extends Stage {
    private WelcomeController welcomeController;
    private Parent root;

    /**
     * Constructs the WelcomeStage by loading its FXML view and configuring its properties.
     * The stage is set to cover the full screen and is initialized with its controller and resources.
     *
     * @since 1.0
     * @throws IOException if the FXML file fails to load.
     * @see FXMLLoader
     */
    public WelcomeStage() {
        super();
        // Get the visible limits of the screen
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

    /**
     * Retrieves the {@link WelcomeController} associated with this stage.
     *
     * @return the controller for the welcome view.
     * @since 1.0
     * @see WelcomeController
     */
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
