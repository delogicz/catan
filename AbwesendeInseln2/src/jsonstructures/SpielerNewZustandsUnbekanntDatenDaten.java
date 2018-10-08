package jsonstructures;
/**
 * Object to be converted to JSON holding status data of a player without their hand
 * @author georgschwab, Felix
 *
 */
public class SpielerNewZustandsUnbekanntDatenDaten {
	SpielerNewZustandsUnbekanntDaten Spieler;
	

	/**
	 * Creates a SpielerZustandsDaten object
	 * @param spieler		SpielerZustandsDaten
	 */
	public SpielerNewZustandsUnbekanntDatenDaten(SpielerNewZustandsUnbekanntDaten spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for SpielerZustandsDaten
	 * @return
	 */
	public SpielerNewZustandsUnbekanntDaten getSpieler() {
		return Spieler;
	}
}
