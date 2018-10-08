package jsonstructures;

/**
 * This class is the wrapper for the WuerfelDaten class the server sends
 * @author Felixi, Georg
 *
 */
public class WuerfelNachricht {
	private WuerfelDaten Wuerfelwurf;

	/**
	 * Creates the WuerfelDaten object
	 * @param spieler		id of the player who rolled the dice
	 * @param wurf			number of the dice roll
	 */
	public WuerfelNachricht(int spieler, int[] wurf) {
		Wuerfelwurf = new WuerfelDaten(spieler, wurf);
	}

	/**
	 * Getter for WuerfelDaten
	 * @return
	 */
	public WuerfelDaten getWuerfelwurf() {
		return Wuerfelwurf;
	}
	
	/**
	 * Setter for WuerfelDaten
	 * @param wuerfelwurf
	 */
	public void setWuerfelwurf(WuerfelDaten wuerfelwurf) {
		Wuerfelwurf = wuerfelwurf;
	}
	
	
}
