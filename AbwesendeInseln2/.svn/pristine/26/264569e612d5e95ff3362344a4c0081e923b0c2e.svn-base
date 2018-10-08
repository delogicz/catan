package model;

import game.Settings;
import javafx.scene.paint.Color;

/**
 * Determines available colors for players 
 * @author Stefanie Kloss
 */
public enum PlayerColor {
	
	RED (Color.SALMON, "knight_red.png" ,"road_red.png"), 
	ORANGE (Color.ORANGE, "knight_yellow.png","road_yellow.png"), 
	WHITE (Color.WHITE, "knight_white.png","road_white.png"), 
	BLUE (Color.DEEPSKYBLUE, "knight_blue.png","road_blue.png");

	private Color color;
	private String knightImageURL;
	private String roadImageURL;
	
	/**
	 * Constructor
	 * @param color The actual color (javafx.scene.paint.Color)
	 * @param knightFileName The filename of the image of the largest army in the suiting color
	 * @param roadFileName The filename of the image of the longest road in the suiting color
	 */
	private PlayerColor(Color color, String knightFileName, String roadFileName){
		
		final String PATH = "/img/";
		this.color = color;
		this.knightImageURL = PATH + knightFileName;
		this.roadImageURL = PATH + roadFileName;
	}
	
	/**
	 * Returns the String needed for json objects
	 * @return the PlayerColor as a string
	 */
	public String toServerString() {
		switch(this){
		case RED: 		return Settings.ROT;
		case BLUE: 		return Settings.BLAU;
		case ORANGE: 	return Settings.ORANGE;
		case WHITE:  	return Settings.WEISS;
		}
		return null;
	}

	/**
	 * Getter for the Color
	 * @return the color
	 */
	public Color getColor(){
		return this.color;
	}

	
	/**
	 * Getter for knightImageURL
	 * @return the knightImageURL
	 */
	public String getKnightImageURL() {
		return knightImageURL;
	}

	/**
	 * Getter for RoadImageURL
	 * @return the roadImageURL
	 */
	public String getRoadImageURL() {
		return roadImageURL;
	}
	
}
