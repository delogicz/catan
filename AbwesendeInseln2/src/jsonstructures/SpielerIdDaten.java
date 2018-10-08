package jsonstructures;

/**
 * Data of players id
 * @author Felixi, Georg
 *
 */
public class SpielerIdDaten {
	int Spieler;
	
	/**
	 * Constructor
	 * @param spielerID
	 */
	public SpielerIdDaten(int spielerID){
		Spieler = spielerID;
	}

	/**
	 * Getter for Spieler
	 * @return
	 */
	public int getSpieler(){
		return Spieler;
	}
}
