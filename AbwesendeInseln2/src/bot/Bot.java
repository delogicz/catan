package bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import model.DevelopmentCardType;
import model.Edge;
import model.Player;
import model.PlayerClient;
import model.PlayerColor;
import model.ResourceType;
import model.Settlement;
import model.TerrainType;
import model.Tile;
import model.TokenNumber;
import model.Vertex;

/**
 * AI for playing catan
 * @author Daniel Panangian
 */
public class Bot extends PlayerClient {
	
	private BotBrain brain;
	private ArrayList<Tile> availableHexes;
	private ArrayList<Vertex> availableVertices;
	private ArrayList<Edge> availableEdges;
	private ArrayList<Vertex> verticesOwned;
	private ArrayList<Vertex> verticesConnected;
	private ArrayList<TerrainType> terrainsOwned;
	private ArrayList<TokenNumber> tokenOwned;
	protected boolean buildingPhaseEnd;
	
	/**
	 * Constructor, creates a Bot
	 * @param playerColor the color of the bot
	 * @param playerId the id of the bot
	 * @param name the name of the bot
	 * @param status the status of the bot
	 */
	public Bot(PlayerColor playerColor, int playerId, String name, String status) {
		super(playerColor, playerId, name,status);
		brain = new BotBrain(this);
		LogMaster.log("[Bot]Bot " + name + " added."+ " [Thread : "+ Thread.currentThread().getId() + "]");
		availableVertices = new ArrayList<Vertex>();
		availableEdges = new ArrayList<Edge>();
		availableHexes = new ArrayList<Tile>();
		verticesOwned = new ArrayList<Vertex>();
		verticesConnected = new ArrayList<Vertex>();
		terrainsOwned = new ArrayList<TerrainType>(); 
		tokenOwned = new ArrayList<TokenNumber>();
	}

