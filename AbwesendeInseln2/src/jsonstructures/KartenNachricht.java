package jsonstructures;

import model.Island;

/**
 * Wrapper for KartenDaten
 * @author Felixi, Georg
 *
 */
public class KartenNachricht {
	
	private KartenDaten Karte;
	/**
	 * Constructor for client
	 * @param kart
	 */
	public KartenNachricht(KartenDaten kart){
		Karte = kart;
	}
	/**
	 * Constructor for server
	 * @param i
	 * @param server
	 */
	public KartenNachricht(Island i, boolean server) {
		Karte = new KartenDaten(i, server);
	}

	/**
	 * Getter for KartenDaten
	 * @return
	 */
	public KartenDaten getKartenDaten(){
		return Karte;
	}
}
