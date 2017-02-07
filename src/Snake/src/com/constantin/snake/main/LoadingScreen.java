package com.constantin.snake.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoadingScreen extends Application {
	private Stage primaryStage;

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
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		
		Label loading = new Label("Loading ...");
		Scene loadingScene = new Scene(loading, 1000, 500);
		
		primaryStage.setScene(loadingScene);
		Main.start(primaryStage);
	}

}
