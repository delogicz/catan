package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.*;
import view.PlayerControlSkin;

/**
 * Controller class to handle user's input on the game
 * 
 * @author Daniel Panangian, Lena Sonnleitner
 */
public class PlayerControl {

	private PlayerControlSkin skin;
	private PlayerClient player;
	
	/**
	 * Constructor
	 */
	public PlayerControl(PlayerClient player){
		
		this.player = player;
		skin = new PlayerControlSkin(this);
		disableAllButtons();
		enableShowDevCardButton();
	}

	/**
	 * Update View of user's Hand
	 */
	public void updateHandView() {
		skin.getRoad().setText("Roads:   " + player.countRoads());
		skin.getCity().setText("Cities:   " + player.countCity());
		skin.getStatus().setText(player.getStatusEnglish());
		skin.getSettlement().setText("Settlements:   " + player.countSettlements());
		skin.getLumber().setText(Integer.toString(player.getHand().getLumberCount()));
		skin.getBrick().setText(Integer.toString(player.getHand().getBrickCount()));
		skin.getWool().setText(Integer.toString(player.getHand().getWoolCount()));
		skin.getGrain().setText(Integer.toString(player.getHand().getGrainCount()));
		skin.getOre().setText(Integer.toString(player.getHand().getOreCount()));
		player.getCatan().getGameSkin().getStatsControl().update();

	}
	
