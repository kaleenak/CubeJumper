package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javax.sound.sampled.*;

/**
 * Description: This class outline the level object used by the Game class.
 * It holds all of the elements (obstacles, background, player and portals) and their details for each level of Cube jumper.
 * This class draws the elements, updates their position and more.
 * Jan 20th, 2026
 * @author Kenneth Tsugawa
 */
public class Level {

	//Fields
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(); 
	private Player player;
	private Background background;
	private ArrayList<Portal> portals = new ArrayList<Portal>();
	private Clip clip;
	private boolean isMusicPlaying;
	
	

	//Setters
	/**
	 * Description: This method sets the obstacles field to the value given in the parameters
	 * @param obstacles -> the value which this.obstacles will be set to
	 */
	public void setObstacles(ArrayList<Obstacle> obstacles) {
		
		this.obstacles = obstacles;
		
	}
	
	/**
	 * Description: This method sets the player field to the value given in the parameters
	 * @param player -> the value which this.player will be set to
	 */
	public void setPlayer(Player player) {
		
		this.player = player;
		
	}
	
	/**
	 * Description: This method sets the background field to the value given in the parameters
	 * @param background -> the value which this.background will be set to
	 */
	public void setBackground(Background background) {
		
		this.background = background;
		
	}
	
	/**
	 * Description: This method sets the portals field to the value given in the parameters
	 * @param portals -> the value which this.portals will be set to
	 */
	public void setPortals(ArrayList<Portal> portals) {
		
		this.portals = portals;
		
	}
	
	/**
	 * Description: This method sets the isMusicPlaying field to the value given in the parameters
	 * @param isMusicPlaying -> the value which this.isMusicPlaying will be set to
	 */
	public void setIsMusicPlaying(boolean isMusicPlaying) {
		
		this.isMusicPlaying = isMusicPlaying;
		
	}
	
	
	
	//Getters
	/**
	 * Description: This method returns the value of the obstacles field
	 * @return this.obstacles
	 */
	public ArrayList<Obstacle> getObstacles() {
		
		return this.obstacles;
		
	}

	/**
	 * Description: This method returns the value of the player field.
	 * @return this.player
	 */
	public Player getPlayer() {
		
		return this.player;
		
	}

	/**
	 * Description: This method returns the value of the background field
	 * @return this.background
	 */
	public Background getBackground() {
		
		return this.background;
		
	}
	
	/**
	 * Description: This method returns the value of the portals field
	 * @return this.portals
	 */
	public ArrayList<Portal> getPortals(){
		
		return this.portals;
		
	}
	
	/**
	 * Description: This method returns the value of the isMusicPlaying field
	 * @return this.isMusicplaying
	 */
	public boolean getIsMusicPlaying(){
		
		return this.isMusicPlaying;
		
	}
	
	
	
	
	//Other Methods
	/**
	 * Description: This method updates the position of each element. It is always called before the draw method in the game class.
	 */
	public void update(){
		
		//only move if player has reached starting position and hasn't reached end of level
		if(player.reachedStart() && !reachedEnd()) {
			
			this.getBackground().update(); //update background
			
			//update obstacles
			for (Obstacle o : obstacles) {
				
				o.move();
				
			}
			
			//update portals
			for(Portal p: portals) {
				
				p.update();
				
			}
			
		}
		
		player.update();//update player
		
	}
	
	/**
	 * This method draws all of the level elements onto the Graphics Context.
	 * @param gc -> The graphics context
	 */
	public void draw(GraphicsContext gc) {
		
		background.draw(gc);//draw background
		
		//draw obstacles
		for(Obstacle o : obstacles) {
			
			o.draw(gc);
			
		}
		
		//draw portals
		for(Portal p: portals) {
			
			p.draw(gc);
			
		}
		
		player.draw(gc);//draw player
		
	}
	
	/**
	 * Description: This method loads music from the file given by the parameters. It returns a clip, which can be used to manage the music.
	 * @param fileName -> audio file which has the music
	 * @return a clip object
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public Clip loadMusic (String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File file = new File(fileName);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		return clip;
		
	}
	
	/**
	 * Description: This method calls clip.start() and will start from the number frame given in the parameter or, start where it was paused if start == -1.
	 * @param start -> Starting frame for music
	 */
	public void playMusic (int start) {
		
		this.setIsMusicPlaying(true);
		
		if(start >= 0) {
			
			clip.setFramePosition(start);
			
		}
		clip.start();
	}
	
	/**
	 * Description: This file calls clip.stop(), stopping any music that is playing.
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void stopMusic () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		clip.stop();
		this.setIsMusicPlaying(false);
		
	}
	
	/**
	 * Description: This method checks if the player has reached its ending position based on the background's location
	 * @return -> true if player has reached ending position and false if not
	 */
	public boolean reachedEnd(){
		
		if((background.getX() <= 1760 - background.getWidth()) && player.getX() < 1760 - player.getSize()) {
			
			player.setReachedEnd(true);
			return true;
			
		}
		
		player.setReachedEnd(false);
		return false;
		
	}
	
	/**
	 * Description: This method calculates the user's percentage progress in the level.
	 * @return user's percentage progress
	 */
	public double progress() {
		
		double percent = ((player.getxPosition() - background.getX())/(background.getWidth() - 1760))*100;
		return percent;
		
	}
	
	
	
	
	//Constructors
	/**
	 * Descriptions: Creates an instance of the Level object and set all fields to null.
	 */
	public Level() {
		
		this.obstacles = null;
		this.player = null;
		this.background = null;
		this.isMusicPlaying = false;
	
	}
		
	/**
	 * 	Description: Creates an instance of the Level object and sets all fields to the values given in the parameters
	 * @param obstacles -> the value that this.obstacles will be set to
	 * @param player -> the value that this.player will be set to
	 * @param background -> the value that this.background will be to 
	 */
	public Level(ArrayList<Obstacle> obstacles, Player player, Background background){
		
		this.obstacles = obstacles;
		this.player = player;
		this.background = background;
		this.isMusicPlaying = false;
		
	}
	
}
