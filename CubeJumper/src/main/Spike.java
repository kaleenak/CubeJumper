package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Description: This class outline the Spike object used by the Game and Level classes.
 * It extends obstacle, as it represents the spike type of obstacle in Cube Jumper.
 * It draws and holds the details of the many spikes in Cube Jumper.
 * Jan 20th, 2026
 * @author Kenneth Tsugawa
 */
public class Spike extends Obstacle {
	
	//Constructors
	/**
	 * Description: Creates an empty spike.
	 */
	public Spike() {
		
		super();
		
	}
	
	/**
	 * Description: Creates a spike that is the shape of a polygon with the given point coordinates.
	 * @param points -> a double array with polygon coordinates
	 */
	public Spike(double[] points) {
		
		super(points);
		
	}
	
	/**
	 * Description: Creates a spike that is the shape of a polygon with the given point coordinates and the given velocity.
	 * @param points -> a double array with polygon coordinates
	 * @param velocity -> the value which this.velocity will be set to
	 */
	public Spike (double[] points, int velocity) {
		
		super(points, velocity);
		
	}
	
	/**
	 * Creates a spike that is the shape of a polygon with the given point coordinates, the given velocity, and the given colour.
	 * @param points -> a double array with polygon coordinates
	 * @param velocity -> the value which this.velocity will be set to
	 * @param colour -> the value which this.colour will be set to
	 */
	public Spike (double[] points, int velocity, Color colour, String type) {
		
		super(points, velocity, colour, type);
		
	}
	
	
	
	
	//Other Methods
	/**
	 * Description: Draws a spike on the graphics context
	 * @param gc -> the graphics context which is drawn on
	 */
	public void draw (GraphicsContext gc) {
		
		//Outline
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokePolygon(this.getxPoints(), this.getyPoints(), this.getPoints().size()/2);
		
		//Spike
		gc.setFill(this.getColour());
		gc.fillPolygon(this.getxPoints(), this.getyPoints(), this.getPoints().size()/2);
		
	}
	
}
