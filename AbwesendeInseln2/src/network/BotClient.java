package network;

import game.GameManager;
import jsonstructures.FehlerNachricht;

/**
 * Creates a BotClient connecting to the server
 * @author Daniel Panangian
 * 
 */
public class BotClient extends Client {
	
	/**
	 * Constructor
	 * @param host IP-Adress of Host
	 * @param port Port
	 * @param gameManager GameManager of the game
	 * @throws Exception Exception in case connection to server gets refused
	 */
	public BotClient(String host, int port, GameManager gameManager) throws Exception{
		super(host, port, gameManager);
		super.version = "JavaFXClient 0.2 (KI)";
	}
	
	/**
	 * Bool for isBotClient
	 */
	public boolean isBotClient(){
		return true;
	}
	
	/**
	 * handles the error messages
	 * @param text error message
	 */
	@SuppressWarnings("unused")
	private void handleError(String text) {
		FehlerNachricht fn = gson.fromJson(text, FehlerNachricht.class);
		String error = fn.getDaten();
		String head = error.substring(0,10);
		switch(head){
		case "{\"You hav": gameManager.getCatan().getPlayerClient().discardCard();break;
		
		}
//		gameManager.getApp().popUpMessage(error);
	}
	
}
