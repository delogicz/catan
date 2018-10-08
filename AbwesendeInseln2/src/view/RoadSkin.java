package view;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Edge;
import model.Road;

/**
 * View of a road.
 * @author Stefanie Kloss
 */
public class RoadSkin extends Rectangle {
	
	private final Edge edge;

	/**
	 * Constructor
	 * @param road
	 * @param islandSkin
	 */
	public RoadSkin(Road road, IslandSkin islandSkin){
		
		super(Settings.EDGE_LENGTH, Settings.EDGE_WIDTH*1.5);
		this.edge = road.getEdge();
		Color color = road.getOwner().getPlayerColor().getColor();
		if(color == Color.SALMON){
			color = Color.CRIMSON;
		}
		setFill(color);
		if(color != Color.WHITE){
			setStroke(Color.ANTIQUEWHITE);
		}
		else setStroke(Color.BLACK);
		setStrokeWidth(0.03);
		setScaleX(Settings.SCALE);
		setScaleY(Settings.SCALE);
		setTranslateX(edge.getSkin().getShape().getTranslateX());
		setTranslateY(edge.getSkin().getShape().getTranslateY());
		setRotate(edge.getSkin().getShape().getRotate());
		
		RoadSkin roadSkin = this;
		
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent mouseEvent) {
				road.checkConnectedRoads();
			}
		});
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				islandSkin.getChildren().add(roadSkin);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	/**
	 * Constructor for DevelopmentCard RoadBuilding or 
	 * the displaying of edges one can build roads on
	 * @param edge Edge
	 * @param islandSkin IslandSkin
	 * @param devCard DevelopmentCard
	 */
	public RoadSkin(Edge edge, IslandSkin islandSkin, boolean devCard){
		
		super(Settings.EDGE_LENGTH, Settings.EDGE_WIDTH*1.5);
		this.edge = edge;
		Color color;
		if(devCard) color = Color.BLACK;
		else{ color = islandSkin.getIsland().getCatan().getPlayerClient().getPlayerColor().getColor();
			  color = Color.hsb(color.getHue(), color.getSaturation(), color.getBlue(), 0.4);
		}
		setFill(color);
		setStroke(Color.DARKGRAY);
		setStrokeWidth(0.03);
		setScaleX(Settings.SCALE);
		setScaleY(Settings.SCALE);
		setTranslateX(edge.getSkin().getShape().getTranslateX());
		setTranslateY(edge.getSkin().getShape().getTranslateY());
		setRotate(edge.getSkin().getShape().getRotate());
		setMouseTransparent(true);
		RoadSkin roadSkin = this;
		
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				islandSkin.getChildren().add(roadSkin);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	
	/**
	 * Getter for Edge
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}
}
