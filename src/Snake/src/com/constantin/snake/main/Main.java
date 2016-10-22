package com.constantin.snake.main;

import java.util.ArrayList;
import java.util.Random;

import com.constantin.snake.util.Location;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Mainclass of Snake game.
 * 
 * @author Constantin Schulte
 * @version 1.0
 */
public class Main extends Application{
	
	public static final int FIELD_WIDTH = 25;
	public static final int FIELD_HEIGHT = 20;
	public static final String VERSION = "v1.1.0";
	
	private int score = 0;
	
	private static Main appl;
	
	private AnimationTimer mainThread;
	private Scene playing;
	private Button[][] buttons;
	private GridPane field, window, buttonPane;
	public Stage primaryStage;
	private Snake snake;
	private Apple apple;
	private Label scoreLabel;
	private Button menu;
	private Button pause;
	private Button restartButton;
	private Random r;
	private static ArrayList<String> input = new ArrayList<>();
	private boolean stopped = false;
	
	/**
	 * Main method. Just starting window.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Initializing instances.
	 * Calling methods to initialize Field and start game.
	 *
	 * @author Constantin
	 * @version 0.0
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		r = new Random();
		buttons = new Button[FIELD_WIDTH][FIELD_HEIGHT];
		field = new GridPane();
		window = new GridPane();
		buttonPane = new GridPane();
		menu = new Button("Menu");
		pause = new Button("||");
		restartButton = new Button("Neu starten");
		snake = new Snake(new Location((int)FIELD_WIDTH/2, (int)FIELD_HEIGHT/2));
		apple = new Apple(new Location(r.nextInt(FIELD_WIDTH), r.nextInt(FIELD_HEIGHT)));
		scoreLabel = new Label("Score: " + score);
		
		appl = this;
		
		initField();
		
		play();
	}
	
	public static void restart(){
		appl.score = 0;
		appl.primaryStage.setScene(appl.playing);
		appl.mainThread.start();
		appl.stopped = false;
	}
	
	private void restartGame(){
		score = 0;
		for(Button[] row : buttons){
			for(Button button : row){
				button.setId("button");
			}
		}
		scoreLabel.setText("Score: "+score);
		snake = new Snake(new Location((int)FIELD_WIDTH/2, (int)FIELD_HEIGHT/2));
		apple = new Apple(new Location(r.nextInt(FIELD_WIDTH), r.nextInt(FIELD_HEIGHT)));
		stopped = false;
		mainThread.start();
	}
	
	/**
	 * Preparing playing field and input.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void initField(){
		
		field.setHgap(5);
		field.setVgap(5);
		
		for(int x = 0; x < FIELD_WIDTH; ++x){
			for(int y = 0; y < FIELD_HEIGHT; ++y){
				buttons[x][y] = new Button();
				buttons[x][y].setId("button");
				buttons[x][y].setPrefSize(32, 32);
				field.add(buttons[x][y], x, y);
			}
		}
		
		menu.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				mainThread.stop();
				stopped = true;
				Menu.menu(primaryStage);
			}
			
		});
		
		pause.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(stopped){
					mainThread.start();
					stopped = false;
					pause.setText("||");
				}else{
					mainThread.stop();
					stopped = true;
					pause.setText(">");
				}
			}
			
		});
		
		restartButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				restartGame();
			}
			
		});
 
		buttonPane.setHgap(64);
		
		buttonPane.add(scoreLabel, 0, 0);
		buttonPane.add(menu, 1, 0);
		buttonPane.add(pause, 2, 0);
		buttonPane.add(restartButton, 3, 0);
		
		window.setHgap(32);
		window.setVgap(32);
		window.setPadding(new Insets(64, 64, 64, 64));
		
		window.add(field, 0, 0);
		window.add(buttonPane, 0, 1);
		
		playing = new Scene(window, 1050, 1000);
		
		playing.setOnKeyPressed(e->{
            String code = e.getCode().toString();
            if(!input.contains(code) && !stopped){
                input.add(code);
            }
		});
		
		playing.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
		
		primaryStage.setScene(playing);
		primaryStage.show();
	}
	
	/**
	 * Main loop. Just handle input, do logic and render.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void play() {
		mainThread = new AnimationTimer(){

            @Override
            public void handle(long now) {
                inputHandler();
                draw();
                snake.move();
                checkCollisions();
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
		};
		mainThread.start();
		stopped = false;
	}
	
	/**
	 * Rendering method. Sets all buttons as buttons and then checks the snake locations
	 * and marks them.
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void draw(){
		int[][] snakeLoc = snake.getSnake();
		
		for(int x = 0; x < FIELD_WIDTH; ++x){
			for(int y = 0; y < FIELD_HEIGHT; ++y){
				if(buttons[x][y].getId().equals("snake")){
					buttons[x][y].setId("button");
				}
			}
		}
		
		Location appleLocation = apple.getLoc();
		buttons[appleLocation.getX()][appleLocation.getY()].setId("apple");

		for(int i = 0; i < snakeLoc.length; ++i){
			buttons[snakeLoc[i][0]][snakeLoc[i][1]].setId("snake");
		}
	}

	/**
	 * Handling pressed keys and changes the direction if possible.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void inputHandler(){
		if(input.contains("DOWN")){
			if(snake.getDirection().equals("d") || snake.getDirection().equals("u")){
				input.remove("DOWN");
				return;
			}
			snake.setDirection("d");
			input.remove("DOWN");
		}else if(input.contains("LEFT")){
			if(snake.getDirection().equals("l") || snake.getDirection().equals("r")){
				input.remove("LEFT");
				return;
			}
			snake.setDirection("l");
			input.remove("LEFT");
		}else if(input.contains("RIGHT")) {
			if(snake.getDirection().equals("r") || snake.getDirection().equals("l")){
				input.remove("RIGHT");
				return;
			}		
			snake.setDirection("r");
			input.remove("RIGHT");
		}else if(input.contains("UP")){
			if(snake.getDirection().equals("d") || snake.getDirection().equals("u")){
				input.remove("UP");
				return;
			}
			snake.setDirection("u");
			input.remove("UP");
		}
	}
	
	
	/**
	 * Checks whether there are collisions with apple or other parts of snake.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void checkCollisions(){
		if(apple.getLoc().equals(new Location(snake.getSnake()[0][0], snake.getSnake()[0][1]))){
			snake.incrementSnake();
			score += 10;
			scoreLabel.setText("Score: " + score);
			Location newAppleLocation = new Location(0, 0);
			do{
				newAppleLocation = new Location(r.nextInt(FIELD_WIDTH), r.nextInt(FIELD_HEIGHT));
			}while(isSnake(newAppleLocation));
			apple.setLocation(newAppleLocation);
		}
		Location[] snakeLoc = snake.getSnakeLocs();
		for(int i = 1; i < snakeLoc.length; ++i){
			if(snakeLoc[i].equals(snakeLoc[0])){
				loose();
			}
		}
	}
	
	
	/**
	 * Called when bitten your own end. Edit in later versions.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void loose(){
		mainThread.stop();
		stopped = true;
	}
	
	
	/**
	 * Checks whether there is snake at a certain location.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param location - a location to be checked
	 * @return true if snake is at this position
	 */
	private boolean isSnake(Location location){
		int[][] snakeLoc = snake.getSnake();
		for(int[] loc : snakeLoc){
			Location snakeLocation = new Location(loc[0], loc[1]);
			if(snakeLocation.equals(location)){
				return true;
			}
		}
		return false;
	}
}
