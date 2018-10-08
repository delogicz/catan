package jsonstructures;

/**
 * Wrapper for MonopolServerDaten
 * @author Felixi, Georg
 *
 */
public class MonopolServerNachricht {
	MonopolServerDaten Monopol;
	
	/**
	 * Constructor
	 * @param rohstoff
	 * @param player
	 */
	public MonopolServerNachricht(String rohstoff, int player) {
		Monopol = new MonopolServerDaten(rohstoff, player);
	}
	
	/**
	 * Getter for MonopolServerDaten
	 * @return
	 */
	public MonopolServerDaten getMonopol() {
		return Monopol;
	}
	
}
