package com.constantin.snake.util;

/**
 * Util class saving a location in a two-dimensional area as x- and y-coordinates.
 * 
 * @author Constantin Schulte
 * @version 1.0
 */
public class Location {
	private int x, y;
	
	/**
	 * Creating new Location with two coordinates.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Location( int x, int y ){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates new Location out of location transmitted.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param location
	 */
	public Location(Location location){
		this.x = location.getX();
		this.y = location.getY();
	}
	
	/**
	 * Returns x as int
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return x as int
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Returns y as int
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @return y as int
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Sets x-coordinate. 
	 *
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param x
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Sets y-coordinate
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Checks wether two locations have same position.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param loc - Location to check
	 * @return true if both are the same
	 */
	public boolean equals(Location loc){
		if(loc.getX() == x && loc.getY() == y){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * Sets own location as Location transmitted.
	 * 
	 * @author Constantin Schulte
	 * @version 1.0
	 * @param loc - new Location
	 */
	public void setLocation(Location loc){
		x = loc.getX();
		y = loc.getY();
	}
}
