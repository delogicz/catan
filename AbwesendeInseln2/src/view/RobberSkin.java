package view;

import java.net.URISyntaxException;

import controller.LogMaster;
import game.Settings;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import model.Island;
import model.Robber;
import model.Tile;

/**
 * The view of a Robber
 * @author Stefanie Kloss
 *
 */
public class RobberSkin extends Ellipse{
	
	private Robber robber;
	private Island island;
	
	/**
	 * Constructor, also creates a new Robber
	 * @param tile the tile on which the robber is to be located
	 * @param island the island on which the robber is located
	 */
	public RobberSkin(Tile tile, Island island){
		
		super();
		robber = new Robber(tile, this);
		this.island = island;
		this.island.setRobber(robber);
		Shape tileShape = tile.getSkin().getShape();
		String path = "";
		try {
			path = TokenNumberSkin.class.getResource("/img/token/robber.png").toURI().toString();
		} catch (URISyntaxException | NullPointerException e) {
			LogMaster.log("[UI]" + e.getMessage() + " Robber image not found "+ " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		
		Image image = new Image(path);
		ImagePattern imagePattern = new ImagePattern(image);
		
		setRadiusX(0.25 * Settings.SCALE);
		setRadiusY(0.55 * Settings.SCALE);
		
		setFill(imagePattern);
		setTranslateX(tileShape.getTranslateX());
		setTranslateY(tileShape.getTranslateY());
		setMouseTransparent(true);
	}
	
	/**
	 * Relocates the robberSkin onto a new tile
	 * @param tile the tile the robberSkin gets positioned on
	 * @param islandSkin the islandSkin
	 */
	public void reposition(Tile tile, IslandSkin islandSkin) {
		
		Shape tileShape = tile.getSkin().getShape();
		setTranslateX(tileShape.getTranslateX());
		setTranslateY(tileShape.getTranslateY());
		
	}
	
	
}
