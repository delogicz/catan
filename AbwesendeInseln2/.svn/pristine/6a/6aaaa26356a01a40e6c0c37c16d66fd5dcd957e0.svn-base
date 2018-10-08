package jsonstructures;

import model.PlayerColor;
/**
 * Object to be converted to JSON holding status data of a player without their hand
 * @author georgschwab, Felix
 *
 */
public class SpielerZustandsUnbekanntDaten {
	
	private int id;					
	private String Farbe;
	private String Name;
	private String Status;
	private int Siegpunkte;
	private RohstoffUnbekanntDaten Rohstoffe;
	private int Rittermacht;
	private RohstoffUnbekanntDaten Entwicklungskarten;
	
	/**
	 * Creates a SpielerZustandsUnbekanntDaten object	
	 * @param id			id of the player
	 * @param farbe			color of the player
	 * @param name			name
	 * @param status		status 
	 * @param siegpunkte	victory points
	 * @param ressourcenAnzahl		resources
	 * @param ritterGespielt 
	 * @param entwicklungskartenAnzahl 
	 */
	public SpielerZustandsUnbekanntDaten(int id, String farbe, String name, String status, int siegpunkte, int ressourcenAnzahl, int ritterGespielt, int entwicklungskartenAnzahl) {
		this.id = id;
		Farbe = farbe;
		Name = name;
		Status = status;
		Siegpunkte = siegpunkte;
		Rohstoffe = new RohstoffUnbekanntDaten(ressourcenAnzahl);
		Rittermacht = ritterGespielt;
		Entwicklungskarten = new RohstoffUnbekanntDaten(entwicklungskartenAnzahl);
		
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
	public RohstoffUnbekanntDaten getEntwicklungskarten() {
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
	 * Getter for Farbe
	 * @return
	 */
	public PlayerColor getFarbe() {
		if(Farbe != null){
			switch(Farbe){
			case "Rot":		return PlayerColor.RED;
			case "Orange":	return PlayerColor.ORANGE;
			case "Blau":	return PlayerColor.BLUE;
			case "Weiss":	return PlayerColor.WHITE;
			}
		}
		return null;
	}
	
	/**
	 * Setter for Farbe
	 * @param farbe
	 */
	public void setFarbe(String farbe) {
		Farbe = farbe;
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
	public RohstoffUnbekanntDaten getRohstoffe() {
		return Rohstoffe;
	}
	
	/**
	 * Setter for RohstoffDaten
	 * @param rohstoffe
	 */
	public void setRohstoffe(RohstoffUnbekanntDaten rohstoffe) {
		Rohstoffe = rohstoffe;
	}
}
