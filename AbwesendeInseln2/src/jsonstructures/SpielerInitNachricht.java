package jsonstructures;

/**
 * This class is the wrapper class for the SpielerDaten class
 * @author Felixi, Georg
 *
 */
public class SpielerInitNachricht {
	private SpielerDaten Spieler;

	/**
	 * Creates a new SpielerDaten object
	 * @param name		name of the player
	 * @param farbe		color of the player
	 */
	public SpielerInitNachricht(String name, String farbe) {
		Spieler = new SpielerDaten(name, farbe);
	}
	
	/**
	 * @return the SpielerDaten
	 */
	public SpielerDaten getSpielerDaten(){
		return Spieler;
	}
}
