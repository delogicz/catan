package jsonstructures;

import java.util.ArrayList;

import model.DevelopmentCardType;
import model.ResourceType;
/**
 * Object to be converted to JSON holding status data of a Player
 * @author georgschwab, Felix
 *
 */
public class SpielerNewZustandsDaten {

	private int id;					
	private String Name;
	private String Status;
	private int Siegpunkte;
	private RohstoffDaten Rohstoffe;
	private int Rittermacht;
	private EntwicklungKarteDatenDaten Entwicklungskarten;
	
	
	/**
	 * Creates a SpielerNewZustandsDaten object	
	 * @param id			id of the player
	 * @param status		status 
	 * @param siegpunkte	victory points
	 * @param resourcen		resources
	 * @param ritterGespielt 
	 * @param entwicklungsKarten 
	 */
	public SpielerNewZustandsDaten(int id,  String status, int siegpunkte, ArrayList<ResourceType> resourcen, int ritterGespielt, ArrayList<DevelopmentCardType> entwicklungsKarten) { // Entwicklungskarten and Rittermacht are still missing please add when they are implemented for the players 
		this.id = id;
		Name = null;
		Status = status;
		Siegpunkte = siegpunkte;
		Rohstoffe = new RohstoffDaten(resourcen);
		Rittermacht = ritterGespielt;
		Entwicklungskarten = new EntwicklungKarteDatenDaten(entwicklungsKarten);
		
	}
	
	/**
	 * Getter for Rittermacht
	 * @return
	 */
	public int getRittermacht() {
		return Rittermacht;
	}

	/**
	 * Getter for Entwicklungskarten
	 * @return
	 */
	public EntwicklungKarteDatenDaten getEntwicklungskarten() {
		return Entwicklungskarten;
	}


	/**
	 * Getter for id
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter for ID
	 * @param id
	 */

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for Name
	 * @return
	 */

	public String getName() {
		return Name;
	}
	
	
	/**
	 * Setter for Name
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}
	
	
	/**
	 * Getter for Status
	 * @return
	 */
	public String getStatus() {
		return Status;
	}
	
	
	/**
	 * Setter for Status
	 * @param status
	 */
	public void setStatus(String status) {
		Status = status;
	}
	
	/**
	 * Getter for Siegpunkte
	 * @return
	 */
	public int getSiegpunkte() {
		return Siegpunkte;
	}
	
	/**
	 * Setter for Siegpunkte
	 * @param siegpunkte
	 */

	public void setSiegpunkte(int siegpunkte) {
		Siegpunkte = siegpunkte;
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
