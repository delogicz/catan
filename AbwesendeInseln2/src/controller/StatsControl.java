package controller;

import java.util.ArrayList;

import model.Catan;
import model.Player;
import model.PlayerClient;
import view.StatsControlSkin;
import view.StatsControlSkin3p;
import view.StatsControlSkin4p;

/**
 *	This class is for the Status of all players
 */
public class StatsControl {
	private StatsControlSkin skin;
	private ArrayList<Player> players;
	private PlayerClient player;
	
	/**
	 * Construtor
	 * @param catan
	 */
	public StatsControl(Catan catan) {
		players = catan.getPlayers();
		player = catan.getPlayerClient();
		if (players.size() == 3){
			skin = new StatsControlSkin3p(player,players);
		}
		else{
		if (players.size() == 4){
			skin = new StatsControlSkin4p(player,players);
			}
		}
	}
	/**
	 * Getter for StatsControlSkin
	 * @return	skin
	 */
	public StatsControlSkin getSkin() {
		return skin;
	}

}
