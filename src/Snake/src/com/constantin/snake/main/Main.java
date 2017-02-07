package com.constantin.snake.main;

import java.util.ArrayList;
import java.util.Random;

import com.constantin.snake.util.Location;
import com.constantin.snake.util.Constants;

import javafx.animation.AnimationTimer;
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
public class Main {
	
	private static int score = 0;
	
	
	private static AnimationTimer mainThread;
	private static Scene playing;
	private static Button[][] buttons;
	private static GridPane field, window, buttonPane;
	private static Stage primaryStage, chooseLevel;
	private static Snake snake;
	private static Apple apple;
	private static Label scoreLabel;
	private static Button menu, pause,  restartButton;
	private static Random r;
	private static ArrayList<String> input = new ArrayList<>();
	private static String[][] level;
	private static Location snakeStartLocation
					= new Location((int)Constants.FIELD_WIDTH/2, (int)Constants.FIELD_HEIGHT/2);
	private static boolean stopped, lost = false;
	
	/**
	 * Initializing instances.
	 * Calling methods to initialize Field and start game.
	 *
	 * @author Constantin
	 * @version 0.0
	 */
	public static void start(Stage stage) throws Exception {
		primaryStage = stage;
		
		chooseLevel();
	}
	
	public static void restart(){
		score = 0;
		primaryStage.setScene(playing);
		mainThread.start();
		stopped = false;
	}
	
	private static void restartGame(){
		score = 0;
		for(int x = 0; x < Constants.FIELD_WIDTH; ++x){
			for(int y = 0; y < Constants.FIELD_HEIGHT; ++y){
				buttons[x][y].setId("button");
				if(level != null){
					if(level[x][y].equals("block")){
						buttons[x][y].setId("block");
					}
				}
			}
		}
		lost = false;
		scoreLabel.setText("Score: "+score);
		snake = new Snake(new Location(snakeStartLocation));
		apple = new Apple(newAppleLocation());
		stopped = false;
		mainThread.start();
	}
	
	static void chooseLevel(){
		chooseLevel = new Stage();
		chooseLevel.setTitle("Levelauswahl");
		
		GridPane allLevels = new GridPane();
		allLevels.setPadding(new Insets(64));
		allLevels.setHgap(32);
		allLevels.setVgap(32);
		
		Button empty = new Button("Empty");
		empty.setPrefSize(128, 32);
		allLevels.add(empty, 2, 0);
		empty.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				level = Constants.EMPTY;
				startPlaying();
			}
			
		});
		
		Button box = new Button("Box");
		box.setPrefSize(128, 32);
		allLevels.add(box, 2, 1);
		box.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				level = Constants.BOX;	
				startPlaying();
			}
			
		});
		
		Button fields = new Button("Fields");
		fields.setPrefSize(128, 32);
		allLevels.add(fields, 2, 2);
		fields.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				level = Constants.FIELDS;
				snakeStartLocation = new Location((int)Constants.FIELD_WIDTH/4*3,
						(int)Constants.FIELD_HEIGHT/4*3);
				startPlaying();
			}
			
		});
		
		Scene levels = new Scene(allLevels, 500, 250);
		
		chooseLevel.setScene(levels);
		chooseLevel.setAlwaysOnTop(true);
		chooseLevel.show();
	}
	
	private static void startPlaying(){
		chooseLevel.close();
		initField();
	}
	
	/**
	 * Preparing playing field and input.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private static void initField(){
		r = new Random();
		buttons = new Button[Constants.FIELD_WIDTH][Constants.FIELD_HEIGHT];
		field = new GridPane();
		window = new GridPane();
		buttonPane = new GridPane();
		menu = new Button("Menu");
		pause = new Button("||");
		restartButton = new Button("Neu starten");
		scoreLabel = new Label("Score: " + score);
		scoreLabel.setPrefWidth(128);
		
		field.setHgap(5);
		field.setVgap(5);
		
		for(int x = 0; x < Constants.FIELD_WIDTH; ++x){
			for(int y = 0; y < Constants.FIELD_HEIGHT; ++y){
				buttons[x][y] = new Button();
				buttons[x][y].setId("button");
				buttons[x][y].setPrefSize(32, 32);
				field.add(buttons[x][y], x, y);
				if(level != null){
					if(level[x][y].equals("block")){
						buttons[x][y].setId("block");
					}
				}
			}
		}
		snake = new Snake(new Location(snakeStartLocation));
		apple = new Apple(newAppleLocation());
		
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
				if(stopped && !lost){
					mainThread.start();
					stopped = false;
					pause.setText("||");
				}else if(!stopped){
					mainThread.stop();
					stopped = true;
					pause.setText(">");
				}else{
					restartGame();
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
		
		play();
	}
	
	/**
	 * Main loop. Just handle input, do logic and render.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private static void play() {
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
	private static void draw(){
		int[][] snakeLoc = snake.getSnake();
		
		for(int x = 0; x < Constants.FIELD_WIDTH; ++x){
			for(int y = 0; y < Constants.FIELD_HEIGHT; ++y){
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
	private static void inputHandler(){
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
	private static void checkCollisions(){
		if(apple.getLoc().equals(new Location(snake.getSnake()[0][0], snake.getSnake()[0][1]))){
			snake.incrementSnake();
			score += 10;
			scoreLabel.setText("Score: " + score);
			apple.setLocation(newAppleLocation());
		}
		Location[] snakeLoc = snake.getSnakeLocs();
		for(int i = 1; i < snakeLoc.length; ++i){
			if(snakeLoc[i].equals(snakeLoc[0])
					|| buttons[snakeLoc[0].getX()][snakeLoc[0].getY()].getId().equals("block")){
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
	private static void loose(){
		mainThread.stop();
		lost = true;
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
	private static boolean isSnake(Location location){
		int[][] snakeLoc = snake.getSnake();
		for(int[] loc : snakeLoc){
			Location snakeLocation = new Location(loc[0], loc[1]);
			if(snakeLocation.equals(location)){
				return true;
			}
		}
		return false;
	}
	
	private static Location newAppleLocation(){
		Location newAppleLocation = new Location(0, 0);
		do{
			newAppleLocation = new Location(r.nextInt(Constants.FIELD_WIDTH),
					r.nextInt(Constants.FIELD_HEIGHT));
		}while(isSnake(newAppleLocation)
				|| buttons[newAppleLocation.getX()][newAppleLocation.getY()].getId().equals("block"));
		return newAppleLocation;
	}
}
