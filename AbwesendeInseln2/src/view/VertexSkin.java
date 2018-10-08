package view;


import game.Settings;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Vertex;

/**
 * View of a vertex.
 * @author Daniel Panangian
 */
public class VertexSkin {

	private Circle shape;
	/**
	 * Constructor
	 */
	public VertexSkin(Vertex vertex) {
		
		shape = new Circle(0.155 * Settings.SCALE);
		shape.setFill(Settings.VERTEX_SKIN_COLOR);
		shape.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent mouseEvent) {
//				System.out.println(vertex);
//				
				if(vertex.getCatan().getPlayerClient().isBuildSettlementPressed()){
					vertex.getCatan().getGameManager().getClient().sendConstructSettlement(vertex);
				}
				else if(vertex.getCatan().getPlayerClient().isBuildCityPressed()){
					vertex.getCatan().getGameManager().getClient().sendConstructCity(vertex);
				}
			}
		});

	}

	/**
	 * Getter for the Shape
	 * @return the Shape
	 */
	public Shape getShape() {
		return shape;
	}

	
}
