package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.shape.Shape;
import view.IslandSkin;
import view.SettlementSkin;

/**
 * A settlement. Defined by associated vertex and player.
 * @author Daniel Panangian
 */
public class Settlement extends Building {
	
	private Vertex vertex;
	private List<Tile> neighboringTiles;
	
	protected Shape settlementSkin;
	protected BuildingType buildingType;
	
	/**
	 * Constructor for Client
	 * @param vertex The vertex the settlement is built on
	 * @param player The Owner of the settlement
	 * @param islandSkin The skin the settlement is built on
	 */
	public Settlement(Vertex vertex, Player player, IslandSkin islandSkin){
		this.vertex = vertex;
		this.owner = player;
		vertex.setOccupyingBuilding(this);
		this.neighboringTiles = vertex.getHexes();
		this.settlementSkin = new SettlementSkin(this, islandSkin);
		this.buildingType = BuildingType.Settlement;
		
	}

	/**
	 * Constructor for Server
	 * @param vertex The vertex the settlement is built on
	 * @param player The Owner of the settlement
	 */
	public Settlement(Vertex vertex, PlayerClient player) {
		this.vertex = vertex;
		vertex.setOccupyingBuilding(this);
		if(vertex.getHarbour() != null){
			player.getHarbours().add(vertex.getHarbour());
		}
		this.owner = player;
		this.neighboringTiles = vertex.getHexes();
		this.buildingType = BuildingType.Settlement;
		
	}
	
	
	/**
		 * calculates the shortest path between two vertices
		 * @return shortest Path from one vertex to another vertex (list of edges)
		 */
		public List<Vertex> shortestPath(Vertex to){
			if (this.getVertex().equals(to)){
				@SuppressWarnings("serial")
				ArrayList<Vertex> list = new ArrayList<Vertex>() {{
				    add(to);
				}};
				return list;
			}
		    //Initialization.
		    HashMap<Vertex, Vertex> nextVertexMap = new HashMap<Vertex, Vertex>();
		    Vertex currentVertex = this.getVertex();
	
		    //Queue
		    Queue<Vertex> queue = new LinkedList<Vertex>();
		    queue.add(currentVertex);
	
		    ArrayList<Vertex> visitedVertices = new ArrayList<Vertex>();
		    visitedVertices.add(currentVertex);
	
		    //Search.
		    while (!queue.isEmpty()) {
		        currentVertex = queue.remove();
		        if (currentVertex.equals(to)) break;
		            for (Edge childEdge : currentVertex.getEdges()) {
		            	if(childEdge.getOccupyingBuilding() != null){
		            		if(childEdge.getOccupyingBuilding().getOwner() != (this.owner)) break;
		            	}
		            	Vertex nextVertex;
		            	if(!currentVertex.equals((childEdge.getVertexTo())))nextVertex = childEdge.getVertexTo();
		            	else nextVertex = childEdge.getVertexFrom();
		            	if(nextVertex.getOccupyingBuilding() != null){
		       
		            		if(nextVertex.getOccupyingBuilding().getOwner() != (this.getOwner()))break;
		            	}
		                if (!visitedVertices.contains(nextVertex)) {
		                    queue.add(nextVertex);
		                    visitedVertices.add(nextVertex);
	
		                    //Look up of next vertex instead of previous.
		                    nextVertexMap.put(nextVertex, currentVertex);
		                    if (nextVertex.equals(to)) break;
		                }
		            }
		    }
	
		    //If all vertices are explored and the destination vertex hasn't been found.
		    if (!currentVertex.equals(to)) {
		        throw new RuntimeException("No feasible path.");
		    }
	
		    //Reconstruct path.
		    List<Vertex> directions = new LinkedList<Vertex>();
		    for (Vertex vertex = to ; vertex != null; vertex = nextVertexMap.get(vertex)) {
		        directions.add(vertex);
		        if(vertex.equals(this)) break;
		    }
		    
		    Collections.reverse(directions);
		    return directions;
	}

	/**
	 * Getter for SettlementSkin
	 * @return the settlementSkin
	 */
	public Shape getSettlementSkin() {
		return settlementSkin;
	}
	

	/**
	 * Getter for Owner
	 */
	public Player getOwner(){
		return this.owner;
	}
	/**
	 * Getter for Vertex
	 * @return
	 */
	public Vertex getVertex(){
		return this.vertex;
	}
	
	/**
	 * @return the neighboringTiles
	 */
	public List<Tile> getNeighboringTiles() {
		return neighboringTiles;
	}
	
}
