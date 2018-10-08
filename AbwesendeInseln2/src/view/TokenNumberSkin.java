package view;



import java.net.URISyntaxException;

import controller.LogMaster;
import game.Settings;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import model.Tile;
import model.TokenNumber;

/**
 * The view of a numbered token.
 * @author Daniel Panangian
 */
public class TokenNumberSkin {

	private Shape shape;
	private TokenNumber token;
	/**
	 * Constructor
	 * @param hex
	 */
	public TokenNumberSkin(Tile hex) {
		
		Shape hexShape = hex.getSkin().getShape();
		token = hex.getToken();
		String path = "";
		
		try {
			path = TokenNumberSkin.class.getResource("/" +  token.getImgUrl() ).toURI().toString();
		} catch (URISyntaxException | NullPointerException e) {
			LogMaster.log("[UI]" +e.getMessage() + " Token image not found "+ " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		
		Image image = new Image(path);
		ImagePattern imagePattern = new ImagePattern(image);
		
		if(token != TokenNumber.SEVEN){
			shape = new Circle(0.4 * Settings.SCALE);
		}
		else{
			shape = new Ellipse();
			((Ellipse)shape).setRadiusX(0.25 * Settings.SCALE);
			((Ellipse)shape).setRadiusY(0.55 * Settings.SCALE);
		}
		
		shape.setFill(imagePattern);
		shape.setTranslateX(hexShape.getTranslateX());
		shape.setTranslateY(hexShape.getTranslateY());
		shape.setMouseTransparent(true);
		
	}
	
	/**
	 * Getter for shape
	 * @return the Shape
	 */
	public Shape getShape() {
		return shape;
	}

	
}