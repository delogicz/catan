package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import game.Settings;
import jsonstructures.ConvertMaster;
import jsonstructures.OrtDaten;
import jsonstructures.RohstoffDaten;
import model.Catan;
import model.DevCardException;
import model.DevelopmentCardType;
import model.Edge;
import model.LongestRoadChanges;
import model.Player;
import model.PlayerClient;
import model.PlayerColor;
import model.PortAlreadyInUseException;
import model.ResourceType;
import model.Road;
import model.Tile;
import model.TradeOffer;
import model.Vertex;
import model.WrongStatusException;
import network.Server;

/**
 * This class is the controller of the server that manages the model of the server
 * 
 * @author Stefanie Kloss
 */

public class ServerControl {
	
	private Server server;
	
	private Catan catan;
	private ArrayList<Player> players;
	private ArrayList<Integer> tradeIdList;
	/**
	 * Count of players with more than seven cards when the robber gets moved
	 */
	private int bigHandCount;
	/**
	 * PlayerID of player that has diced a 7 in this turn, "-1" if player diced sth else 
	 */
	private int playerToRelocateRobber;

	
	/**
	 * Constructor
	 * @param port the port of the server
	 * @param normalTokenSequence true if player wants to play with the normal build-up
	 * @throws PortAlreadyInUseException
	 */
	public ServerControl(int port, boolean normalTokenSequence) throws PortAlreadyInUseException {
		server = new Server(this,port, normalTokenSequence);
		server.start();
		LogMaster.log("[Thr]Started new Thread Server with ID "  + server.getId() + " [Thread : "+ Thread.currentThread().getId() + "]");
		players = new ArrayList<Player>();
		tradeIdList = new ArrayList<Integer>();
		bigHandCount = 0;
	}

