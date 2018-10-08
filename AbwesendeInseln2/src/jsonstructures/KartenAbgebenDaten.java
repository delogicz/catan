package jsonstructures;

/**
 * When the player has to give away cards
 * @author Felixi, Georg
 *
 */
public class KartenAbgebenDaten {
	RohstoffDaten Abgeben;

	
	/**
	 * Initializes Abgeben
	 * @param lumber count of lumber to give away
	 * @param brick count of brick to give away
	 * @param wool count of wool to give away
	 * @param grain count of grain to give away
	 * @param ore count of ore to give away
	 */
	public KartenAbgebenDaten(int lumber, int brick, int wool, int grain, int ore) {
		
		Abgeben = new RohstoffDaten(lumber, brick, wool, grain, ore);
	}

	/**
	 * Getter for Abgeben
	 * @return
	 */
	public RohstoffDaten getAbgeben() {
		return Abgeben;
	}

	/**
	 * Setter for Abgeben
	 * @param abgeben
	 */
	public void setAbgeben(RohstoffDaten abgeben) {
		Abgeben = abgeben;
	}
	
	
}
