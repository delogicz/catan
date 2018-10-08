package model;

import java.util.ArrayList;
import java.util.Collections;

import view.IslandSkin;
import view.RoadSkin;

/**
 * A road. Defined by associated edge and player.
 * @author Daniel Panangian, Stefanie Kloss
 */
public class Road extends Building {
	
	private Edge edge;
	@SuppressWarnings("unused")
	private ArrayList<Edge> edgesTo;
	@SuppressWarnings("unused")
	private ArrayList<Edge> edgesFrom;
	private int connectedRoads;
	private ArrayList<Edge> tempEdges;
	
	/**
	 * Constructor 
	 * @param edge The edge the road is built on
	 * @param player The owner of the road
	 * @param islandSkin The skin the road is built on
	 */
	public Road(Edge edge, Player player, IslandSkin islandSkin){
		this.edge = edge;
		edge.setOccupyingBuilding(this);
		initializeEdgesToFrom();
		this.owner = player;
		new RoadSkin(this, islandSkin);
		this.tempEdges = new ArrayList<Edge>();
		
		owner.setConnectedRoads(0);
		for(Road road : player.getRoads()){
			road.checkConnectedRoads();
		}
	}


	/**
	 * Constructor for Server
	 * @param edge
	 * @param player
	 */
	public Road(Edge edge, Player player){
		this.edge = edge;
		edge.setOccupyingBuilding(this);
		initializeEdgesToFrom();
		this.owner = player;
		this.tempEdges = new ArrayList<Edge>();
		
		owner.setConnectedRoads(0);
		for(Road road : player.getRoads()){
			road.checkConnectedRoads();
		}
	
	}
	
	/**
	 * Initializes the Edges "to" and "from"
	 */
	public void initializeEdgesToFrom() {
		this.edgesTo = edge.getVertexTo().getEdges();
		this.edgesFrom = edge.getVertexFrom().getEdges();
		
	}
	