	/**
	 * React to showDevCard Button 
	 * open showDevCard Skin
	 */
	public final EventHandler<MouseEvent> devCardHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			
			player.getCatan().getGameSkin().getDevelopmentCardSkin().showDevelopmentCardOverview();
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Show Development Cards]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	
	/**
	 * React to buildSettlement Button 
	 * set BuildSetttlemenPressed to true
	 */
	public final EventHandler<MouseEvent> buildSettlementHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			
			player.setBuildCityPressed(false);
			player.setBuildRoadPressed(false);
			player.setBuildSettlementPressed(true);
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Build Settlement]"+" [Thread : "+ Thread.currentThread().getId() + "]");
			
		}
	};
	

	/**
	 * React to buildRoad Button 
	 * set BuildRoadPressed to true
	 */
	public final EventHandler<MouseEvent> buildRoadHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			player.setBuildCityPressed(false);
			player.setBuildRoadPressed(true);
			player.setBuildSettlementPressed(false);
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Build Road]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	
	
	/**
	 * React to buildCity Button 
	 * set BuildCityPressed to true
	 */
	public final EventHandler<MouseEvent> buildCityHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			player.setBuildCityPressed(true);
			player.setBuildRoadPressed(false);
			player.setBuildSettlementPressed(false);
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Build City]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	/**
	 * React to buyDevCard Button 
	 * Try to buy a Development Card
	 */
	public final EventHandler<MouseEvent> buyDevCardHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			
			player.getCatan().getGameManager().getClient().sendBuyDevelopmentCard();
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Buy Development Card]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	/**
	 * React to trade Button 
	 * Open trade skin
	 */
	public final EventHandler<MouseEvent> tradeHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			
			player.getCatan().getGameSkin().getTradeSkin().showTradeOptionsStage();
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Show Trade Options]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	/**
	 * React to endTurn Button 
	 * Ending turn , change the player and upddate the hand's View
	 */
	public final EventHandler<MouseEvent> endTurnHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {

			player.migrateDevCards();
			player.getCatan().getGameManager().getApp().closeAllPopUpStages();
			player.getCatan().getGameManager().getClient().sendEndTurn();
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [End Turn]"+" [Thread : "+ Thread.currentThread().getId() + "]");
			
		}
	};
	/**
	 * React to rollDice Button 
	 * roll the dice, if 7 disable the button until the player moves the robber
	 */
	public final EventHandler<MouseEvent> rollDiceHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {

			Dice.diceMusic();
			player.migrateHarbours();
			player.getCatan().getGameManager().getClient().sendRollDice();
			LogMaster.log("[UI]Player: "  + player.getPlayerID() + " pressed [Roll Dice]"+" [Thread : "+ Thread.currentThread().getId() + "]");
		}
	};
	

	/**
	 * Enable endTurn button
	 */
	private void enableEndTurnButton() {
		Button endTurnButton = skin.getEndTurnButton();
		endTurnButton.setDisable(false);
		endTurnButton.addEventHandler(MouseEvent.MOUSE_CLICKED, endTurnHandler);
		
	}
	/**
	 * Enable buildSet button
	 */
	public void enableBuildSetButton() {
		Button buildSetButton = skin.getBuildSetButton();
		buildSetButton.setDisable(false);
		buildSetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buildSettlementHandler);
		
	}
	/**
	 * Disable buildSet button
	 */
	public void disableBuildSetButton() {
		Button buildSetButton = skin.getBuildSetButton();
		player.setBuildSettlementPressed(false);
		buildSetButton.setDisable(true);
		
		
	}
	/**
	 * Enable buildRoad button
	 */
	public void enableBuildRoadButton() {
		Button buildRoadButton = skin.getBuildRoadButton();
		buildRoadButton.setDisable(false);
		buildRoadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buildRoadHandler);
	}
	/**
	 * Disable buildRoad button
	 */
	public void disableBuildRoadButton() {
		Button buildRoadButton = skin.getBuildRoadButton();
		player.setBuildRoadPressed(false);
		buildRoadButton.setDisable(true);
		
	}
	/**
	 * Enable buildCity button
	 */
	public void enableBuildCityButton() {
		Button buildCityButton = skin.getBuildCityButton();
		buildCityButton.setDisable(false);
		buildCityButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buildCityHandler);
		
	}
	/**
	 * Disable buildCity button
	 */
	public void disableBuildCityButton() {
		Button buildCityButton = skin.getBuildCityButton();
		player.setBuildCityPressed(false);
		buildCityButton.setDisable(true);
		
	}
	/**
	 * Enable buyDevCard button
	 */
	public void enableBuyDevCardButton() {
		Button buyDevCardButton = skin.getBuyDevCardButton();
		buyDevCardButton.setDisable(false);
		buyDevCardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buyDevCardHandler);
		
	}
	/**
	 * Disable buyDevCard button
	 */
	public void disableBuyDevCardButton() {
		Button buyDevCardButton = skin.getBuyDevCardButton();
		buyDevCardButton.setDisable(true);
		
	}
	/**
	 * Enable showDevCard button
	 */
	public void enableShowDevCardButton() {
		skin.getShowDevCardButton().setDisable(false);
		skin.getShowDevCardButton().addEventHandler(MouseEvent.MOUSE_CLICKED, devCardHandler);
		
	}
	/**
	 * Disable showDevCard button
	 */
	public void disableShowDevCardButton() {
		skin.getShowDevCardButton().setDisable(true);
	}
	
	
	/**
	 * Enable trade button
	 */
	public void enableTradeButton() {
		skin.getTradeButton().setDisable(false);
		skin.getTradeButton().addEventHandler(MouseEvent.MOUSE_CLICKED, tradeHandler);
		
	}
	/**
	 * Disable trade button
	 */
	public void disableTradeButton() {
		skin.getTradeButton().setDisable(true);		
	}
	/**
	 * Enable rollDice button
	 */
	public void enableRollDiceButton() {
		Button RollDiceButton = skin.getRollDiceButton();
		RollDiceButton.setDisable(false);
		RollDiceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, rollDiceHandler);
		
	}
	/**
	 * Disable rollDice button
	 */
	public void disableRollDiceButton() {
		Button RollDiceButton = skin.getRollDiceButton();
		RollDiceButton.setDisable(true);
		
	}
	/**
	 * Disable endTurn button
	 */
	public void disableEndTurnButton() {
		Button EndTurnButton = skin.getEndTurnButton();
		EndTurnButton.setDisable(true);
	}
	/**
	 * Disable all controller button
	 */
	public void disableAllButtons() {
		disableEndTurnButton();
		disableBuildCityButton();
		disableBuildRoadButton();
		disableBuildSetButton();
		disableBuyDevCardButton();
		disableTradeButton();
		disableRollDiceButton();
		disableShowDevCardButton();
	}
	/**
	 * Enable all controller button
	 */
	public void enableAllButtons() {
		enableEndTurnButton();
		enableBuildCityButton();
		enableBuildRoadButton();
		enableBuildSetButton();
		enableBuyDevCardButton();
		enableTradeButton();
		enableShowDevCardButton();
	}
	/**
	 * Sets SettlementPressed true
	 */
	public void buildSettlement() {
		player.setBuildCityPressed(false);
		player.setBuildRoadPressed(false);
		player.setBuildSettlementPressed(true);
	}

	/**
	 * Sets RoadPressed true
	 */
	public void buildRoad(){
		player.setBuildRoadPressed(true);
		player.setBuildCityPressed(false);
		player.setBuildSettlementPressed(false);
	}

	/**
	 * @return skin
	 */
	public PlayerControlSkin getSkin() {
		return skin;
	}

	/**
	 * 	Getter for Player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

}