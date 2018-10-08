package jsonstructures;
/**
 * Object to be converted to JSON and sent to client as Answer in Handshake
 * @author georgschwab, Felix
 */
public class HalloServerNachricht {
	
	VersionProtokollDaten Hallo;
	
	
	/**
	 * Getter for VersionDaten
	 * @return
	 */
	public VersionProtokollDaten getDaten(){
		return Hallo;
	}
	
	/**
	 * Creates a HalloNachricht object that the client receives from the server
	 * @param version			version of the server
	 * @param protokoll			version of the protokoll
	 */
	
	public HalloServerNachricht(String version,String protokoll){
		Hallo = new VersionProtokollDaten(version,protokoll);
		
		
	}

	/**
	 * Getter for Version
	 * @return
	 */
	public String getVersion() {
		return Hallo.getVersion();
	}

	/**
	 * Getter for Protokoll
	 * @return
	 */
	public String getProtokoll() {
		return Hallo.getProtokoll();
	}
}
