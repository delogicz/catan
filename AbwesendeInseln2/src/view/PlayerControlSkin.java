package view;

import controller.PlayerControl;
import game.Settings;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player;
/**
 * View of a game control
 * @author Daniel Panangian, Stefanie Kloss
 */
public class PlayerControlSkin extends BorderPane {
	
	private VBox chatVBox, otherButtons;
	private GridPane allInfo, buildAndBuyButtons;
	private Button buildSetButton,buildRoadButton,buildCityButton,buyDevCardButton; 
	private Button tradeButton, rollDiceButton,endTurnButton, showDevCardButton;
	private Text lumber, brick, wool, grain, ore, settlement, city, road, status,turn;
	private ImageView largestKnight,longestRoad;
	private Player player;
	
	/**
	 * Constructor
	 */
	public PlayerControlSkin(PlayerControl playerControl){
		
		this.player = playerControl.getPlayer();
		
		setPrefSize(Settings.GAME_SKIN_WIDTH, Settings.GAME_SKIN_HEIGHT);
		setPickOnBounds(false);
		
		this.buildAndBuyButtons = new GridPane();
		this.otherButtons = new VBox();
		
		buildSetButton = new Button("Build Settlement");
		buildSetButton.setMinWidth(Settings.BUTTON_NORMAL_WIDTH);

	    buildRoadButton = new Button("Build Road");
	    buildRoadButton.setMinWidth(Settings.BUTTON_SLIM_WIDTH);
	    
	    buildCityButton = new Button("Build City");
	    buildCityButton.setMinWidth(Settings.BUTTON_SLIM_WIDTH);
	    
	    buyDevCardButton = new Button("Buy Development Card");

	    tradeButton = new Button("Trading Options");
	    tradeButton.setMinWidth(Settings.BUTTON_NORMAL_WIDTH);
	    rollDiceButton = new Button("Roll Dice");
	    rollDiceButton.setMinWidth(Settings.BUTTON_SLIM_WIDTH);
	    rollDiceButton.setMinHeight(Settings.HEIGHT_IMPORTANT_BUTTON);
	    endTurnButton = new Button("End Turn");
	    endTurnButton.setMinWidth(Settings.BUTTON_SLIM_WIDTH);
	    endTurnButton.setMinHeight(Settings.HEIGHT_IMPORTANT_BUTTON);
	    showDevCardButton = new Button("Developement Cards");
	    showDevCardButton.setMinWidth(Settings.BUTTON_WIDE_WIDTH);
	    
	    largestKnight = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/knight_default.png"),50,50,true,true));
	    largestKnight.setOpacity(Settings.LARGEST_KNIGHT_OPACITY);
	    longestRoad = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/road_default.png"),50,50,true,true));
	    longestRoad.setOpacity(Settings.LONGEST_ROAD_OPACITY);
	    
	    HBox hbox = new HBox();
	    hbox.getChildren().addAll(largestKnight,longestRoad);
	    hbox.setSpacing(15);
	    
	    buildAndBuyButtons.add(buyDevCardButton, 0, 1, 3, 1);
	    buildAndBuyButtons.add(buildSetButton, 0, 2);
	    buildAndBuyButtons.add(buildRoadButton, 1, 2);
	    buildAndBuyButtons.add(buildCityButton, 2, 2);
	    buildAndBuyButtons.add(hbox,0,0);
	    buildAndBuyButtons.setVgap(Settings.BUILD_AND_BUY_BUTTONS_VGAP);
	    buildAndBuyButtons.setHgap(Settings.BUILD_AND_BUY_BUTTONS_HGAP);
	    
	    VBox tradeAndDevVBox = new VBox();
	    tradeAndDevVBox.getChildren().addAll(tradeButton,showDevCardButton);
	    tradeAndDevVBox.setSpacing(Settings.TRADE_AND_DEV_BOX_SPACING);
	    
	    VBox diceAndEndVBox = new VBox();
	    diceAndEndVBox.getChildren().addAll(rollDiceButton,endTurnButton);
	    diceAndEndVBox.setSpacing(Settings.DICE_AND_END_BOX_SPACING);
	    
	    otherButtons.getChildren().addAll(tradeAndDevVBox,diceAndEndVBox);
	    otherButtons.setSpacing(120);
	    
	    final String PATH = "/img/resourcetypes/";
	    
	    ImageView lumberImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
	    		(PATH + "lumber.png"),80,80,true,true));
		ImageView brickImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "brick.png"),80,80,true,true));
		ImageView woolImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "wool.png"),70,70,true,true));
		ImageView grainImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "grain.png"),65,65,true,true));
		ImageView oreImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "ore.png"),70,70,true,true));
		ImageView settlementImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "settlementCost.png"),200,100,true,true));
		ImageView cityImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "cityCost.png"),200,100,true,true));
		ImageView roadImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "roadCost.png"),120,60,true,true));
		ImageView developmentImg = new ImageView(new Image(TradeSkin.class.getResourceAsStream
				(PATH + "developmentCardCost.png"),150,75,true,true));
		
	    Tooltip settleT = new Tooltip();
	    settleT.setGraphic(settlementImg);
	    buildSetButton.setTooltip(settleT);
	    
	    Tooltip cityT = new Tooltip();
	    cityT.setGraphic(cityImg);
	    buildCityButton.setTooltip(cityT);
	    
	    Tooltip roadT = new Tooltip();
	    roadT.setGraphic(roadImg);
	    buildRoadButton.setTooltip(roadT);
	    
	    Tooltip devT = new Tooltip();
	    devT.setGraphic(developmentImg);
	    buyDevCardButton.setTooltip(devT);
		
	    allInfo = new GridPane();
	    
	    String resources_id = "infotext";
	    lumber = new Text("0");
	    lumber.setId(resources_id);
	    brick = new Text("0");
	    brick.setId(resources_id);
	    wool = new Text("0");
	    wool.setId(resources_id);
	    grain = new Text("0");
	    grain.setId(resources_id);
	    ore = new Text("0");
	    ore.setId(resources_id);
	    
	    status = new Text(Settings.WARTEN_BEGINN);
	    turn = new Text();
	    settlement = new Text("0" );
	    city = new Text("0");
	    road = new Text("0");
	    
	    changeColor(road);
	    changeColor(city);
	    status.setFill(Color.WHITE);
	    turn.setFill(Color.WHITE);
	    changeColor(settlement);
	    lumber.setFill(Color.WHITE);
	    brick.setFill(Color.WHITE);
	    grain.setFill(Color.WHITE);
	    ore.setFill(Color.WHITE);
	    wool.setFill(Color.WHITE);
		
		StackPane lum = new StackPane();
		lum.getChildren().addAll(lumberImg,lumber);
		StackPane.setAlignment(lumber, Pos.BOTTOM_RIGHT);
		StackPane bri = new StackPane();
		bri.getChildren().addAll(brickImg,brick);
		StackPane.setAlignment(brick, Pos.BOTTOM_RIGHT);
		StackPane woo = new StackPane();
		woo.getChildren().addAll(woolImg,wool);
		StackPane.setAlignment(wool, Pos.BOTTOM_RIGHT);
		StackPane gra = new StackPane();
		gra.getChildren().addAll(grainImg,grain);
		StackPane.setAlignment(grain, Pos.BOTTOM_RIGHT);
		StackPane or = new StackPane();
		or.getChildren().addAll(oreImg,ore);
		StackPane.setAlignment(ore, Pos.BOTTOM_RIGHT);
		
		StackPane resBox = new StackPane();
		Rectangle background = new Rectangle(430,90);
		HBox res = new HBox();
		res.setAlignment(Pos.CENTER);
		res.setSpacing(15);
		res.getChildren().addAll(lum,bri,woo,gra,or);
		background.setStroke(player.getPlayerColor().getColor());
		background.setId("resbackground");
		resBox.getChildren().addAll(background,res);
				
	    allInfo.setHgap(35);
	    allInfo.setVgap(10);
	    allInfo.add(resBox, 0, 4);
	    allInfo.add(status, 1, 4, 3, 1);
	    allInfo.add(settlement, 1, 5);
	    allInfo.add(city, 2, 5);
	    allInfo.add(road, 3, 5);
	    allInfo.add(turn, 6, 4);
	    GridPane.setRowSpan(resBox, 2);
	    GridPane.setValignment(turn, VPos.TOP);
	    GridPane.setHalignment(turn, HPos.CENTER);

	    changeAllFonts();
	    
	    chatVBox = new ChatSkin(playerControl);
	    chatVBox.setPickOnBounds(false);
	    
	    this.getChildren().addAll(buildAndBuyButtons, allInfo, otherButtons, chatVBox);
	    this.setPickOnBounds(false);
	    
	    buildAndBuyButtons.setMouseTransparent(false);
	    buildAndBuyButtons.setPickOnBounds(false);
	    buildAndBuyButtons.setTranslateX(Settings.BUILD_AND_BUY_BUTTONS_POS_X);
	    buildAndBuyButtons.setTranslateY(Settings.BUILD_AND_BUY_BUTTONS_POS_Y);
	    
	    allInfo.setMouseTransparent(true);
	    allInfo.setTranslateX(Settings.ALL_INFO_POS_X);
	    allInfo.setTranslateY(Settings.ALL_INFO__POS_Y);
	    
	    otherButtons.setMouseTransparent(false);
	    otherButtons.setPickOnBounds(false);
	    otherButtons.setTranslateX(Settings.OTHER_BUTTONS_POS_X);
	    otherButtons.setTranslateY(Settings.OTHER_BUTTONS_POS_Y);
	    
	    chatVBox.setTranslateX(Settings.CHAT_BOX_POS_X);
	    chatVBox.setTranslateY(Settings.CHAT_BOX_POS_Y);
	    BorderPane.setAlignment(chatVBox,Pos.CENTER);

	}

	/**
	 * changes font of a labeled object
	 * @param labeled the labeled object which's font is getting changed
	 */
	private void changeFont(Labeled labeled){
		labeled.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.LITTLE_FONT_SIZE));
	}
	
	/**
	 * changes font of a text
	 * @param text the text which's font is getting changed
	 */
	private void changeFontGiant(Text text){
		text.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_22));
		text.setStyle("-fx-font-weight: 900;");
	}

	/**
	 * Changes the color of a text
	 * @param text the text which's color is getting changed
	 */
	private void changeColor(Text text){
		text.setFill(player.getPlayerColor().getColor());
	}
	
	/**
	 * changes font of all labeled objects in class
	 */
	private void changeAllFonts(){
	    changeFont(buildSetButton);
	    changeFont(buildRoadButton);
	    changeFont(buildCityButton);
	    changeFont(buyDevCardButton);
	    changeFont(tradeButton);
	    changeFont(rollDiceButton);
	    changeFont(endTurnButton);
	    changeFont(showDevCardButton);
	    turn.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.HUGE_FONT_SIZE));
	    changeFontGiant(lumber);
	    changeFontGiant(brick);
	    changeFontGiant(wool);
	    changeFontGiant(grain);
	    changeFontGiant(ore);
	    status.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_30));
	    changeFontGiant(settlement);
	    changeFontGiant(city);
	    changeFontGiant(road);
	    if(status.toString().length() > 10){
	    	status.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_30)); }
	    else {
	    	status.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_30*1.5));
	    }
	}

	/**
	 * Getter for ore
	 * @return
	 */
	public Text getOre() {
		return ore;
	}

	/**
	 * Getter for grain
	 * @return
	 */
	public Text getGrain() {
		return grain;
	}

	/**
	 * Getter for wool
	 * @return
	 */
	public Text getWool() {
		return wool;
	}

	/**
	 * Getter for brick
	 * @return
	 */
	public Text getBrick() {
		return brick;
	}

	/**
	 * Getter for lumber
	 * @return
	 */
	public Text getLumber() {
		return lumber;
	}

	/**
	 * Getter for EndTurnButton
	 * @return the endTurnButton
	 */
	public Button getEndTurnButton() {
		return endTurnButton;
	}

	/**
	 * Getter for RollDiceButton
	 * @return the rollDiceButton
	 */
	public Button getRollDiceButton() {
		return rollDiceButton;
	}

	/**
	 * Getter for TradeButton
	 * @return the tradeButton
	 */
	public Button getTradeButton() {
		return tradeButton;
	}

	/**
	 * Getter for BuildRoadButton
	 * @return the BuildRoadButton
	 */
	public Button getBuildRoadButton() {
		return buildRoadButton;
	}

	/**
	 * Getter for BuildSetButton
	 * @return the buildSetButton
	 */
	public Button getBuildSetButton() {
		return buildSetButton;
	}

	/**
	 * Getter for BuildCityButton
	 * @return the buildCityButton
	 */
	public Button getBuildCityButton() {
		return buildCityButton;
	}

	/**
	 * Getter for ShowDevelopmentCardButton
	 * @return the showDevCardButton
	 */
	public Button getShowDevCardButton() {
		return showDevCardButton;
	}

	/**
	 * Getter for Status
	 * @return the status
	 */
	public Text getStatus() {
		return status;
	}

	/**
	 * Getter for Settlement
	 * @return the settlement
	 */
	public Text getSettlement() {
		return settlement;
	}

	/**
	 * Getter for city
	 * @return
	 */
	public Text getCity() {
		return city;
	}

	/**
	 * Getter for road
	 * @return
	 */
	public Text getRoad() {
		return road;
	}

	/**
	 * Getter for BuyDevelopmentCardButton
	 * @return the buyDevelopmentCard
	 */
	public Button getBuyDevCardButton() {
		return buyDevCardButton;
	}

	/**
	 * Getter for ChatVBox
	 * @return the chatVBox
	 */
	public ChatSkin getChatVBox(){
		return (ChatSkin) chatVBox;
	}

	/**
	 * Getter for Turn
	 * @return the turn
	 */
	public Text getTurn() {
		return turn;
	}

	/**
	 * Setter for city
	 * @param city
	 */
	public void setCity(Text city) {
		this.city = city;
	}
	
	/**
	 * Setter for road
	 * @param road
	 */
	public void setRoad(Text road) {
		this.road = road;
	}
	
	/**
	 * Setter for the LargestKnight
	 * @param playerID Player's ID
	 */
	public void setLargestKnight(int playerID){
		Player p = player.getCatan().getPlayerWithID(playerID);
		this.largestKnight.setImage(new Image(TradeSkin.class.getResourceAsStream(p.getPlayerColor().getKnightImageURL()),50,50,true,true));
		largestKnight.setOpacity(1);
	}
	/**
	 * Setter for the LongestRoad
	 * @param playerID Player's ID
	 */
	public void setLongestRoad(int playerID){
		Player p = player.getCatan().getPlayerWithID(playerID);
		this.longestRoad.setImage(new Image(TradeSkin.class.getResourceAsStream
				(p.getPlayerColor().getRoadImageURL()),50,50,true,true));
		longestRoad.setOpacity(1);
	}
	/**
	 * Setter for the LargestKnight
	 */
	public void setLargestKnight(){
		this.largestKnight.setImage(new Image(TradeSkin.class.getResourceAsStream
				("/img/knight_default.png"),50,50,true,true));
		largestKnight.setOpacity(0.4);
	}
	/**
	 * Setter for the LongestRoad
	 */
	public void setLongestRoad(){
		this.longestRoad.setImage(new Image(TradeSkin.class.getResourceAsStream
				("/img/road_default.png"),50,50,true,true));
		longestRoad.setOpacity(0.4);
	}
	
	/**
	 * Changes the text of "turn"
	 * @param name the name of the current turn taker (or "Your")
	 */
	public void setTurnText(String name) {
		if(name.equals("Your")){
			this.turn.setText("  "+name + " turn");
		}
		else this.turn.setText("  "+name + "'s turn");
	}
	
}
