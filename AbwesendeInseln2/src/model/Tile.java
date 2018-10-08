package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.LogMaster;
import view.TileSkin;


/**
 * Describes a tile in shape of a hexagon. Defined by it's TerrainType and position. 
 * @author Daniel Panangian, Stefanie Kloss, 
 */
public class Tile {
	
	private Catan catan;
	/** Vertices of the Tile, starting from top going clockwise */
	private List<Vertex> vertices;
	private TerrainType terrainType;
	private boolean hasRobberOnIt;
	private TokenNumber token;
	private TileSkin skin; 
	private int row;
	
	/**
	 * Position in the row
	 */
	private int pos;
	
	
	/**
	 * Constructor
	 * @param row
	 * @param pos
	 * @param terrain
	 * @param token
	 * @param hasRobberOnIt
	 * @param catan
	 */
	public Tile(int row, int pos, TerrainType terrain, TokenNumber token, boolean hasRobberOnIt, Catan catan) {
		
		this.catan = catan;
		this.row = row;
		this.pos = pos;
		this.terrainType = terrain;
		this.token = token;
		this.hasRobberOnIt = hasRobberOnIt;
		skin = new TileSkin(this);
		vertices = new ArrayList<Vertex>();
		
	}
	
	public void addVertices(Vertex... vertex) {
		vertices.addAll(Arrays.asList(vertex));
	}

	/**
	 * Relocates Robber when TileSkin clicked on 
	 * Gives out information about the tile when clicked on with the mouse
	 */
	public void relocateRobber() {
			
		ArrayList<Player> players = new ArrayList<Player>();
		for(Vertex vertex :vertices){
			if(vertex.isOccupied()){
				Player owner = vertex.getOccupyingBuilding().getOwner();
				if(!players.contains(owner) && owner != catan.getPlayerClient()){
					players.add(owner);
				}
			}
		}
		catan.getPlayerClient().robbingVictim(this, players);
		
		LogMaster.log("[Mod]"+terrainType +" hexagon number "+token+ " [Thread : "+ Thread.currentThread().getId() + "]");
	}

	/**
	 * @return the skin of the tile
	 */
	public TileSkin getSkin() {
		return skin;
	}
	
	/**
	 * @return the position in the row
	 */
	public int getPos() {
		return pos;
	}
	
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return hasRobberOnIt
	 */
	public boolean getHasRobberOnIt(){
		return hasRobberOnIt;
	}
	
	/**
	 * @return the terrainType
	 */
	public TerrainType getTerrainType() {
		return terrainType;
	}

	/**
	 * @return the token on the tile
	 */
	public TokenNumber getToken() {
		return token;
	}
	
	/**
	 * Getter vor vertices
	 * @return
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * @return the catan
	 */
	public Catan getCatan() {
		return catan;
	}

	/**
	 * set the boolean hasRobberOnIt
	 * @param hasRobberOnIt
	 */
	public void setRobber(boolean hasRobberOnIt) {
		this.hasRobberOnIt = hasRobberOnIt;
	}

	/**
	 * Setter for TerrainType
	 * @param newType
	 */
	public void setTerrainType(TerrainType newType){
		terrainType = newType;
	}
	
	/**
	 * Setter for Tokennumber
	 * @param newNumber
	 */
	public void setTokenNumber(TokenNumber newNumber){
		token = newNumber;
	}
	
}
