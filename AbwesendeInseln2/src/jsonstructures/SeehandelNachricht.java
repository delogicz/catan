package jsonstructures;


/**
 * This class is for the client when he wands to trade with a harbor
 * @author Felixi, Georg
 *
 */
public class SeehandelNachricht {
		
	ClientHandelAngebot Seehandel;

	/**
	 * Initializes the Seehandel
	 * @param angebot
	 * @param nachfrage
	 */
	public SeehandelNachricht(int[] angebot, int[]  nachfrage) {
		Seehandel = new ClientHandelAngebot(angebot,nachfrage);
	}

	/**
	 * Getter for Seehandel
	 * @return
	 */
	public ClientHandelAngebot getSeehandel() {
		return Seehandel;
	}

	/**
	 * Setter for Seehandel
	 * @param seehandel
	 */
	public void setSeehandel(ClientHandelAngebot seehandel) {
		Seehandel = seehandel;
	}
	
}