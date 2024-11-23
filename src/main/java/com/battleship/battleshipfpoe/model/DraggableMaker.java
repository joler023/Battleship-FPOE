package com.battleship.battleshipfpoe.model;

import javafx.scene.Node;

/**
 * Class responsible for making a {@link Node} draggable within a JavaFX scene.
 * It allows the user to drag any given node by handling mouse events for the node.
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
public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     * Makes a {@link Node} draggable by handling mouse press and drag events.
     * This method allows the node to be moved by dragging it with the mouse.
     * It calculates the new position of the node based on the mouse's location.
     *
     * @param node the {@link Node} to be made draggable. This node will be moved when dragged by the mouse.
     * @since 1.0
     * @see Node
     */
    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX() - node.getTranslateX();
            mouseAnchorY = mouseEvent.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setTranslateY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
