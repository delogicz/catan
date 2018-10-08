package jsonstructures;


/**
 * This class containes the message and the player that sends a text in the chat
 * @author Felixi, Georg
 *
 */
public class ServerChatDaten {
	private int Absender;
	private String Nachricht;
	
	/**
	 * Creates the ServerChatDaten object
	 * @param absender		the id of the player who send the message 
	 * @param nachricht		message in the chat
	 */
	public ServerChatDaten(int absender, String nachricht) {
		Absender = absender;
		Nachricht = nachricht;
	}
	
	
	/**
	 * Getter for Absender
	 * @return
	 */
	public int getId() {
		return Absender;
	}
	
	/**
	 * Getter for Nachricht
	 * @return
	 */
	public String getNachricht(){
		return Nachricht;
	}
}
