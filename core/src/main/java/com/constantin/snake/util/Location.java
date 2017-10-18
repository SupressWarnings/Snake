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
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Location( int x, int y ){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns x as int
	 * 
	 * @author Constantin Schulte
	 * @return x as int
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Returns y as int
	 * 
	 * @author Constantin Schulte
	 * @return y as int
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Checks wether two locations have same position.
	 * 
	 * @author Constantin Schulte
	 * @param loc - Location to check
	 * @return true if both are the same
	 */
	public boolean equals(Location loc) {
        return loc != null && loc.getX() == x && loc.getY() == y;
    }
	
	
	/**
	 * Sets own location as Location transmitted.
	 * 
	 * @author Constantin Schulte
	 * @param loc - new Location
	 */
	public void setLocation(Location loc){
		x = loc.getX();
		y = loc.getY();
	}

	public Location clone(){
	    return new Location(x, y);
    }
}
