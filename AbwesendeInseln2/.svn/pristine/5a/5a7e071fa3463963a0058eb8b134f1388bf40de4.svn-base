package view;

import java.util.ArrayList;

import game.Settings;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Player;
import model.PlayerClient;

/**
 * Creates 4 playerBoxes if you play as 4 people
 * @author Daniel Panangian, Felixi
 *
 */
public class StatsControlSkin4p extends StatsControlSkin {
	

	private Player player1,player2,player3,player4;
	private PlayerBox player1box,player2box,player3box,player4box;
	private HBox list;
	
	public StatsControlSkin4p(PlayerClient player,ArrayList<Player> players){
		setPrefSize(Settings.GAME_SKIN_WIDTH, Settings.GAME_SKIN_HEIGHT);
		setPickOnBounds(false);
		
		for(Player p:players){
			if (player.getPlayerColor() == p.getPlayerColor()){
				p = player;
				break;
			}
		}
	
		player1 = players.get(0);
		player2 = players.get(1);
		player3 = players.get(2);
		player4 = players.get(3);
		
		
		player1box = new PlayerBox(player1);
		player2box = new PlayerBox(player2);
		player3box = new PlayerBox(player3);
		player4box = new PlayerBox(player4);
		
		list = new HBox();
		
		list.getChildren().addAll(player1box,player2box,player3box,player4box);
		this.setTop(list);
		list.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(list, Pos.TOP_CENTER);
		
		
		}
	
	/**
	 * Updates the playerBoxes
	 */
	public void update(){
		player1box.update();
		player2box.update();
		player3box.update();
		player4box.update();
	}
}
