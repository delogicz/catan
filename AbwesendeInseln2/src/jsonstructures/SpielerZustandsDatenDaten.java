package jsonstructures;

/**
 * This class is the wrapper of the SpielerZustandsDaten class
 * @author Felixi, Georg
 *
 */
public class SpielerZustandsDatenDaten {
	private SpielerZustandsDaten Spieler;

	/**
	 * Creates a SpielerZustandsDaten object
	 * @param spieler		SpielerZustandsDaten
	 */
	public SpielerZustandsDatenDaten(SpielerZustandsDaten spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for SpielerZustandsDaten
	 * @return
	 */
	public SpielerZustandsDaten getSpieler() {
		return Spieler;
	}
	
	/**
	 * Setter for SpielerZustandsDaten
	 * @param spieler
	 */
	public void setSpieler(SpielerZustandsDaten spieler) {
		Spieler = spieler;
	}
}
