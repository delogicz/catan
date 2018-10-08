package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import jsonstructures.ConvertMaster;
import view.HarbourSkin;
import view.IslandSkin;

/**
 * Represents the island of Catan. Contains all tiles, edges and vertices.
 * @author Daniel Panangian, Stefanie Kloss
 */
public class Island {
	
	private List<Vertex> vertices;
	private List<Edge> edges;
	
	private Catan catan;
	
	private final IslandSkin skin;
	public static final int[] hexagonsPerRow = Settings.HEXAGON_LAYOUT;
	public static final int[] verticesPerRow = Settings.VERTEX_LAYOUT;

	private ArrayList<Tile> boardHexes;
	
	private List<TerrainType> gameHexTerrains;
	private List<TokenNumber> tokens;
	private Harbour[] harbours;
	private int[] harbourPositions;
	private Map<Vertex, Harbour> harbourLocation ;
	
	private Robber robber;
	
	/**
	 * Constructor for Server
	 * @param catan the catan of the island
	 * @param normalTokenSequence true if player wants to play with the normal build-up
	 */
	public Island(Catan catan, boolean normalTokenSequence) {
		
		this.catan = catan;
		if(!normalTokenSequence){
			while(boardHexes == null){
				initializeHexTerrains();
				initializeNumberTokensRandom();
				addHexesToBoard();
			}
		}
		
		else{
			initializeHexTerrains();
			initializeNumberTokensSequence();
			addHexesToBoardWithTokenSequence();
		}
		
		addVertices();
		addVerticesToHexes();
		addVerticesToEdges();
		addHexesToVertices();
		addEdgesToVertices();
				
		this.harbours = initializeHarbours();
		while(harbourPositions == null){
			this.harbourPositions = initializeHarbourPositions();
		}
		addHarboursToVertices();
	
		this.skin = new IslandSkin(this);
		ConvertMaster.setServerIsland(this);

	}

	/**
	 * Constructor for Client
	 * @param catan the catan of the island
	 * @param terrains the predefined TerrainTypes
	 * @param tokens the predefined TokenNumbers
	 * @param harbours the predefined Harbours
	 * @param harbourPositions the predefined positions of the Harbours
	 */
	public Island(Catan catan, TerrainType[] terrains, TokenNumber[] tokens, Harbour[] harbours, int[] harbourPositions) {
		this.catan = catan;
		this.harbours = harbours;
		this.harbourPositions = harbourPositions;
		addHexesToBoard(terrains, tokens);
		addVertices();
		addVerticesToHexes();
		addVerticesToEdges();
		addHexesToVertices();
		addEdgesToVertices();
		addHarboursToVertices();
		this.skin = new IslandSkin(this);
		addHarboursToBoard();
		ConvertMaster.setClientIsland(this);
	}
	
	/**
	 * add adjacent hexes as list to each vertex
	 *
	 */
	private void addHexesToVertices() {
	
		for(Tile hex : boardHexes) {
			List<Vertex> vertices = hex.getVertices();
			for(Vertex vertex : vertices){
				vertex.addHex(hex);
			}
		}
		
	}

	/**
	 * Fills "List<Edge> edges" of each vertices with connected edges
	 */
	private void addEdgesToVertices() {
		for(Edge edge : edges) {
			edge.getVertexFrom().addEdge(edge);
			edge.getVertexTo().addEdge(edge);
		}
	}
	
	/**
	 * Adds six vertices to each tile/ hexagon.
	 * Order:      1
	 *          /     \
	 *         0       2
	 *         |       |
	 *         3       5
	 *          \     /
	 *             4
	 */
	private void addVerticesToHexes() {

		int pos = 0;
		int topOffset = 0;
		int bottomOffset = 0;
		for (int vertexOrder = 0; vertexOrder < Settings.AMOUNT_OF_VERTEX_IN_HEX; vertexOrder++) {
			bottomOffset += verticesPerRow[vertexOrder];
			for (int hexagonsRow = 0; hexagonsRow < hexagonsPerRow[vertexOrder]; hexagonsRow++) {
				getHexagon(pos).addVertices(getVertex(2 * hexagonsRow + topOffset), getVertex(2 * hexagonsRow + 1 + topOffset),
						getVertex(2 * hexagonsRow + 2 + topOffset));

				getHexagon(pos).addVertices(getVertex(2 * hexagonsRow + 1 + bottomOffset), getVertex(2 * hexagonsRow + 2 + bottomOffset),
						getVertex(2 * hexagonsRow + 3 + bottomOffset));
				pos++;
			}
			topOffset += verticesPerRow[vertexOrder];
			if (vertexOrder == 2) {
				topOffset++;
			}
			if (vertexOrder == 1) {
				bottomOffset--;
			}
		}
	}
	
