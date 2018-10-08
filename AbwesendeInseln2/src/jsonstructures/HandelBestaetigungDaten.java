package jsonstructures;

/**
 * Class for Data of a trade
 * @author Felixi, Georg
 *
 */
public class HandelBestaetigungDaten {
	
	int Q;  //not correct name yet
	int Spieler;
	int Mitspieler;
	
	/**
	 * Constructor
	 * @param spieler
	 * @param mitspieler
	 */
	public HandelBestaetigungDaten(int handelID, int spieler, int mitspieler) {
		Q = handelID;
		Spieler = spieler;
		Mitspieler = mitspieler;
	}
	/**
	 * Getter for Spieler
	 * @return
	 */
	public int getSpieler() {
		return Spieler;
	}
	/**
	 * Getter for Mitspieler
	 * @return
	 */
	public int getMitspieler() {
		return Mitspieler;
	}
	
	
}