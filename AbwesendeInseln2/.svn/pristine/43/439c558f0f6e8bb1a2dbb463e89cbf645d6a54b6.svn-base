package jsonstructures;

/**
 * This class containes the data of the building, inside the GebaeudeDatenDaten class
 * @author Felixi, Georg
 *
 */
public class GebaeudeDaten {
	private int Eigentuemer;
	private String Typ;
	private OrtDaten[] Ort;
	
	
	/**
	 * Creates a GabaeudeDaten object
	 * @param eigentuemer		id of player
	 * @param typ				type of building
	 * @param ort				location of building
	 */
	public GebaeudeDaten(int eigentuemer, String typ, String ort) {
		Eigentuemer = eigentuemer;
		Typ = typ;
		if(Typ.equals("Strasse")){
			Ort = ConvertMaster.convertStringToEdgeLocation(ort);
		}else{
			Ort = ConvertMaster.convertStringToVertexLocation(ort);
		}
	}

	/**
	 * Getter for the eigentuemer
	 * @return
	 */

	public int getEigentuemer() {
		return Eigentuemer;
	}

	/**
	 * Setter for the eigentuemer
	 * @param eigentuemer
	 */
	public void setEigentuemer(int eigentuemer) {
		Eigentuemer = eigentuemer;
	}

	/**
	 * Getter for the type
	 * @return
	 */
	public String getTyp() {
		return Typ;
	}

	/**
	 * Setter for the type
	 * @param typ
	 */
	public void setTyp(String typ) {
		Typ = typ;
	}

	/**
	 * Getter for the location
	 * @param server
	 * @return the location
	 */
	public Object getOrt(boolean server) {
		switch(Typ){
		case "Strasse": return ConvertMaster.convertToEdge(Ort[0],Ort[1], server); 
		case "Dorf":	return ConvertMaster.convertToVertex(Ort[0],Ort[1],Ort[2], server); 
		case "Stadt":	return ConvertMaster.convertToVertex(Ort[0],Ort[1],Ort[2], server);
		}
		return null;
	}

	/**
	 * Setter for the location
	 * @param ort
	 */
	public void setOrt(String ort) {
		if(Typ.equals("Strasse")){
			Ort = ConvertMaster.convertStringToEdgeLocation(ort);
		}else{
			Ort = ConvertMaster.convertStringToVertexLocation(ort);
		}
	}
}
