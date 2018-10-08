package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;

/**
 * This class contains the message of the cost the client has to pay
 * @author Felixi, Georg
 *
 */
public class KostenDaten {
	int Spieler;
	RohstoffDaten Rohstoffe;
	
	/**
	 * Contructor for KostenDaten
	 * @param spieler
	 * @param rohstoffe
	 */
	public KostenDaten(int spieler, RohstoffDaten rohstoffe){
		this.Spieler = spieler;
		this.Rohstoffe = rohstoffe;
	}
	
	/**
	 * Getter for Spieler
	 * @return
	 */
	public int getSpieler() {
		return Spieler;
	}
	
	/**
	 * Setter for Spieler
	 * @param spieler
	 */
	public void setSpieler(int spieler) {
		Spieler = spieler;
	}
	
	/**
	 * Getter for Rohstoffe ArrayList
	 * @return
	 */
	public ArrayList<ResourceType> getRohstoffe() {
		return Rohstoffe.getResources();
	}
	
	/**
	 * Setter for Rohstoffe
	 * @param rohstoffe
	 */
	public void setRohstoffe(RohstoffDaten rohstoffe) {
		Rohstoffe = rohstoffe;
	}
}
