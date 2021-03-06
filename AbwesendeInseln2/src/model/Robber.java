package model;

import view.RobberSkin;

/**
 * The Robber
 * @author Stefanie Kloss
 *
 */
public class Robber {
	
	private Tile tile;
	private RobberSkin skin;
	
	/**
	 * Constructor
	 * @param tile tile on which the robber sits
	 * @param skin view of the robber
	 */
	public Robber(Tile tile, RobberSkin skin){
		this.tile = tile;
		this.skin = skin;
	}
	
	/**
	 * @return the tile
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return the skin
	 */
	public RobberSkin getSkin() {
		return skin;
	}

	/**
	 * @param tile the tile to set
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
