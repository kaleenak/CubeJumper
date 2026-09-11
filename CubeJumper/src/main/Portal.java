package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Description: This class outlines the Portal object used by the Game and Level classes. 
 * The Portals represent points where the Player's PlayerMode will change, and therefore Portal has a playerMode field. 
 * This class draws, updates the position and holds the details of the Portals of the different levels in Cube Jumper.
 * Jan 20th, 2026
 * @author Kaleena Kanagarajan
 */
public class Portal extends Ellipse {
	
	//Fields
	private double x;
	private double y;
	private double width;
	private double height;
	private double speed;
	private Color colour;
	private PlayerMode playerMode;
	
	
	
	//Behaviors
	//Setters
	/**
	 * Description: This method sets the x field
	 * @param x -> the value which x field will be set to
	 */
	public void setX(double x) {
		
		this.x = x;
		
	}
	
	/**
	 * Description: This method sets the y field
	 * @param y -> the value which y field will be set to
	 */
	public void setY(double y) {
		
		this.y = y;
		
	}
	
	/**
	 * Description: This method sets a new value for the width field.
	 * @param width -> value which this.width will be set to.
	 */
	public void setWidth(double width) {
		
		this.width = width;
		
	}
	
	/**
	 * Description: This method sets a new value for the height field.
	 * @param height -> value which this.height will be set to.
	 */
	public void setHeight(double height) {
		
		this.height = height;
		
	}
	
	/**
	 * Description: This method sets the playerMode field
	 * @param playerMode -> the value which playerMode field will be set to
	 */
	public void setPlayerMode(PlayerMode playerMode) {
		
		this.playerMode = playerMode;
		
	}
	
	/**
	 * Description: This method sets the colour field
	 * @param colour -> the value which the colour field will be set to
	 */
	public void setColour(Color colour) {
		
		this.colour = colour;
		
	}
	
	/**
	 * Description: This method sets a new value for the speed field.
	 * @param speed -> value which this.speed will be set to.
	 */
	public void setSpeed(double speed) {
		
		this.speed = speed;
		
	}
	
	
	
	
	//Getters
	/**
	 * Description: This method return the value of the x field
	 * @return -> the value of the x field
	 */
	public double getX() {
		
		return this.x;
		
	}
	
	/**
	 * Description: This method return the value of the y field
	 * @return -> the value of the y field
	 */
	public double getY() {
		
		return this.y;
		
	}
	
	/**
	 * Description: This method returns the value of the width field
	 * @return -> the value of the width field
	 */
	public double getWidth() {
		
		return this.width;
		
	}
	
	/**
	 * Description: This method returns the value of the height field
	 * @return -> the value of the height field
	 */
	public double getHeight() {
		
		return this.height;
		
	}
	
	/**
	 * Description: This method returns the value of the playerMode field
	 * @return -> value of playerMode field
	 */
	public PlayerMode getPlayerMode() {
		
		return playerMode;
		
	}
	
	/**
	 * Description: This method returns the value of the colour field.
	 * @return
	 */
	public Color getColour() {
		
		return this.colour;
		
	}
	
	/**
	 * Description: This method return the value of the speed field
	 * @return -> the value of the speed field
	 */
	public double getSpeed() {
		
		return this.speed;
		
	}
	

	
	
	//Constructors
	/**
	 * Description: Creates an instance of Portal with the width and height given by the parameters
	 * @param width -> value which this.width is set to
	 * @param height -. the value which this.height is set to
	 */
	public Portal(double width, double height) {
		
		super(width, height);
		
		
	}
	
	/**
	 * Description: Creates an instance of Portal with the x, y, width and height given by the parameters.
	 * @param x -> the value which this.x is set to
	 * @param y -> the value which this.y is set to
	 * @param width -> the value which this.width is set to
	 * @param height -> the value which this.height is set to
	 */
	public Portal(double x, double y, double width, double height) {
		
		super(x, y, width, height);
		
	}
	
	
	/**
	 * Description: Creates an instance of Portal with the x, y, width, height, and playerMode given by the parameters.
	 * @param x -> the value which this.x is set to
	 * @param y -> the value which this.y is set to
	 * @param width -> the value which this.width is set to
	 * @param height -> the value which this.height is set to
	 * @param playerMode -> the value which this.playerMode is set to
	 */
	public Portal(double x, double y, double width, double height, double speed, Color colour, PlayerMode playerMode) {
		
		super(0, 0, width, height);
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setSpeed(speed);
		this.setColour(colour);
		this.setPlayerMode(playerMode);
		
	}
	
	
	
	
	//Other Methods
	/**
	 * Description: This method updates the x position of the portal depending on the speed. This method is always called before draw().
	 */
	public void update() {
		
		x -= speed;
		this.setLayoutX(x);
		
	}
	
	/**
	 * Description: Draws a spike on the graphics context
	 * @param gc -> the graphics context which is drawn on
	 */
	public void draw(GraphicsContext gc) {
		
		//Outline
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(10);
		gc.strokeOval(x, y, width, height);
		
		//Portal
		gc.setFill(this.getColour());
		gc.fillOval(x, y, width, height);
		
	}


}