	/**
	 * Updates the information of the player
	 * @param victoryPoints
	 * @param name
	 * @param status
	 * @param color
	 * @param resources
	 */
	@Override
	public void update(int victoryPoints, String name, String status, PlayerColor color,
			ArrayList<ResourceType> resources, int largestArmy, ArrayList<DevelopmentCardType> developmentCards) {
		catan.getGameManager().getApp().closeAllPopUpStages();
		this.victoryPoints = victoryPoints;
		this.name = name;
		this.status = status;
		this.playerColor = color;
		this.hand.updateHand(resources);
		this.resCardsCount = resources.size();
		this.knightCount = largestArmy;
		this.devCards = developmentCards; 
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				playerControl.updateHandView();
				playerControl.disableAllButtons();
				switch(status){
					case Settings.DORF_BAUEN : buildSettlement(); break;
					case Settings.STRASSE_BAUEN: buildRoad(); break;
					case Settings.WUERFELN: rollDice(); break;
					case Settings.KARTEN_ABGEBEN: discardCard(); break;
					case Settings.RAEUBER_VERSETZEN: setRobber();
						endTurn(); 
						break;
					case Settings.HANDELN_BAUEN: 
						play();
						buildingPhaseEnd = true;
						break;
					case Settings.WARTEN: if(previousStatus.equals(Settings.KARTEN_ABGEBEN))catan.getGameManager().getApp().closeAllPopUpStages();
				}
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
			
		});
		try {
			LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		
	}
	
	/**
	 * Called when the bot has to "Trade or build"
	 */
	protected void play() {
		
		
		pause(1);
		
		
		
		if (this.hand.canBuyCity()){
			if (!this.getSettlements().isEmpty()){
				buildCity(); 
			}
		}
		if (this.hand.canBuySettlement()){
			List<Vertex> targets = verticesConnected;
			Collections.shuffle(targets);
			for (Vertex target : targets){
				if (canBuildSettlement(target)){
					buildSettlement(target);
					break;
				}
			}
		}
		if (this.hand.canBuyRoad()) {
			boolean targetFound= false;
			List <Edge> targetedEdge = this.getLrPath();
			Collections.reverse(targetedEdge);
			for (Edge target : targetedEdge){
				for(Vertex v : target.getVertices()){
					for(Edge e: v.getEdges()){
						if(canBuildRoad(e)){
							buildRoad(e);
							targetFound = true;
							break;
						}
						if(targetFound)break;
					}
					if(targetFound)break;
				}
				if(targetFound)break;
			}
		}
		
		tradeMaritime();
		
		
		endTurn();
		
	}

	/**
	 * Realizes the maritime trade of a bot
	 */
	private void tradeMaritime() {
		for(ResourceType res: this.getHand().getResCards()){
			if(Collections.frequency(this.getHand().getResCards(), res) > 3){
				int[] request = {0,0,0,0,0};
				int[] offer = {0,0,0,0,0};
				ResourceType req = checkWhatToTrade();
				List<ResourceType> resourceType = Arrays.asList(ResourceType.values());
				for(int i = 0; i<5;i++){
					if(req.equals(resourceType.get(i))){
						request[i] = 1;
					}
					if(res.equals(resourceType.get(i))){
						offer[i] = 1;
						offer[i] = this.getMaritimePrices(req)[i];
					}
				}
				catan.getGameManager().getClient().sendMaritimeTrade(offer, request);
				
			}
		}
		
	}
	
	/**
	 * Method for maritime trade (choose requested resource type)
	 * @return the selected resource type 
	 */
	private ResourceType checkWhatToTrade() {
		List<ResourceType> resourceType = Arrays.asList(ResourceType.values());
		for(ResourceType res : resourceType){
			if(Collections.frequency(this.getHand().getResCards(), res) == 0){
				return res;
			}
		}
		Collections.shuffle(resourceType);
		return resourceType.get(0);
		
	}

	/**
	 * When a 7 is getting rolled and the Bot has more than 7 cards 
	 * he has to discard cards with this method
	 */
	public void discardCard() {
		LogMaster.log("[Bot]Bot "+ name + " is trying to discard cards"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		int mustGiven = this.getResCards().size()/2;
		
	
		int lumberCount = 0 ;
		int brickCount = 0;
		int woolCount = 0;
		int grainCount = 0;
		int oreCount = 0;
		
		ArrayList<ResourceType> resCards = hand.getResCards();
		Collections.shuffle(resCards);
		
		for (ResourceType resource : resCards){
			switch (resource){
			case LUMBER	: lumberCount++;
			break;
			case BRICK	: brickCount++;
			break;
			case WOOL	: woolCount++;
			break;
			case GRAIN	: grainCount++;
			break;
			case ORE	: oreCount++;
			break;
			}
			mustGiven --;
			if (mustGiven == 0) break;
		}
		
		
		catan.getGameManager().getClient().sendGiveCards(lumberCount, 
				brickCount, woolCount, grainCount, oreCount);
		
	}
	
	/**
	 * When the Bot rolled a 7 he can rob another player this is the method he uses
	 */
	public void robbingVictim(Tile tile, ArrayList<Player> players) {
		final boolean SEVEN_DICED = status.equals(Settings.RAEUBER_VERSETZEN);
		LogMaster.log("[Bot]Bot "+ name + " is trying to rob"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		if(players.size() == 0){
			if(SEVEN_DICED) catan.getGameManager().getClient().sendSetRobber(tile, -1);
			else catan.getGameManager().getClient().sendPlayKnight(tile, -1);
		}
		else if(players.size() == 1){
			if(SEVEN_DICED) catan.getGameManager().getClient().sendSetRobber(tile, players.get(0).getPlayerID());
			else catan.getGameManager().getClient().sendPlayKnight(tile, players.get(0).getPlayerID());
		}
		else {
			int index = new Random().nextInt(players.size());
			if(SEVEN_DICED) catan.getGameManager().getClient().sendSetRobber(tile, players.get(index).getPlayerID());
			else catan.getGameManager().getClient().sendPlayKnight(tile, players.get(index).getPlayerID());
		}
	}

	/**
	 * Rolls the dice
	 */
	private void rollDice() {
		catan.getGameManager().getClient().sendRollDice();
		
	}

	/**
	 * Ends the turn of the Bot
	 */
	private void endTurn() {
		LogMaster.log("[Bot]Bot "+ name + " is trying to end the turn"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		active = false;
		catan.getGameManager().getClient().sendEndTurn();
	}

	/**
	 * Builds a Road for the Bot
	 */
	private void buildRoad() {
		pause(1);
		LogMaster.log("[Bot]Bot "+ name + " is trying to build a Road"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		checkAvailableEdges();
		Collections.shuffle(availableEdges);
		Edge space = availableEdges.get(0);
			
		space.getCatan().getGameManager().getClient().sendConstructRoad(space);
		for(Vertex vertex : space.getVertices()){
			if(vertex.getOccupyingBuilding() != null){
				if(!verticesConnected.contains(vertex)) verticesConnected.add(vertex);
			}
		}
		availableEdges.clear();	
		}
	
	/**
	 * Builds a Road for the Bot
	 */
	private void buildRoad(Edge e) {
		LogMaster.log("[Bot]Bot "+ name + " is trying to build a Road"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		Edge space = e;
		space.getCatan().getGameManager().getClient().sendConstructRoad(space);
		}
		

	/**
	 * Builds a settlment for the Bot
	 */
	private void buildSettlement() {
		pause(1);
		checkAvailableVertex();
		brain.analyzeVertices();
		
		boolean canBuildSettlement = false;
		int i = 0;
		while(!canBuildSettlement){
			LogMaster.log("[Bot]Bot "+ name + " is trying to build Settlement" +" [Thread : "+ Thread.currentThread().getId() + "]");
			Vertex space = brain.getRankedVertex().get(i);
			if(canBuildSettlement(space)){
				//System.out.println(ConvertMaster.convertVertex(space));
				//if(space == null) System.out.println("space is null");
				space.getCatan().getGameManager().getClient().sendConstructSettlement(space);
				if (verticesConnected.contains(space)) verticesConnected.remove(space);
				verticesOwned.add(space);
				for(Tile hex :space.getHexes()){
					tokenOwned.add(hex.getToken());
					terrainsOwned.add(hex.getTerrainType());
				}
				canBuildSettlement = true;
			}
			i++;
		}
	}
	
	
	/**
	 * Builds a settlment for the Bot
	 */
	private void buildCity() {
			
			LogMaster.log("[Bot]Bot "+ name + " is trying to build City" +" [Thread : "+ Thread.currentThread().getId() + "]");
			int index = (int)(Math.random()* this.getSettlements().size());
			Settlement space = this.getSettlements().get(index);
			
			this.getCatan().getGameManager().getClient().sendConstructCity(space.getVertex());
		}
	
	/**
	 * Builds a settlment for the Bot
	 */
	private void buildSettlement(Vertex v) {
		
			LogMaster.log("[Bot]Bot "+ name + " is trying to build Settlement" +" [Thread : "+ Thread.currentThread().getId() + "]");
			Vertex space = v;
			if(canBuildSettlement(space)){
				space.getCatan().getGameManager().getClient().sendConstructSettlement(space);
				verticesOwned.add(space);
				for(Tile hex :space.getHexes()){
					tokenOwned.add(hex.getToken());
				
		}
			}
	}
		

	/**
	 * check empty vertices
	 * 
	 */
	public void checkAvailableVertex() {
		if(!availableVertices.isEmpty()) {
			for (Vertex vertex:catan.getIsland().getVertices()){
				boolean zero = true;
				if (!vertex.isOccupied()){
					for(Edge edge : vertex.getEdges()){
						if (edge.getOccupyingBuilding() != null) 
							zero = false;
							break;
						}
				}
				else zero = false;
				if (zero == false) availableVertices.remove(vertex);
			}
			
		}
		else{
			for (Vertex vertex:catan.getIsland().getVertices()){
				boolean zero = true;
				if (!vertex.isOccupied()){
					for(Edge edge : vertex.getEdges()){
						if (edge.getOccupyingBuilding() != null) 
							zero = false;
					break;
					}
				
				}
				else zero = false;
				if (zero == true) availableVertices.add(vertex);
			}
		}
	}
	/**
	 * check empty edges
	 * 
	 */
	public void checkAvailableEdges() {
			int index = catan.getPlayerClient().getSettlements().size() - 1;
			Settlement lastSettlement = catan.getPlayerClient().getSettlements().get(index);
			for (Edge edge: lastSettlement.getVertex().getEdges() ){
				if (!edge.isOccupied()){
					availableEdges.add(edge);
				}
			}
	}
	
	/**
	 * Lets the Bot set the robber
	 */
	private void setRobber() {

		LogMaster.log("[Bot]Bot "+ name + " is trying to set the robber"+" [Thread : "+ Thread.currentThread().getId() + "]" );
		brain.analyzeThreats();
		availableHexes.clear();
		for(Tile hex :catan.getIsland().getHexes()){
			boolean myHex = false;
			if(!hex.getHasRobberOnIt()){
				for(Vertex vertex :hex.getVertices()){
					if(vertex.isOccupied()){
						Player owner = vertex.getOccupyingBuilding().getOwner();
						if(owner == catan.getPlayerClient()){
							myHex = true;
							break;
						}
					}
				}
				
				if(myHex == false){ 
						availableHexes.add(hex);
				}
			}
		}
		
		brain.analyzeHexesToRob();
		Tile newRobberPos = brain.getRankedHexes().get(0);
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		for(Vertex vertex :newRobberPos.getVertices()){
			if(vertex.isOccupied()){
				Player owner = vertex.getOccupyingBuilding().getOwner();
				if(!players.contains(owner) && owner != catan.getPlayerClient()){
					players.add(owner);
				}
			}
		}
		robbingVictim(newRobberPos,players);
	}
	
	/**
	 * pause robot brain recreating human way of thinking
	 * @param seconds
	 */
	private void pause(int seconds){
		try {
			LogMaster.log("[Thr]Thread sleeps for "+Settings.SLEEP_BOT*seconds+"ms [Thread : "+ Thread.currentThread().getId() + "]");
		    Thread.sleep(Settings.SLEEP_BOT * seconds);                
		} catch(InterruptedException ex) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + ex.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		    Thread.currentThread().interrupt();
		}		
	}

	/**
	 * @return availableVertices
	 * 
	 */
	public ArrayList<Vertex> getAvailableVertices() {
		return availableVertices;
	}
	/**
	 * @return verticesOwned
	 * 
	 */
	public ArrayList<Vertex> getVerticesOwned() {
		return verticesOwned;
	}
	/**
	 * @return terrainsOwned
	 * 
	 */
	public ArrayList<TerrainType> getTerrainsOwned() {
		return terrainsOwned;
	}
	/**
	 * @return tokenOwned
	 * 
	 */
	public ArrayList<TokenNumber> getTokenOwned() {
		return tokenOwned;
	}
	
	/**
	 * Get the available tiles
	 * @return availableHexes
	 */
	public ArrayList<Tile> getAvailableHexes() {
		return availableHexes;
	}


	/**
	 * returns true if a player is a bot
	 * 
	 */
	public boolean isBot(){
		return true;
	}

	/**
	 * Get the value of buildingPhaseEnd
	 * @return buildingPhaseEnd
	 */
	public boolean isBuildingPhaseEnd() {
		return buildingPhaseEnd;
	}

}
