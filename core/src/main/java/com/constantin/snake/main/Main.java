package com.constantin.snake.main;

import com.constantin.snake.game.Field;
import com.constantin.snake.gui.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Main method. Just starting window.
     *
     * @param args empty
     * @author Constantin Schulte
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Field field = new Field(40, 24);
        GUI gui = new GUI(primaryStage, field);
    }
}
