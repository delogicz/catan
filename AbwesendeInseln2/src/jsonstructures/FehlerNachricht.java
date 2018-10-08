package jsonstructures;

/**
 * This class is the wrapper class for the FehlerDaten class
 * @author Felixi, Georg
 *
 */
public class FehlerNachricht {
	private FehlerDaten Fehler;

	
	/**
	 * creates a Fehlerdaten object
	 * @param fehler	error message
	 */
	public FehlerNachricht(String fehler) {
		Fehler = new FehlerDaten(fehler);
	}
	
	/**
	 * Getter for FehlerDaten
	 * @return
	 */
	public String getDaten(){
		return Fehler.getMeldung();
	}
}
