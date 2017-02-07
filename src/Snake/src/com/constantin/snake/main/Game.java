package com.constantin.snake.main;

import com.constantin.snake.util.Constants;
import com.constantin.snake.util.Location;

import java.util.Random;

public class Game {
	private Snake snake;
	private Location apple;
	private Random r;
	
	private int score;
	
	public Game(){
		r = new Random();
		snake = new Snake(new Location(10, 10));
		apple = new Location(r.nextInt(Constants.FIELD_WIDTH), r.nextInt(Constants.FIELD_HEIGHT));
		score = 0;
	}
	
	public void input(char input){
		if(!(input == 'n')){
			snake.setDirection(input);
		}
	}
	
	public void moveSnake(){
		snake.move();
	}
	
	public Location[] getSnake(){
		Location[] locations = new Location[snake.getLength()];
		int index = 0;
		for(Location location : snake.getSnakeLocs()){
			locations[index] = location;
			++index;
		}
		return locations;
	}
	
	public Location getApple(){
		return apple;
	}
	
	public boolean checkCollisions(){
		Location[] snakeLocs = snake.getSnakeLocs();
		for(int index = 1; index < snakeLocs.length; ++index){
			if(snakeLocs[index].equals(snakeLocs[0])){
				return true;
			}
		}
		return false;
	}
	
	public boolean appleEaten(){
		if(apple.equals(snake.getSnakeLocs()[0])){
			snake.incrementSnake();
			apple.setLocation(new Location(r.nextInt(Constants.FIELD_WIDTH), r.nextInt(Constants.FIELD_HEIGHT)));
			score += 10;
			return true;
		}
		return false;
	}
	
	public int getScore(){
		return score;
	}
}
