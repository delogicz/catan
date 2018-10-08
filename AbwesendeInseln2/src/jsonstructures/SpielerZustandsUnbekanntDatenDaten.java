package jsonstructures;
/**
 * Object to be converted to JSON holding status data of a player without their hand
 * @author georgschwab, Felix
 *
 */
public class SpielerZustandsUnbekanntDatenDaten {
	SpielerZustandsUnbekanntDaten Spieler;
	

	/**
	 * Creates a SpielerZustandsDaten object
	 * @param spieler		SpielerZustandsDaten
	 */
	public SpielerZustandsUnbekanntDatenDaten(SpielerZustandsUnbekanntDaten spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for SpielerZustandsDaten
	 * @return
	 */
	public SpielerZustandsUnbekanntDaten getSpieler() {
		return Spieler;
	}
	
	/**
	 * Setter for SpielerZustandsDaten
	 * @param spieler
	 */
	public void setSpieler(SpielerZustandsUnbekanntDaten spieler) {
		Spieler = spieler;
	}
}
