package jsonstructures;

import java.util.ArrayList;

import model.DevelopmentCardType;
import model.ResourceType;

public class StatusNewUpdateUnbekanntNachricht {

	SpielerNewZustandsUnbekanntDatenDaten Statusupdate;
	

	/**
	 * Creates a StatusNewUpdateUnbekanntNachricht object
	 * @param id 
	 * @param status 
	 * @param siegPunkte		
	 */
	public StatusNewUpdateUnbekanntNachricht(int id, String status, int siegPunkte,
			ArrayList<ResourceType> resourcen, int ritterGespielt, ArrayList<DevelopmentCardType> entwicklungsKarten) {
		Statusupdate = new SpielerNewZustandsUnbekanntDatenDaten(new SpielerNewZustandsUnbekanntDaten(id, status, siegPunkte, resourcen.size(), ritterGespielt, entwicklungsKarten.size()));
	}

	/**
	 * Getter for SpielerZustandsDatenDaten
	 * @return
	 */
	public SpielerNewZustandsUnbekanntDatenDaten getStatusupdate() {
		return Statusupdate;
	}
	

}