	/**
	 * Creates all 54 vertices and adds them to the list "vertices"
	 */
	private void addVertices() {
		vertices = new ArrayList<Vertex>();
		for (int verticesColumn = 0; verticesColumn < 6; verticesColumn++) {
			for (int verticesRow = 0; verticesRow < verticesPerRow[verticesColumn]; verticesRow++) {
				vertices.add(new Vertex(verticesColumn, verticesRow, catan));
				
			}
		}
	}
	
	/**
	 * Assigns two vertices to each edge ("to" and "from" attributes of Edge)
	 */
	private void addVerticesToEdges() {
		// make connections and edges
		edges = new ArrayList<Edge>();
		Vertex to;
		for (int verticesRow = 0; verticesRow < 6; verticesRow++) {
			for (int vertexOrder = 0; vertexOrder < verticesPerRow[verticesRow]; vertexOrder++) {
				Vertex from = getVertex(verticesRow, vertexOrder);

				if (vertexOrder < verticesPerRow[verticesRow] - 1) { // not last in row
	
					to = getVertex(verticesRow, vertexOrder + 1);
					edges.add(new Edge(from, to,catan));
					from.addConnectedVertex(to);
				}
				if (vertexOrder > 0) { // not first in row
					to = getVertex(verticesRow, vertexOrder - 1);
					from.addConnectedVertex(to);
				}
				if (verticesRow < 5) { // not last row
					int offset = (verticesPerRow[verticesRow + 1] - verticesPerRow[verticesRow]) / 2;
					if ((vertexOrder % 2 == 0 && verticesRow < 3) || (vertexOrder % 2 == 1 && verticesRow >= 3)) { // vertical
						to = getVertex(verticesRow + 1, vertexOrder + offset);
						edges.add(new Edge(from, to,catan));
						from.addConnectedVertex(to);
						to.addConnectedVertex(from);
					}
				}
			}
		}
	}
	
