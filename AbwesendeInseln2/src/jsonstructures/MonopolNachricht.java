package jsonstructures;

/**
 * Wrapper for MonopolDaten
 * @author Felixi, Georg
 *
 */
public class MonopolNachricht {
	MonopolDaten Monopol;

	/**
	 * Constructor
	 * @param monopol
	 */
	public MonopolNachricht(String monopol) {
		Monopol = new MonopolDaten(monopol);
	}
	/**
	 * Getter for Monopol
	 * @return
	 */
	public MonopolDaten getMonopol() {
		return Monopol;
	}
	/**
	 * Getter for Rohstoff
	 * @return
	 */
	public String getRohstoff(){
		return Monopol.getRohstoff();
	}
	
}
