package view;

import java.util.ArrayList;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.*;

/**
 * View of the island. Contains views of all tiles, edges, vertices, tokens.
 *@author Daniel Panangian
 */
public class IslandSkin extends Pane {

	private Island island;
	
	private ArrayList<RoadSkin> buildableRoadSkins;
	private ArrayList<SettlementSkin> buildableSettlementSkins;
	
	/**
	 * Constructor
	 * @param island the island to be viewed
	 */
	public IslandSkin(Island island) {
		setPrefSize(Settings.ISLAND_SKIN_WIDTH, Settings.ISLAND_SKIN_HEIGHT);
		this.island = island;
		addHexes();
		positionVerticesAroundHexes();
		addVertices();
		alignEdgesToVertices();
		addEdges();
		this.buildableRoadSkins = new ArrayList<RoadSkin>();
		this.buildableSettlementSkins = new ArrayList<SettlementSkin>();
	}
	
	/**
	 * Positions the edges with the help of already positioned vertices "to" and "from"
	 */
	private void alignEdgesToVertices() {
		for (Edge edge : island.getEdges()) {
			double fromX = edge.getVertexFrom().getSkin().getShape().getTranslateX();
			double fromY = edge.getVertexFrom().getSkin().getShape().getTranslateY();
			double toX = edge.getVertexTo().getSkin().getShape().getTranslateX();
			double toY = edge.getVertexTo().getSkin().getShape().getTranslateY();
			edge.getSkin().getShape().setTranslateX((fromX + toX) / 2);
			edge.getSkin().getShape().setTranslateY((fromY + toY) / 2);
			if ((fromX - toX) < -0.01 * Settings.SCALE) { // left to right
				if (fromY > toY) { // going up
					edge.setRotation(-30);
				} else if (fromY < toY) { // going down
					edge.setRotation(30);
				}
			} else { // vertical
				edge.setRotation(90);
			}

		}
	}
	
	/**
	 * Adds EdgeSkins of all edges to the IslandSkin
	 */
	private void addEdges() {
		for (Edge edge : island.getEdges()) {
			getChildren().add(edge.getSkin().getShape());
		}
	}
	
	/**
	 * Adds VertexSkins of all vertices to the IslandSkin
	 */
	private void addVertices() {
		for (Vertex vertex : island.getVertices()) {
			getChildren().add(vertex.getSkin().getShape());
		}
	}
	
	/**
	 * Positions vertices around the tiles/ hexagons
	 */
	private void positionVerticesAroundHexes() {
		for (Tile hex : island.getHexes()) {
			Shape hexagon = hex.getSkin().getShape();
			for (int i = 0; i < Settings.VERTEX_LAYOUT.length; i++) {
				Vertex vertex = hex.getVertices().get(i);
				double offsetX = 0;
				double offsetY = 0;
				switch (i) {
				case 0:
					offsetX = -Settings.HALF_SQRT_THREE;
					offsetY = -0.5;
					break;
				case 1:
					offsetX = 0d;
					offsetY = -1d;
					break;
				case 2:
					offsetX = Settings.HALF_SQRT_THREE;
					offsetY = -0.5;
					break;
				case 3:
					offsetX = -Settings.HALF_SQRT_THREE;
					offsetY = 0.5;
					break;
				case 4:
					offsetX = 0d;
					offsetY = 1d;
					break;
				case 5:
					offsetX = Settings.HALF_SQRT_THREE;
					offsetY = 0.5;
					break;
				default:
					break;
				}
				vertex.getSkin().getShape().setTranslateX(hexagon.getTranslateX() + (offsetX * Settings.SCALE));
				vertex.getSkin().getShape().setTranslateY(hexagon.getTranslateY() + (offsetY * Settings.SCALE));
			}
		}
	}
	
	/**
	 * Adds TileSkins of all tiles and belonging TokenNumberSkins to the IslandSkin
	 */
	private void addHexes() {
		Tile desert = null;
		for (Tile hex : island.getHexes()) {
			getChildren().add(hex.getSkin().getShape());
			if(hex.getToken() != TokenNumber.SEVEN){
				getChildren().add((new TokenNumberSkin(hex)).getShape());
			}
			else {
				desert = hex;
			}
		}
		getChildren().add(new RobberSkin(desert, this.island));
	}
	
	/**
	 * Adds arbitrary count of new nodes to the IslandSkin
	 * @param nodes 
	 */
	public void addElements(Node... nodes) {
		getChildren().addAll(nodes);
	}
	
	/**
	 * Displays edges one can build roads on
	 * @param buildableEdges
	 */
	public void showBuildableEdges(ArrayList<Edge> buildableEdges) {
		IslandSkin iS = this;
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				removeBuildableEdges();
				for(Edge edge: buildableEdges){
					buildableRoadSkins.add(new RoadSkin(edge, iS, false));
				}
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	/**
	 * Removes all items in "buildableRoadSkins"from view
	 * Then clears the list "buildableRoadSkins"
	 */
	public void removeBuildableEdges() {

		for(RoadSkin roadSkin: buildableRoadSkins){
			this.getChildren().remove(roadSkin);
			roadSkin.setVisible(false);
		}
		buildableRoadSkins.clear();

	}
	
	/**
	 * Displays vertices one can build settlements on
	 * @param buildableVertices
	 */
	public void showBuildableVertices(ArrayList<Vertex> buildableVertices) {
		IslandSkin iS = this;
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				removeBuildableVertices();
				for(Vertex vertex: buildableVertices){
					buildableSettlementSkins.add(new SettlementSkin(vertex, iS));
				}
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	/**
	 * Removes all items in "buildableSettlementSkins"from view
	 * Then clears the list "buildableSettlementSkins"
	 */
	public void removeBuildableVertices() {
		for(SettlementSkin settlementSkin: buildableSettlementSkins){
			this.getChildren().remove(settlementSkin);
		}
		buildableSettlementSkins.clear();
	}

	/**
	 * Getter for Island
	 * @return the island
	 */
	public Island getIsland() {
		return island;
	}

	
}
