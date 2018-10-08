package model;

import javafx.scene.shape.Shape;

/**
 * Represent a game piece
 * 
 * @author Daniel Panangian
 */
public abstract class Building {
	/**
	 * Type of game pieces
	 * 
	 */
	public enum BuildingType {
		Road,City,Settlement;
	}
	
		protected Player owner;
		protected BuildingType buildingType;
		
		/**
		 * Checks if the roads are connected
		 */
		public void checkConnectedRoads() {	
		}
		
		/**
		 * Checks if an edge has connected roads
		 * @param edge
		 * @return
		 */
		public int checkConnectedRoadsFrom(Edge edge) {
			return 0;
			
		}
		
		/**
		 * checks if an edge has connected roads to this edge
		 * @param edge
		 * @return
		 */
		public int checkConnectedRoadsTo(Edge edge) {
			return 0;
			
		}
		/**
		 *Check if a player is the owner of a game piece
		 *@param player
		 *@return True if the player is the owner, otherwise false
		 */
		public boolean isOwner(Player player) {
			return owner.getPlayerID() == player.getPlayerID();
		}

		/**
		 *Check if a player is the owner of a game piece
		 *@param player
		 *@param building
		 *@return True if the player is the owner, otherwise false
		 */
		public static boolean isOwner(Player player, Building building) {
			return building != null && building.isOwner(player);
		}

		/**
		 * Getter for Connected Roads
		 * @return connectedRoads
		 */
		public int getConnectedRoads() {
			return 0;
		}

		/**
		 *Get the owner's color
		 *@return color of the owner
		 */
		public PlayerColor getColor() {
		
			return owner.getPlayerColor();
		}

		/**
		 * Getter for SettlementSkin
		 * @return null
		 */
		public Shape getSettlementSkin() {
			return null;
		}

		/**
		 *Get the owner of the game piece
		 * @return owner
		 */
		public Player getOwner() {
			return owner;
		}

		/**
		 * Getter for Buildingtype
		 * @return buildingType
		 */
		public BuildingType getBuildingType() {
			return buildingType;
		}


		
	}