	/**
	 * For the server
	 * Creates all 19 tiles and adds them to "boardHexes"
	 * Sets "boardHexes = null" if process wasn't successful
	 * Call if tokens are placed randomly (but 6 and 8 don't get located next to each other)
	 */
	private void addHexesToBoard() {
		boolean[] sixOrEightPossible = new boolean[tokens.size() + 1];  
		for(int tokenOrder = 0; tokenOrder < tokens.size() + 1; tokenOrder++){  
			sixOrEightPossible[tokenOrder] = true;
		}

		boardHexes = new ArrayList<Tile>();
		
		int position = 0;
		for (int tokenOrder = 0; tokenOrder < 5; tokenOrder++) {
			for (int hexOrder = 0; hexOrder < hexagonsPerRow[tokenOrder]; hexOrder++) {
				TerrainType terrain = getRandomGameHexTerrain();
				TokenNumber token = null;
				if (terrain == TerrainType.DESERT) {
					token = TokenNumber.SEVEN;
					boardHexes.add(new Tile(tokenOrder, hexOrder, terrain, token, true, catan));
					LogMaster.log("[UI]"+terrain.name() +", "+ token.name() + " at position " + position+ " [Thread : "+ Thread.currentThread().getId() + "]");
				} 
				else {
					token = getNextValidTokenNumber(sixOrEightPossible, position);
					if(token == null){
						boardHexes = null;	
						return;
					}
					boardHexes.add(new Tile(tokenOrder, hexOrder, terrain, token, false, catan));
					LogMaster.log("[UI]" + terrain.name() +", "+ token.name() + " at position " + position + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
				position++;
			}
		}
	}
	
	/**
	 * For the server 
	 * Creates all 19 tiles and adds them to "boardHexes"
	 * Call if tokens shall be placed in predefined order
	 */
	private void addHexesToBoardWithTokenSequence(){
		
		boardHexes = new ArrayList<Tile>();
		
		for (int row = 0; row < 5; row++) {
			for (int posInRow = 0; posInRow < hexagonsPerRow[row]; posInRow++) {
				
				TerrainType terrain = getRandomGameHexTerrain();
				TokenNumber token = null;
				
				boardHexes.add(new Tile(row, posInRow, terrain, token, false, catan));
			}
		}
		
		// spirally spreading the tokens on the tiles 
		int randomCorner = (int)(Math.random()*6);
		
		int[] SPIRALLY_SEQUENCE = null;
		
		switch(randomCorner){
		case 0: SPIRALLY_SEQUENCE = Settings.SEQUENCE_UPPER_LEFT_CORNER; break;
		case 1: SPIRALLY_SEQUENCE = Settings.SEQUENCE_UPPER_RIGHT_CORNER; break;
		case 2: SPIRALLY_SEQUENCE = Settings.SEQUENCE_MIDDLE_LEFT_CORNER; break;
		case 3: SPIRALLY_SEQUENCE = Settings.SEQUENCE_MIDDLE_RIGHT_CORNER; break;
		case 4: SPIRALLY_SEQUENCE = Settings.SEQUENCE_BOTTOM_LEFT_CORNER; break;
		case 5: SPIRALLY_SEQUENCE = Settings.SEQUENCE_BOTTOM_RIGHT_CORNER; break;
		}
		
		for(int hexCount = 0; hexCount < Settings.AMOUNT_OF_HEXES; hexCount++){
			
			Tile currentTile = boardHexes.get(SPIRALLY_SEQUENCE[hexCount]);
			if(currentTile.getTerrainType() == TerrainType.DESERT){
				currentTile.setTokenNumber(TokenNumber.SEVEN);
				currentTile.setRobber(true);
			}
			else currentTile.setTokenNumber(getNextTokenNumber());
			LogMaster.log("[UI]"+ currentTile.getTerrainType().name() +", "+ currentTile.getToken().name() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}

	}
	
	/**
	 * For the Clients
	 * Adds 19 Tiles that have been sent from the server
	 */
	private void addHexesToBoard(TerrainType[] terrains, TokenNumber[] tokens) {
		boardHexes = new ArrayList<Tile>();
		int nextInArray = 0;
		for (int hexesRow = 0; hexesRow < 5; hexesRow++) {
			for (int hexOrder = 0; hexOrder < hexagonsPerRow[hexesRow]; hexOrder++) {
				TerrainType nextTerrain = terrains[nextInArray];
				TokenNumber nextToken = tokens[nextInArray];
				if (nextToken == TokenNumber.SEVEN) {
					boardHexes.add(new Tile(hexesRow, hexOrder, nextTerrain, nextToken, true, catan));
				} else {
					boardHexes.add(new Tile(hexesRow, hexOrder, nextTerrain, nextToken, false, catan));
				}
				nextInArray++;
			}
		}
	}
	
	/**
	 * Creates the HarbourSkins of all harbours
	 */
	private void addHarboursToBoard(){
		for(Vertex v : vertices){
			Harbour harbour = v.getHarbour();
			if(harbour != null){
				new HarbourSkin(harbour,v,this.getSkin());
			}
		}	
	}
	
	/**
	 * For server
	 * Fills the "tokens"-list with 18 needed instances of TokenNumber (random order)
	 */
	private void initializeNumberTokensRandom() {
		this.tokens = new ArrayList<TokenNumber>();
		tokens.addAll(Arrays.asList(new TokenNumber[] { TokenNumber.TWO, TokenNumber.THREE, TokenNumber.THREE,
				TokenNumber.FOUR, TokenNumber.FOUR, TokenNumber.FIVE, TokenNumber.FIVE, TokenNumber.SIX,
				TokenNumber.SIX, TokenNumber.EIGHT, TokenNumber.EIGHT, TokenNumber.NINE, TokenNumber.NINE,
				TokenNumber.TEN, TokenNumber.TEN, TokenNumber.ELEVEN, TokenNumber.ELEVEN, TokenNumber.TWELVE }));
		Collections.shuffle(tokens);
	}
		
	/**
	 * For server
	 * Fills the "tokens"-list with 18 needed instances of TokenNumber (special sequence)
	 */
	private void initializeNumberTokensSequence() {

		this.tokens = new ArrayList<TokenNumber>();
		this.tokens.addAll(Arrays.asList(new TokenNumber[] { 
				           TokenNumber.FIVE, TokenNumber.TWO, TokenNumber.SIX, TokenNumber.THREE, TokenNumber.EIGHT, 
						   TokenNumber.TEN, TokenNumber.NINE, TokenNumber.TWELVE, TokenNumber.ELEVEN,  TokenNumber.FOUR,
						   TokenNumber.EIGHT, TokenNumber.TEN, TokenNumber.NINE, TokenNumber.FOUR, TokenNumber.FIVE, 
						   TokenNumber.SIX, TokenNumber.THREE, TokenNumber.ELEVEN }));
		
	}
	
	/**
	 * For server
	 * @param sixOrEightPossible Array that keeps updated about where 6 and 8 can be placed on
	 * @param position The position of the tile for which the token gets chosen
	 * @return The token to be placed onto the tile, null if there is no possible token left
	 */
	private TokenNumber getNextValidTokenNumber(boolean[] sixOrEightPossible, int position){

		TokenNumber currentToken = tokens.get(0);
		int nextSwap = 1;
		while(!sixOrEightPossible[position] && (currentToken == TokenNumber.EIGHT || currentToken == TokenNumber.SIX)){
			if(nextSwap < tokens.size()){        // if there is another swap-option
				Collections.swap(tokens, 0, nextSwap);
				nextSwap++;
			}
			else{                                      // else retry initialization
				return null;
				}
			currentToken = tokens.get(0);
		}
		
		if(currentToken == TokenNumber.EIGHT || currentToken == TokenNumber.SIX){
			switch(position){
			case 0: sixOrEightPossible[1] = false; sixOrEightPossible[3] = false; sixOrEightPossible[4] = false; break;
			case 1: sixOrEightPossible[2] = false; sixOrEightPossible[4] = false; sixOrEightPossible[5] = false; break;
			case 2: sixOrEightPossible[5] = false; sixOrEightPossible[6] = false; break;
			case 3: sixOrEightPossible[4] = false; sixOrEightPossible[7] = false; sixOrEightPossible[8] = false; break;
			case 4: sixOrEightPossible[5] = false; sixOrEightPossible[8] = false; sixOrEightPossible[9] = false; break;      
			case 5: sixOrEightPossible[6] = false; sixOrEightPossible[9] = false; sixOrEightPossible[10] = false; break;    
			case 6: sixOrEightPossible[10] = false; sixOrEightPossible[11] = false; break;
			case 7: sixOrEightPossible[8] = false; sixOrEightPossible[12] = false; break;
			case 8: sixOrEightPossible[9] = false; sixOrEightPossible[12] = false; sixOrEightPossible[13] = false; break;
			case 9: sixOrEightPossible[10] = false; sixOrEightPossible[13] = false; sixOrEightPossible[14] = false; break;
			case 10: sixOrEightPossible[11] = false; sixOrEightPossible[14] = false; sixOrEightPossible[15] = false; break;
		 	case 11: sixOrEightPossible[15] = false; break;
			case 12: sixOrEightPossible[13] = false; sixOrEightPossible[16] = false; break;
			case 13: sixOrEightPossible[14] = false; sixOrEightPossible[16] = false; sixOrEightPossible[17] = false; break;
			case 14: sixOrEightPossible[15] = false; sixOrEightPossible[17] = false; sixOrEightPossible[18] = false; break;
			case 15: sixOrEightPossible[18] = false; break;
			case 16: sixOrEightPossible[17] = false; break;
			case 17: sixOrEightPossible[18] = false; break;
			}
		}
		return getNextTokenNumber();
	}

	/**
	 * Fills an array with 9 needed instances of Harbour (random order)
	 * @return The filled harbour-array
	 */
	private Harbour[] initializeHarbours() {
		ArrayList<Harbour> harbourList = new ArrayList<Harbour>();
		harbourList.addAll(Arrays.asList(new Harbour[] { Harbour.THREETOONE, Harbour.THREETOONE, Harbour.THREETOONE, 
				Harbour.THREETOONE, Harbour.TWOTOONEBRICK, Harbour.TWOTOONELUMBER, Harbour.TWOTOONEGRAIN, 
				Harbour.TWOTOONEWOOL, Harbour.TWOTOONEORE,}));
		Collections.shuffle(harbourList);
		Harbour[] harbours = new Harbour[9];
		harbourList.toArray(harbours);
		return harbours;
	}
	
	/**
	 * Determines the positions of the harbours 
	 * @return the new created Edges Array
	 */
	private int[] initializeHarbourPositions() {
		ArrayList<Integer> unpossibleEdges = new ArrayList<Integer>();
		
		int[] outerEdges = Settings.OUTEREDGES_POSITION;
		int[] harbourPositions = new int[9];
		
		for(int harbourPos = 0; harbourPos < 9; harbourPos++){
			Random rand = new Random();
			int randInt = rand.nextInt(outerEdges.length);
			while(unpossibleEdges.contains(randInt)){
				randInt = rand.nextInt(outerEdges.length);
				if(unpossibleEdges.size() >= outerEdges.length){
					return null;
				}
			}
			harbourPositions[harbourPos] = outerEdges[randInt];
			unpossibleEdges.add(randInt);
			
			//Make sure that harbours aren't on two edges that lie next to each other	
			if(randInt > 1 && randInt < outerEdges.length - 2){
				unpossibleEdges.add(randInt+1);
				unpossibleEdges.add(randInt+2);
				unpossibleEdges.add(randInt-1);
				unpossibleEdges.add(randInt-2);
			}
			else if(randInt == 0){
				unpossibleEdges.add(randInt+1);
				unpossibleEdges.add(randInt+2);
				unpossibleEdges.add(outerEdges.length-1);
				unpossibleEdges.add(outerEdges.length-2);
			}
			else if(randInt == 1){
				unpossibleEdges.add(randInt+1);
				unpossibleEdges.add(randInt+2);
				unpossibleEdges.add(randInt-1);
				unpossibleEdges.add(outerEdges.length-1);
			}
			else if(randInt == outerEdges.length-1){
				unpossibleEdges.add(0);
				unpossibleEdges.add(1);
				unpossibleEdges.add(randInt-1);
				unpossibleEdges.add(randInt-2);
			}
			else if(randInt == outerEdges.length-2){
				unpossibleEdges.add(randInt+1);
				unpossibleEdges.add(0);
				unpossibleEdges.add(randInt-1);
				unpossibleEdges.add(randInt-2);
			}
		}
		return harbourPositions;
	}
	
	/**
	 * Gets next TokenNumber in tokens and removes it from the list
	 * @return random TokenNumber
	 */
	private TokenNumber getNextTokenNumber() {
		TokenNumber token = tokens.get(0);
		tokens.remove(0);
		return token;
	}
	
	/**
	 * For server
	 * Fills the "gameHexTerrains"-list with 19 needed instances of TerrainType (random order)
	 */
	private void initializeHexTerrains() {
		
		gameHexTerrains = new ArrayList<TerrainType>();

		for (int i = 0; i < Settings.AMOUNT_OF_HILLS; i++) {
			gameHexTerrains.add(TerrainType.HILLS);
		}
		
		gameHexTerrains.add(TerrainType.DESERT);
		
		for (int i = 0; i < Settings.AMOUNT_OF_MOUNTAINS; i++) {
			gameHexTerrains.add(TerrainType.MOUNTAINS);
		}
		for (int i = 0; i < Settings.AMOUNT_OF_PASTURE; i++) {
			gameHexTerrains.add(TerrainType.PASTURE);
		}
		for (int i = 0; i < Settings.AMOUNT_OF_FIELDS; i++) {
			gameHexTerrains.add(TerrainType.FIELDS);
		}
		for (int i = 0; i < Settings.AMOUNT_OF_FOREST; i++) {
			gameHexTerrains.add(TerrainType.FOREST);
		}
		Collections.shuffle(gameHexTerrains);
	}
	
	/**
	 * Gets next TerrainType in gameHexResources and removes it from the list
	 * @return random TerrainType
	 */
	private TerrainType getRandomGameHexTerrain() {
		TerrainType terrain = gameHexTerrains.get(0);
		gameHexTerrains.remove(0);
		return terrain;
	}
	
	/**
	 * Adds harbours to the vertices
	 */
	private void addHarboursToVertices() {
		harbourLocation = new HashMap<Vertex,Harbour>();
		for(int harbourPos = 0; harbourPos < harbours.length; harbourPos++){
			edges.get(harbourPositions[harbourPos]).getVertexFrom().setHarbour(harbours[harbourPos]);
			harbourLocation.put(edges.get(harbourPositions[harbourPos]).getVertexFrom(),harbours[harbourPos] );
			edges.get(harbourPositions[harbourPos]).getVertexTo().setHarbour(harbours[harbourPos]);
			harbourLocation.put(edges.get(harbourPositions[harbourPos]).getVertexTo(),harbours[harbourPos] );
		}
	}
	
	/**
	 * Change position of the robber to a new tile
	 * @param tile the new position of the robber
	 */
	public void changeRobberPosition(Tile tile){
		tile.setRobber(true);
		this.robber.getTile().setRobber(false);
		robber.setTile(tile);
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				robber.getSkin().reposition(tile, skin);
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
	 * Moves skins of all Vertices and Edges to front
	 */
	public void moveVerticesAndEdgesToFront() {
		for(Vertex vertex: vertices){
			vertex.getSkin().getShape().toFront();
		}
		for(Edge edge: edges){
			edge.getSkin().getShape().toFront();
		}
		
	}

	/**
	 * 
	 * @return harbourLocation
	 */
	public Map<Vertex, Harbour> getHarbourLocation() {
		return harbourLocation;
	}

	/**
	 * get hex by layout of hexes (row,pos)
	 *@return hex
	 */
	public Tile getHexagon(int row, int pos) {
		if (row >= 5 || pos >= hexagonsPerRow[row]) {
			return null;
		}
		int position = 0;
		for (int hexesRow = 0; hexesRow < 5; hexesRow++) {
			for (int hexOrder = 0; hexOrder < hexagonsPerRow[hexesRow]; hexOrder++) {
				if (hexesRow == row && hexOrder == pos) {
					return boardHexes.get(position);
				}
				position++;
			}
		}
		return null;
	}
	/**
	 * get hex by position (order)
	 *@return hex
	 */
	public Tile getHexagon(int pos) {
		if (pos < Settings.AMOUNT_OF_HEXES) {
			return boardHexes.get(pos);
		}
		return null;
	}
	/**
	 * get island skin
	 *@return skin
	 */
	public IslandSkin getSkin() {
		return skin;
	}
	/**
	 * get vertex by position (order from list)
	 *@return vertex
	 */
	public Vertex getVertex(int pos) {
		return vertices.get(pos);
	}
	/**
	 * get vertex by layout of vertices (row,pos)
	 *@return vertex
	 */
	public Vertex getVertex(int row, int pos) {
		int position = 0;
		for (int verticesRow = 0; verticesRow < 6; verticesRow++) {
			for (int vertexOrder = 0; vertexOrder < verticesPerRow[verticesRow]; vertexOrder++) {
				if (verticesRow == row && vertexOrder == pos) {
					return vertices.get(position);
				}
				position++;
			}
		}
		return null;
	}
	/**
	 * get hexes
	 *@return boardHexes
	 */
	public List<Tile> getHexes() {
		return boardHexes;
	}
	/**
	 * get vertices
	 *@return vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}
	/**
	 * get edges
	 *@return edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}
	/**
	 * @return the harbours
	 */
	public Harbour[] getHarbours() {
		return harbours;
	}

	/**
	 * @return the harbourPositions
	 */
	public int[] getHarbourPositions() {
		return harbourPositions;
	}

	/**
	 * Getter for Robber
	 * @return
	 */
	public Robber getRobber(){
		return robber;
	}

	/**
	 * @return the catan
	 */
	public Catan getCatan() {
		return this.catan;
	}

	/**
	 * set robber
	 *@param robber
	 */
	public void setRobber(Robber robber) {
		this.robber = robber;
		
	}

}
