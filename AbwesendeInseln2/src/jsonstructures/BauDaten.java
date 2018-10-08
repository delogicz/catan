package jsonstructures;

import model.Edge;
import model.Vertex;

/**
 * This class is used to send from the Client to the Server a message
 * to say what and where the Player wants to build!
 * @author Felix, Georg
 */

public class BauDaten {
	private String Typ;
	private OrtDaten[] Ort;
	
	/**
	 * Creates a BauDaten Object
	 * @param typ	type of building like Dorf, Strasse or Stadt 
	 * @param ort	location of where to build the object
	 */
	public BauDaten(String typ, String ort) {
		Typ = typ;
		if(Typ.equals("Strasse")){
			Ort = ConvertMaster.convertStringToEdgeLocation(ort);
		}else{
			Ort = ConvertMaster.convertStringToVertexLocation(ort);
		}
	}
	
	/**
	 * Getter for Typ
	 * @return Typ
	 */
	public String getTyp(){
		return Typ;
	}
	
	/**
	 * Getter for Kante
	 * @param server
	 * @return edge
	 */
	public Edge getKante(boolean server){
		if(Typ.equals("Strasse")){
			return ConvertMaster.convertToEdge(Ort[0],Ort[1], server);
		}else{
			return null;
		}
	}
	/**
	 * Getter for Ecke
	 * @param server
	 * @return vertex
	 */
	public Vertex getEcke(boolean server){
		if(Typ.equals("Strasse")){
			return null;
		}else{
			return ConvertMaster.convertToVertex(Ort[0],Ort[1],Ort[2], server);
		}
	}
}
