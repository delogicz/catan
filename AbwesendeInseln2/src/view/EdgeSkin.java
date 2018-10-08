package view;

import game.Settings;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Edge;

/**
 * View of an edge.
 * @author Daniel Panangian
 */
public class EdgeSkin {

	
	private Shape shape;
	
	/**
	 * Constructor
	 * @param edge
	 */
	public EdgeSkin(Edge edge) {
		shape = new Rectangle(Settings.EDGE_LENGTH, Settings.EDGE_WIDTH);
		shape.setScaleX(Settings.SCALE);
		shape.setScaleY(Settings.SCALE);
		shape.setFill(Settings.EDGE_SKIN_COLOR);
		
		shape.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(edge.getCatan().getPlayerClient().isBuildRoadPressed()){
					edge.getCatan().getGameManager().getClient().sendConstructRoad(edge);
				}
				else if(edge.getCatan().getPlayerClient().isRoadBuilding()){
					edge.getCatan().getPlayerClient().addTemporarilyRoadSkin(new RoadSkin(edge, edge.getCatan().getIsland().getSkin(), true));
				}
			}
		});

		
	}

	/**
	 * Getter for Shape
	 * @return
	 */
	public Shape getShape() {
		return shape;
	}

}
