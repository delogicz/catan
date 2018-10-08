package model;

import controller.LogMaster;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Determines all different types of resources.
 */
public enum ResourceType {
	
	BRICK("brick.png"),
	GRAIN("grain.png"),
	WOOL("wool.png"),
	LUMBER("lumber.png"),
	ORE("ore.png");
	
	private ImageView skin;
	
	/**
	 * Constructor
	 * @param skinFileName Name of belonging picture file in resources folder
	 */
	private ResourceType(String skinFileName){
		
		final String PATH = "/img/resourcetypes/";
		try{
			skin = new ImageView(new Image(ResourceType.class.getResourceAsStream(PATH + skinFileName), 100, 100, true, true));
			}
		catch (Exception e){
			LogMaster.log("[Mod]Problem loading skin of ResourceType" + e.getStackTrace() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
	}
	/**
	 * Converts the ResourceType to a string
	 */
	public String toString(){
		switch(this){
		case BRICK : 	return "Brick";
		case GRAIN : 	return "Grain";
		case WOOL : 	return "Wool";
		case LUMBER : 	return "Lumber";
		case ORE : 		return "Ore";
		default:		return null;
		}
	}
	
	/**
	 * Gets the skin of the ResourceTyoe
	 * @return the Skin
	 */
	public ImageView getSkin() {
		return skin;
	}
	
}


