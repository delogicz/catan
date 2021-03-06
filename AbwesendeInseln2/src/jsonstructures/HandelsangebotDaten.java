package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;

/** 
 * Class for data of a trade
 * @author Felixi, Georg
 *
 */
public class HandelsangebotDaten {
	int Spieler;
	int Q;   // not correct name yet ?!?!?!
	RohstoffDaten Angebot;
	RohstoffDaten Nachfrage;
	
	/**
	 * Constructor
	 * @param spieler
	 * @param handel
	 * @param angebot
	 * @param demand
	 */
	public HandelsangebotDaten(int spieler, int handel, ArrayList<ResourceType> angebot, RohstoffDaten demand) {
		Spieler = spieler;
		Q = handel;
		
		int lumber = 0,grain = 0,brick = 0,wool = 0,ore = 0;
		
		for(ResourceType type : angebot){
			switch(type){
			case LUMBER: ++lumber;break;
			case GRAIN: ++grain;break;
			case BRICK: ++brick;break;
			case WOOL: ++wool;break;
			case ORE: ++ore;break;
			}
		}
		Angebot = new RohstoffDaten(lumber,brick,wool,grain,ore);
		Nachfrage = demand;
	}
	/**
	 * Getter for Spieler
	 * @return
	 */
	public int getSpieler() {
		return Spieler;
	}
	
	/**
	 * Getter for Handel
	 * @return
	 */
	public int getHandel() {
		return Q;
	}
	
	/**
	 * Getter Angebot
	 * @return
	 */
	public RohstoffDaten getAngebot() {
		return Angebot;
	}
	
	/**
	 * Getter for Nachfrage
	 * @return
	 */
	public RohstoffDaten getNachfrage() {
		return Nachfrage;
	}
	
	
}
