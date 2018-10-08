package jsonstructures;

/**
 * This class handles the event when the client has to pay resources
 * @author Felixi, Georg
 *
 */
public class KostenNachricht {
	KostenDaten Kosten;
	
	/**
	 * Creates a KostenNachricht
	 * @param kosten
	 */
	public KostenNachricht(KostenDaten kosten){
		this.Kosten = kosten;
	}
	
	/**
	 * Getter for Kosten
	 * @return
	 */
	public KostenDaten getKosten() {
		return Kosten;
	}
	/**
	 * Setter for Kosten
	 * @param kosten
	 */

	public void setKosten(KostenDaten kosten) {
		Kosten = kosten;
	}
	
}
