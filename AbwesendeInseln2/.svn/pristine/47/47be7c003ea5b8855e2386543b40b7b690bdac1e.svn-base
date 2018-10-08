package jsonstructures;

import model.PlayerColor;

/**
 * This class contains the data of a player to initialize 
 * @author Felixi, Georg
 *
 */
public class SpielerDaten {
	private String Name;
	private String Farbe;
	
	/**
	 * Creates a SpielerDaten object
	 * @param name			name of the player
	 * @param farbe			color of the player
	 */
	public SpielerDaten(String name, String farbe) {
		Name = name;
		Farbe = farbe;
	}

	/**
	 * Getter for Name
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Getter for Farbe
	 * @return
	 */
	public PlayerColor getFarbe() {
		switch(Farbe){
		case "Rot":		return PlayerColor.RED;
		case "Orange":	return PlayerColor.ORANGE;
		case "Blau":	return PlayerColor.BLUE;
		case "Weiss":	return PlayerColor.WHITE;
		}
		return null;
	}
}