	/**
	 * Handles the start game message from the client
	 * @param clientId ID of the client who sent the message
	 */
	public void handleStartGame(int clientId) {
		PlayerClient p = getPlayerWithID(clientId);
		server.sendOk(clientId);
		boolean allPlayersReady = true;
		p.setReadyToStartGame(true);
		server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), Settings.WARTEN_BEGINN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(),p.getDevCards());
		int playerCount = 0;
		for(Player player: players){
			playerCount++;
			if(!((PlayerClient)player).isReadyToStartGame() && playerCount <= Settings.MAX_PLAYER_COUNT){
				allPlayersReady = false;
			}
		}
		if(allPlayersReady && (players.size() == Settings.MIN_PLAYER_COUNT || players.size() >= Settings.MAX_PLAYER_COUNT)){
			int currentPlayer = -1;
			int notReadyPlayer = -1;
			for(Player player: players){
				currentPlayer++;
					if(!((PlayerClient)player).isReadyToStartGame()){
						notReadyPlayer = currentPlayer;
						}
			}
			if(notReadyPlayer > -1) {
				Player pl = players.get(notReadyPlayer);
				server.removeServerHandler(pl.getPlayerID());
				players.remove(notReadyPlayer);
			}
			server.setGameStarted(true);
			this.catan = new Catan(players, server.isNormalTokenSequence());
			server.sendStartMatch(catan.getIsland());
			PlayerClient turnTaker = (PlayerClient)catan.getCurrentTurnTaker();
			server.sendStatusUpdate(turnTaker.getPlayerID(), turnTaker.getPlayerColor().toServerString(), turnTaker.getName(), Settings.WUERFELN, turnTaker.getVictoryPoints(), ((PlayerClient)turnTaker).getResCards(),turnTaker.getKnightCount(),turnTaker.getDevCards());
			for(Player c : players){
				if(!turnTaker.equals(c)){
					server.sendStatusUpdate(c.getPlayerID(), c.getPlayerColor().toServerString(), c.getName(), Settings.WARTEN, c.getVictoryPoints(), ((PlayerClient)c).getResCards(),c.getKnightCount(),((PlayerClient)c).getDevCards());
				}
			}
			server.sendError(turnTaker.getPlayerID(), "Dice to determine in which order you're going to play.");
		}
	}

	
	/**
	 * Rolls the dice and sends the number to the clients
	 * Distributes resources
	 * @param clientId ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleDiceRoll(int clientId) throws WrongStatusException {
		
		if(getPlayerWithID(clientId).getStatus().equals(Settings.WUERFELN)){
			server.sendOk(clientId);
			int[] diceResultArray = catan.getDice().diceResult();
			int diceResult = catan.getDice().getDice1() + catan.getDice().getDice2();
			
			server.sendOk(clientId);
			server.sendDice(clientId, diceResultArray);
			
			if(catan.getRound() == 0){
				dicedToDetermineOrder(diceResult);
				return;
			}
			
			PlayerClient p = (PlayerClient)getPlayerWithID(clientId);
			if((diceResult) == 7){
				playerToRelocateRobber = clientId;
				server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), Settings.WARTEN, p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
				for(Player pl : catan.getPlayers()){
					if(((PlayerClient)pl).getResCards().size() > 7){
						bigHandCount++;
						server.sendStatusUpdate(pl.getPlayerID(), pl.getPlayerColor().toServerString(), pl.getName(), Settings.KARTEN_ABGEBEN, pl.getVictoryPoints(), ((PlayerClient)pl).getResCards(), pl.getKnightCount(),((PlayerClient)pl).getDevCards());
					}
				}
				waitThenMoveRobber(false);
			}
			else { 
				for(Player pl : players){
					int id = ((PlayerClient)pl).getPlayerID();
					server.sendProduce(id, catan.distributeResources(diceResult, (PlayerClient)pl));
				}
				server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), Settings.HANDELN_BAUEN, p.getVictoryPoints(), ((PlayerClient)p).getResCards(),p.getKnightCount(),((PlayerClient)p).getDevCards());
				sendStatusUpdatesOfAll();
			}
		}
		else throw new WrongStatusException();
	}
	
	/**
	 * Handles the KartenAbgabeDaten
	 * @param clientID ID of the client who sent the message
	 * @param resources resources the client wants to give away
	 * @throws WrongStatusException 
	 */
	public void handleGiveCards(int clientID, ArrayList<ResourceType> resources) throws WrongStatusException {
		
		if(getPlayerWithID(clientID).getStatus().equals(Settings.KARTEN_ABGEBEN)){
			
			PlayerClient p = (PlayerClient) catan.getPlayerWithID(clientID);
			int discardCount = p.getHand().getCardsToDiscard();
			if(resources.size() == p.getHand().getCardsToDiscard()){
				if(p.getHand().containsRes(resources)){
					p.getHand().spend(resources);
					catan.giveCardsBack(resources);
					server.sendOk(clientID);
					server.sendCosts(clientID, resources);
					server.sendStatusUpdate(clientID, p.getPlayerColor().toServerString(), p.getName(), Settings.WARTEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
					waitThenMoveRobber(true);
				}
				else{
					server.sendError(clientID, "You can only discard resources you own!\nPlease try again.");
				}
			}
			else server.sendError(clientID, "You have to discard exactly " + discardCount + " cards!\nPlease try again.");
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the ClientRäuberDaten message from a Client 
	 * @param clientID ID of the client who sent the message
	 * @param ortDaten the Tile the Robber should be placed on
	 * @param aimedPlayerID the player that is getting robbed from
	 * @throws WrongStatusException 
	 */
	public void handleRelocateRobber(int clientID, OrtDaten ortDaten, int aimedPlayerID) throws WrongStatusException{
		
		if(getPlayerWithID(clientID).getStatus().equals(Settings.RAEUBER_VERSETZEN)){
			
			Tile newTile = ConvertMaster.convertToTile(ortDaten, true);
			if(newTile != catan.getIsland().getRobber().getTile()){
				catan.getIsland().getRobber().getTile().setRobber(false);
				catan.getIsland().getRobber().setTile(newTile);
				newTile.setRobber(true);
				server.sendOk(clientID);
				server.sendRobberRelocated(clientID, ConvertMaster.convertToTile(ortDaten, true), aimedPlayerID);
				PlayerClient robber = (PlayerClient)catan.getPlayerWithID(clientID);
				if(aimedPlayerID >= 0){
					PlayerClient aim = (PlayerClient)catan.getPlayerWithID(aimedPlayerID);
					try {
					ResourceType robbedRes = aim.getHand().getRandomResource();
					aim.getHand().spend(robbedRes);
					server.sendCosts(aimedPlayerID, robbedRes);
					server.sendError(aimedPlayerID, robber.getName() + " just stole you a " + robbedRes + "!");
					robber.getHand().addResource(robbedRes);
					server.sendProduce(clientID, robbedRes);
					server.sendError(clientID, "You just stole " + aim.getName() +  " a " + robbedRes + "!");
					}
					catch(Exception e) {
						server.sendError(clientID, Settings.CANNOT_ROB);
					}
					
				}
				server.sendStatusUpdate(clientID, robber.getPlayerColor().toServerString(), robber.getName(), Settings.HANDELN_BAUEN, robber.getVictoryPoints(), robber.getResCards(),robber.getKnightCount(), ((PlayerClient)robber).getDevCards());
				sendStatusUpdatesOfAll();
			}else{
				server.sendError(clientID, Settings.ROBBER_ON_SAME_TILE);
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the end turn event of a client
	 * @param clientId ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleEndTurn(int clientId) throws WrongStatusException {
		if(getPlayerWithID(clientId).getStatus().equals(Settings.HANDELN_BAUEN)){
			Player p = getPlayerWithID(clientId);
			server.sendOk(clientId);
			server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), Settings.WARTEN, p.getVictoryPoints(), ((PlayerClient)p).getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
			catan.changeTurnTaker(); 
			Player turn  = catan.getCurrentTurnTaker();
			if(catan.getRound() > Settings.SECOND_FOUNDATION_PHASE){
				server.sendStatusUpdate(turn.getPlayerID(), turn.getPlayerColor().toServerString(), turn.getName(), Settings.WUERFELN, turn.getVictoryPoints(), ((PlayerClient)turn).getResCards(),turn.getKnightCount(), ((PlayerClient)turn).getDevCards());
			}else{
				server.sendStatusUpdate(turn.getPlayerID(), turn.getPlayerColor().toServerString(), turn.getName(), Settings.DORF_BAUEN, turn.getVictoryPoints(), ((PlayerClient)turn).getResCards(),turn.getKnightCount(), ((PlayerClient)turn).getDevCards());
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the build road event of a client
	 * @param edge	edge on which the road is tried to be built on
	 * @param clientId	ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleBuildRoad(Edge edge, int clientId) throws WrongStatusException {
		
		PlayerClient p = getPlayerWithID(clientId);
		
		if(p.getStatus().equals(Settings.HANDELN_BAUEN) || p.getStatus().equals(Settings.STRASSE_BAUEN)){
		
			boolean allowedLocation = p.canBuildRoad(edge);
			if(!allowedLocation){
				server.sendError(clientId, "You cannot build a road on this edge!");
				return;
			}
			
			if((catan.getRound() == Settings.FIRST_FOUNDATION_PHASE && p.countRoads() == 0) 
					|| (catan.getRound() == Settings.SECOND_FOUNDATION_PHASE && p.countRoads() == 1)){
				server.sendOk(clientId);
				server.sendConstruction(p.build(edge, true));
				server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(),Settings.WARTEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
				catan.changeTurnTaker();
				PlayerClient turn = (PlayerClient)catan.getCurrentTurnTaker();
				if(catan.getRound() <= Settings.SECOND_FOUNDATION_PHASE){
					server.sendStatusUpdate(turn.getPlayerID(), turn.getPlayerColor().toServerString(), turn.getName(), Settings.DORF_BAUEN, turn.getVictoryPoints(), turn.getResCards(),turn.getKnightCount(), ((PlayerClient)turn).getDevCards());
				}
				else{
					catan.distributeResources(server);
					turn.migrateHarbours();
					server.sendStatusUpdate(turn.getPlayerID(), turn.getPlayerColor().toServerString(), turn.getName(), Settings.WUERFELN, turn.getVictoryPoints(), turn.getResCards(),turn.getKnightCount(), ((PlayerClient)turn).getDevCards());
					sendStatusUpdatesOfAll();
				}
			}
			else{
				if(p.getHand().canBuyRoad() && (p.getRoadsLeft() > 0)){ 
					p.getHand().buyRoad();
					server.sendOk(clientId);
					server.sendConstruction(p.build(edge, true));
					checkLongestRoad();
					ArrayList<ResourceType> resList = new ArrayList<ResourceType>();
					resList.add(ResourceType.BRICK);
					resList.add(ResourceType.LUMBER);
					server.sendCosts(clientId, resList);
					catan.giveCardsBack(resList);
					server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
				}else{
					if(p.getRoadsLeft() <= 0) server.sendError(clientId, Settings.NO_ROADS_LEFT);
					else server.sendError(clientId, Settings.RESOURCES_ERROR);
				}
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the build settlement event
	 * @param vertex	vertex on which the settlement is tried to be built on
	 * @param clientId	ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleBuildSettlement(Vertex vertex, int clientId) throws WrongStatusException {
	
		PlayerClient p = getPlayerWithID(clientId);
		
		if(p.getStatus().equals(Settings.HANDELN_BAUEN) || p.getStatus().equals(Settings.DORF_BAUEN)){
	
			boolean allowedLocation = p.canBuildSettlement(vertex);
			if(!allowedLocation){
				server.sendError(clientId, "You cannot build a settlement on this vertex!");
				return;
			}
			
			if((catan.getRound() == Settings.FIRST_FOUNDATION_PHASE && p.countSettlements() == 0) 
					|| (catan.getRound() == Settings.SECOND_FOUNDATION_PHASE && p.countSettlements() == 1)){
				p.addAndCheckVictoryPoints(Settings.VICTORY_POINTS_FOR_SETTLEMENT);
				server.sendOk(clientId);
				server.sendConstruction(p.build(Settings.DORF, vertex, true));
				server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(),Settings.STRASSE_BAUEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
			}
			else if(catan.getRound() > Settings.SECOND_FOUNDATION_PHASE){
				if(p.getHand().canBuySettlement() && (p.getSettlementsLeft() > 0)){ 
					p.getHand().buySettlement();
					server.sendOk(clientId);
					server.sendConstruction(p.build(Settings.DORF, vertex, true));
					ResourceType[] res = {ResourceType.BRICK, ResourceType.LUMBER, ResourceType.WOOL, ResourceType.GRAIN};
					ArrayList<ResourceType> resList = new ArrayList<ResourceType>();
					Collections.addAll(resList, res);
					catan.giveCardsBack(resList);
					server.sendCosts(clientId, resList);
					boolean gameWon = p.addAndCheckVictoryPoints(Settings.VICTORY_POINTS_FOR_SETTLEMENT);
					server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(),Settings.HANDELN_BAUEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
					checkLongestRoad();
					if(gameWon){
						server.sendEndMatch(p, p.getDevVictoryPoints());
						for(Player player : players){
							player.setStatus(Settings.GAME_OVER, true);
						}
					}
				}else{
					if(p.getSettlementsLeft() <= 0) server.sendError(clientId, Settings.NO_SETTLEMENTS_LEFT);
					else server.sendError(clientId, Settings.RESOURCES_ERROR);
				}
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the build city event
	 * @param vertex	vertex on which the city is tried to be built on
	 * @param clientId	ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleBuildCity(Vertex vertex, int clientId) throws WrongStatusException {
		
		PlayerClient p = getPlayerWithID(clientId);
		
		if(p.getStatus().equals(Settings.HANDELN_BAUEN)){
			
			boolean allowedLocation = vertex.getOccupyingBuilding().isOwner(p);
			if(!allowedLocation){
				server.sendError(clientId, "You cannot build a city on this vertex!");
				return;
			}
			
			if(p.getHand().canBuyCity() && (p.getCitiesLeft() > 0)){ 
				p.getHand().buyCity();
				server.sendOk(clientId);
				server.sendConstruction(p.build(Settings.STADT, vertex, true));
				ResourceType[] res = {ResourceType.ORE, ResourceType.ORE, ResourceType.ORE, ResourceType.GRAIN, ResourceType.GRAIN};
				ArrayList<ResourceType> resList = new ArrayList<ResourceType>();
				Collections.addAll(resList, res);
				catan.giveCardsBack(resList);
				server.sendCosts(clientId, resList);
				boolean gameWon = p.addAndCheckVictoryPoints(Settings.VICTORY_POINTS_FOR_CITY);
				server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(),Settings.HANDELN_BAUEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
				if(gameWon){
					server.sendEndMatch(p, p.getDevVictoryPoints());
					for(Player player : players){
						((PlayerClient)player).setStatus(Settings.GAME_OVER, true);
					}
				}
			}else{
				if(p.getCitiesLeft() <= 0) server.sendError(clientId, Settings.NO_CITIES_LEFT);
				else server.sendError(clientId, Settings.RESOURCES_ERROR);
			}
		}	
		else throw new WrongStatusException();
	}

	/**
	 * Handles the BuyDevelopment message from a Client
	 * @param playerID ID of the player that's trying to buy a DevelopmentCard
	 * @throws WrongStatusException 
	 */
	public void handleBuyDevelopment(int playerID) throws WrongStatusException{
		PlayerClient p = getPlayerWithID(playerID);
		if(p.getStatus().equals(Settings.HANDELN_BAUEN)){
			if(p.getHand().canBuyDevCard()){
				DevelopmentCardType devCard;
				try {
					devCard = catan.getDevCardDeck().getNextDevCard();
				} catch (DevCardException e) {
					server.sendError(playerID, e.getMessage());
					return;
				}
				server.sendOk(playerID);
				p.getHand().buyDevCard();
				server.sendDevelopmentCard(playerID, devCard);
				ResourceType[] res = {ResourceType.ORE, ResourceType.WOOL, ResourceType.GRAIN};
				ArrayList<ResourceType> resList = new ArrayList<ResourceType>();
				Collections.addAll(resList, res);
				catan.giveCardsBack(resList);
				server.sendCosts(playerID, resList);
				server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(),Settings.HANDELN_BAUEN, p.getVictoryPoints(), p.getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
				if(devCard.isVictoryPoint()){
					p.getDevCards().add(devCard);
					p.setDevVictoryPoints(p.getDevVictoryPoints()+Settings.VICTORY_POINTS_FOR_DEV_CARD);
					if(p.addAndCheckVictoryPoints(0)){
						server.sendEndMatch(p, p.getDevVictoryPoints());
						for(Player player : players){
							player.setStatus(Settings.GAME_OVER, true);
						}
						return;
					}
				}
				else p.getDevCardsNext().add(devCard);
			}
			else server.sendError(playerID, Settings.RESOURCES_ERROR);
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the PlayKnight message from a Client 
	 * @param playerID ID of the player who sent the message
	 * @param ortDaten the location of the tile as string
	 * @param aimID the player that is getting robbed from (-1 if no one is getting robbed from)
	 * @throws DevCardException 
	 * @throws WrongStatusException 
	 */
	public void handlePlayKnight(int playerID, OrtDaten ortDaten, int aimID) throws DevCardException, WrongStatusException{
		
		PlayerClient p = getPlayerWithID(playerID);
		
		if(!p.isCanPlayDevCard()) throw new DevCardException(Settings.ONE_DEVCARD_PER_TURN);
		else if(!p.getDevCards().contains(DevelopmentCardType.KNIGHT)){
			throw new DevCardException(Settings.NOT_OWNING_DEVCARD);
		}
		
		else if((p.getStatus().equals(Settings.HANDELN_BAUEN) || p.getStatus().equals(Settings.WUERFELN))){
			p.setCanPlayDevCard(false);
			p.getDevCards().remove(DevelopmentCardType.KNIGHT);
			server.sendOk(playerID);
			server.sendRobberRelocated(playerID, ConvertMaster.convertToTile(ortDaten, true), aimID);
			if(aimID >= 0){
				PlayerClient aim = (PlayerClient)catan.getPlayerWithID(aimID);
				try {
				ResourceType robbedRes = aim.getHand().getRandomResource();
				aim.getHand().spend(robbedRes);
				server.sendCosts(aimID, robbedRes);
				server.sendError(aimID, p.getName() + " just stole you a " + robbedRes + "!");
				p.getHand().addResource(robbedRes);
				server.sendProduce(playerID, robbedRes);
				server.sendError(playerID, "You just stole " + aim.getName() +  " a " + robbedRes + "!");
				} 
				catch(Exception e) {
					server.sendError(playerID, Settings.CANNOT_ROB);
				}
			}
			if(p.addAndCheckKnightCount()){
				server.sendBiggestArmy(playerID);
				sendStatusUpdatesOfAll();
				if(p.addAndCheckVictoryPoints(0)){
					server.sendEndMatch(p, p.getDevVictoryPoints());
					for(Player player : players){
						player.setStatus(Settings.GAME_OVER, true);
					}
				}
			}
			server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getDevVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the Monopoly message from a Client
	 * @param playerID ID of the client who sent the message
	 * @param res the resourceType the player has a monopoly on
	 * @throws WrongStatusException 
	 * @throws DevCardException 
	 */
	public void handleMonopoly(int playerID, ResourceType res) throws WrongStatusException, DevCardException{
		
		PlayerClient p = getPlayerWithID(playerID);
		
		if(!p.isCanPlayDevCard()) throw new DevCardException(Settings.ONE_DEVCARD_PER_TURN);
		else if(!p.getDevCards().contains(DevelopmentCardType.MONOPOLY)){
			throw new DevCardException(Settings.NOT_OWNING_DEVCARD);
		}
		else if((p.getStatus().equals(Settings.HANDELN_BAUEN) || p.getStatus().equals(Settings.WUERFELN))){
			p.setCanPlayDevCard(false);
			p.getDevCards().remove(DevelopmentCardType.MONOPOLY);
			server.sendOk(playerID);
			server.sendMonopoly(playerID, ConvertMaster.convertResource(res));
			for(Player player : players){
				if(playerID != player.getPlayerID()){
					ArrayList<ResourceType> resCards = new ArrayList<ResourceType>();
					int resCount;
					switch(res){
					case GRAIN: 	resCount = ((PlayerClient)player).getHand().getGrainCount(); break;
					case WOOL:	 	resCount = ((PlayerClient)player).getHand().getWoolCount(); break;
					case LUMBER: 	resCount = ((PlayerClient)player).getHand().getLumberCount(); break;
					case BRICK: 	resCount = ((PlayerClient)player).getHand().getBrickCount(); break;
					case ORE: 		resCount = ((PlayerClient)player).getHand().getOreCount(); break;
					default: 		resCount = 0; break;
					}
					for(int amountOfCards = 0; amountOfCards < resCount; amountOfCards++){
						resCards.add(res);
					}
					((PlayerClient)player).getHand().spend(resCards);
					server.sendCosts(player.getPlayerID(), resCards);
					p.getHand().produce(resCards);
					server.sendProduce(playerID, resCards);
				}
			}
			server.sendOk(playerID);
			this.sendStatusUpdatesOfAll();
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the YearOfPlenty message from a Client
	 * @param playerID ID of the client who sent the message
	 * @param resources the resources the Client wants to receive
	 * @throws DevCardException 
	 * @throws WrongStatusException 
	 */
	public void handleYearOfPlenty(int playerID, ArrayList<ResourceType> resources) throws DevCardException, WrongStatusException{
		
		PlayerClient p = getPlayerWithID(playerID);
		
		if(!p.isCanPlayDevCard()) throw new DevCardException(Settings.ONE_DEVCARD_PER_TURN);
		else if(!p.getDevCards().contains(DevelopmentCardType.YEAROFPLENTY)){
			throw new DevCardException(Settings.NOT_OWNING_DEVCARD);
		}
		else if((p.getStatus().equals(Settings.HANDELN_BAUEN) || p.getStatus().equals(Settings.WUERFELN))){
			if(resources.size() == 2){
				p.setCanPlayDevCard(false);
				p.getDevCards().remove(DevelopmentCardType.YEAROFPLENTY);
				resources = catan.distributeResources(playerID, resources);
				server.sendOk(playerID);
				server.sendYearOfPlenty(playerID, resources);
				server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getDevVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
			}
			else server.sendError(playerID, "You have to select exactly two resources");
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the hello message from the client
	 * @param clientID ID of the client who sent the message
	 */
	public void handleHello(int clientID) {
		
			server.sendOk(clientID);
			PlayerClient p = new PlayerClient(null, clientID, null,null);
			players.add(p);
			p.setStatus(Settings.SPIEL_STARTEN, true);
			Iterator<Player> iter = players.iterator();
			
			while (iter.hasNext()) {
			    Player oldP = iter.next();
			    if(oldP.getPlayerID() != clientID && oldP.getPlayerColor() != null ){
			    	
					server.sendStatusUpdate(oldP.getPlayerID(), oldP.getPlayerColor().toServerString(), 
					  oldP.getName(), oldP.getStatus(), oldP.getVictoryPoints(), ((PlayerClient)oldP).getResCards(),oldP.getKnightCount(), ((PlayerClient)oldP).getDevCards());
				}else{
	
					server.sendStatusNewUpdate(oldP.getPlayerID(), oldP.getStatus(), oldP.getVictoryPoints(), ((PlayerClient)oldP).getResCards(),oldP.getKnightCount(), ((PlayerClient)oldP).getDevCards());
				}
			}
	
	}

	/**
		 * Handles the SpielerInitNachricht
		 * @param name requested name
		 * @param color requested color
		 * @param clientID ID of the client who sent the message
		 */
		public void handlePlayerInit(String name, PlayerColor color, int clientID){
			
			if(name.equals("") ||name.trim().length() == 0 ){
				server.sendError(clientID, "Enter a valid name!");
				return;
			}
			if(name.length() > 10){
				server.sendError(clientID, "Your name is too long!");
				return;
			}
			if(name.contains(Settings.UNBEKANNT)  || name.contains(Settings.FARBE)){
				server.sendError(clientID, "Your name is not valid!");
				return;
			}
			Iterator<Player> iter = players.iterator();
			Player player;
			while (iter.hasNext()) {
			    player = iter.next();
			    if(player.getPlayerColor() != null){
					if(player.getPlayerColor().equals(color) && player.getPlayerID() != clientID ){
						server.sendError(clientID, "This color is already taken!");
						return;
					}if(player.getName().equals(name) && player.getPlayerID() != clientID ){
						server.sendError(clientID, "This name is already taken!");
						return;
					}
				}
					getPlayerWithID(clientID).setName(name);
					getPlayerWithID(clientID).setPlayerColor(color);
					getPlayerWithID(clientID).createPlayerControl();
			}

			server.sendOk(clientID);
			sendAllStatus();
			
		}

	/**
	 * Checks whether the player that accepted a trade has enough resources for the trade
	 * @param tradeId ID of the trade that got accepted
	 * @param clientId ID of the client who sent the message
	 * @throws WrongStatusException 
	 */
	public void handleAcceptTrade(int tradeId, int clientId) throws WrongStatusException {
		if(getPlayerWithID(clientId).getStatus().equals(Settings.WARTEN)){
			TradeOffer trade = catan.getTrade(tradeId);
			if(trade != null){
				if(getPlayerWithID(clientId).getHand().containsRes(trade.getDemand())){
					server.sendOk(clientId);
					server.sendTradeAccepted(tradeId,clientId);
					catan.getTrade(tradeId).accept(clientId);
				}
				else  server.sendError(clientId, Settings.RESOURCES_ERROR);
			}
			else server.sendError(clientId, Settings.TRADE_ALREADY_DONE);
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles a declined trade
	 * @param tradeId ID of the trade that got declined
	 * @param clientId ID of the client that declined the trade
	 * @throws WrongStatusException 
	 */
	public void handleNotAcceptTrade(int tradeId, int clientId) throws WrongStatusException {
		if(getPlayerWithID(clientId).getStatus().equals(Settings.WARTEN)){
			TradeOffer trade = catan.getTrade(tradeId);
			if(trade != null){
				if(clientId != trade.getPlayerId()){
					server.sendOk(clientId);
					server.sendTradeNotAccepted(tradeId,clientId);
					catan.getTrade(tradeId).decline(clientId);
				}
				else server.sendError(clientId, "You cannot decline your own trade, but you can delete it.");
			}
			else server.sendError(clientId, Settings.TRADE_ALREADY_DONE);
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles an incoming trade offer
	 * @param offer offered resources
	 * @param demand demanded resources
	 * @param idPlayer id of the offerer
	 * @throws WrongStatusException 
	 */
	public void handleTradeOffer(ArrayList<ResourceType> offer, RohstoffDaten demand, int idPlayer) throws WrongStatusException {
		
		if(getPlayerWithID(idPlayer).getStatus().equals(Settings.HANDELN_BAUEN)){
			if(getPlayerWithID(idPlayer).getHand().containsRes(offer)){
				if(isGivingOrGettingCardsForFree(offer, demand.getResources())){
					server.sendError(idPlayer, Settings.CANNOT_GIVE_FOR_FREE);
					return;
				}
				server.sendOk(idPlayer);
				int idTrade = newTradeId();
				catan.getTrades().add(new TradeOffer(idTrade, getPlayerWithID(idPlayer), offer, demand.getResources(), true));
				server.sendTradeOffer(idPlayer, offer, demand, idTrade);
			}
			else server.sendError(idPlayer, Settings.INVALID_OFFER);
		}
		else throw new WrongStatusException();
	}

	/**
	 * Cancels a trade or adds a player to the declined Traders
	 * @param tradeId ID of the trade that gets canceled
	 * @param clientId ID of the client who sent the message
	 */
	public void handleCancelTrade(int tradeId, int clientId) {
		if(catan.getTrade(tradeId) != null){
			server.sendOk(clientId);
			server.sendCancelledTrade(tradeId,clientId);
			if(catan.getTrade(tradeId).getPlayerId() == clientId){
				catan.removeOfferWithID(tradeId);;
			}else{
				catan.getTrade(tradeId).decline(clientId);
				if(catan.getTrade(tradeId).getDeclinedTraders().size() == catan.getPlayerCount()-1){
					catan.removeOfferWithID(tradeId);
				}
			}
		}
		else server.sendError(clientId, Settings.TRADE_ALREADY_DONE);
	}

	/**
	 * Handles the request of a player to do a maritime trades
	 * @param clientId ID of the client who sent the message
	 * @param offer offered resources represented by an int array 
	 * @param offerList offered resources
	 * @param demand demanded resources
	 * @throws WrongStatusException 
	 */
	public void handleMaritimeTrade(int clientId, int[] offer,ArrayList<ResourceType> offerList, ArrayList<ResourceType> demand) throws WrongStatusException{
		
		PlayerClient p = getPlayerWithID(clientId);
		
		if(p.getStatus().equals(Settings.HANDELN_BAUEN)){
			boolean priceOk = false;
			for(int currentRes = 0; currentRes < 5; currentRes++){
				if(offer[currentRes] == p.getMaritimePrices(demand.get(0))[currentRes]){
					priceOk = true;
				}
			}
			if(priceOk){
				if(p.getHand().containsRes(offerList)){
					p.getHand().produce(demand);
					p.getHand().remResource(offerList);
					server.sendOk(clientId);
					server.sendCosts(clientId, offerList);
					server.sendProduce(clientId, demand);
					server.sendStatusUpdate(clientId, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
				}else{
					server.sendError(clientId, Settings.RESOURCES_ERROR); 
				}
			}else{
				server.sendError(clientId, "You don't control the right Harbour to trade at this rate!");
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Handles the road building event when someone played the Road building development card
	 * @param playerID ID of the client who sent the message
	 * @param one  edge on which the first road is tried to be built on
	 * @param two second edge on which the second road is tried to be built on
	 * @throws DevCardException 
	 * @throws WrongStatusException 
	 */
	public void handleRoadBuilding(int playerID, Edge one, Edge two) throws WrongStatusException, DevCardException {
		PlayerClient p = getPlayerWithID(playerID);
		
		Road roadOne = null;
		Road roadTwo = null;
		boolean ableToBuildSth = false;
		
		if(canDoRoadBuilding(p,2)){
			if(p.canBuildRoad(one)) roadOne = p.build(one, true);
			if(p.canBuildRoad(two)) roadTwo = p.build(two, true);
			if(roadOne == null && p.canBuildRoad(one)) roadOne = p.build(one, true);
			if(roadOne != null && roadTwo != null){
				ableToBuildSth = true;
				String build = "{\"Strassenbaukarte ausspielen\":{\"Spieler\":" + playerID  +",\"Strasse 1\":"
		              + ConvertMaster.convertEdge(one, true) + ",\"Strasse 2\":" + ConvertMaster.convertEdge(two, true) + "}}";
				server.sendOk(playerID);
				server.sendToAll(build);
				server.sendConstruction(roadOne);
				server.sendConstruction(roadTwo);
			}
			else{
				server.sendError(playerID, Settings.TRY_AGAIN_ROADBUILDING);
				return;
			}
		}
		
		else if(canDoRoadBuilding(p,1)){
			if(p.canBuildRoad(one)){
				roadOne = p.build(one, true);
				ableToBuildSth = true;
				String build = "{\"Strassenbaukarte ausspielen\":{\"Spieler\":" + playerID  
						+",\"Strasse 1\":" + ConvertMaster.convertEdge(one, true) +"}}";
				server.sendOk(playerID);
				server.sendToAll(build);
				server.sendConstruction(roadOne);
				server.sendError(playerID, "The second road couldn't be built");
			}
			else if(p.canBuildRoad(two)){
				ableToBuildSth = true;
				roadTwo = p.build(two, true);
				String build = "{\"Strassenbaukarte ausspielen\":{\"Spieler\":" + playerID  
						+",\"Strasse 1\":" + ConvertMaster.convertEdge(two, true) +"}}";
				server.sendOk(playerID);
				server.sendToAll(build);
				server.sendConstruction(roadTwo);
				server.sendError(playerID, "The first road couldn't be built");
			}
		}
		else if(p.getRoadsLeft() == 0 || p.getBuildableEdges().size() == 0){
			server.sendError(playerID, "You have already built all 15 roads or there is\n"
					+ "no edge you could place a road on right now.");
		}
		if(ableToBuildSth){
			p.setCanPlayDevCard(false);
			p.getDevCards().remove(DevelopmentCardType.ROADBUILDING);
			server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getDevVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
		}
		else server.sendError(playerID, Settings.UNSUCCESSFUL_ROADBUILDING);
	}

	/**
	 * Handles the road building event when someone played the Road building development card
	 * @param playerID ID of the client who sent the message
	 * @param one edge the road is tried to be built on
	 * @throws DevCardException 
	 * @throws WrongStatusException 
	 */
	public void handleRoadBuilding(int playerID, Edge one) throws WrongStatusException, DevCardException {
		PlayerClient p = getPlayerWithID(playerID);
		if(canDoRoadBuilding(p, 1) && p.canBuildRoad(one)){
			p.setCanPlayDevCard(false);
			Road roadOne = p.build(one, true);
			String build = "{\"Strassenbaukarte ausspielen\":{\"Spieler\":" + playerID  +
						",\"Strasse 1\":" + ConvertMaster.convertEdge(one, true) +"}}";
			server.sendToAll(build);
			server.sendConstruction(roadOne);
			p.getDevCards().remove(DevelopmentCardType.ROADBUILDING);
			server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getDevVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
		}
		else server.sendError(playerID, Settings.UNSUCCESSFUL_ROADBUILDING);
	}

	/**
	 * Handles the incident that the connection to a client got lost
	 * @param clientId ID of the client who lost connection
	 */
	public void handleLostConnection(int clientId) {
		try{
			Player hans = null;
			for(Player pl : players){
				if(pl.getPlayerID() == clientId)
					hans = pl;
			}
			if(hans != null){
				players.remove(hans);
			}
			if(catan != null){
				PlayerClient p = (PlayerClient) catan.getPlayerWithID(clientId);
				server.sendStatusUpdate(clientId, p.getPlayerColor().toString(), p.getName(), Settings.VERBINDUNG_VERLOREN, p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
				for(Player player: players){
					server.sendError(player.getPlayerID(), "The player: " + player.getName() + " has left the game, the server is shutting down the game.");
				}
			}
			server.closeConnection(clientId);
		}catch(Exception e){
			LogMaster.log("[Exc]Closing the connection was not possible because of: " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
	}

	/**
	 * Executes a trade. Sends cost and produce messages.
	 * @param tradeId ID of the trade that's tried to get executed
	 * @param acceptedID ID of the player the offerer decided to trade with
	 * @param offererID ID of the offerer of the trade
	 * @throws WrongStatusException 
	 */
	public void executeTrade(int tradeId, int acceptedID, int offererID) throws WrongStatusException {
		
		if(getPlayerWithID(offererID).getStatus().equals(Settings.HANDELN_BAUEN)){
			
			TradeOffer tradeOffer = catan.getTrade(tradeId);
			if(tradeOffer != null && tradeOffer.hasAccepted(acceptedID)){
				if(getPlayerWithID(acceptedID).getHand().containsRes(tradeOffer.getDemand()) 
						&& getPlayerWithID(offererID).getHand().containsRes(tradeOffer.getOffer())){
					server.sendOk(offererID);
					getPlayerWithID(offererID).getHand().produce(tradeOffer.getDemand());
					getPlayerWithID(offererID).getHand().remResource(tradeOffer.getOffer());
					getPlayerWithID(acceptedID).getHand().remResource(tradeOffer.getDemand());
					getPlayerWithID(acceptedID).getHand().produce(tradeOffer.getOffer());
					server.sendSuccessfulTrade(tradeId, catan.getTrade(tradeId).getPlayerId(), acceptedID);
					server.sendCosts(offererID, tradeOffer.getOffer());
					server.sendProduce(offererID, tradeOffer.getDemand());
					server.sendCosts(acceptedID, tradeOffer.getDemand());
					server.sendProduce(acceptedID, tradeOffer.getOffer());
					catan.getTrades().remove(tradeOffer);
					this.sendStatusUpdatesOfAll();
				}
				else{
					server.sendError(offererID, Settings.TRADING_NOT_POSSIBLE);
				}
			}else{
				if(tradeOffer == null) server.sendError(offererID, Settings.TRADE_ALREADY_DONE);
				else if(!tradeOffer.hasAccepted(acceptedID)){
					server.sendError(offererID, getPlayerWithID(acceptedID).getName() + " doesn't want to trade!");
				}
			}
		}
		else throw new WrongStatusException();
	}

	/**
	 * Sends statusUpdates of all players if they have a playerColor
	 */
	private void sendAllStatus() {
		for(Player oldP: players){
			if(oldP.getPlayerColor() != null){
				server.sendStatusUpdate(oldP.getPlayerID(), oldP.getPlayerColor().toServerString(), 
				  oldP.getName(), oldP.getStatus(), oldP.getVictoryPoints(), ((PlayerClient)oldP).getResCards(),oldP.getKnightCount(), ((PlayerClient)oldP).getDevCards());
			}
		}
	}

	/**
	 * Sends StatusUpdates of every Player
	 */
	private void sendStatusUpdatesOfAll(){
		for(Player player : players){
			PlayerClient p = (PlayerClient) player;
			server.sendStatusUpdate(p.getPlayerID(), p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
		}
	}

	/**
	 * Updates the status of a player
	 * @param id id of the playerClient
	 * @param status new status of the playerClient
	 */
	public void updatePlayerStatus(int id, String status) {
		getPlayerWithID(id).setStatus(status, true);
		if(status.equals(Settings.WUERFELN)){
			for(TradeOffer trade : catan.getTrades()){
				server.sendCancelledTrade(trade.getPlayerId(), trade.getPlayerId());
			}
			catan.getTrades().clear();
		}
	}

	/**
	 * Activates cheat and gives the player 5 of each resource
	 * @param playerID ID of the client who sent the message
	 */
	public void greedCheatActivated(int playerID) {
		ArrayList<ResourceType> cheat = new ArrayList<ResourceType>();
		for(int cheatResCount = 0; cheatResCount < 5;cheatResCount++ ){
			cheat.add(ResourceType.BRICK);
			cheat.add(ResourceType.GRAIN);
			cheat.add(ResourceType.LUMBER);
			cheat.add(ResourceType.ORE);
			cheat.add(ResourceType.WOOL);
		}
		PlayerClient p = (PlayerClient) catan.getPlayerWithID(playerID);
		p.getHand().produce(cheat);
		server.sendProduce(playerID,cheat);
		server.sendStatusUpdate(playerID, p.getPlayerColor().toServerString(), p.getName(), p.getStatus(), p.getVictoryPoints(), p.getResCards(), p.getKnightCount(), p.getDevCards());
	}

	/**
	 * Checks for all Trades if the offerers still own the offered resources.
	 * If that's not the case the affected trade gets removed.
	 */
	public void checkTrades() {
		ArrayList<TradeOffer> trades = catan.getTrades();
		if(trades.size() > 0){
			ArrayList<TradeOffer> tradesToDelete = new ArrayList<TradeOffer>();
			for(TradeOffer trade : trades){
				if(!getPlayerWithID(trade.getPlayerId()).getHand().containsRes(trade.getOffer())){
					server.sendError(trade.getPlayerId(), "Your Trade Offer with the ID " + trade.getTradeId() 
					           + " got deleted because you don't own all offered resources anymore.");
					server.sendCancelledTrade(trade.getTradeId(),trade.getPlayerId());
					tradesToDelete.add(trade);
				}
			}	
			for(TradeOffer trade :  tradesToDelete){
				trades.remove(trade);
			}
		}
	}

	/**
	 * Use when player rolled the dice to determine the order of players 
	 * Sends correct StatusUpdates to all players
	 * @param diceResult the results of both dices added together
	 */
	private void dicedToDetermineOrder(int diceResult){
		catan.saveDiceResultAndChangeOrder(diceResult);
		catan.changeTurnTaker();
		PlayerClient c = (PlayerClient)catan.getCurrentTurnTaker();
		for(Player p : players){
			if(!c.equals(p)){
				server.sendStatusUpdate(p.getPlayerID(), p.getPlayerColor().toServerString(), p.getName(), Settings.WARTEN, p.getVictoryPoints(), ((PlayerClient)p).getResCards(),p.getKnightCount(),((PlayerClient)p).getDevCards());
			}
		}
		
		if(catan.getRound() == 0){
			server.sendStatusUpdate(c.getPlayerID(), c.getPlayerColor().toServerString(), c.getName(), Settings.WUERFELN, c.getVictoryPoints(), ((PlayerClient)c).getResCards(),c.getKnightCount(), ((PlayerClient)c).getDevCards());
			server.sendError(c.getPlayerID(), "Dice to determine in which order you're going to play.");
		}
		else{
			server.sendStatusUpdate(c.getPlayerID(), c.getPlayerColor().toServerString(), c.getName(), Settings.DORF_BAUEN, c.getVictoryPoints(), ((PlayerClient)c).getResCards(),c.getKnightCount(), ((PlayerClient)c).getDevCards());
		}
	}
	
	/**
	 * Waits until all players have given away cards, then sends the message to relocate the robber
	 * @param newPlayerGaveCards true if bigHandCount should get reduced, otherwise false
	 */
	private void waitThenMoveRobber(boolean newPlayerGaveCards) {
		if(newPlayerGaveCards) bigHandCount--;
		if(bigHandCount == 0){
			Player p = getPlayerWithID(playerToRelocateRobber);
			playerToRelocateRobber = -1;                      //reset variable for next robber action       
			server.sendStatusUpdate(p.getPlayerID(), p.getPlayerColor().toServerString(), p.getName(), Settings.RAEUBER_VERSETZEN, p.getVictoryPoints(), ((PlayerClient)p).getResCards(),p.getKnightCount(), ((PlayerClient)p).getDevCards());
		}
	}
	
	/**
	 * Checks whether the owner of the longestRoad has changed
	 */
	private void checkLongestRoad() {
		
		LongestRoadChanges lrc = catan.checkLongestRoad();
		
		switch(lrc){
		case NEW_OWNER: server.sendLongestRoad(catan.getPlayerWithLongestRoad());
						sendStatusUpdatesOfAll(); break;
		case NO_ONE:	server.sendLongestRoad(-1);
						sendStatusUpdatesOfAll(); break;
		case SAME_OWNER:break;
		}
	}

	/**
	 * Gets the player, the playerId belongs to
	 * @param playerID ID of the PlayerClient that is being looked for
	 * @return the searched player
	 */
	private PlayerClient getPlayerWithID(int playerID){
		
		for(Player player: players){
			if(player.getPlayerID() == playerID){
				return (PlayerClient)player;
			}
		}
		return null;
	}
	
	/**
	 * Checks whether offer and demand of a trade share a same resource
	 * or whether offer or demand are empty to prevent the action of
	 * giving away or getting cards for free
	 * @return true if there is a resource occurring in both offer and demand
	 */
	private boolean isGivingOrGettingCardsForFree(ArrayList<ResourceType> offer, ArrayList<ResourceType> demand) {
		if(offer.size() == 0 || demand.size() == 0) return true;
		for(ResourceType offerType : offer){
			for(ResourceType demandType : demand){
				if(offerType == demandType){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Generates a random TradeID (between 0 and 1000) that isn't assigned yet
	 * @return The new TradeID
	 */
	private int newTradeId(){
		Random rand = new Random();
		int tradeId = rand.nextInt(Settings.MAX_TRADE_ID);
		while(tradeIdList.contains(tradeId)){
			tradeId = rand.nextInt(Settings.MAX_TRADE_ID);
		}
		tradeIdList.add(tradeId);
		return tradeId;
	}
	
	/**
	 * Check whether the player fulfills all needed factors to build one or two roads
	 * @param player the client who's playing the DevelopmentCard "RoadBuilding"
	 * @param roadCount amount of roads that are planned to be built (1 or 2)
	 * @return true if the player is able to build the requested amount of roads
	 * @throws WrongStatusException 
	 * @throws DevCardException 
	 */
	private static boolean canDoRoadBuilding(PlayerClient player, int roadCount) throws WrongStatusException, DevCardException{
		boolean correctStatus = player.getStatus().equals(Settings.HANDELN_BAUEN) || player.getStatus().equals(Settings.WUERFELN);
		boolean canPlayDevCard = player.isCanPlayDevCard();
		boolean ownsRoadCard = player.getDevCards().contains(DevelopmentCardType.ROADBUILDING);
		boolean ableToBuildRoads = player.getRoadsLeft() >= roadCount;
		if(!correctStatus) throw new WrongStatusException();
		else if(!canPlayDevCard) throw new DevCardException(Settings.ONE_DEVCARD_PER_TURN);
		else if(!ownsRoadCard) throw new DevCardException(Settings.NOT_OWNING_DEVCARD);
		return correctStatus && canPlayDevCard && ownsRoadCard && ableToBuildRoads;
	}


}