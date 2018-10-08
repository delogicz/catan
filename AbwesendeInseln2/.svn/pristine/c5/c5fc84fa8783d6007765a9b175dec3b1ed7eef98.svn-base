package jsonstructures;

import model.Edge;
import model.Vertex;

/**
 * This Class is top class of when something gets build
 * it converts the game logic of location to the server logic
 * @author Felixi, Georg
 */
public class BauNachricht {
	private BauDaten Bauen;

	/**
	 * Creates a BauNachricht object for settlemets and cities
	 * @param typ		type of object to build, settlement or city
	 * @param ort		location of building
	 * @param server
	 */
	public BauNachricht(String typ, Vertex ort, boolean server) {
		String ort2 = convertVertex(ort, server);
		Bauen = new BauDaten(typ, ort2);
	}
	
	
	/**
	 * Creates a BauNachricht object for roads
	 * @param typ		road
	 * @param ort		location of street
	 * @param server
	 */
	public BauNachricht(String typ, Edge ort, boolean server) {
		String ort2 = convertEdge(ort, server);
		Bauen = new BauDaten(typ, ort2);
	}

	/**
	 * Converts the game logic of an edge logic to the server logic 
	 * @param ort		location of the edge
	 * @param server
	 * @return			converted location of the street
	 */
	private String convertEdge(Edge ort, boolean server) {
		return ConvertMaster.convertEdge(ort, server);
	}
	
	/**
	 * Converts the game logic of a vertex location to the server logic
	 * @param ort		location of the vertex
	 * @param server
	 * @return			converted location of the settlement or city
	 */
	private String convertVertex(Vertex ort, boolean server) {
		return ConvertMaster.convertVertex(ort, server);
	}
	
	/**
	 * Getter for Typ
	 * @return
	 */
	public String getTyp(){
		return Bauen.getTyp();
	}
	
	/**
	 * Getter for Kante
	 * @param server
	 * @return
	 */
	public Edge getKante(boolean server){
		return Bauen.getKante(server);
	}
	
	/**
	 * Getter for Ecke
	 * @param server
	 * @return
	 */
	public Vertex getEcke(boolean server){
		return Bauen.getEcke(server);
	}
}
