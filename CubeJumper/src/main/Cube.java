package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Description: This class outline the Cube object used by the Game and Level classes.
 * It extends obstacle, as it represents the cube type of obstacle in Cube Jumper.
 * It draws and holds the details of the many cubes in Cube Jumper.
 * Jan 20th, 2026
 * @author Kenneth Tsugawa
 */
public class Cube extends Obstacle {
	
	//Constructors
	/**
	 * Description: Creates an empty cube.
	 */
	public Cube() {
		
		super();
		
	}

	/**
	 * Description: Creates a cube based on the points.
	 * @param points -> a double array of polygon coordinates
	 */
	public Cube(double[] points) {
		
		super(points);
		
	}

	/**
	 * Description: Creates a cube based on the points and moves it according to the velocity.
	 * @param points -> a double array of polygon coordinates
	 * @param velocity -> the value which this.velocity 
	 */
	public Cube(double[] points, int velocity) {
		
		super(points, velocity);
		
	}

	/**
	 * Description: Creates a cube based on the points, moves it according to the velocity, and sets the colour.
	 * @param points -> a double array of polygon coordinates
	 * @param velocity -> the value which this.velocity 
	 * @param colour -> the value which this.colour will be set to
	 */
	public Cube(double[] points, int velocity, Color colour, String type) {
		
		super(points, velocity, colour, type);
		
	}
	
	
	
	
	//Other Methods 
	/**
	 * Description: Draws a cube onto the graphics context
	 * @param gc -> the graphics context which drawn on.
	 */
	public void draw(GraphicsContext gc) {
		
		//Outline
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokePolygon(getxPoints(), getyPoints(), getPoints().size()/2);
		
		//Cube
		gc.setFill(this.getColour());
		gc.fillPolygon(getxPoints(), getyPoints(), getPoints().size()/2);
		
	}
	
	
}
