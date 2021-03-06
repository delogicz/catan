package jsonstructures;

/**
 * This class containes the message that the client sends to the server
 * @author Felixi, Georg
 */
public class ClientChatDaten {
	private String Nachricht;

	
	/**
	 * Creates a ClientChatDaten object
	 * @param nachricht		message to be send in the chat
	 */
	public ClientChatDaten(String nachricht) {
		Nachricht = nachricht;
	}
	
	/**
	 * Getter for Nachricht
	 * @return
	 */
	public String getNachricht(){
		return Nachricht;
	}
}
