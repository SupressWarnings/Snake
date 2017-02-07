package com.constantin.snake.main;

import com.constantin.snake.util.Location;

/**
 * The apple the snake eats to grow.
 * 
 * @author Constantin Schulte
 * @version 1.0
 */
public class Apple {
	private Location location;
	
	/**
	 * Creates apple at Location
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param location - apple's Location
	 */
	public Apple(Location location){
		this.location = location;
	}
	
	/**
	 * Returns apple's Location.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return location of apple
	 */
	public Location getLoc(){
		return location;
	}
	
	
	/**
	 * Sets apple's Location
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param newLoc - new Location of apple.
	 */
	public void setLocation(Location newLoc){
		this.location = newLoc;
	}
}
