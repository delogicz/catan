package jsonstructures;

/**
 * This class handles the event, when someone won the game 
 * @author Felixi, Georg
 *
 */
public class SpielBeendetDaten {

	String Nachricht;
	int Sieger;
	
	/**
	 * Creates a SpielBeendetDaten object
	 * @param winText
	 * @param playerID
	 */
	public SpielBeendetDaten(String winText, int playerID) {
		Nachricht = winText;
		Sieger = playerID;
	}

	/**
	 * Getter for Nachricht
	 * @return
	 */
	public String getNachricht() {
		return Nachricht;
	}
	
	/**
	 * Setter for Nachricht
	 * @param nachricht
	 */
	public void setNachricht(String nachricht) {
		Nachricht = nachricht;
	}
	
	/**
	 * Getter for Sieger
	 * @return
	 */
	public int getSieger() {
		return Sieger;
	}
	
	/**
	 * Setter for Sieger
	 * @param sieger
	 */
	public void setSieger(int sieger) {
		Sieger = sieger;
	}
	
}
