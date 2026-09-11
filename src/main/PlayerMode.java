package main;
/**
 * Description: This class creates the PlayerMode enums used by the Player class. 
 * Each enum represents a different type of player, changing how the player moves on the game screen.
 * January 25, 2025
 * @author Branden Jiang
 */
public enum PlayerMode {
	
	//Enums
	CUBE(100, 2, 32, 1),
	SHIP(75, 3, 10, 1),
	BALL(100, 0.5, 4, -1),
	WAVE(40, 0, -10, 0); 
	
	
	//Constants
	final int size; //size of player
	final double gravity; //value which player's y position will be changing by until it hits a platform or the floor
	final double velocityChange; //value which the player's velocity will change by
	final double gravityChange; //value which the gravity applied to the player will be changed by 
	
	
	//Constructor
	/**
	 * Description: This creates a new PlayerMode enum using the values given the parameters.
	 * @param size -> value which the enum's size constant will be set to
	 * @param gravity -> value which the enum's gravity constant will be set to
	 * @param velocityChange -> value which the enum's velocityChange constant will be set to
	 * @param gravityChange -> value which the enum's gravityChange constant will be set to
	 */
	PlayerMode(int size, double gravity, double velocityChange, double gravityChange) {
		
		this.size = size;
		this.gravity = gravity;
		this.velocityChange = velocityChange;
		this.gravityChange = gravityChange;
		
	}
	
}
