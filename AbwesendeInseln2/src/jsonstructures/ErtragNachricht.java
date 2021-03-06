package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;

/**
 * This class is the wrapper for the resources the client gets from the server 
 * @author Felixi, Georg
 *
 */
public class ErtragNachricht {
	
	private ErtragsDaten Ertrag;

	/**
	 * creates a ErtragsDaten object
	 * @param spieler		player who receives the resources
	 * @param resources		resource object with all that he is getting
	 */
	public ErtragNachricht(int spieler, ArrayList<ResourceType> resources) {
		Ertrag = new ErtragsDaten(spieler,new RohstoffDaten(resources));
	}

	/**
	 * Getter for Spieler
	 * @return spieler
	 */
	public int getSpieler() {
		return Ertrag.getSpieler();
	}

	/**
	 * Getter for Rohstoffe
	 * @return Rohstoffe
	 */
	public RohstoffDaten getRohstoffe() {
		return Ertrag.getRohstoffe();
	}
	
	
}
