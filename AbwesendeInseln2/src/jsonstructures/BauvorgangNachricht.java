package jsonstructures;

import model.Building;
import model.City;
import model.Road;
import model.Settlement;

/**
 * This class is send by the server to all clients when someone build a new building
 * @author Felixi, Georg
 */

public class BauvorgangNachricht {
	private GebaeudeDatenDaten Bauvorgang;

	/**
	 * Initializes GebaeudeDatenDaten object
	 * @param b Building to be converted
	 * @param server
	 */
	public BauvorgangNachricht(Building b, boolean server) {
		int id = b.getOwner().getPlayerID();
		String typ = "ERROR";
		String ort = "ERROR";
		if(b instanceof Road){
			Road r = (Road) b;
			typ = "Strasse";
			ort = ConvertMaster.convertEdge(r.getEdge(), server);
		}else if (b instanceof City){
			City c = (City) b;
			typ = "Stadt";
			ort = ConvertMaster.convertVertex(c.getVertex(), server);
		}else if (b instanceof Settlement){
			Settlement s = (Settlement) b;
			typ = "Dorf";
			ort = ConvertMaster.convertVertex(s.getVertex(), server);
		}	
		Bauvorgang = new GebaeudeDatenDaten(new GebaeudeDaten(id,typ,ort));
	}

	/**
	 * Getter for GebaeudeDatenDaten
	 * @return	Bauvorgang
	 */
	public GebaeudeDatenDaten getBauvorgang() {
		return Bauvorgang;
	}

	/**
	 * Setter for GebaeudeDatenDaten
	 * @param bauvorgang
	 */
	public void setBauvorgang(GebaeudeDatenDaten bauvorgang) {
		Bauvorgang = bauvorgang;
	}
	

}