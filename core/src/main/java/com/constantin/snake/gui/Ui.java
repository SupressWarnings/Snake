package com.constantin.snake.gui;

import java.util.ArrayList;

import com.constantin.snake.main.Game;
import com.constantin.snake.util.Constants;
import com.constantin.snake.util.Location;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Ui {

	private Stage primaryStage;
	private static Scene playing;
	
	private AnimationTimer mainThread;
	private Game game;
	
	private int time = 90;

	private Canvas bg, snakeCanvas, fruitCanvas;
	private GraphicsContext gcs, gcf;
	private GridPane window, buttonPane;
	private Pane field;
	private Label scoreLabel;

	private static ArrayList<String> input = new ArrayList<>();
	
	public Ui(Stage primaryStage, Game game){
		this.primaryStage = primaryStage;
		this.game = game;
		init();
	}
	
	private void init(){
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Snake");
		
		Label loading = new Label("Loading ...");
		loading.setAlignment(Pos.CENTER);
		Scene loadingScene = new Scene(loading, 1000, 500);
		
		primaryStage.setScene(loadingScene);
		primaryStage.show();
		
		mainThread = new AnimationTimer(){

            @Override
            public void handle(long now) {
                game.input(handleInput());
                draw(game.getSnake());
                
                game.moveSnake();
                if(game.appleEaten()){
                	drawApple(game.getApple());
                	updateScoreLabel();
                }
                boolean lost = game.checkCollisions();
                if(lost){
                	this.stop();
                }
                try {
                	Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
		};
	}
	
	public void chooseLevel(){
		Stage chooseLevel = new Stage();
		chooseLevel.setTitle("Levelauswahl");
		
		GridPane allLevels = new GridPane();
		allLevels.setPadding(new Insets(64));
		allLevels.setHgap(32);
		allLevels.setVgap(32);
		
		ToggleGroup level = new ToggleGroup();
		
		RadioButton empty = new RadioButton("Empty");
		empty.setPrefSize(128, 32);
		allLevels.add(empty, 2, 0);
		empty.setOnAction(e->{
		});
		
		RadioButton box = new RadioButton("Box");
		box.setPrefSize(128, 32);
		allLevels.add(box, 2, 1);
		box.setOnAction(e->{
		});
		
		RadioButton fields = new RadioButton("Fields");
		fields.setPrefSize(128, 32);
		allLevels.add(fields, 2, 2);
		fields.setOnAction(e->{
		});
		
		ToggleGroup difficulty = new ToggleGroup();
		
		RadioButton easy = new RadioButton("easy");
		RadioButton hardRadiobutton = new RadioButton("hard");
		
		allLevels.add(easy, 0, 0);
		allLevels.add(hardRadiobutton, 0, 1);
		
		easy.setOnAction(e->{
			//boolean hard = false;
		});
		hardRadiobutton.setOnAction(e->{
			//boolean hard = true;
		});
		
		easy.setToggleGroup(difficulty);
		hardRadiobutton.setToggleGroup(difficulty);

		empty.setToggleGroup(level);
		box.setToggleGroup(level);
		fields.setToggleGroup(level);
		
		Button start = new Button("Start");
		start.setOnAction(e->{
			//Trash.startPlaying(this.level, false);
			chooseLevel.close();
		});
		
		allLevels.add(start, 3, 0);
		
		empty.setToggleGroup(level);
		box.setToggleGroup(level);
		fields.setToggleGroup(level);
		
		Scene levels = new Scene(allLevels, 500, 250);
		
		chooseLevel.setScene(levels);
		chooseLevel.setAlwaysOnTop(true);
		chooseLevel.show();
	}
	
	/**
	 * Rendering method. Sets all buttons as buttons and then checks the snake locations
	 * and marks them.
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	public void draw(Location[] loctns){
		gcs.clearRect(0, 0,
				Constants.FIELD_WIDTH*Constants.BOX_LOCATION_FACTOR,
				Constants.FIELD_HEIGHT*Constants.BOX_LOCATION_FACTOR);
		gcs.setFill(Paint.valueOf("GREEN"));
		gcs.rect(0, 0, 20, 20);
		for(int i = 0; i < loctns.length; ++i){
			gcs.fillRect(loctns[i].getX()*Constants.BOX_LOCATION_FACTOR,
					loctns[i].getY()*Constants.BOX_LOCATION_FACTOR,
					Constants.BOX_LENGTH, Constants.BOX_LENGTH);
		}
		snakeCanvas.toFront();
	}
	
	public void drawApple(Location newLocation){
		gcf.clearRect(0, 0,
				Constants.FIELD_WIDTH*Constants.BOX_LOCATION_FACTOR,
				Constants.FIELD_HEIGHT*Constants.BOX_LOCATION_FACTOR);
		gcf.fillRect(newLocation.getX()*Constants.BOX_LOCATION_FACTOR,
					newLocation.getY()*Constants.BOX_LOCATION_FACTOR,
					Constants.BOX_LENGTH, Constants.BOX_LENGTH);
	}
	
	public void updateScoreLabel(){
		scoreLabel.setText("Score: " + game.getScore());
	}
	
	public void prepareGame(){
		window = new GridPane();
		window.setHgap(32);
		window.setVgap(32);
		window.setAlignment(Pos.CENTER);

		
		field = new Pane();
		bg = new Canvas(1000, 750);
		bg.toBack();
		GraphicsContext gc = bg.getGraphicsContext2D();
		gc.setFill(Paint.valueOf("GRAY"));
		for(int x = 0; x < Constants.FIELD_WIDTH; ++x){
			for(int y = 0; y < Constants.FIELD_HEIGHT; ++y){
				gc.fillRect(x*Constants.BOX_LOCATION_FACTOR, y*Constants.BOX_LOCATION_FACTOR,
						Constants.BOX_LENGTH, Constants.BOX_LENGTH);
				
			}
		}
		
		
		snakeCanvas = new Canvas(1000, 750);
		snakeCanvas.toFront();

		gcs = snakeCanvas.getGraphicsContext2D();
		gcs.setFill(Paint.valueOf("GREEN"));
		
		fruitCanvas = new Canvas(1000, 750);
		gcf = fruitCanvas.getGraphicsContext2D();
		gcf.setFill(Paint.valueOf("RED"));
		
		field.getChildren().addAll(bg, snakeCanvas, fruitCanvas);

		buttonPane = new GridPane();
		buttonPane.setHgap(64);
		
		scoreLabel = new Label("Score: " + game.getScore());
		scoreLabel.setPrefWidth(128);
		
		buttonPane.add(scoreLabel, 0, 0);
		
		window.add(field, 0, 0);
		window.add(buttonPane, 0, 1);
		
		playing = new Scene(window, 1050, 1000);
		playing.setOnKeyPressed(e->{
			String code = e.getCode().toString();
			if(!input.contains(code)){
				input.add(code);
			}
		});
		
		primaryStage.setScene(playing);
		primaryStage.show();
		primaryStage.setFullScreen(true);
	}
	
	/**
	 * Handling pressed keys and changes the direction if possible.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	public char handleInput(){
		if(input.contains("DOWN")){
			input.remove("DOWN");
			return 'd';
		}else if(input.contains("LEFT")){
			input.remove("LEFT");
			return 'l';
		}else if(input.contains("RIGHT")) {
			input.remove("RIGHT");
			return 'r';
		}else if(input.contains("UP")){
			input.remove("UP");
			return 'u';
		}
		return 'n';
	}
	
	public void play(){
		drawApple(game.getApple());
		mainThread.start();
	}
}
