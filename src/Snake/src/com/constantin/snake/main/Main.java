package com.constantin.snake.main;

import com.constantin.snake.gui.Ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private Ui ui;
	private Game game;	

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		game = new Game();
		ui = new Ui(primaryStage, game);
		//ui.chooseLevel();
		ui.prepareGame();
		
		ui.play();
		
	}
}
