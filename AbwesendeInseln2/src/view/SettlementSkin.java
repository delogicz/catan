package view;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Settlement;
import model.Vertex;

/**
 * View of a road.
 * @author Stefanie Kloss
 */
public class SettlementSkin extends Circle{

	/**
	 * Constructor
	 * @param building
	 * @param islandSkin
	 */
	public SettlementSkin(Settlement building, IslandSkin islandSkin) {
		
		super(0.1);
		Color color = building.getOwner().getPlayerColor().getColor();
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
		setTranslateX(building.getVertex().getSkin().getShape().getTranslateX());
		setTranslateY(building.getVertex().getSkin().getShape().getTranslateY());	
		
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			//System.out.println(building.getVertex());
			if(building.getOwner().getCatan().getPlayerClient().isBuildCityPressed()){
				building.getOwner().getCatan().getGameManager().getClient().sendConstructCity(building.getVertex());
			}
		}
		});
		
		SettlementSkin settlementSkin = this;
		
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				islandSkin.getChildren().add(settlementSkin);
				LogMaster.log("[UI]Siedlung auf IslandSkin"+ " [Thread : "+ Thread.currentThread().getId() + "]");
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
	 * Constructor for the displaying of vertices one can build settlements on
	 * @param vertex
	 * @param islandSkin
	 */
	public SettlementSkin(Vertex vertex, IslandSkin islandSkin) {
		super(0.1);
		Color color = islandSkin.getIsland().getCatan().getPlayerClient().getPlayerColor().getColor();
		color = Color.hsb(color.getHue(), color.getSaturation(), color.getBlue(), 0.4);
		setFill(color);
		setStroke(Color.DARKGRAY);
		setStrokeWidth(0.03);
		setScaleX(Settings.SCALE);
		setScaleY(Settings.SCALE);
		setTranslateX(vertex.getSkin().getShape().getTranslateX());
		setTranslateY(vertex.getSkin().getShape().getTranslateY());
		setMouseTransparent(true);
		SettlementSkin settlementSkin = this;
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				islandSkin.getChildren().add(settlementSkin);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
}