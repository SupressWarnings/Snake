package com.constantin.snake.gui;

import com.constantin.snake.game.Field;
import com.constantin.snake.util.Location;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI {

    private static int TILE_WIDTH = 30;
    private static int TILE_DISTANCE = 10;

    private Stage primary;
    private Scene mainScene;
    private GridPane mainPane;
    private Canvas fieldCanvas, background;
    private GraphicsContext fieldContext, backgroundContext;
    private AnimationTimer gameThread;

    private char input = 'n';

    private Field field;

    public GUI(Stage primary, Field field){
        this.primary = primary;
        this.field = field;
        fieldCanvas = new Canvas(1800, 1000);
        background = new Canvas(1800, 1000);
        mainPane = new GridPane();
        fieldContext = fieldCanvas.getGraphicsContext2D();
        backgroundContext = background.getGraphicsContext2D();
        mainScene = new Scene(mainPane, 2000, 1200);

        gameThread = new AnimationTimer() {
            long lastInteraction = 0;
            @Override
            public void handle(long now) {
                if(now > lastInteraction + 150000000 /*0.15 seconds*/){
                    if(field.logic(input)){
                        this.stop();
                    }
                    input = 'n';
                    drawField();
                    lastInteraction = now;
                }
            }
        };
        init();
    }

    private void init(){
        mainPane.add(fieldCanvas, 0, 0);
        mainPane.add(background, 0, 0);
        backgroundContext.setFill(Color.GRAY);
        for(int i = 1; i <= field.getMaxX(); ++i){
            for(int j = 1; j <= field.getMaxY(); ++j){
                backgroundContext.fillRoundRect(10 + (TILE_WIDTH + TILE_DISTANCE) * i,
                        10 + (TILE_WIDTH + TILE_DISTANCE) * j, TILE_WIDTH, TILE_WIDTH, 5, 5);
            }
        }
        mainScene.setOnKeyPressed(e-> input = e.getCode().toString().charAt(0));
        primary.setTitle("Snake");
        primary.centerOnScreen();
        primary.setScene(mainScene);
        primary.show();
        primary.setFullScreen(true);
        fieldCanvas.toFront();
        drawField();
        gameThread.start();
    }

    private void drawField(){
        fieldContext.clearRect(0, 0, fieldCanvas.getWidth(), fieldCanvas.getHeight());
        fieldContext.setFill(Color.GREEN);
        for(Location location : field.getSnake().getSnake()){
            fieldContext.fillRoundRect(10 + (TILE_WIDTH + TILE_DISTANCE) * location.getX(),
                    10 + (TILE_WIDTH + TILE_DISTANCE) * location.getY(), TILE_WIDTH, TILE_WIDTH, 5, 5);
        }
        fieldContext.setFill(Color.RED);
        fieldContext.fillRoundRect(10 + (TILE_WIDTH + TILE_DISTANCE) * field.getApple().getX(),
                10 + (TILE_WIDTH + TILE_DISTANCE) * field.getApple().getY(), TILE_WIDTH, TILE_WIDTH, 5, 5);
    }
}
