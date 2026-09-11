package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Description: This class outline the Player object used by the Game and Level classes. It represents the player in Cube Jumper.
 * It draws, updates the position and holds the details of the player of the different levels in Cube Jumper.
 * Addtionally, different types of Players are determined by PlayerMode enums. The enum determines values such as gravity, changing how the player moves on the screen.
 * January 20, 2025
 * @author Branden Jiang
 */
public class Player extends Rectangle{

	
	private double size;
	private Color colour;
	private double yPosition;
	private double xPosition;
	private double yVelocity;
	private double gravity = 0.25;
	private double rotationSpeed;
	private boolean isAlive;
	private double floor;
	private double platform;
	private boolean isOnPlatform;
	private boolean reachedEnd;
	private PlayerMode playerMode;
	
	
	/**
	 * Description: Creates an instance of the player class and sets the colour, xPosition, yPosition, playerMode and floor fields with the given values.
	 * @param colour -> the value which this.colour will be set to
	 * @param xPosition -> the value which this.xPositon will be set to
	 * @param yPosition -> the value which this.yPosition will be set to
	 * @param playerMode -> the value which this.playerMode will be set to
	 * @param floor -> the value which this.playerMode will be set to
	 */
	public Player(Color colour, double xPosition, double yPosition, PlayerMode playerMode, double floor) {
		super(0, 0, 100, 100);
		
		this.setIsAlive(true);
		
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		
		this.setyPosition(yPosition);
		this.setxPosition(xPosition);
		
		this.colour = colour;
		this.setFloor(floor);
		this.setPlayerMode(playerMode);
		this.setIsOnPlatform(false);
		this.setReachedEnd(false);
		}
	
	/**
	 * Method that is constantly updating where the player should be
	 */
	public void update() {
		
		if(!reachedStart() || this.getReachedEnd()) {
			xPosition += 14;
			this.setLayoutX(xPosition);
		}
			
		//Set the player's yPostion
		yPosition -= yVelocity;
		this.setLayoutY(yPosition);
		
		if (isOnFloor() || getIsOnPlatform()) {
			yVelocity = 0;
			
			if(isOnFloor()) {
				this.setyPosition(floor - size);
			}
		}
		
		this.setSize(playerMode.size);
		if (playerMode == PlayerMode.CUBE || playerMode == PlayerMode.BALL) {
			if (!isOnFloor() && !getIsOnPlatform()) {
				this.setVelocity(this.getVelocity() - playerMode.gravity);
			}
		}
		else if (playerMode == PlayerMode.SHIP) {
			if (!isOnFloor() && !getIsOnPlatform() && yVelocity > -5) {
				this.setVelocity(this.getVelocity() - playerMode.gravity);
			}
		}
			
		
		
		this.setIsOnPlatform(false);
	
	}
	
	/**
	 * Checks if the Player is on the Floor
	 * @return True if Player is on the Floor
	 */
	private boolean isOnFloor() {
		if (yPosition >= floor - size) {
			return true;
		}
		else
		return false;
	}
	
	/**
	 * Description: This method checks if the player has reached its starting position
	 * @return -> true if player has reached starting position and false if not
	 */
	public boolean reachedStart() {
		if(this.getxPosition() < 480) {
			return false;
		}
		return true;
	}

	/**
	 * Method that makes the player Jump 
	 */
	public void jump() {
		
		if(reachedStart()){
			if (playerMode == PlayerMode.CUBE || playerMode == PlayerMode.BALL) {
	
				if (isOnFloor() || this.isOnPlatform) {
					this.setVelocity(this.getVelocity() + playerMode.velocityChange);
					this.setGravity(playerMode.gravity * playerMode.gravityChange);
					this.setIsOnPlatform(false);
				}
			}
			else if (playerMode == PlayerMode.SHIP) {
				this.setVelocity(this.getVelocity() + playerMode.velocityChange);
			}
			else if (playerMode == PlayerMode.WAVE) {
				this.setVelocity(playerMode.velocityChange * -1);
			}
		}
	}
		
