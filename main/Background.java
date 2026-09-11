package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Description: This class outlines the Background object used by the Game and Level classes. 
 * It draws, updates the position and holds the details of the backgrounds of the different levels in Cube Jumper.
 * Jan 20th, 2026
 * @author Kaleena Kanagarajan
 *
 */
public class Background {
	
	//Fields
	private Color colour;
	private double height;
	private double width;
	private double x;
	private double speed;
	
	
	
	
	//Behaviors
	//Getters
	/**
	 Description: This method gets the x coordinate of the Background object.
	 @return value of the x field.
	 */
	public double getX() {
		
		return this.x;
		
	}
	
	/**
	 * Description: This method returns the value of the width field
	 * @return -> value of the width field
	 */
	public double getWidth() {
		
		return this.width;
		
	}
	
	
	
	
	//Setters
	/**
	 * Description: This method sets a new value for the colour field.
	 * @param colour -> value which this.colour will be set to.
	 */
	public void setColour(Color colour) {
		
		this.colour = colour;
		
	}

	/**
	 * Description: This method sets a new value for the height field.
	 * @param height -> value which this.height will be set to.
	 */
	public void setHeight(double height) {
		
		this.height = height;
		
	}
	
	/**
	 * Description: This method sets a new value for the width field.
	 * @param width -> value which this.width will be set to.
	 */
	public void setWidth(double width) {
		
		this.width = width;
		
	}
	
	/**
	 Description: This method sets a new value for the x field.
	 @param x -> value which this.x will be set to. 
	 */
	public void setX(double x) {
		
		this.x = x;
		
	}
	
	/**
	 * Description: This method sets a new value for the speed field.
	 * @param speed -> value which this.speed will be set to.
	 */
	public void setSpeed(double speed) {
		
		this.speed = speed;
		
	}
	
	
	
	
	//Other Methods
	/**
	 * Description: Updates the x coordinate position of the Background object based on the set speed.
	 */
	public void update() {
		
		this.x -= speed;
		
	}

	/**
	 * Description: Draws the background, or a rectangle, onto the graphics context
	 * @param gc -> the graphics context that the methods draws on
	 */
	public void draw(GraphicsContext gc) {
		
		gc.setFill(this.colour);
		gc.fillRect(x, 0, width, height);
		
	}
	
	
	
	
	//Constructors
	/**
	 * Description: Creates an instance of the Background object and sets the colour, height, width, x and speed fields to the according parameters input.
	 * @param colour -> value which this.colour will be set to
	 * @param height -> value which this.height will be set to
	 * @param width -> value which this.width will be set to
	 * @param x -> value which this.x will be set to
	 * @param speed -> value which this.speed will be set to
	 */
	public Background(Color colour, double height, double width, double x, double speed) {
		
		this.colour = colour;
		this.height = height;
		this.width = width;
		this.x = x;
		this.speed = speed;
		
	}
	
	/**
	 * Description: Creates an instance of the Background object and sets the colour, width, x and speed fields to the according parameter input.
	 * @param colour -> value which this.colour will be set to
	 * @param width -> value which this.width will be set to
	 * @param x -> value which this.x will be set to
	 * @param speed -> value which this.speed will be set to
	 */
	public Background(Color colour, double width, double x, double speed) {
		
		this.colour = colour;
		this.height = 990; //width of stage
		this.width = width;
		this.x = x;
		this.speed = speed;
		
	}
	
	/**
	 * Description: Creates an instance of the Background object and sets the colour, width and speed fields to the according parameter input.
	 * @param colour -> value which this.colour will be set to
	 * @param width -> value which this.width will be set to
	 * @param speed -> value which this.speed will be set to
	 */
	public Background(Color colour, double width, double speed) {
		
		this.colour = colour;
		this.height = 990; //width of stage
		this.width = width;
		this.x = 0;
		this.speed = speed;
		
	}
	
	/**
	 * Description: Creates an instance of the Background object and set all fields to null or 0.
	 */
	public Background() {
		
		this.colour = null;
		this.height = 0;
		this.width = 0;
		this.x = 0;
		this.speed = 0;
		
	}
	
	
	

}
