package model;

import java.util.ArrayList;

import game.Settings;

/**
 * Describes a participant in the game. Stores information the playerclient has to know about
 * @author Daniel Panangian, Stefanie Kloss, Lena Sonnleitner, Samantha Meindl
 */
public class Player {
	protected int victoryPoints;
	protected PlayerColor playerColor;
	protected int playerID;
	protected String name;
	protected String status;
	protected String imgURL;
	
	protected Catan catan;
	
	protected int connectedRoads;
	protected int resCardsCount;
	protected int devCardCount;
	
	protected int knightCount;
	
	protected boolean active;
	private ArrayList<Edge> lrPath;
	protected ArrayList<Road> roads;
	
	/**
	 * Creates a Player, all Players are Players, also bots
	 * @param playerColor
	 * @param playerId
	 * @param name
	 * @param status
	 */
	public Player(PlayerColor playerColor, int playerId, String name, String status) {
		
		this.catan = null;
		this.playerID = playerId;
		this.name = name;
		if(status != null){
			this.status = status;
		}else{
			this.status = "";
		}
		
		this.victoryPoints = 0;
		this.playerColor = playerColor;
		this.resCardsCount = 0;
		this.devCardCount = 0;
		this.connectedRoads =0;
		this.knightCount = 0;
		lrPath = new ArrayList<Edge> ();
		roads = new ArrayList<Road> ();
		
		if(playerColor != null){
			setImage();
		}
	}
	
	/**
	 * Updates the information of the player
	 * @param victoryPoints
	 * @param name
	 * @param status
	 * @param color
	 * @param unknownResources
	 * @param knights 
	 * @param devCards
	 */
	public void update(int victoryPoints, String name, String status, PlayerColor color,
			int unknownResources, int knights, int devCards) {
		this.victoryPoints = victoryPoints;
		this.name = name;
		this.status = status;
		this.playerColor = color;
		this.resCardsCount = unknownResources;
		this.knightCount = knights;
		this.devCardCount = devCards;
		switch(status){
			case Settings.WUERFELN : catan.getTrades().clear(); 
									 catan.getPlayerControl().getSkin().setTurnText(name); 
									 break;
			case Settings.DORF_BAUEN: catan.getPlayerControl().getSkin().setTurnText(name); break; 
		}
	}
	
	/**
	 * Builds a new settlement or city (handleConstruction)
	 * @param typ type of the new building
	 * @param vertex vertex on which the building is located
	 */
	public Building build(String typ, Vertex vertex, boolean server) {
		if(typ.equals(Settings.DORF)){
			Settlement newSettlement = new Settlement(vertex, this, catan.getIsland().getSkin());
			vertex.setOccupyingBuilding(newSettlement);
			vertex.setOccupied(true);
			vertex.checkIntersection();
			return newSettlement;
		}
		else if(typ.equals(Settings.STADT)){
			City newCity = new City(vertex, this, catan.getIsland().getSkin());
			vertex.setOccupyingBuilding(newCity);
			vertex.setOccupied(true);
			return newCity;
		}
		return null;
	}
	
	/**
	 * Builds a new road (handleConstruction)
	 * @param edge edge on which the road is located
	 */
	public Road build(Edge edge, boolean server) {
		Road newRoad = new Road(edge, this, catan.getIsland().getSkin());
		roads.add(newRoad);
		edge.setOccupyingBuilding(newRoad);
		edge.setOccupied(true);
		
		return newRoad;
	}
	
	/**
	 * Activates the bot
	 */
	public void activateBot() {
		
	}

	/**
	 * Adds 1 to the devCardCount
	 */
	public void addDevCard(){
		this.devCardCount++;
	}

	/**
	 * Removes 1 from the devCardCount
	 */
	public void removeDevCard() {
		this.devCardCount--;	
	}
	
	/**
	 * Use when player has costs in form of resource cards
	 */
	public void resCardsCosts(int costs){
		this.resCardsCount -= costs;
	}
	
	/**
	 * Use when player receives resource cards
	 * @param resources
	 */
	public void resCardsCountProduce(int resources) {
		this.resCardsCount += resources;
	}

	/**
	 * Getter for knightCoung
	 * @return
	 */
	public int getKnightCount() {
		return knightCount;
	}

	/**
	 * Getter for devCardCount
	 * @return
	 */
	public int getDevCardCount(){
		return this.devCardCount;
	}

	/**
	 * get player's color
	 *@return playerColor
	 */
	public PlayerColor getPlayerColor(){
		return this.playerColor;
	}

	/**
	 * get victory point
	 *@return victoryPoints
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets the playerID
	 * @return the playerID
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * @return the catan
	 */
	public Catan getCatan() {
		return catan;
	}

	/**
	 * @return the resCardsCount
	 */
	public int getResCardsCount() {
		return resCardsCount;
	}

	/**
	 * Getter for ConnectedRoads
	 * @return
	 */
	public int getConnectedRoads() {
		return connectedRoads;
	}

	/**
	 * Getter for imgURL
	 * @return
	 */
	public String getImgURL() {
		return imgURL;
	}

	/**
	 * Getter for lrPath
	 * @return
	 */
	public ArrayList<Edge> getLrPath() {
		return lrPath;
	}

	/**
	 * Getter for Roads
	 * @return
	 */
	public ArrayList<Road> getRoads() {
		
		return roads;
	}

	/**
	 * Setter for connectedRoads
	 * @param connectedRoads
	 */
	public void setConnectedRoads(int connectedRoads) {
		this.connectedRoads = connectedRoads;
	}

	/**
	 * Setter for LrPath
	 * @param lrPath
	 */
	public void setLrPath(ArrayList<Edge> lrPath) {
		this.lrPath = lrPath;
	}

	/**
	 * Setts the Image of a Player
	 */
	private void setImage() {
		switch(playerColor){
		case BLUE:
			imgURL = "/img/character1.jpg";break;
		case ORANGE:
			imgURL = "/img/character2.jpg";break;
		case RED:
			imgURL = "/img/character3.jpg";break;
		case WHITE:
			imgURL = "/img/character4.jpg";break;
		default:
			break;
		
		}
		
	}

	/**
	 * set victory point
	 *@param newVictoryPoints
	 */
	public void setVictoryPoints(int newVictoryPoints) {
		victoryPoints = newVictoryPoints;		
	}

	/**
	 * Sets the status of the player
	 * @param status the status to set
	 * @param server true if the server is calling this method
	 */
	public void setStatus(String status, boolean server) {
		this.status = status;
	}

	/**
	 * @param playerColor the playerColor to set
	 */
	public void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param catan the catan to set
	 */
	public void setCatan(Catan catan) {
		this.catan = catan;
	}

	/**
	 * Bool for bot
	 * @return
	 */
	public boolean isBot(){
		return false;
	}


}