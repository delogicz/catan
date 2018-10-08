package view;

import java.net.URISyntaxException;

import game.Settings;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Dice;

/**
 * The skin of the dice
 * @author Stefanie Kloss
 */
public class DiceSkin {
	
	private Rectangle dice1;
	private Rectangle dice2;
	
	/**
	 * Constructor
	 * @param dice the dice
	 */
	public DiceSkin(Dice dice){
		this.dice1 = new Rectangle(Settings.DICE_SIZE, Settings.DICE_SIZE);
		this.dice2 = new Rectangle(Settings.DICE_SIZE, Settings.DICE_SIZE);
		dice.getDice1();
		dice.getDice2();
		
		String path;
		try {
			path = DiceSkin.class.getResource("/img/dice/dice1.png").toURI().toString();
			Image image = new Image(path);
			ImagePattern imagePattern = new ImagePattern(image);
			this.dice1.setFill(imagePattern);
			this.dice2.setFill(imagePattern);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Changes fill of dices to the right picture
	 * @param countOfDice1
	 * @param countOfDice2
	 */
	public void changeViewOfDices(int countOfDice1, int countOfDice2 ){
		try {
			String path1 = DiceSkin.class.getResource("/img/dice/dice" +countOfDice1+ ".png").toURI().toString();
			Image image = new Image(path1);
			ImagePattern imagePattern = new ImagePattern(image);
			image.isSmooth();
			this.dice1.setFill(imagePattern);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		try {
			String path2 = DiceSkin.class.getResource("/img/dice/dice" +countOfDice2+ ".png").toURI().toString();
			Image image = new Image(path2);
			ImagePattern imagePattern = new ImagePattern(image);
			image.isSmooth();
			this.dice2.setFill(imagePattern);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * @return the shape of the first dice
	 */
	public Rectangle getDice1(){
		return this.dice1;
	}
	
	/**
	 * @return the shape of the second dice
	 */
	public Rectangle getDice2(){
		return this.dice2;
	}
}