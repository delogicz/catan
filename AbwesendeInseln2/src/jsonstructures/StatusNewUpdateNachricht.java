package jsonstructures;

import java.util.ArrayList;

import model.DevelopmentCardType;
import model.ResourceType;
/**
 * Object to be converted to JSON holding a players status data
 * @author georgschwab, Felix
 *
 */
public class StatusNewUpdateNachricht {

	private SpielerNewZustandsDatenDaten Statusupdate;


	/**
	 * Creates a StatusNewUpdateNachricht, for the beginning of the player status
	 * @param id
	 * @param status
	 * @param siegPunkte
	 * @param resourcen
	 * @param knightsPlayed
	 * @param devCards
	 */
	public StatusNewUpdateNachricht(int id, String status, int siegPunkte,
			ArrayList<ResourceType> resourcen, int knightsPlayed, ArrayList<DevelopmentCardType> devCards) {
		Statusupdate = new SpielerNewZustandsDatenDaten(new SpielerNewZustandsDaten(id,status,siegPunkte,resourcen,knightsPlayed, devCards));
	}

	/**
	 * Getter for SpielerZustandsDatenDaten
	 * @return
	 */
	public SpielerNewZustandsDatenDaten getStatusupdate() {
		return Statusupdate;
	}
	
}
