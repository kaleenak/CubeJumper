package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Description: Main class for Cube Jumper game. Cube Jumper is a copy of the Geometry Dash game. 
 * The user must tap their keys in order to avoid the obstacles on the side-scrolling screen. 
 * Additional features include multiple level, high score readings, progress readings, attempt counter, music, and more.
 * Jan 20th, 2026
 * @author Kaleena Kanagarajan
 */
public class Game extends Application{
	
	//Global Variables
	int currentLevel; //keeps track of which level the user is playing
	
	int attempts = 1; //keeps track of number of attempts
	
	Level level; //holds the level the user is currently player (will be initialized as soon as a level is selected)
	
	double progress; //keep track of user's progress percentage 
	
	int [] highScores = new int[3]; //hold current high score for each level
	
	Stage winStg = new Stage(); //stage which displays win menu if user wins
	
	Stage pauseStg = new Stage(); //stage which displays pause menu
	
	Stage howToPlayStg = new Stage(); //stage which displays game instructions
	
	ImageView attempt; //"attempt" text displayed when user starts a level
	
	Font geoDashFont;
	
	private AnimationTimer game; //animation timer

	
	
	/**
	 * Entry point to program
	 * @param args
	 */
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	

	/**
	 * Description: This method is used to initialize variables before start() is called.
	 */
	public void init() {
		
		loadHighScore(); //load current high scores
		
		try {
            geoDashFont = Font.loadFont(getClass().getResourceAsStream("/fonts/geoDashFont.ttf"), 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	
	
	/**
	 * Description: This method is the start of the application, called after init(). The program's visual setup is done here.
	 * @param primaryStage -> main window of the application which opens on launch
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Primary Stage setup
		primaryStage.setTitle("Cube Jumper");
		primaryStage.getIcons().add(new Image("/images/icon.png"));
		
		//Master Canvas
		Canvas canvas = new Canvas(1760, 990);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		
		
		//Animation Timer
		game = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
			
				update(); //update element positions
				
				checkCollision(); //check if player has collied with any game elements
				
				draw(gc); //draw game elements
					
			}
		};
		
		
		
		
		//Start Scene
		//Images
		ImageView startImg = createNewImageView(new Image("/images/Start.png"), 1760, 990);
		
		//Buttons
		Button startBtn = createNewButton(new Image("/images/startButton.png"), 250, 730, 350);
        Button statsBtn = createNewButton(new Image("/images/stats.png"), 160, 500, 400);
		Button instructionsBtn = createNewButton(new Image("/images/instructions.png"), 160, 1045, 400);
 	
		Scene startScn = createNewScene(1760, 990, startImg, startBtn, statsBtn, instructionsBtn);
		
		
		
		//How to Play Stage and Scene
		//Stage
		Stage howToPlayStg = new Stage(); 
		howToPlayStg.setTitle("How to Play"); 
		Canvas howToPlayCvs = new Canvas(800, 600);
	  
		//Icon 
		howToPlayStg.getIcons().add(new Image("/images/instructions.png"));
		
		//Scene
		Scene instructionsScn = createNewScene(800, 600, howToPlayCvs, createNewImageView(new Image("/images/howToPlay.png"), 800, 600));
		
		
		
		//Stats Stage and Scene
		//Stage
		Stage statsStg = new Stage();
		statsStg.setTitle("Stats");
		Canvas statsCvs = new Canvas(800, 600);
		
		//Icon
		statsStg.getIcons().add(new Image("/images/stats.png"));
		
		//Images
		ImageView statsImg = createNewImageView(new Image("/images/statsMenu.png"), 800, 600);
		
		//Button
		Button clearBtn = createNewButton(new Image("/images/clearHistory.png"), 96, 230, 440);
		
		//Text
		Text level1Score = new Text();
		level1Score.setFill(Color.WHITE);
		level1Score.setFont(Font.font("Pusab", 60));
		level1Score.setX(460);
		level1Score.setY(220);
		
		Text level2Score = new Text();
		level2Score.setFill(Color.WHITE);
		level2Score.setFont(Font.font("Pusab", 60));
		level2Score.setX(460);
		level2Score.setY(320);
		
		Text level3Score = new Text();
		level3Score.setFill(Color.WHITE);
		level3Score.setFont(Font.font("Pusab", 60));
		level3Score.setX(460);
		level3Score.setY(420);
		
		
		//Scene
		Scene statsScn = createNewScene(800, 600, statsCvs, statsImg, clearBtn, level1Score, level2Score, level3Score);
		statsStg.setScene(statsScn);
		
		
		
		//Level 1 Select Scene
		//Images 
		ImageView levelSelect1Img = createNewImageView(new Image("/images/levelSelect1.png"), 1760, 990);
		
		//Buttons
		Button levelStart1Btn = createNewButton(new Image("/images/level1Start.png"), 300, 356, 170);
		Button back1Btn = createNewButton(new Image("/images/backButton.png"), 80, 50, 40);
		Button rightLevel1Btn = createNewButton(new Image("/images/rightButton.png"), 140, 1500, 400);
		Button leftLevel1Btn = createNewButton(new Image("/images/leftButton.png"), 140, 90, 400);
		
		//Other Nodes
		Rectangle progressBar1 = new Rectangle(327, 638, 0, 25);
		progressBar1.setFill(Color.web("f499d2ff"));
		
		Text percent1 = new Text();
		percent1.setFill(Color.WHITE);
		percent1.setFont(Font.font("Pusab", 96));
		percent1.setX(785);
		percent1.setY(596);
		
		Scene levelSelect1Scn = createNewScene(1760, 990, levelSelect1Img, levelStart1Btn, back1Btn, rightLevel1Btn, leftLevel1Btn, progressBar1, percent1);
		
		
		
		//Level 2 Select Scene
		//Images
		ImageView levelSelect2Btn = createNewImageView(new Image("/images/levelSelect2.png"), 1760, 990);
		
		//Buttons
		Button levelStart2Btn = createNewButton(new Image("/images/level2Start.png"), 300, 356, 170);
		Button back2Btn = createNewButton(new Image("/images/backButton.png"), 80, 50, 40);
		Button rightLevel2Btn = createNewButton(new Image("/images/rightButton.png"), 140, 1500, 400);
		Button leftLevel2Btn = createNewButton(new Image("/images/leftButton.png"), 140, 90, 400);
		
		//Other Nodes
		Rectangle progressBar2 = new Rectangle(327, 638, 0, 25);
		progressBar2.setFill(Color.web("f499d2ff"));
		
		Text percent2 = new Text();
		percent2.setFill(Color.WHITE);
		percent2.setFont(Font.font("Pusab", 96));
		percent2.setX(785);
		percent2.setY(596);
		
		Scene levelSelect2Scn = createNewScene(1760, 990, levelSelect2Btn, back2Btn, rightLevel2Btn, leftLevel2Btn, levelStart2Btn, progressBar2, percent2);
		
		
		
		//Level 3 Select Scene
		//Images
		ImageView levelSelect3Btn = createNewImageView(new Image("/images/levelSelect3.png"), 1760, 990);
		
		//Buttons
		Button levelStart3Btn = createNewButton(new Image("/images/level3Start.png"), 300, 356, 170);
		Button back3Btn = createNewButton(new Image("/images/backButton.png"), 80, 50, 40);
		Button rightLevel3Btn = createNewButton(new Image("/images/rightButton.png"), 140, 1500, 400);
		Button leftLevel3Btn = createNewButton(new Image("/images/leftButton.png"), 140, 90, 400);
		
		//Other Nodes
		Rectangle progressBar3 = new Rectangle(327, 638, 0, 25);
		progressBar3.setFill(Color.web("f499d2ff"));
		
		Text percent3 = new Text();
		percent3.setFill(Color.WHITE);
		percent3.setFont(Font.font("Pusab", 96));
		percent3.setX(785);
		percent3.setY(596);
		
		Scene levelSelect3Scn = createNewScene(1760, 990, levelSelect3Btn, back3Btn, rightLevel3Btn, leftLevel3Btn, levelStart3Btn, progressBar3, percent3);
		
		
		
		//Game Scene
		//Images
		ImageView progressImg = createNewImageView(new Image("/images/progress.png"), 800, 80);
		progressImg.setX(430);
		progressImg.setY(0);
		
		//Attempts
		attempt = createNewImageView(new Image("/images/attempt.png"), 572, 92);
		attempt.setX(500);
		attempt.setY(300);
		
		//Buttons
		Button pauseBtn = createNewButton(new Image("/images/pause.png"), 100, 1580, 50);

		Scene gameScn = createNewScene(1760, 990, canvas, pauseBtn, attempt, progressImg);
		
		
		
		//Pause Menu Stage and Scene
		//Stage
		Stage pauseStg = new Stage();
		pauseStg.initStyle(StageStyle.UNDECORATED); //gets rid of top boarder of window, preventing user from easily exiting
		Canvas pauseCvs = new Canvas(800, 600);
		pauseStg.setAlwaysOnTop(true);
		pauseStg.toFront();
		
		//Icon
		pauseStg.getIcons().add(new Image("/images/pauseIcon.png"));
		
		//Button
		Button playBtn = createNewButton(new Image("/images/play.png"), 200, 300, 290);
		Button restart1Btn = createNewButton(new Image("/images/restart.png"), 150, 550, 315);
		Button backToLevelSelect1Btn = createNewButton(new Image("/images/backToLevelSelect.png"), 150, 100, 315);
		
		//Other Nodes
		Rectangle progressBarPause = new Rectangle(137, 193, 525, 44);
		progressBarPause.setFill(Color.web("f499d2ff"));
		Text percentPause = new Text();
		
		//Scene
		Scene pauseScn = createNewScene(800, 600, pauseCvs, createNewImageView(new Image("/images/pauseMenu.png"), 800, 600), playBtn, restart1Btn, backToLevelSelect1Btn, progressBarPause, percentPause);
		pauseStg.setScene(pauseScn);
		
		
		
		//Win Menu Stage and Scene
		//Stage
		winStg.initStyle(StageStyle.UNDECORATED); //gets rid of top boarder of window, preventing user from easily exiting
		Canvas winCvs = new Canvas(800, 600);
		winStg.setAlwaysOnTop(true);
		winStg.toFront();
		
		//Icon
		winStg.getIcons().add(new Image("/images/win.png"));
		
		//Image
		ImageView winImg = createNewImageView(new Image("/images/winMenu.png"), 800, 600);
		
		//Buttons
		Button restart2Btn = createNewButton(new Image("/images/restart.png"), 150, 500, 350);
		Button backToLevelSelect2Btn = createNewButton(new Image("/images/backToLevelSelect.png"), 150, 150, 350);
		
		//Nodes
		Text attemptTxt = new Text();
		attemptTxt.setFill(Color.GOLD);
		attemptTxt.setFont(Font.font("Pusab", 80));
		attemptTxt.setText(attempts + "");
		attemptTxt.setX(480);
		attemptTxt.setY(263);
		
		Scene winScn = createNewScene(800, 600, winCvs, winImg, restart2Btn, backToLevelSelect2Btn, attemptTxt);
		winStg.setScene(winScn);
		
		
		
		
		//Button Events
		
		//Start Button
	   startBtn.setOnAction (e -> {
		   
		   //Check and Calculate current high score
		   progressBar1.setWidth(((highScores[0]*1104)/100));
		   percent1.setText((int)highScores[0] + "%");
		   
		   primaryStage.setScene(levelSelect1Scn);
        	
        });
	   
	   
	   
		//Instructions Button 
	   instructionsBtn.setOnAction (e -> { 
	  
		   howToPlayStg.setScene(instructionsScn); 
		   howToPlayStg.show();
		   
	   });
	   
	   
	   
	   //Stats Button
	   statsBtn.setOnAction (e -> {
		   
		   //Get Current High Scores
		   level1Score.setText(highScores[0] + "%");
		   level2Score.setText(highScores[1] + "%");
		   level3Score.setText(highScores[2] + "%");
		 
		   statsStg.show();
		   
	   });
	   
	   
	   
	   //Clear History Button
	   clearBtn.setOnAction (e -> { 
		 
		   //Set all high scores to zero
		   highScores[0] = 0;
		   highScores[1] = 0;
		   highScores[2] = 0;
		   
		   statsStg.close();
		   
	   });
	   
		
	   
		//Level 1 Start Button
	   levelStart1Btn.setOnAction (e -> {
		   		   
		   attempts = 1; //reset attempt counter 
		   currentLevel = 1; //set current level
		   level = loadLevel(new File("src/levels/level1.txt")); //load level and initialize global level as level 1

		   primaryStage.setScene(gameScn);
		   level.playMusic(0);
		   game.start(); 
		   
	   });
		
	   
	   
		//Level 2 Start Button
	   levelStart2Btn.setOnAction (e -> {
		   
		   attempts = 1; //reset attempt counter
		   currentLevel = 2; //set current level
		   level = loadLevel(new File("src/levels/level2.txt")); //load level and initialize global level as level 2
		   
		   primaryStage.setScene(gameScn);
		   level.playMusic(0);
		   game.start();  
		   
        });
		
	   
	   
		//Level 3 Start Button
		levelStart3Btn.setOnAction (e -> {
			
			attempts = 1; //reset attempt counter
			currentLevel = 3; //set current level
			level = loadLevel(new File("src/levels/level3.txt")); //load level and initialize global level as level 3
			
			primaryStage.setScene(gameScn);
			level.playMusic(80000); //start later in song, as music is delayed in audio file
			game.start();
			
        });
		
		
		
		//Pause Button
		pauseBtn.setOnAction (e -> {
			
			//Pause music
			try {
				
				level.stopMusic();
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				
				e1.printStackTrace();
			}
			
			//Progress Bar
			double width = ((progress*525)/100); //calculate progress bar length
			progressBarPause.setWidth(width); //display it
			
			//Progress Percentage
			progress = 100; //initially assume 100 to prevent going over 100
			
			if(level.getPlayer().getxPosition() <= 490 && level.progress() <= 100) {
				
				progress = level.progress();
				
			}
			
			//display it 
			percentPause.setText((int)progress + "%");
			percentPause.setFill(Color.WHITE);
			percentPause.setFont(Font.font("Pusab", 60));
			percentPause.setX(360);
			percentPause.setY(237);
			
			pauseStg.show();
			game.stop();
			
		});
		
		
		
		//Play Button
		playBtn.setOnAction (e -> {
			
			level.playMusic(-1); //unpause music
			
			pauseStg.close();
			 game.start();
			 
		});
		
		
		
		//Restart Buttons
		restart1Btn.setOnAction (e -> {
			
			pauseStg.close();
			attempts++;
			restart();
			
		});
		
		restart2Btn.setOnAction (e -> {
			
			winStg.close();
			attempts = 1;
			restart();
			
		});
		
		
		
		//Back to Level Select Buttons
		backToLevelSelect1Btn.setOnAction (e -> {
			
			pauseStg.close();
			
			//Stop music
			try {
				
				level.stopMusic();
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				
				e1.printStackTrace();
			}
			
			if(currentLevel == 1) {
				
				//Check and Calculate current high score
				progressBar1.setWidth(((highScores[0]*1104)/100));
				percent1.setText((int)highScores[0] + "%");
				
				primaryStage.setScene(levelSelect1Scn);
				
			}
			else if(currentLevel == 2) {
				
				//Check and Calculate current high score
				progressBar2.setWidth(((highScores[1]*1104)/100));
				percent2.setText((int)highScores[1] + "%");
				
				primaryStage.setScene(levelSelect2Scn);
				
			}
			else {
				
				//Check and Calculate current high score
				progressBar3.setWidth(((highScores[2]*1104)/100));
				percent3.setText((int)highScores[2] + "%");
				
				primaryStage.setScene(levelSelect3Scn);
				
			}
			
		});
		
		backToLevelSelect2Btn.setOnAction (e -> {
			
			winStg.close();
			
			//Stop music
			try {
				
				level.stopMusic();
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				
				e1.printStackTrace();
			}
			
			if(currentLevel == 1) {
				
				//Check and Calculate current high score
				progressBar1.setWidth(((highScores[0]*1104)/100));
				percent1.setText((int)highScores[0] + "%");
				
				primaryStage.setScene(levelSelect1Scn);
				
			}
			else if(currentLevel == 2) {
				
				//Check and Calculate current high score
				progressBar2.setWidth(((highScores[1]*1104)/100));
				percent2.setText((int)highScores[1] + "%");
				
				primaryStage.setScene(levelSelect2Scn);
				
			}
			else {
				
				//Check and Calculate current high score
				progressBar3.setWidth(((highScores[2]*1104)/100));
				percent3.setText((int)highScores[2] + "%");
				
				primaryStage.setScene(levelSelect3Scn);
			}
			
		});
		
		
		
		//Back Buttons
		back1Btn.setOnAction (e -> { primaryStage.setScene(startScn); });
		back2Btn.setOnAction (e -> { primaryStage.setScene(startScn); });
		back3Btn.setOnAction (e -> { primaryStage.setScene(startScn); });
		
		
		
		//Right Buttons on Level Select
		rightLevel1Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar2.setWidth(((highScores[1]*1104)/100));
			percent2.setText((int)highScores[1] + "%");
			
			primaryStage.setScene(levelSelect2Scn); 
			
        });
		
		rightLevel2Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar3.setWidth(((highScores[2]*1104)/100));
			percent3.setText((int)highScores[2] + "%");
			
        	primaryStage.setScene(levelSelect3Scn); 
        	
        });
		
		rightLevel3Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar1.setWidth(((highScores[0]*1104)/100));
			percent1.setText((int)highScores[0] + "%");
			
        	primaryStage.setScene(levelSelect1Scn);
        	
        });
		
		
		
		//Left Button on Level Select
		leftLevel1Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar3.setWidth(((highScores[2]*1104)/100));
			percent3.setText((int)highScores[2] + "%");
			
        	primaryStage.setScene(levelSelect3Scn);
        	
        });
		
		leftLevel2Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar1.setWidth(((highScores[0]*1104)/100));
			percent1.setText((int)highScores[0] + "%");
			
        	primaryStage.setScene(levelSelect1Scn); 
        	
        });
		
		leftLevel3Btn.setOnAction (e -> {
			
			//Check and Calculate current high score
			progressBar2.setWidth(((highScores[1]*1104)/100));
			percent2.setText((int)highScores[1] + "%");
			
        	primaryStage.setScene(levelSelect2Scn);
        	
        });
		 
		
		

		primaryStage.setScene(startScn);
		primaryStage.show();
		
		
		
		
		//Key Events
		
		//Jump
		gameScn.setOnKeyPressed(event -> {
			
		   level.getPlayer().jump();
		   
		});
		
		gameScn.setOnKeyReleased(event -> {
			
			if (level.getPlayer().getPlayerMode().equals(PlayerMode.WAVE)) {
				
				level.getPlayer().setVelocity(-8);
				
			}
		});
		
	}
	
	
	
	/**
	 * Description: This method creates and returns a button using the details given by the parameters.
	 * @param image -> image that will be set as graphic for the button
	 * @param height -> the value that the height of the button will be set to
	 * @param x -> the x coordinate of the button
	 * @param y -> the y coordinate of the button
	 * @return the new button
	 */
	public Button createNewButton(Image image, double height, double x, double y) {
		
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setPreserveRatio(true);
		
		Button button = new Button();
		button.setGraphic(imageView);
		button.setStyle("-fx-background-color: transparent;"); //gets rid of white boarder around button
		button.setText(null); //get rid of "..." next to button
		button.setLayoutX(x);
		button.setLayoutY(y);
		
		button.setFocusTraversable(false); //Prevents the button from being triggered by keyboard
		
		return button;
		
	}
	
	
	
	/**
	 * Description: This method creates a new ImageView with the details given in the parameters.
	 * @param image -> the image
	 * @param height -> the height which the new ImageView will be set to
	 * @param width -> the width which the new ImageView will be set to
	 * @return The new ImageView
	 */
	public ImageView createNewImageView(Image image, double width, double height) {
		
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		imageView.setX(0);
		imageView.setY(0);
		
		return imageView;
	}
	
	
	
	/**
	 * Description: This method creates and returns a scene using the details given by the parameters.
	 * @param width -> the width of the new scene
	 * @param height -> the height of the new scene
	 * @param nodes -> nodes which will be added to the new scene
	 * @return the new scene
	 */
	public Scene createNewScene(double width, double height, Node... nodes) {
		
		Pane layout = new Pane();
		layout.getChildren().addAll(nodes);
		Scene scene = new Scene(layout, width, height);
		
		return scene;
		
	}
	
	
	
	/**
	 * Description: This methods makes and returns a level by scanning in the data given by the file given in the parameters.
	 * @param file -> the file which holds the level's data
	 * @return the new level
	 */
	public Level loadLevel(File file){
		
		Level level = new Level();
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		ArrayList<Portal> portals = new ArrayList<Portal>();
		int levelSpeed = 14; //speed of all level elements except player, creating the illusion that the player is moving
		
		try {
			
			Scanner fsc = new Scanner(file);
			
			double floorY = 0; //y position of floor, which will be given to player class for isOnFloor() calculation 
			
			//Load Music
			try {
				
				level.loadMusic(fsc.nextLine());
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				
				e.printStackTrace();
				
			}
			
			while(fsc.hasNextLine()) {
				
				String [] obstacle = fsc.nextLine().split(",");
				
				String type = obstacle[0];
				double x = Double.parseDouble(obstacle[1]);
				double y = Double.parseDouble(obstacle[2]);
				double width = Double.parseDouble(obstacle[3]);
				double height = Double.parseDouble(obstacle[4]);
				int r = Integer.parseInt(obstacle[5]);
				int g = Integer.parseInt(obstacle[6]);
				int b = Integer.parseInt(obstacle[7]);
				
				Color colour = Color.rgb(r, g, b);
				
				if(type.equals("Background")) {
					
					level.setBackground(new Background(colour, width, levelSpeed));
					
				}
				else if(type.equals("Floor")) {
					
					double [] points = {x, y, x + width, y, x + width, y + height, x, y + height};
					obstacles.add(new Cube(points, levelSpeed, colour, type));
					
					floorY = y;
					
				}
				else if(type.equals("Portal")) {
					
					String playerModeStr = obstacle[8];
					PlayerMode playerMode;
					
					if(playerModeStr.equals("Wave")) {
						
						playerMode = PlayerMode.WAVE;
						
					}
					else if(playerModeStr.equals("Ship")) {
						
						playerMode = PlayerMode.SHIP;
						
					}
					else if(playerModeStr.equals("Ball")) {
						
						playerMode = PlayerMode.BALL;
						
					}
					else{
						
						playerMode = PlayerMode.CUBE;
						
					}
					
					portals.add(new Portal(x, y, width, height, levelSpeed, colour, playerMode));
					
				}
				else if(type.equals("Player")) {
					
					level.setPlayer(new Player(colour, x, y, PlayerMode.CUBE, floorY));
					
				}
				else if(type.equals("Spike")) {
					
					int rotation = Integer.parseInt(obstacle[8]);
					double [] points;
					
					if(rotation == 90) {
						
						points = new double [] {x, y, x + height, y + width/2, x, y + width};
						
					}
					else if(rotation == 180) {
						
						points = new double [] {x, y, x + width/2, y + height, x + width, y};
						
					}
					else if(rotation == 270) {
						
						points = new double [] {x + height, y, x, y + width/2, x + height, y + width};
						
					}
					else{
						
						points = new double [] {x, y + height, x + width/2, y, x + width, y + height};
						
					}
					
					obstacles.add(new Spike(points, levelSpeed, colour, type));
					
				}
				else if(type.equals("Cube")) {
					
					double [] points = {x, y, x + width, y, x + width, y + height, x, y + height};
					obstacles.add(new Cube(points, levelSpeed, colour, type));
					
				}
	
			}
			
			level.setPortals(portals);
			level.setObstacles(obstacles);
			
			fsc.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("FILE NOT FOUND");
			
		}
		
		return level;
		
	}
	
	
	
	/**
	 * Description: This method checks if the player has collided with any game elements. If player has collided, will be handled differently based on element type and/or situation.
	 */
	public void checkCollision() {
		
		Player player = level.getPlayer();
		ArrayList<Obstacle> obstacles = level.getObstacles();
		ArrayList<Portal> portals = level.getPortals();
		
		Bounds playBounds = player.getBoundsInParent();
		
		//check if player has collided with portal
		for(Portal p: portals) {
			
			Shape overlap = Shape.intersect(player, p);
			
			if(!overlap.getBoundsInLocal().isEmpty()) {
				
				player.setPlayerMode(p.getPlayerMode());
				break;
				
			}
			
		}
		
		//check if player has collied with obstacle
		for(Obstacle o: obstacles) {
			
			Bounds obsBounds = o.getBoundsInParent();
	
			if(playBounds.intersects(obsBounds)) {
				
				if(o.getType().equalsIgnoreCase("Floor")) {
					
					//nothing happens
					
				}
				else if(o instanceof Cube) {
					
					//add buffer in case player goes below platform
					double buffer = Math.max(50, Math.abs(player.getVelocity()));
					
					if((player.getVelocity() <= 0) &&(playBounds.getMaxY() <= obsBounds.getMinY() + buffer)) {
						
						player.setIsOnPlatform(true);
						player.setVelocity(0);
						player.setLayoutY(obsBounds.getMinY() - player.getSize());
						player.setyPosition(obsBounds.getMinY() - player.getSize());
						
						playBounds = player.getBoundsInParent();
						
					}
					else{
						
						lose();
						break;
						
					}
					
				}
				else if(o instanceof Spike) {
					
					Shape overlap = Shape.intersect(player, o);
					
					if(!overlap.getBoundsInLocal().isEmpty()) {
						
						lose();
						break;
						
					}
					
				}
				
			}
			
		}
		
		//Make sure player cannot leave screen
		if(playBounds.getMaxY() < - 100 || playBounds.getMaxY() > 1090) {
			
			lose();
			
		}
	}
	
	
	
	/**
	 * Description: This method restarts the game. It stops the animation, resets the game graphics and then starts the animation timer.
	 */
	public void restart() {
		
		
		game.stop(); //stop animation timer
		
		//Stop music
		try {
			
			level.stopMusic();
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			
			e.printStackTrace();
		}
		
		
		//Reset Game
		if(currentLevel == 1) {
			
			level = loadLevel(new File("src/levels/level1.txt"));
			
		}
		else if(currentLevel == 2) {

			level = loadLevel(new File("src/levels/level2.txt"));
			
		}
		else {
			
			level = loadLevel(new File("src/levels/level3.txt"));
			
		}
		
		//Restart music
		if(currentLevel != 3) {
			
			level.playMusic(0); 
			
		}
		else {
			
			level.playMusic(80000); //start later in song, as music is delayed in audio file
			
		}
		
		game.start(); //restart animation timer
		
	}
	
	
	
	/**
	 * Description: This method handles the death of the player and restarts the level
	 * @param level -> the level the user is playing
	 */
	public void lose() {
		
		level.getPlayer().die();
		game.stop(); //stop animation timer
		attempts++;
		
		restart();
		
	}
	
	
	
	/**
	 * Description: This method checks if the player has won the level
	 * @return -> true if player won, and false if not
	 */
	public boolean checkWin() {
		
		if(level.getPlayer().getxPosition() > 1760) {
			
				return true;
		}

		return false;
	}
	
	
	
	/**
	 * Description: This method checks if the user has passed a current high score
	 * @return -> true if the user has passed high score and false if not
	 */
	public boolean checkHighScore() {

		if(progress > highScores[0] && currentLevel == 1) {
			
			highScores[0] = (int)progress;
			return true;
			
		}
		else if(progress > highScores[1]&& currentLevel == 2) {
			
			highScores[1] = (int)progress;
			return true;
			
		}
		else if(progress > highScores[2] && currentLevel == 3) {
			
			highScores[2] = (int)progress;
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	/**
	 * Description: This method saves the new highest scores in a text file.
	 */
	public void saveHighScore() {
	
		try {
			
			PrintStream fps = new PrintStream(new File("src/highScores.txt"));
			
			fps.println(Arrays.toString(highScores));
			
			fps.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ERROR!! FILE DOES NOT EXIST!!");
			
		}
		
	}
	
	
	
	/**
	 * Description: This method loads current highest scores from a text file.
	 */
	public void loadHighScore() {
		
		try {
			
			Scanner fsc = new Scanner(new File("src/highScores.txt"));
			
			String scoresStrg = fsc.nextLine();
			String [] scores = scoresStrg.substring(1, scoresStrg.length() - 1).split(", ");
			
			highScores[0] = Integer.parseInt(scores[0]);
			highScores[1] = Integer.parseInt(scores[1]);
			highScores[2] = Integer.parseInt(scores[2]);
			
			fsc.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ERROR!! FILE DOES NOT EXIST!!");
			
		}
		
	}
	
	
	
	/**
	 * Description: Draws the game graphics onto the GraphicsContest from the canvas.
	 * @param gc -> The GraphicsContext from the canvas you wish to draw on.
	 */
	public void draw(GraphicsContext gc) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
			level.draw(gc); //draw level
			
			
			//Progress Percentage
			progress = 100;
			if(level.getPlayer().getxPosition() <= 490 && level.progress() <= 100) {
				
				progress = level.progress();
				
			}
			
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font("Pusab", 50));
			gc.fillText((int)progress + "%", 1050, 58);
			
			
			//Progress bar
			double width = 545;
			if(level.getPlayer().getxPosition() <= 490 && level.progress() <= 100) {
				
				width = (progress*545)/100;
				
			}
			
			gc.setFill(Color.web("f499d2ff"));
			gc.fillRect(468, 32, width, 15);
			
			
			//Check high score
			checkHighScore();
			
			
			//Check if player Won
			if(checkWin()) {
				
				game.stop();
				winStg.show();
				
			}
			
			//Display attempt counter
			if(level.getBackground().getX() > -1760) {
				
				attempt.setX(level.getBackground().getX() + 500);
				
				gc.setFill(Color.WHITE);
				gc.setFont(Font.font("Pusab", 100));
				gc.fillText(attempts + "", level.getBackground().getX() + 1140, 384);
				
			}
				
				
			}
			
		});
		
	}
	
	
	
	/**
	 * Description: This method updates and check the position of the player and other elements. It is called before the draw method in the animation timer.
	 */
	public void update() {
		
		level.update();
		
	}
	
	
	
	/**
	 * Description: This method is automatically called whenever primaryStage is closed, or Platform.exit(); is used. It stops the animation timer, music and saves the high scores.
	 */
	public void stop() {
		
		saveHighScore(); //save current high scores
		
		//Stop Music
		if(level != null && level.getIsMusicPlaying()) {
			try {
				
				level.stopMusic();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		}
		
		game.stop(); //stop animation timer()
		
	}
	
}
