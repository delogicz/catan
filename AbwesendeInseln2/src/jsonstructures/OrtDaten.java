package jsonstructures;
/**
 * Object to be converted to JSON holding coordinates of a Tile
 * @author georgschwab, Felix
 *
 */
public class OrtDaten {
	int x;
	int y;
	
	/**
	 * Creates a OrtDaten object in the coordinate form
	 * @param x
	 * @param y
	 */
	public OrtDaten(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a OrtDaten object in the String form
	 * @param feld
	 */
	public OrtDaten(String feld){
		int komma = feld.indexOf(",");
		String stX = feld.substring(0, komma);
		this.x = Integer.parseInt(stX.substring(stX.length()-1, stX.length()));
		if(stX.contains("-"))this.x*= -1;
		String stY = feld.substring(komma, feld.length());
		this.y = Integer.parseInt(stY.substring(stY.length()-2, stY.length()-1));
		if(stY.contains("-"))this.y*= -1;
	}
	
	/**
	 * Getter for X
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for Y
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	
}
