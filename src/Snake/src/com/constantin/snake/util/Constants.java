package com.constantin.snake.util;

public class Constants {
	
	public static final int FIELD_WIDTH = 25;
	public static final int FIELD_HEIGHT = 20;
	public static final String VERSION = "v1.2.0";

	public static final String[][] BOX = createBoxLevel();
	public static final String[][] FIELDS = createFieldLevel();
	public static final String[][] EMPTY = createEmptyLevel();
	
	public static String[][] createBoxLevel(){
		String[][] box = new String[FIELD_WIDTH][FIELD_HEIGHT];
		for(int x = 0; x < FIELD_WIDTH; ++x){
			for(int y = 0; y < FIELD_HEIGHT; ++y){
				box[x][y] = "";
			}
		}
		for(int i = 0; i < FIELD_HEIGHT; ++i){
			box[0][i] = "block";
			box[FIELD_WIDTH-1][i] = "block";
		}
		for(int i = 0; i < FIELD_WIDTH; ++i){
			box[i][0] = "block";
			box[i][FIELD_HEIGHT-1] = "block";
		}
		return box;
	}
	
	public static String[][] createFieldLevel(){
		String[][] field = new String[FIELD_WIDTH][FIELD_HEIGHT];
		for(int x = 0; x < FIELD_WIDTH; ++x){
			for(int y = 0; y < FIELD_HEIGHT; ++y){
				field[x][y] = "";
			}
		}
		for(int i = 0; i < FIELD_HEIGHT; ++i){
			field[(int)FIELD_WIDTH/2][i] = "block";
		}
		for(int i = 0; i < FIELD_WIDTH; ++i){
			field[i][(int)FIELD_HEIGHT/2] = "block";
		}
		return field;
	}
	
	public static String[][] createEmptyLevel(){
		String[][] empty = new String[FIELD_WIDTH][FIELD_HEIGHT];
		for(int x = 0; x < FIELD_WIDTH; ++x){
			for(int y = 0; y < FIELD_HEIGHT; ++y){
				empty[x][y] = "";
			}
		}
		return empty;
	}
}
