package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;

/** 
 * Data of the ErfindungDaten send by the server
 * @author Felixi, Georg
 *
 */
public class ErfindungServerDaten {
	
	private int Spieler;
	private RohstoffDaten Rohstoffe;
	
	/**
	 * Constructor
	 * @param spieler
	 * @param rohstoffe
	 */
	public ErfindungServerDaten(int spieler, ArrayList<ResourceType> rohstoffe) {
		Spieler = spieler;
		Rohstoffe = new RohstoffDaten(rohstoffe);
	}
	
	/**
	 * Getter for Spieler
	 * @return
	 */
	public int getSpieler() {
		return Spieler;
	}
	/**
	 * Getter for Rohstoffe
	 * @return
	 */
	public RohstoffDaten getRohstoffe() {
		return Rohstoffe;
	}
	
	
}
