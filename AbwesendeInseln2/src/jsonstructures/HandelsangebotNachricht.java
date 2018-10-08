package jsonstructures;

import java.util.ArrayList;

import model.ResourceType;
/**
 * Wrapper for HandelsangobtDaten
 * @author Felixi, Georg
 *
 */
public class HandelsangebotNachricht {
		HandelsangebotDaten Handelsangebot;
		
		/**
		 * Constructor
		 * @param clientId
		 * @param tradeId
		 * @param offer
		 * @param demand
		 */
		public HandelsangebotNachricht(int clientId, int tradeId, ArrayList<ResourceType> offer, RohstoffDaten demand) {
			Handelsangebot = new HandelsangebotDaten(clientId,tradeId,offer,demand);
		}

		/**
		 * Getter for HandelsangebotDaten
		 * @return
		 */
		public HandelsangebotDaten getHandelsangebot() {
			return Handelsangebot;
		}
		
		
		
}
