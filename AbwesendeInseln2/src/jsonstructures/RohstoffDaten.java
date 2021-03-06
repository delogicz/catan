package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;

/**
 * This class is used for the resources if someone wants to trade or someone gets resources
 * @author Felixi, Georg
 *
 */
public class RohstoffDaten {

	private int Holz;
	private int Lehm;
	private int Wolle;
	private int Getreide;
	private int Erz;
	
	/**
	 * Creates a RohstoffDaten object 
	 * @param holz			amount of lumber
	 * @param lehm			amount of bricks
	 * @param wolle			amount of wool
	 * @param getreide		amount of grain
	 */
	public RohstoffDaten(int holz, int lehm, int wolle, int getreide, int erz) {
		Holz = holz;
		Lehm = lehm;
		Wolle = wolle;
		Getreide = getreide;
		Erz = erz;
	}
	
	/**
	 * Constructor
	 * @param resourcen
	 */
	public RohstoffDaten(ArrayList<ResourceType> resourcen) {
		Holz = 0;
		Lehm = 0;
		Wolle = 0;
		Getreide = 0;
		Erz = 0;
		
		for(ResourceType r : resourcen){
			switch(r){
			case LUMBER: Holz++;break;
			case BRICK: Lehm++;break;
			case WOOL: Wolle++;break;
			case GRAIN: Getreide++;break;
			case ORE: Erz++;break;
			}
		}
	}

	/**
	 * Getter for all Resources
	 * @return
	 */
	public ArrayList<ResourceType> getResources(){
		
		ArrayList<ResourceType> resources = new ArrayList<ResourceType>();
		for(int i = 0;i < this.getHolz() ;i++){
			resources.add(ResourceType.LUMBER);
		}
		for(int i = 0;i < this.getLehm() ;i++){
			resources.add(ResourceType.BRICK);
		}
		for(int i = 0;i < this.getWolle() ;i++){
			resources.add(ResourceType.WOOL);
		}
		for(int i = 0;i < this.getGetreide() ;i++){
			resources.add(ResourceType.GRAIN);
		}
		for(int i = 0;i < this.getErz() ;i++){
			resources.add(ResourceType.ORE);
		}
		return resources;
	}
	
	/**
	 * Getter for Holz
	 * @return
	 */
	public int getHolz() {
		return Holz;
	}
	/**
	 * Setter for Holz
	 * @param holz
	 */
	public void setHolz(int holz) {
		Holz = holz;
	}
	
	/**
	 * Getter for Lehm
	 * @return
	 */
	public int getLehm() {
		return Lehm;
	}
	/**
	 * Setter for Lehm
	 * @param lehm
	 */
	public void setLehm(int lehm) {
		Lehm = lehm;
	}
	
	/**
	 * Getter for Wolle
	 * @return
	 */
	public int getWolle() {
		return Wolle;
	}
	
	/**
	 * Setter for Wolle
	 * @param wolle
	 */

	public void setWolle(int wolle) {
		Wolle = wolle;
	}
	
	/**
	 * Getter for Getreide
	 * @return
	 */
	public int getGetreide() {
		return Getreide;
	}
	
	/**
	 * Setter for Getreide
	 * @param getreide
	 */

	public void setGetreide(int getreide) {
		Getreide = getreide;
	}
	
	/**
	 * Getter for Erz
	 * @return
	 */
	public int getErz() {
		return Erz;
	}
	
	/**
	 * Setter for Erz
	 * @param erz
	 */

	public void setErz(int erz) {
		Erz = erz;
	}

	/**
	 * Getter for all Resources
	 * @return
	 */
	public int[] getAll() {
		int[] alle = {Holz,Lehm,Wolle,Getreide,Erz};
		return alle;
	}
}
