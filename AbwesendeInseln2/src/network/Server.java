package network;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.google.gson.Gson;

import controller.LogMaster;
import controller.ServerControl;
import game.Settings;
import jsonstructures.*;
import model.*;

/**
 * Creates the server the clients can connect to
 * @author Felix Sommer, Georg Schwab
 */
public class Server extends Thread{
	private static final String VERSION = "AbwesendeInselServer 1.0";
	private static final String PROTOCOL = "1.0";
	private static final String SERVER_OK = "OK";
	private Gson gson;
	private ServerSocket server;
	private ArrayList<ServerHandler> clientHandlers;
	private ServerControl control;
	private ArrayList<Integer> idList;
	private Socket client;
	private boolean gameStarted;
	private boolean normalTokenSequence;

	
	/**
	 * Creates Server with Port number
	 * @param serverControl the server control
	 * @param port the port
	 * @param normalTokenSequence true if player wants to play with the normal build-up
	 * @throws PortAlreadyInUseException
	 */
	public Server(ServerControl serverControl, int port, boolean normalTokenSequence) throws PortAlreadyInUseException{
		LogMaster.log("[Net]Created Server on Port " + port + " [Thread : "+ Thread.currentThread().getId() + "]");
		//System.out.println("[Net]Created Server on Port " + port + " [Thread : "+ Thread.currentThread().getId() + "]");
		gson = new Gson();
		control = serverControl;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			LogMaster.logExc("[Exc]Port " + port + " already in use");
			throw new PortAlreadyInUseException();
		}
		clientHandlers = new ArrayList<ServerHandler>();
		idList = new ArrayList<Integer>();
		gameStarted = false;
		this.normalTokenSequence = normalTokenSequence;
	}

	/**
	 * Run method of the Server Thread
	 * checks for clients
	 */
	@Override
	public void run() {
		if(server != null){
			while(!server.isClosed() && !gameStarted){ 
				client = null;
				try
				{	
					client = server.accept();
					if(gameStarted){
						client.close();
						LogMaster.log("[Net]New client declined since server is full [Thread : "+ Thread.currentThread().getId() + "]");
						break;
					}
					ServerHandler clientHandler = new ServerHandler(client, this);
					clientHandlers.add(clientHandler);
					LogMaster.log("[Net]Server got new client [Thread : "+ Thread.currentThread().getId() + "]");
					LogMaster.log("[Thr]Started new Thread ClientHandler with ID "  + clientHandler.getId() + " [Thread : "+ Thread.currentThread().getId() + "]");
					clientHandler.start();
				}
				catch ( IOException e ) {
					try {
						if(gameStarted){
							server.close();
						}
					} catch (IOException e1) {
						LogMaster.log("[Exc]Server throws IOException: " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
					}
				}
				try {
					LogMaster.log("[Thr]Thread sleeps for 20ms [Thread : "+ Thread.currentThread().getId() + "]");
					Thread.sleep(Settings.SLEEP_SHORT);
				} catch (InterruptedException e) {
					LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		}
		LogMaster.logNet("[Net]Shutting down Server");
	}
	
	/**
	 * Handles all the messages the server gets from the clients
	 * @param text			message	
	 * @param source		which client
	 */
	public void handleInput(String text, ServerHandler source) {
		LogMaster.log("[Net]Server received: " + text + "from Client: " + source.getClientId()+ " [Thread : "+ Thread.currentThread().getId() + "]");
		String head = text.substring(0,10);
		String head2 = "";
		if(text.length() >= 19){
			 head2 = text.substring(0,19);
		}
		switch(head){
		case "{\"Hallo\":{": handleHello(source,text);break;
		case "{\"Bauen\":{": handleBuild(text,source);break;
		case "{\"Zug been": handleEndTurn(source);break;
		case "{\"Wuerfeln": handleRollDice(source);break;
		case "{\"Spiel st": handleStartGame(source);break;
		case "{\"Spieler\"": handlePlayerInit(text, source);break;
		case "{\"Chatnach": handleChatMessage(text,source);break;
		case "{\"Raeuber ": handleRelocateRobber(text, source); break;
		case "{\"Seehande": handleMaritimeTrade(text, source); break;
		case "{\"Karten a": handleGiveCards(text,source);break;
		case "{\"Ritter a": handlePlayKnight(text,source);break;
		case "{\"Monopol\"": handleMonopoly(text,source);break;
		case "{\"Erfindun": handleYearOfPlenty(text,source);break;
		case "{\"Strassen": handleRoadBuilding(text,source);break;
		case "{\"Entwickl": handleBuyDevelopment(source);break;
		case "{\"Handel a":	switch(head2){
							case "{\"Handel anbieten\":": handleTrade(text, source);break;
							case "{\"Handel annehmen\":": handleAcceptTrade(text, source);break;
							case "{\"Handel abschliess": handleDoTrade(text, source);break;
							case "{\"Handel abbrechen\"": handleCanceledTrade(text,source);break;
							}break;
		default: sendUnknownMessageError(source, text);
		}
	}

	
	/**
	 * Handles buy development card event
	 * @param source
	 */
	private void handleBuyDevelopment(ServerHandler source) {
		try {
			control.handleBuyDevelopment(source.getClientId());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}


	/**
	 * Handles Road Building dev card 
	 * @param text
	 * @param source
	 */
	private void handleRoadBuilding(String text, ServerHandler source) {

		int playerID = source.getClientId();
		
		
		if(text.contains("Strasse 2")){ //building 2 roads
			int startOne = text.indexOf("Strasse 1")+11;
			int endOne = text.indexOf("Strasse 2")-2;
			String streetOne = text.substring(startOne, endOne);
			Edge one = ConvertMaster.convertToEdge(streetOne, true);
			
			int startTwo = text.indexOf("Strasse 2")+11;
			int endTwo = text.indexOf("}]}}")+2;
			String streetTwo = text.substring(startTwo, endTwo);
			Edge two = ConvertMaster.convertToEdge(streetTwo, true);
				
			try {
				control.handleRoadBuilding(playerID, one, two);
			} catch (WrongStatusException | DevCardException e) {
				sendError(playerID, e.getMessage());
			}

		}else{	//build only 1 road
			int startOne = text.indexOf("Strasse 1")+11;
			int endOne = text.indexOf("}]}}")+2;
			String streetOne = text.substring(startOne, endOne);
			Edge one = ConvertMaster.convertToEdge(streetOne, true);
			try {
				control.handleRoadBuilding(playerID, one);
			} catch (WrongStatusException | DevCardException e) {
				sendError(playerID, e.getMessage());
			}
		}
	}

	/**
	 * Handles year of plenty dev card
	 * @param text
	 * @param source
	 */
	private void handleYearOfPlenty(String text, ServerHandler source) {
		ErfindungNachricht en = gson.fromJson(text, ErfindungNachricht.class);
		ArrayList<ResourceType> resources = en.getErfindung().getRohstoffe().getResources();
		try {
			control.handleYearOfPlenty(source.getClientId(), resources);
		} catch (DevCardException | WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}	
	}

	/**
	 * Handles Monopoly dev card
	 * @param text
	 * @param source
	 */
	private void handleMonopoly(String text, ServerHandler source) {
		MonopolNachricht mn = gson.fromJson(text, MonopolNachricht.class);
		String resource = mn.getRohstoff();
		ResourceType res = ConvertMaster.convertToResource(resource);
		try {
			control.handleMonopoly(source.getClientId(), res);
		} catch (WrongStatusException | DevCardException e) {
			sendError(source.getClientId(), e.getMessage());
		}		
	}


	/**
	 * Handles knight dev card play
	 * @param text
	 * @param source
	 */
	private void handlePlayKnight(String text, ServerHandler source) {
		int start = text.indexOf(":")+2;
		int end = text.length()-1;
		String sub = text.substring(start, end);
		ClientRaeuberDaten crd = gson.fromJson(sub, ClientRaeuberDaten.class);
		try {
			control.handlePlayKnight(source.getClientId(), crd.getOrt(), crd.getZiel());
		} catch (DevCardException | WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}


	/**
	 * Handles client's information about which cards he wants to get rid of (due to a diced 7)
	 * @param text
	 * @param source
	 */
	private void handleGiveCards(String text, ServerHandler source) {
		int start = text.indexOf(":")+1;
		int end = text.length()-1;
		String sub = text.substring(start, end);
		RohstoffDaten rd = gson.fromJson(sub, RohstoffDaten.class);
		try {
			control.handleGiveCards(source.getClientId(), rd.getResources());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}

	/**
	 * Handles message of a client to cancel a trade
	 * @param text
	 * @param source
	 */
	private void handleCanceledTrade(String text, ServerHandler source) {
		int start = text.indexOf(" id") +5;
		String sub = text.substring(start, text.length()-2);
		int tradeId = Integer.parseInt(sub);
		control.handleCancelTrade(tradeId,source.getClientId());
	}
	
	/**
	 * Handles client's request to execute a trade
	 * @param text
	 * @param source
	 */
	private void handleDoTrade(String text, ServerHandler source) {
		int start = text.indexOf(" id") + 5;
		int mid = text.indexOf(",");
		int end = text.indexOf("spieler") + 9;
		String trade = text.substring(start, mid);
		String player = text.substring(end, text.length()-2);
		int tradeId = Integer.parseInt(trade);
		int playerId = Integer.parseInt(player);
		try {
			control.executeTrade(tradeId, playerId, source.getClientId());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}

	/**
	 * Handles client's request to accept a trade
	 * @param text
	 * @param source
	 */
	private void handleAcceptTrade(String text, ServerHandler source) {
		int start = text.indexOf("id\":") +4;
		int end = text.indexOf("\"Annehmen\"");
		String sub = text.substring(start,end-1);
		int tradeId = Integer.parseInt(sub);
		if(text.contains("false")){
			try {
				control.handleNotAcceptTrade(tradeId,source.getClientId());
			} catch (WrongStatusException e) {
				sendError(source.getClientId(), e.getMessage());
			}
		}else{
			try {
				control.handleAcceptTrade(tradeId,source.getClientId());
			} catch (WrongStatusException e) {
				sendError(source.getClientId(), e.getMessage());
			}
		}
	
	}

	/**
	 * Handles an incoming TradeOffer
	 * @param text
	 * @param source
	 */
	private void handleTrade(String text, ServerHandler source) {
		int start = text.indexOf(":") + 1;
		int end = text.length() - 1;
		String sub = text.substring(start, end);
		ClientHandelAngebot cha = gson.fromJson(sub, ClientHandelAngebot.class);
		ArrayList<ResourceType> offer = cha.getAngebot().getResources();
		RohstoffDaten demand = cha.getNachfrage();
		try {
			control.handleTradeOffer(offer,demand,source.getClientId());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}

	/**
	 * Handles the request of a player to do a maritime trade
	 * @param text
	 * @param source
	 */
	private void handleMaritimeTrade(String text, ServerHandler source) {
			SeehandelNachricht sn = gson.fromJson(text, SeehandelNachricht.class);
			int[] offerArray = sn.getSeehandel().getAngebot().getAll();
			ArrayList<ResourceType> offer = sn.getSeehandel().getAngebot().getResources();
			ArrayList<ResourceType> demand = sn.getSeehandel().getNachfrage().getResources();
			try {
				control.handleMaritimeTrade(source.getClientId(),offerArray,offer,demand);
			} catch (WrongStatusException e) {
				sendError(source.getClientId(), e.getMessage());
			}
	}

/**
 * Handles the message that a client wants to relocate the robber to a certain tile and
 * steal a card from a certain player. Both (tile and robber) are specified in that message
 * @param text
 * @param source
 */
	private void handleRelocateRobber(String text, ServerHandler source) {
		int start = text.indexOf(":") + 1;
		int end = text.length() - 1;
		String sub = text.substring(start, end);
		ClientRaeuberDaten crd = gson.fromJson(sub, ClientRaeuberDaten.class);
		try {
			control.handleRelocateRobber(source.getClientId(), crd.getOrt(), crd.getZiel());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}


	/**
	 * Handles the configuration of the player
	 * @param text			message
	 * @param source		client
	 */
	private void handlePlayerInit(String text, ServerHandler source) {
		SpielerInitNachricht sin = gson.fromJson(text, SpielerInitNachricht.class);
		SpielerDaten sd = sin.getSpielerDaten();
		String name = sd.getName();
		PlayerColor color = sd.getFarbe();
		int playerID = source.getClientId();
		control.handlePlayerInit(name, color, playerID);
	}
	
	
	/**
	 * Sends an error message when the Server receives a message that he doesn't know
	 * @param source		client
	 * @param text			message
	 */
	private void sendUnknownMessageError(ServerHandler source,String text) {
		FehlerNachricht fl = new FehlerNachricht("Unknown Message: \"" + text + "\" is not supported");
		String txt = gson.toJson(fl);
		sendToOne(source,txt);
	}

	
	/**
	 * Handles the chat messages, the client sends
	 * @param text			messages
	 * @param source		client
	 */
	private void handleChatMessage(String text, ServerHandler source) {
		int start = text.indexOf(":")+1;
		int end = text.length()-1;
		String sub = text.substring(start, end);
		ClientChatDaten ccd = gson.fromJson(sub, ClientChatDaten.class);
		String message = ccd.getNachricht();
		int id = source.getClientId();
		sendOk(source.getClientId());
		sendChatMessage(id,message);
		if(message.equals("greedisgood")) control.greedCheatActivated(id);
		
	}

	/**
	 * Handles the message, when a client is ready to play
	 * @param source
	 */
	private void handleStartGame(ServerHandler source) {
		if(!gameStarted){
			control.handleStartGame(source.getClientId());
		}
	}

	
	/**
	 * Handles the message, when a client wants to roll the dice
	 * @param source
	 */
	private void handleRollDice(ServerHandler source) {
		try {
			control.handleDiceRoll(source.getClientId());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}
	
	/**
	 * Handles the message that a client wants to end its turn
	 * @param source
	 */
	private void handleEndTurn(ServerHandler source) {
		try {
			control.handleEndTurn(source.getClientId());
		} catch (WrongStatusException e) {
			sendError(source.getClientId(), e.getMessage());
		}
	}

	/**
	 * Handles the event of a building from the client
	 * @param text			message
	 * @param source		client
	 */
	private void handleBuild(String text,ServerHandler source) {
		BauNachricht bn = gson.fromJson(text, BauNachricht.class);
		String type = bn.getTyp();
		switch(type){
		case "Strasse": try {
							control.handleBuildRoad(bn.getKante(true), source.getClientId());
						} catch (WrongStatusException e) {
							sendError(source.getClientId(), e.getMessage());
						}break;
		case "Dorf": try {
						control.handleBuildSettlement(bn.getEcke(true),source.getClientId());
					} catch (WrongStatusException e) {
						sendError(source.getClientId(), e.getMessage());
					}break;
		case "Stadt": try {
						control.handleBuildCity(bn.getEcke(true),source.getClientId());
					} catch (WrongStatusException e) {
						sendError(source.getClientId(), e.getMessage());
					}break;
		}
	}

	/**
	 * Handles the Hello Message from the client and sends welcome message
	 * @param source		client
	 * @param text			text
	 */
	private void handleHello(ServerHandler source,String text) {
		if(!gameStarted){
			HalloNachricht hn = gson.fromJson(text, HalloNachricht.class);
			@SuppressWarnings("unused")
			String ver = hn.getDaten().getVersion();
			sendWelcome(source);
			control.handleHello(source.getClientId());
		}
	}

	/**
	 * Handles lost connection
	 * @param clientId
	 */
	public void handleLostConnection(int clientId) {
		control.handleLostConnection(clientId);
	}

	/**
	 * Sends Server ok after valid message
	 * @param id
	 */
	public void sendOk(int id){
		sendServerMessage(id,SERVER_OK);
	}
	
	/**
	 * Sends the hello message to client
	 * @param target		client
	 */
	public void sendHello(ServerHandler target){
		HalloServerNachricht hn = new HalloServerNachricht(VERSION, PROTOCOL);
		String text = gson.toJson(hn);
		sendToOne(target,text);
	}
	
	/**
	 * Sends welcome message
	 * @param source		client
	 */
	public void sendWelcome(ServerHandler source){
		Random rand = new Random();
		int id = rand.nextInt(100);
		while(idList.contains(id)){
			id = rand.nextInt(100);
		}
		idList.add(id);
		source.setId(id);
		WillkommenNachricht wn = new WillkommenNachricht(id);
		String text = gson.toJson(wn);
		sendToOne(source,text);
	}
	
	/**
	 * Sends ServerMessage
	 * @param id		client
	 * @param text		message
	 */
	public void sendServerMessage(int id, String text){
		ServerantwortNachricht san = new ServerantwortNachricht(text);
		String message = gson.toJson(san);
		sendToOne(id,message);
	}
	
	/**
	 * sends chat messages to clients
	 * @param id		client
	 * @param text		chat message
	 */
	public void sendChatMessage(int id, String text){
		ServerChatNachricht scn = new ServerChatNachricht(id, text);
		String chat = gson.toJson(scn);
		sendToAll(chat);
	}
	
	/**
	 * Sends error message  to client
	 * @param id			client
	 * @param error			error message
	 */
	public void sendError(int id, String error){
		FehlerNachricht fl = new FehlerNachricht(error);
		String txt = gson.toJson(fl);
		sendToOne(id,txt);
	}
	
	/**
	 * Sends start match message to clients
	 * @param i			island for map
	 */
	public void sendStartMatch(Island i){
		KartenNachricht kn = new KartenNachricht(i, true);
		String text = gson.toJson(kn);
		sendToAll("{\"Spiel gestartet\":"+text+"}");
	}
	
	/**
	 * Sends a status update to clients, depending on who it is getting send to
	 * @param id				client id who's status got updated
	 * @param color				color of client
	 * @param name				name of client
	 * @param status			new status of client
	 * @param victoryPoints		victory Points of client
	 * @param resources			resources of player
	  *@param knightsPlayed		
	 * @param devCards			
	 */
	public void sendStatusUpdate(int id,String color, String name, String status, int victoryPoints, ArrayList<ResourceType> resources, int knightsPlayed, ArrayList<DevelopmentCardType> devCards){
		control.updatePlayerStatus(id,status);
		StatusUpdateNachricht sun = new StatusUpdateNachricht(id,color,name,status,victoryPoints,resources, knightsPlayed, devCards);
		StatusUpdateUnbekanntNachricht suun = new StatusUpdateUnbekanntNachricht(id,color,name,status,victoryPoints,resources, knightsPlayed, devCards);
		String secret = gson.toJson(sun);
		String notSecret = gson.toJson(suun);
		sendToOne(id,secret);
		sendToAllButOne(id,notSecret);
	}
	
	public void sendStatusNewUpdate(int playerID, String status,int victoryPoints, 
			ArrayList<ResourceType> resCards, int playersArmy, ArrayList<DevelopmentCardType> devCards) {
			StatusNewUpdateNachricht snun = new StatusNewUpdateNachricht(playerID,status,victoryPoints,resCards,playersArmy,devCards);
			StatusNewUpdateUnbekanntNachricht snuun = new StatusNewUpdateUnbekanntNachricht(playerID,status,victoryPoints,resCards,playersArmy,devCards);
			String secret = gson.toJson(snun);
			int begin = secret.indexOf(",");
			String secret2 = secret.substring(0,begin);
			String secret3 = secret.substring(begin,secret.length());
			String secret4 = secret2 + ",\"Name\":null" + secret3;
			String notSecret = gson.toJson(snuun);
			
			int beginNot = notSecret.indexOf(",");
			String notSecret2 = notSecret.substring(0,beginNot);
			String notSecret3 = notSecret.substring(begin,notSecret.length());
			String notSecret4 = notSecret2 + ",\"Name\":null" + notSecret3;
			sendToOne(playerID,secret4);
			sendToAllButOne(playerID,notSecret4);
	}
	/**
	 * Sends dice roll
	 * @param id
	 * @param diceResult
	 */
	public void sendDice(int id, int[] diceResult){
		WuerfelNachricht wn = new WuerfelNachricht(id,diceResult);
		String text = gson.toJson(wn);
		sendToAll(text);
	}
	
	/**
	 * Sends the resources to a client
	 * @param id			client
	 * @param resources		resources client got
	 */
	public void sendProduce(int id, ArrayList<ResourceType> resources){
		
		ErtragNachricht en = new ErtragNachricht(id,resources);
		String text = gson.toJson(en);
		sendToOne(id, text);
		
	}
	
	/**
	 * Sends the resource to a client
	 * @param id			client
	 * @param resource		resource client got
	 */
	public void sendProduce(int id, ResourceType resource) {
		ArrayList<ResourceType> resources = new ArrayList<ResourceType>();
		resources.add(resource);
		sendProduce(id, resources);
	}
	
	/**
	 * Sends the spent resources to a client
	 * @param id			client
	 * @param resources		resources client lost
	 */
	public void sendCosts(int id, ArrayList<ResourceType> resources){
		RohstoffDaten rd = new RohstoffDaten(resources);
		KostenDaten kd = new KostenDaten(id, rd);
		KostenNachricht kn = new KostenNachricht(kd);
		String text = gson.toJson(kn);
		control.checkTrades();
		sendToOne(id, text);
	}
	
	/**
	 * Sends the spent resource to a client
	 * @param id			client
	 * @param resource		resource client lost
	 */
	public void sendCosts(int id, ResourceType resource) {
		ArrayList<ResourceType> resources = new ArrayList<ResourceType>();
		resources.add(resource);
		sendCosts(id, resources);
		control.checkTrades();
	}
	
	/**
	 * Sends when a client build something 
	 * @param b			building the client build
	 */
	public void sendConstruction(Building b){
		BauvorgangNachricht bn = new BauvorgangNachricht(b, true);
		String text = gson.toJson(bn);
		sendToAll(text);
	}
	
	/**
	 * Sends Robber Relocated
	 * @param playerID
	 * @param tile
	 * @param aimedID
	 */
	public void sendRobberRelocated(int playerID, Tile tile, int aimedID){
		RaeuberDaten rd = new RaeuberDaten(playerID, ConvertMaster.convertTile(tile, true), aimedID);
		String txt = "{\"Raeuber versetzt\":" + gson.toJson(rd) + "}";
		sendToAll(txt);
	}
	
	/**
	 * Sends message to signalise that someone won the game and it is over
	 * @param winner
	 * @param devCardVP the amount of victory points the player had from development cards
	 */
	public void sendEndMatch(Player winner, int devCardVP){
		String winText = "Player " + winner.getName() + " won the game.";
		String devCardVPText = "";
		if(devCardVP > 0){
			devCardVPText = "\n" + winner.getName() + " had " + devCardVP + " VictoryPoint - development cards!";
		}
		SpielBeendetDaten gd = new SpielBeendetDaten(winText + devCardVPText,winner.getPlayerID());
		String json = gson.toJson(gd);
		String text = "{\"Spiel beendet\":" + json + "}";
		sendToAll(text);
	}
	
	/**
	 * Sends message that there is a new TradeOffer with all needed information
	 * @param idPlayer
	 * @param offer
	 * @param demand
	 * @param tradeId
	 */
	public void sendTradeOffer(int idPlayer, ArrayList<ResourceType> offer, RohstoffDaten demand,int tradeId){
		HandelsangebotNachricht hn = new HandelsangebotNachricht(idPlayer, tradeId, offer, demand);
		String message = gson.toJson(hn);
		int qPos = message.indexOf("Q");
		String sub1 = message.substring(0, qPos);
		String sub2 = message.substring(qPos+1, message.length());
		String finl = sub1 + "Handel id" + sub2;
		sendToAll(finl);
		sendError(idPlayer, "Your offer is now online!");
	}
	
	/**
	 * Sends message that a trade was executed successfully 
	 * @param player
	 * @param tradePartner
	 */
	public void sendSuccessfulTrade(int tradeID, int player, int tradePartner){
		HandelBestaetigungDaten hbd = new HandelBestaetigungDaten(tradeID,player,tradePartner);
		String message = "{\"Handel ausgefuehrt\":" + gson.toJson(hbd) +  "}";
		int qPos = message.indexOf("Q");
		String sub1 = message.substring(0, qPos);
		String sub2 = message.substring(qPos+1, message.length());
		String finl = sub1 + "Handel id" + sub2;
		control.checkTrades();
		sendToAll(finl);
	}
	
	/**
	 * Sends the message to one client
	 * @param target			target client
	 * @param message			message
	 */
	public void sendToOne(ServerHandler target,String message){
		target.sendMessage(message);
	}
	
	/**
	 * Sends the message to one client
	 * @param id				target client
	 * @param message
	 */
	public void sendToOne(int id,String message){
		try {
			LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		for(ServerHandler handler : clientHandlers){
			handler.sendIf(id,message);
			
		}
		
	}
	
	/**
	 * Sends the message to all clients but one
	 * @param id			id of client not included
	 * @param message		
	 */
	public void sendToAllButOne(int id, String message){
		Iterator<ServerHandler> iter = clientHandlers.iterator();
		ServerHandler handler;
		while (iter.hasNext()) {
		    handler = iter.next();
		    try {
				LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
				Thread.sleep(Settings.SLEEP_SHORT);
			} catch (InterruptedException e) {
				LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
			}
			//doppelt?
			LogMaster.log("[Net]Server sends: ["+message +"] to Client: "+handler.getClientId() + " [Thread : "+ Thread.currentThread().getId() + "]");
			handler.sendIfNot(id,message);
		}
//		for(ServerHandler handler : clientHandlers){
//			try {
//				LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
//				Thread.sleep(Settings.SLEEP_SHORT);
//			} catch (InterruptedException e) {
//				LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
//			}
//			//doppelt?
//			LogMaster.log("[Net]Server sends: ["+message +"] to Client: "+handler.getClientId() + " [Thread : "+ Thread.currentThread().getId() + "]");
//			handler.sendIfNot(id,message);
//		}
	}
	
	/**
	 * Sends message to all clients
	 * @param message
	 */
	public void sendToAll(String message){
		for(ServerHandler handler : clientHandlers){
			//doppelt?
			LogMaster.log("[Net]Server sends: ["+message +"] to Client: "+handler.getClientId() + " [Thread : "+ Thread.currentThread().getId() + "]");
			handler.sendMessage(message);
		}
	}

	/**
	 * Sends message that a client accepted a trade
	 * @param tradeId
	 * @param clientId
	 */
	public void sendTradeAccepted(int tradeId, int clientId) {
		String text = "{\"Handelsangebot angenommen\":{\"Mitspieler\":"+
				clientId+",\"Handel id\":"+tradeId+ ",\"Annehmen\":true" +"}}";
		sendToAll(text);
	}
	
	public void sendTradeNotAccepted(int tradeId, int clientId) {
		String text = "{\"Handelsangebot angenommen\":{\"Mitspieler\":"+
				clientId+",\"Handel id\":"+tradeId+ ",\"Annehmen\":false" +"}}";
		sendToAll(text);
	}

	/**
	 * Sends message that a trade got cancelled
	 * @param tradeId
	 * @param clientId
	 */
	public void sendCancelledTrade(int tradeId, int clientId) {
		String text = "{\"Handelsangebot abgebrochen\":{\"Spieler\":"+
				clientId+",\"Handel id\":"+tradeId+"}}";
		sendToAll(text);
	}
	
	/**
	 * Sends Longest road
	 * @param playerID (Less than 0 if no one has the longestRoad anymore)
	 */
	public void sendLongestRoad(int playerID){
		if(playerID < 0) sendLongestRoadSpecial();
		else{
			SpielerIdDaten sid = new SpielerIdDaten(playerID);
			String text = "{\"Laengste Handelsstrasse\":" + gson.toJson(sid) + "}";
			sendToAll(text);
		}
	}
	
	/**
	 * Sends biggest army
	 * @param playerID
	 */
	public void sendBiggestArmy(int playerID){
		SpielerIdDaten sid = new SpielerIdDaten(playerID);
		String text = "{\"Groesste Rittermacht\":" + gson.toJson(sid) + "}";
		sendToAll(text);
	}
	/**
	 * Sends if longest road got canceled
	 */
	public void sendLongestRoadSpecial(){
		String text = "{\"Laengste Handelsstrasse\":{}}";
		sendToAll(text);
	}
	
	/**
	 * Sends monopoly card played
	 * @param player
	 * @param resource
	 */
	public void sendMonopoly(int player, String resource) {
		MonopolServerNachricht msn = new MonopolServerNachricht(resource, player);
		String text = gson.toJson(msn);
		sendToAll(text);
	}
	
	/**
	 * Sends messages to the Clients after successful YearOfPlenty-action
	 * @param playerID
	 * @param resources the resources the player (who played the DevelopmentCard) received
	 */
	public void sendYearOfPlenty(int playerID, ArrayList<ResourceType> resources){
		
		ErfindungServerNachricht esn = new ErfindungServerNachricht(playerID,resources);
		String yearOfPlenty = gson.toJson(esn);
		sendToAll(yearOfPlenty);
		
		ErtragUnbekanntNachricht eun = new ErtragUnbekanntNachricht(playerID, resources.size());
		String produceUnknown = gson.toJson(eun);
		ErtragNachricht ern = new ErtragNachricht(playerID, resources);
		String produceKnown = gson.toJson(ern);
		sendToAllButOne(playerID,produceUnknown);
		sendToOne(playerID,produceKnown);
	}
	
	/**
	 * Send development card played
	 * @param player
	 * @param devCard
	 */
	public void sendDevelopmentCard(int player, DevelopmentCardType devCard) {
		String developmentCard = ConvertMaster.convertDevCard(devCard); 
		EntwicklungKarteDaten ekd = new EntwicklungKarteDaten(player,developmentCard);
		
		String card = gson.toJson(ekd);
		String text = "{\"Entwicklungskarte gekauft\":" + card + "}";
		sendToOne(player,text);
		
		String unknown = Settings.UNBEKANNT;
		EntwicklungKarteDaten unknownEkd = new EntwicklungKarteDaten(player,unknown);
		
		String unknownCard = gson.toJson(unknownEkd);
		String unknownText = "{\"Entwicklungskarte gekauft\":" + unknownCard + "}";
		sendToAllButOne(player,unknownText);
	}

	/**
	 * Removes a serverhandler 
	 * @param id
	 */
	public void removeServerHandler(int id){
		int i = -1;
		int s = -1;
		for(ServerHandler h : clientHandlers){
			i++;
			if(h.getClientId() == id){
				s = i;
			}
		}
		if(s > -1) clientHandlers.remove(s);
	}

	/**
	 * Closes the connection when someone lost connection
	 * @param clientId the id of the player that's closing the connection
	 */
	public void closeConnection(int clientId){
		try {
			if(gameStarted){
			server.close();
			LogMaster.log("[Exc]Server closed the connection to the ServerSocket" + " [Thread : "+ Thread.currentThread().getId() + "]");
			}
			ServerHandler franz = null;
			for(ServerHandler h: clientHandlers){
				if(h.getClientId() == clientId){
					franz = h;
					h.close();
				}
			}
			if(franz != null){
				clientHandlers.remove(franz);
			}
			LogMaster.log("[Exc]Server closed the connection to the ServerHandlers" + " [Thread : "+ Thread.currentThread().getId() + "]");
			
		} catch (IOException e) {
			LogMaster.log("[Exc]Closing the connection caused: " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		//Platform.exit();
		//System.exit(0);
		
	}

	/**
	 * Setter for Gamesstarted
	 * @param gameStarted
	 */
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Getter for gameStarted
	 * @return
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * @return the normalTokenSequence
	 */
	public boolean isNormalTokenSequence() {
		return normalTokenSequence;
	}
	


}




