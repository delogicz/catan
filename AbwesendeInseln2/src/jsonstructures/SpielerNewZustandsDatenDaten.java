package jsonstructures;
/**
 * Object to be converted to JSON holding status data of player
 * @author georgschwab, Felix
 *
 */
public class SpielerNewZustandsDatenDaten {
	private SpielerNewZustandsDaten Spieler;

	/**
	 * Creates a SpielerZustandsDaten object
	 * @param spieler		SpielerZustandsDaten
	 */
	public SpielerNewZustandsDatenDaten(SpielerNewZustandsDaten spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for SpielerZustandsDaten
	 * @return
	 */
	public SpielerNewZustandsDaten getSpieler() {
		return Spieler;
	}
	
	/**
	 * Setter for SpielerZustandsDaten
	 * @param spieler
	 */
	public void setSpieler(SpielerNewZustandsDaten spieler) {
		Spieler = spieler;
	}
}
