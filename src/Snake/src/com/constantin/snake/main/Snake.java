package com.constantin.snake.main;

import java.util.ArrayList;

import com.constantin.snake.util.Location;

/**
 * Snake.
 * 
 * @author Constantin Schulte
 * @version 1.0
 */
public class Snake {
	private ArrayList<Location> box;
	private Location headLoc;
	private String direction;
	
	/**
	 * Creates a new snake with the location of the head as location transmitted.
	 * Calls initSnake() method to make the snake a snake.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param headLoc
	 */
	public Snake(Location headLoc){
		this.headLoc = headLoc;
		direction = "r";
		box = new ArrayList<Location>();
		box.add(headLoc);
		initSnake();
	}

	/**
	 * Initializing Snake. Just adding four boxes behind the head's location
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	private void initSnake(){
		int x, y;
		x = headLoc.getX();
		y = headLoc.getY();
		int length = 1;
		for(int i = x-1; i > 0; --i){
			box.add(new Location(i, y));
			++length;
			if(length == 5){
				break;
			}
		}
	}
	
	public void incrementSnake(){
		box.add(new Location(box.get(box.size()-1)));
	}
	
	/**
	 * Moving snake one field forward, depending on direction.
	 * First gives the old locations to the next snake-part behind and then creating new location
	 * for the head, also checks whether intersects boundary.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 */
	public void move(){
		for(int index = box.size()-1; index > 0; --index){
			box.get(index).setLocation(box.get(index-1));
		}
		switch(direction){
		case "u":
			if(box.get(0).getY() == 0){
				int x = box.get(0).getX();
				box.get(0).setLocation(new Location(x, Main.FIELD_HEIGHT-1));
			}else{
				int newX = box.get(0).getX();
				int newY = box.get(0).getY()-1;

				box.get(0).setLocation(new Location(newX, newY));
			}
			break;
		case "r":
			if(box.get(0).getX() == Main.FIELD_WIDTH-1){
				int y = box.get(0).getY();
				box.get(0).setLocation(new Location(0, y));
			}else{
				int newX = box.get(0).getX()+1;
				int newY = box.get(0).getY();

				box.get(0).setLocation(new Location(newX, newY));
			}
			break;
		case "d":
			if(box.get(0).getY() == Main.FIELD_HEIGHT-1){
				int x = box.get(0).getX();
				box.get(0).setLocation(new Location(x, 0));
			}else{
				int newX = box.get(0).getX();
				int newY = box.get(0).getY()+1;

				box.get(0).setLocation(new Location(newX, newY));
			}
			break;
		case "l":
			if(box.get(0).getX() == 0){
				int y = box.get(0).getY();
				box.get(0).setLocation(new Location(Main.FIELD_WIDTH-1, y));
			}else{
				int newX = box.get(0).getX()-1;
				int newY = box.get(0).getY();

				box.get(0).setLocation(new Location(newX, newY));
			}
		}
	}
	
	/**
	 * Returns all locations of the snake as an int-array.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return snake: a two-dimensional int-array with the locations of the snake as x and y coordinates.
	 */
	public int[][] getSnake(){
		int[][] snake = new int[box.size()][2];
		int index = 0;
		for(Location loc : box){
			snake[index][0] = loc.getX();
			snake[index][1] = loc.getY();
			++index;
		}
		return snake;
	}
	
	
	/**
	 * Returns locations of Snake as Location[].
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return locs - a Location[] with all snake locations.
	 */
	public Location[] getSnakeLocs(){
		Location[] locs = new Location[box.size()];
		int index = 0;
		for( Location loc : box ){
			locs[index] = loc;
			++index;
		}
		return locs;
	}
	
	/**
	 * Returns length of snake.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return int length of snake
	 */
	public int getLength(){
		return box.size();
	}
	
	/**
	 * Returns direction of snake.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return String direction
	 */
	public String getDirection(){
		return direction;
	}
	
	/**
	 * Changes the direction of snake to the String transmitted.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param direction as new direction of snake
	 */
	public void setDirection(String direction){
		this.direction = direction;
	}
}
