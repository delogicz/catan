package jsonstructures;

import java.util.ArrayList;

import model.DevelopmentCardType;

/**
 * This class contains all the development cards beeing send by the server in the StatusUpdate
 * @author Felixi, Georg
 *
 */
public class EntwicklungKarteDatenDaten {
	private int Ritter;
	private int Strassenbau;
	private int Monopol;
	private int Erfindung;
	private int Siegpunkt;
	
	/**
	 * Constructor
	 * @param ritter
	 * @param strassenbau
	 * @param monopol
	 * @param erfindung
	 * @param siegpunkt
	 */
	public EntwicklungKarteDatenDaten(int ritter, int strassenbau, int monopol, int erfindung, int siegpunkt) {
		Ritter = ritter;
		Strassenbau = strassenbau;
		Monopol = monopol;
		Erfindung = erfindung;
		Siegpunkt = siegpunkt;
	}
	
	/**
	 * Constructor
	 * @param entwicklungsKarten
	 */
	public EntwicklungKarteDatenDaten(ArrayList<DevelopmentCardType> entwicklungsKarten) {
		extractDevCards(entwicklungsKarten);
	}

	/**
	 * Extracts the data need to create a new EntwicklungsKartenDatenDaten-Object
	 * containing the DevelopmentCardtypes of the parameter entwicklungsKarten
	 * @param entwicklungsKarten
	 */
	public void extractDevCards(ArrayList<DevelopmentCardType> entwicklungsKarten){
		for(DevelopmentCardType d : entwicklungsKarten){
			switch(d){
			case KNIGHT: 		Ritter++; break;
			case VICTORYPOINT: 		Siegpunkt++; break;
			case MONOPOLY:		Monopol++; break;
			case ROADBUILDING:	Strassenbau++; break;
			case YEAROFPLENTY:	Erfindung++; break;
			default: 			break;
			}
		}
	}
	
	/**
	 * Returns an ArrayList containing all the DevelopmentCardTypes present in the EntwicklungKartenDatenDaten
	 * @return
	 */
	public ArrayList<DevelopmentCardType> getAllDevCards(){
		ArrayList<DevelopmentCardType> devCards = new ArrayList<DevelopmentCardType>();
		for(int i = 0;i < Ritter; i++){
			devCards.add(DevelopmentCardType.KNIGHT);
		}
		for(int i = 0;i < Strassenbau; i++){
			devCards.add(DevelopmentCardType.ROADBUILDING);
		}
		for(int i = 0;i < Monopol; i++){
			devCards.add(DevelopmentCardType.MONOPOLY);
		}
		for(int i = 0;i < Erfindung; i++){
			devCards.add(DevelopmentCardType.YEAROFPLENTY);
		}
		for(int i = 0;i < Siegpunkt; i++){
			devCards.add(DevelopmentCardType.VICTORYPOINT);  // not quite the exactest but what ever
		}
		
		return devCards;
	}
	/**
	 * Getter for Ritter
	 * @return
	 */
	public int getRitter() {
		return Ritter;
	}
	
	/**
	 * Getter for Strassenbau
	 * @return
	 */
	public int getStrassenbau() {
		return Strassenbau;
	}
	
	/**
	 * Getter for Monopol
	 * @return
	 */
	public int getMonopol() {
		return Monopol;
	}
	/**
	 * Getter for Erfindung
	 * @return
	 */
	public int getErfindung() {
		return Erfindung;
	}
	
	/**
	 * Getter for Siegpunkt
	 * @return
	 */
	public int getSiegpunkt() {
		return Siegpunkt;
	}
	
	
}