	/**
	 * Method that stops the player when the player dies
	 */
	public void die() {
		this.setVelocity(0); 
		this.setGravity(0);
		this.setRotationSpeed(0);
		this.setIsAlive(false);
	}
	
	/**
	 * Method that is constantly drawing the player
	 * @param gc
	 */
	public void draw(GraphicsContext gc) {
		
		//Outline
		gc.setStroke(Color.DARKORANGE);
		gc.setLineWidth(10);
		gc.strokeRect(xPosition, yPosition, size, size);
		
		//Player
		gc.setFill(this.colour); 
		gc.fillRect(xPosition, yPosition, size, size);
		
	}
	
	/**
	 * Getter for yVelocity
	 * @return the velocity
	 */
	public double getVelocity() {
		return yVelocity;
	}

	/**
	 * Setter for yVelocity
	 * @param velocity the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.yVelocity = velocity;
	}

	/**
	 * Getter for gravity
	 * @return the gravity
	 */
	public double getGravity() {
		return gravity;
	}

	/**
	 * Setter for gravity
	 * @param gravity the gravity to set
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	/**
	 * Getter for rotationSpeed
	 * @return the rotationSpeed
	 */
	public double getRotationSpeed() {
		return rotationSpeed;
	}

	/**
	 * Setter for rotationSpeed
	 * @param rotationSpeed the rotationSpeed to set
	 */
	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	/**
	 * Getter for isAlive
	 * @return the isAlive
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * Setter for isAlive
	 * @param isAlive the isAlive to set
	 */
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * Getter for playerMode
	 * @return the playerMode
	 */
	public PlayerMode getPlayerMode() {
		return playerMode;
	}

	/**
	 * Setter for playerMode
	 * @param playerMode the playerMode to set
	 */
	public void setPlayerMode(PlayerMode playerMode) {
		this.playerMode = playerMode;
		this.size = playerMode.size;
		this.setWidth(playerMode.size);
		this.setHeight(playerMode.size);
	}


	/**
	 * Description: This method gets the value of the yPosition field
	 * @return the yPosition
	 */
	public double getyPosition() {
		return yPosition;
	}

	/**
	 * Description: This method sets the value of the yPosition field
	 * @param yPosition the yPosition to set
	 */
	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * Description: This method gets the value of the xPosition field
	 * @return the xPosition
	 */
	public double getxPosition() {
		return xPosition;
	}

	/**
	 * Description: This method sets the value of the xPosition field
	 * @param xPosition the xPosition to set
	 */
	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Description: This method gets the value of the size field
	 * @return the size
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Description: This method sets the value of the size field
	 * @param size the size to set
	 */
	public void setSize(double size) {
		this.size = size;
	}

	/**
	 * Description: This method gets the value of the floor field
	 * @return the floor
	 */
	public double getFloor() {
		return floor;
	}

	/**
	 * Description: This method sets the value of the floor field
	 * @param floor the floor to set
	 */
	public void setFloor(double floor) {
		this.floor = floor;
	}

	/**
	 * Description: This method gets the value of the platform field
	 * @return the platform
	 */
	public double getPlatform() {
		return platform;
	}

	/**
	 * Description: This method sets the value of the platform field
	 * @param platform the platform to set
	 */
	public void setPlatform(double platform) {
		this.platform = platform;
	}

	/**
	 * Description: This method gets the value of the isOnPlatform field
	 * @return the isOnPlatform
	 */
	public boolean getIsOnPlatform() {
		return isOnPlatform;
	}

	/**
	 * Description: This method sets the value of the isOnPlatform field
	 * @param isOnPlatform the isOnPlatform to set
	 */
	public void setIsOnPlatform(boolean isOnPlatform) {
		this.isOnPlatform = isOnPlatform;
	}
	
	/**
	 * Description: This method returns the value of the reachedEnd field
	 * @return -> the value of the reached end field
	 */
	public boolean getReachedEnd() {
		return this.reachedEnd;
	}
	
	/**
	 * Description: This method sets the value of the reachedEnd field
	 * @param reachedEnd -> the value which this.reachedEnd will be set to
	 */
	public void setReachedEnd(boolean reachedEnd) {
		this.reachedEnd = reachedEnd;
	}
	
	
}


