package jsonstructures;

/**
 * This class is the inside of a ErtragNachricht object, this is beeing send from the server to the client when the client gets resources
 * @author Felixi, Georg
 *
 */
public class ErtragsDaten {
	private int Spieler;
	private RohstoffDaten Rohstoffe;
	
	
	/**
	 * creates an ErtragsDaten object 
	 * @param spieler		player id
	 * @param rohstoffe		resources the player receives
	 */
	public ErtragsDaten(int spieler, RohstoffDaten rohstoffe) {
		Spieler = spieler;
		Rohstoffe = rohstoffe;
	}

	/**
	 * Getter for player id
	 * @return
	 */
	public int getSpieler() {
		return Spieler;
	}

	/**
	 * Setter for player id
	 * @param spieler
	 */
	public void setSpieler(int spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for RohstoffDaten
	 * @return
	 */

	public RohstoffDaten getRohstoffe() {
		return Rohstoffe;
	}

	/**
	 * Setter for RohstoffDaten
	 * @param rohstoffe
	 */

	public void setRohstoffe(RohstoffDaten rohstoffe) {
		Rohstoffe = rohstoffe;
	}
	
}