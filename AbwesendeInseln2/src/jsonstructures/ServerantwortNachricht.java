package jsonstructures;

/**
 * This class is the response of the server to an action of the client
 * @author Felixi, Georg
 *
 */

public class ServerantwortNachricht {
	private String Serverantwort;

	
	/**
	 * Creates a ServerantwortNachricht object 
	 * @param serverantwort		response of the server
	 */
	public ServerantwortNachricht(String serverantwort) {
		Serverantwort = serverantwort;
	}

	/**
	 * Getter for Serverantwort
	 * @return
	 */
	public String getDaten() {
		return Serverantwort;
	}
}
