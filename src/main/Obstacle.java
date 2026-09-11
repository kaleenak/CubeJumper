package main;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Description: This class outline the obstacle object extended by the Spike and Cube classes. In Cube Jumper, spikes and cubes are types of obstacles.
 * This class extends polygons and helps construct a polygon for each obstacle, which is later used in checking for collisions in Game class.
 * The move method updates the obstacle's position (where it is drawn and the polygon's actual position as well).
 * Jan 20th, 2026
 * @author Kenneth Tsugawa
 */
public abstract class Obstacle extends Polygon {

	//Fields
	private int velocity;
	private Color colour;
	private double[] xPoints;
	private double[] yPoints;
	private String type;
	
	
	
	
	//Getters and Setters
	/**
	 * Description: This method separates an array of polygon coordinates into an array of x coordinates and an array of y coordinates.
	 * @param points -> double array of polygon coordinates
	 */
	private void setXYPoints(double[] points) {
		
		//x points
		this.xPoints = new double[points.length/2];
		int j = 0;
		
		for (int i = 0; i < points.length; i+=2) {
			
			getxPoints()[j] = points[i];
			j++;
			
		}
		
		//y points
		this.yPoints = new double[points.length/2];
		j = 0;
		
		for (int i = 1; i < points.length; i+=2) {
			
			getyPoints()[j] = points[i];
			j++;
			
		}
		
	}
	
	/**
	 * Description: This method gets the velocity.
	 * @return the value of this.velocity
	 */
	public int getVelocity() {
		
		return this.velocity;
		
	}

	/**
	 * Description: This method sets the velocity to the value given in the parameters.
	 * @param velocity -> the value which this.velocity will be set to
	 */
	public void setVelocity(int velocity) {
		
		this.velocity = velocity;
		
	}

	/**
	 * Description: This method returns the colour field.
	 * @return -> the value of this.colour.
	 */
	public Color getColour() {
		
		return colour;
		
	}

	/**
	 * Description: This method sets this.colour to the value given in the parameters.
	 * @param colour -> the value which this.colour will be set to.
	 */
	public void setColour(Color colour) {
		
		this.colour = colour;
		
	}

	/**
	 * Description: This method returns the xPoints field.
	 * @return the value of the xPoints field.
	 */
	public double[] getxPoints() {
		
		return xPoints;
		
	}

	/**
	 * Description: This method returns the yPoints field
	 * @return the value of the yPoints field.
	 */
	public double[] getyPoints() {
		
		return yPoints;
		
	}
	
	/**
	 * Description: This method sets the type field
	 * @param type -> The string which the type field will be set to
	 */
	public void setType(String type) {
		
		this.type = type;
		
	}
	
	/**
	 * Description: This method returns the type field.
	 * @return -> the value of the type field
	 */
	public String getType() {
		
		return this.type;
		
	}

	
	
	//Other Methods
	/**
	 * Description: This method moves the obstacle in the horizontal direction based on the velocity.
	 */
	public void move() {
		
		this.setLayoutX(this.getLayoutX() - velocity);
	
		for(int i = 0; i < this.getxPoints().length; i++) {
			
			this.getxPoints()[i] -= velocity;		
			
		}
		
	}
	
	/**
	 * Description: Will draw obstacle on given graphics context.
	 * @param gc -> graphics context which will be drawn on.
	 */
	public abstract void draw(GraphicsContext gc);
	
	/**
	 * Checks if spike intersects with a shape. Returns true if it is hit.
	 * @param shape
	 * @return 
	 */
	public boolean isHit(Shape shape, ArrayList<Obstacle> obstacles) {
		
		if(this == obstacles.get(0)) {
			
			return false;
			
		}
		
		return this.getBoundsInParent().intersects(shape.getBoundsInParent());
		
	}
	

	
	
	//Constructors
	/**
	 * Description: Creates an empty instance of obstacle.
	 */
	public Obstacle() {
		
		super();
		
	}

	/**
	 * Description: Creates an instance of obstacle with the points given in the parameter.
	 * @param points -> double array of polygon coordinates.
	 */
	public Obstacle(double[] points) {
		
		super(points);
		this.setXYPoints(points);
		
	}
	
	/**
	 * Description: Creates an instance of obstacle with the points and velocity given in the parameters.
	 * @param points -> double array of polygon coordinates.
	 * @param velocity -> value which this.velocity is set to.
	 */
	public Obstacle(double[] points, int velocity) {
		
		super(points);
		this.setXYPoints(points);
		this.setVelocity(velocity);
		
	}
	
	/**
	 *Description: Creates an instance of obstacle with the points, velocity and colour given in the parameters.
	 * @param points -> double array of polygon coordinates.
	 * @param velocity -> value which this.velocity is set to.
	 * @param colour -> value which this.colour will be set to.
	 */
	public Obstacle(double[] points, int velocity, Color colour, String type) {
		
		super(points);
		this.setXYPoints(points);
		this.setVelocity(velocity);
		this.setColour(colour);
		this.setType(type);
		
	}
}