	/**
	 * check largest connected roads by counting from the "vertexFrom" then the "vertexTo"
	 */
	public void checkConnectedRoads(){
		if(!tempEdges.isEmpty())tempEdges.clear();
		this.connectedRoads = checkConnectedRoadsFrom(edge) + checkConnectedRoadsTo(edge) +1;
		if(this.connectedRoads > this.owner.getConnectedRoads()){
			this.owner.setConnectedRoads(this.connectedRoads);
			this.owner.setLrPath(tempEdges);
		}
	}
	/**
	 * check largest connected roads from the "vertexFrom"
	 */
	public int checkConnectedRoadsFrom(Edge e){
		
		if(e.getOccupyingBuilding() == null){
			return -1;
		}
		if( e.getOccupyingBuilding().getOwner().getPlayerID() != this.owner.getPlayerID()){
			return -1;
		}
		if (e.getVertexFrom().getOccupyingBuilding() != null){
			if (e.getVertexFrom().getOccupyingBuilding().getOwner().getPlayerID() != this.owner.getPlayerID()) return 0;
		}
		
		if (this.edge != e){
			if(Collections.frequency(tempEdges, e) > 0) return -1;
		}
		if (e.getOccupyingBuilding().getOwner().getPlayerID() == this.owner.getPlayerID()){
		tempEdges.add(e);
		}
		
		
		
		ArrayList<Edge> tempEdgesFrom = e.getVertexFrom().getEdges();
		int i = 0;
		for(Edge edge : tempEdgesFrom){
			if (edge == e) Collections.swap(tempEdgesFrom, i, tempEdgesFrom.size() - 1);
			i++;
		}
		if (tempEdgesFrom.size() == 2 ){
			if (tempEdgesFrom.get(0) == this.edge ) return 0;
			if(tempEdgesFrom.get(0).getOccupyingBuilding() == null){
				return 0;
			}
			
				int tempConnectedRoads;

				
				if (tempEdgesFrom.get(1).getVertexFrom() != tempEdgesFrom.get(0).getVertexFrom()) {
					tempConnectedRoads = checkConnectedRoadsFrom(tempEdgesFrom.get(0));
				}
				else {
					tempConnectedRoads = checkConnectedRoadsTo(tempEdgesFrom.get(0));
				}
				return tempConnectedRoads + 1;
				
			
		}
		else{
				if (tempEdgesFrom.get(0) == this.edge || tempEdgesFrom.get(1)== this.edge ) return 0;
				if(tempEdgesFrom.get(0).getOccupyingBuilding() == null && tempEdgesFrom.get(1).getOccupyingBuilding() == null){
					return 0;
				}
					int tempConnectedRoadsLeft;
					int tempConnectedRoadsRight;
					if (tempEdgesFrom.get(2).getVertexFrom() != tempEdgesFrom.get(0).getVertexFrom()) {
						 tempConnectedRoadsLeft = checkConnectedRoadsFrom(tempEdgesFrom.get(0));
						}
					else {
						tempConnectedRoadsLeft = checkConnectedRoadsTo(tempEdgesFrom.get(0));
					}
					if (tempEdgesFrom.get(2).getVertexFrom() != tempEdgesFrom.get(1).getVertexFrom()) {
						 tempConnectedRoadsRight = checkConnectedRoadsFrom(tempEdgesFrom.get(1));
						}
					else {
						tempConnectedRoadsRight = checkConnectedRoadsTo(tempEdgesFrom.get(1));
					}
					
					int toConnectedRoads = Math.max(tempConnectedRoadsLeft,tempConnectedRoadsRight);
					return toConnectedRoads + 1;
					}
			}
	/**
	 * check largest connected roads from the "vertexTo"
	 */	
	public int checkConnectedRoadsTo(Edge e){

	if(e.getOccupyingBuilding() == null){
		return -1;
	}
	if( e.getOccupyingBuilding().getOwner().getPlayerID() != this.owner.getPlayerID()){
		return -1;
	}
	
	if (this.edge != e){
		if(Collections.frequency(tempEdges, e) > 0) return -1;
	}
	
	if (e.getVertexTo().getOccupyingBuilding() != null){
		if (e.getVertexTo().getOccupyingBuilding().getOwner().getPlayerID() != this.owner.getPlayerID()) return 0;
	}
	if (e.getOccupyingBuilding().getOwner().getPlayerID() == this.owner.getPlayerID()){
			tempEdges.add(e);
			}

	
	
	ArrayList<Edge> tempEdgesTo = e.getVertexTo().getEdges();
	int i = 0;
	for(Edge edge : tempEdgesTo){
		if (edge == e) Collections.swap(tempEdgesTo, i, tempEdgesTo.size() - 1);
		i++;
	}
	if (tempEdgesTo.size() ==2){
		if (tempEdgesTo.get(0) == this.edge ) return 0;
			if(tempEdgesTo.get(0).getOccupyingBuilding() == null){
			return 0;
			}
		
			int tempConnectedRoads;

			
			if (tempEdgesTo.get(1).getVertexTo() != tempEdgesTo.get(0).getVertexTo()) {
				tempConnectedRoads = checkConnectedRoadsTo(tempEdgesTo.get(0));
			}
			else {
				tempConnectedRoads = checkConnectedRoadsFrom(tempEdgesTo.get(0));
			}
			return tempConnectedRoads + 1;
			
		
	}
	else{
			if (tempEdgesTo.get(0) == this.edge || tempEdgesTo.get(1)== this.edge) return 0;
			if(tempEdgesTo.get(0).getOccupyingBuilding() == null && tempEdgesTo.get(1).getOccupyingBuilding() == null){
				return 0;
					}
				int tempConnectedRoadsLeft;
				int tempConnectedRoadsRight;
				if (tempEdgesTo.get(2).getVertexTo() != tempEdgesTo.get(0).getVertexTo()) {
					 tempConnectedRoadsLeft = checkConnectedRoadsTo(tempEdgesTo.get(0));
					}
				else {
					tempConnectedRoadsLeft = checkConnectedRoadsFrom(tempEdgesTo.get(0));
				}
				if (tempEdgesTo.get(2).getVertexTo() != tempEdgesTo.get(1).getVertexTo()) {
					 tempConnectedRoadsRight = checkConnectedRoadsTo(tempEdgesTo.get(1));
					}
				else {
					tempConnectedRoadsRight = checkConnectedRoadsFrom(tempEdgesTo.get(1));
				}
				
				int toConnectedRoads = Math.max(tempConnectedRoadsLeft,tempConnectedRoadsRight);
				return toConnectedRoads + 1;
				}
		}

	/**
	 * @return connectedRoads
	 */
	public int getConnectedRoads() {
		return connectedRoads;
	}
	/**
	 * Getter for owner
	 */
	public Player getOwner(){
		return this.owner;
	}


	/**
	 * Getter for Edge
	 * @return
	 */
	public Edge getEdge(){
		return this.edge;
	}


	/**
	 * Gets the tempEdges (used for the calculation of the longest road)
	 * @return tempEdges
	 */
	public ArrayList<Edge> getTempEdges() {
		return tempEdges;
	}


	/**
	 * connectedRoads setter
	 */
	public void setConnectedRoads(int connectedRoads) {
		this.connectedRoads = connectedRoads;
	}

		
}