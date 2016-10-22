package com.constantin.snake.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Menu {
	
	private static Stage primaryStage;
	private static GridPane menuPane;
	private static Scene menuScene;
	private static Label version;
	private static Label author;
	private static Label gd;
	private static Button back;
	
	public static void menu(Stage Stage){
		primaryStage = Stage;
		
		menuPane = new GridPane();
		version = new Label("Version: " + Main.VERSION);
		author = new Label("Programmierer: Constantin Schulte");
		gd = new Label("Graphical Designer: Constantin Schulte");
		back = new Button("Back");
		
		initField();
	}
	
	private static void initField(){
		menuPane.setHgap(32);
		menuPane.setVgap(32);
		back.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				Main.restart();
			}
			
		});
		
		menuPane.add(back, 2, 5);
		menuPane.add(version, 1, 1);
		menuPane.add(author, 1, 2);
		menuPane.add(gd, 2, 2);
		
		menuScene = new Scene(menuPane, 750, 300);
		
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}
}
