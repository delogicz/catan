package model;

import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import controller.LogMaster;
import view.DiceSkin;


/**
 * creates the two Dices for Settlers
 * @author Lena Sonnleitner, Samantha Meindl, Stefanie Kloss
 *
 */
public class Dice {
	private int dice1;
	private int dice2;
	
	private DiceSkin diceSkin;
	
	/**
	 * Constructor
	 */
	public Dice(){
		setDiceSkin(new DiceSkin(this));
	}
	
	/**
	 * dice the Dice
	 */
	public void dice(){
		
		this.setDice1((int)(Math.random()*6)+1);
		this.setDice2((int)(Math.random()*6)+1);
		diceSkin.changeViewOfDices(this.dice1, this.dice2);

		
	}
	
	/**
	 * dice the Dice and return the result
	 * @return the result
	 */
	public int[] diceResult(){
		
		this.setDice1((int)(Math.random()*6)+1);
		this.setDice2((int)(Math.random()*6)+1);
		int[] dice = {dice1,dice2};
		return dice;	

	}
	
	/**
	 * Sets the dice rolled skin
	 * @param res1
	 * @param res2
	 */
	public void diceRolled(int res1, int res2) {

		diceSkin.changeViewOfDices(res1, res2);
		
	}

	/**
	 * @param dice2 the dice2 to set
	 */
	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	/**
	 * @param dice1 the dice1 to set
	 */
	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	/**
	 * @param diceSkin the diceSkin to set
	 */
	public void setDiceSkin(DiceSkin diceSkin) {
		this.diceSkin = diceSkin;
	}

	/**
	 * @return the dice2
	 */
	public int getDice2() {
		return dice2;
	}

	/**
	 * @return the dice1
	 */
	public int getDice1() {
		return dice1;
	}

	/**
	 * @return the diceSkin
	 */
	public DiceSkin getDiceSkin() {
		return diceSkin;
	}

	/**
 * Plays the music when you roll the dice
 */
	public static synchronized void diceMusic(){
		  new Thread(new Runnable() {
	
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();	
		        BufferedInputStream inputStream =  new BufferedInputStream(getClass().getResourceAsStream("/wuerfelbecher.wav"));
		        
		        AudioInputStream audio = AudioSystem.getAudioInputStream(inputStream);
		        clip.open(audio);
		        clip.start(); 
		  	}catch(Exception e){
				LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
			}
		    }
		  }).start();

		  

	}
	

	
}