package model;

import java.util.ArrayList;
import java.util.Collections;

import game.Settings;

/**
 * Determines all different types of development cards
 * @author Samantha Meindl, Lena Sonnleitner
 */
public class DevelopmentCardDeck {
	
	ArrayList <DevelopmentCardType> devCards;
	
	/**
	 * Constructor
	 */
	public DevelopmentCardDeck(){
		
		devCards = new ArrayList<DevelopmentCardType>();
		addAndRiffleCards();
	};
	
	/**
	 * Adds all DevelopmentCards in a random order
	 */
	private void addAndRiffleCards() {
		for (int i = 0; i < Settings.VICTORYPOINT_CHARDS; i++){
			devCards.add(DevelopmentCardType.VICTORYPOINT);	
		}
		for (int i = 0; i < Settings.KNIGHT_CARDS; i++) {
			devCards.add(DevelopmentCardType.KNIGHT);
		}
		for (int i = 0; i < Settings.MONOPOLY_CARDS; i++) {
			devCards.add(DevelopmentCardType.MONOPOLY);
		}
		for (int i = 0; i < Settings.ROADBUILDING_CARDS; i++) {
			devCards.add(DevelopmentCardType.ROADBUILDING);
		}
		for (int i = 0; i < Settings.YEAROFPLENTY_CARDS; i++) {
			devCards.add(DevelopmentCardType.YEAROFPLENTY);
		}
		
		Collections.shuffle(devCards);
	}
	
	/**
	 * Pick up next DevelopmentCard
	 * @return next DevelopmentCard
	 * @throws DevCardException 
	 */
	public DevelopmentCardType getNextDevCard() throws DevCardException {
		if(devCards.size() > 0){
			DevelopmentCardType card = devCards.get(0);
			devCards.remove(0);
			return card;
		}
		else throw new DevCardException("There are no development cards left!");
	}
		

}
