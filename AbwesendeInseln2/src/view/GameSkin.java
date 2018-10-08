package view;

import java.net.URISyntaxException;
import java.util.ArrayList;

import controller.LogMaster;
import controller.StatsControl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import game.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Catan;
import model.Dice;
import model.Player;
import model.ResourceType;
import model.Tile;


/**
 * View of the game board. Contains e.g. view of island.
 * @author Daniel Panangian, Stefanie Kloss, Lena Sonnleitner
 */
public class GameSkin extends StackPane {
	
	private Catan catan;
	
	private TradeSkin tradeSkin;

	private IslandSkin islandSkin;
	private DevelopmentCardSkin developmentCardSkin;
	
	private StackPane largestArmy, longestRoad;
	
	private StatsControlSkin statsControl;
	/**
	 * x coordinate (for window)
	 */
	private double xOffset = 0;
	/**
	 * y coordinate (for window)
	 */
    private double yOffset = 0;

	/**
	 * Constructor
	 * @param catan
	 */
	public GameSkin(Catan catan){
		
		this.catan = catan;
		setPrefSize(Settings.GAME_SKIN_WIDTH, Settings.GAME_SKIN_HEIGHT);
		this.tradeSkin = new TradeSkin(catan);
		
		this.islandSkin = catan.getIsland().getSkin();
		
		
		this.developmentCardSkin = new DevelopmentCardSkin(catan);
		
		PlayerControlSkin playerControl = catan.getPlayerClient().getPlayerControl().getSkin();
		StackPane.setMargin(playerControl, new Insets(10,20,10,20));
		
		this.statsControl = new StatsControl(catan).getSkin();
		
		String path = null;
		try {
			path = GameSkin.class.getResource("/img/water-only-background3.jpg" ).toURI().toString();
			Image image = new Image(path);
			ImageView waterView = new ImageView(image);
			waterView.setFitWidth(Settings.GAME_SKIN_WIDTH);
			waterView.setFitHeight(Settings.GAME_SKIN_HEIGHT);

			getChildren().addAll(waterView,islandSkin,playerControl);
		} catch (URISyntaxException | NullPointerException e) {
			LogMaster.log("[UI]Specified file " + path + "does not exist." +" [Thread : "+ Thread.currentThread().getId() + "]");
			e.printStackTrace();
		}
		
		initWindow();
		
		locateDice(catan.getDice());
	}
	
	/**
	 * Updates the StatsControlSkin of all playerboxes
	 */
	public void updateStats() {
		statsControl.update();
	}

	/**
	 * Adds the ImageViews of largest army and longest road to the skin
	 */
	@SuppressWarnings("unused")
	private void addlargestArmyAndLongestRoad() {
		
		longestRoad = new StackPane();
		largestArmy = new StackPane();
		ImageView knight = new ImageView(new Image(GameSkin.class.getResourceAsStream("/img/knight.png"),150,150,true,true));
		ImageView road = new ImageView(new Image(GameSkin.class.getResourceAsStream("/img/road.png"),150,150,true,true));;
		longestRoad.getChildren().add(knight);
		largestArmy.getChildren().add(road);
	}

	/**
	 * Window opened to let PlayerClient choose which Player he wants to steal from
	 * @param tile	Tile on which the robber is going to be positioned
	 * @param players Players the PlayerClient can steal from
	 */
	public void chooseRobbingVictim(Tile tile, ArrayList<Player> players) {
		
		Text text = new Text("\nWhich player do you want to rob from?");
		changeFontAndFill(text);
		RadioButton[] playerButtons = new RadioButton[players.size()];
		ToggleGroup toggleGroup = new ToggleGroup();
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().add(text);
		for(int i = 0; i < players.size(); i++){
			playerButtons[i] = new RadioButton(players.get(i).getName() + ", " + players.get(i).getPlayerColor().toServerString());
			playerButtons[i].setToggleGroup(toggleGroup);
			playerButtons[i].setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
			playerButtons[i].setTextFill(Color.WHITE);
			vBox.getChildren().add(playerButtons[i]);
		}
		Button confirm = new Button("OK");
		confirm.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		vBox.getChildren().add(confirm);
		catan.getGameManager().getApp().popUpPane(vBox);
		
		confirm.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				for(int i = 0; i < players.size(); i++){
					if(playerButtons[i].isSelected()){	
						Player player = players.get(i);
						LogMaster.log("[UI]Player: "  + catan.getGameManager().getClient().getClientId() + " pressed [OK] and is trying to rob player: "+ player + " [Thread : "+ Thread.currentThread().getId() + "]");
						ArrayList<Player> victim = new ArrayList<Player>();
						victim.add(player);
						catan.getPlayerClient().robbingVictim(tile, victim);
				}	}
			}
		});		
	}
	
	/**
	 * Open when PlayerClient has to discard half of his cards
	 * @param discardCount count of cards that have to be discarded
	 */
	public void discardCardsStage(int discardCount) {
		
		Text instruction = new Text("Please choose the " + discardCount + " cards you want to get rid of!");
		changeFontAndFill(instruction);
		Text lumber = new Text("Lumber");
		changeFontAndFill(lumber);
		Text brick = new Text("Brick");
		changeFontAndFill(brick);
		Text wool = new Text("Wool");
		changeFontAndFill(wool);
		Text grain = new Text("Grain");
		changeFontAndFill(grain);
		Text ore = new Text("Ore");
		changeFontAndFill(ore);
		
		ChoiceBox<Integer> lumberBox = new ChoiceBox<Integer>();
		ChoiceBox<Integer> brickBox = new ChoiceBox<Integer>();
		ChoiceBox<Integer> woolBox = new ChoiceBox<Integer>();
		ChoiceBox<Integer> grainBox = new ChoiceBox<Integer>();
		ChoiceBox<Integer> oreBox = new ChoiceBox<Integer>();
		
		ImageView brickPic = ResourceType.BRICK.getSkin();
		ImageView grainPic = ResourceType.GRAIN.getSkin();
		ImageView lumberPic = ResourceType.LUMBER.getSkin();
		ImageView orePic = ResourceType.ORE.getSkin();
		ImageView woolPic = ResourceType.WOOL.getSkin();
		
		addItems(lumberBox, catan.getPlayerClient().getHand().getLumberCount());
		addItems(brickBox, catan.getPlayerClient().getHand().getBrickCount());
		addItems(woolBox, catan.getPlayerClient().getHand().getWoolCount());
		addItems(grainBox, catan.getPlayerClient().getHand().getGrainCount());
		addItems(oreBox, catan.getPlayerClient().getHand().getOreCount());

		lumberBox.getSelectionModel().selectFirst();
		brickBox.getSelectionModel().selectFirst();
		woolBox.getSelectionModel().selectFirst();
		grainBox.getSelectionModel().selectFirst();
		oreBox.getSelectionModel().selectFirst();
		
		GridPane choiceBoxes = new GridPane();
		choiceBoxes.add(lumber, 0, 0);
		choiceBoxes.add(lumberBox, 1, 0);
		choiceBoxes.add(lumberPic, 2, 0);
		choiceBoxes.add(brick, 0, 1);
		choiceBoxes.add(brickBox, 1, 1);
		choiceBoxes.add(brickPic, 2, 1);
		choiceBoxes.add(wool, 0, 2);
		choiceBoxes.add(woolBox, 1, 2);
		choiceBoxes.add(woolPic, 2, 2);
		choiceBoxes.add(grain, 0, 3);
		choiceBoxes.add(grainBox, 1, 3);
		choiceBoxes.add(grainPic, 2, 3);
		choiceBoxes.add(ore, 0, 4);
		choiceBoxes.add(oreBox, 1, 4);
		choiceBoxes.add(orePic, 2, 4);
		choiceBoxes.setHgap(Settings.CHOICEBOXES_GAP);
		choiceBoxes.setVgap(Settings.CHOICEBOXES_GAP);
		
		Button confirm = new Button("OK");
		confirm.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().addAll(instruction, choiceBoxes, confirm);
		
		catan.getGameManager().getApp().popUpPane(vBox);


		confirm.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				catan.getGameManager().getClient().sendGiveCards(lumberBox.getValue(), 
					brickBox.getValue(), woolBox.getValue(), grainBox.getValue(), oreBox.getValue());
				if((lumberBox.getValue()+brickBox.getValue()+ woolBox.getValue()+ grainBox.getValue()+ oreBox.getValue()) == discardCount){
					catan.getGameManager().getApp().closeAllPopUpStages();
				}
					
				LogMaster.log("[UI]Player: " + catan.getGameManager().getClient().getClientId() + " pressed [Ok] to send his cards he has to give up" +" [Thread : "+ Thread.currentThread().getId() + "]");
			}
		});
		
		
	}
	
	/**
	 * Adds right amount of items to a choice box of the "discardCards - view"
	 * @param choiceBox resource - choiceBox
	 * @param resourceCount amount of cards the player has from this resource
	 */
	private void addItems(ChoiceBox<Integer> choiceBox, int resourceCount) {
		for(int counter = 0; counter <= resourceCount; counter++){
			choiceBox.getItems().add(counter);
		}
	}

	/**
	 * Checks if the player has all the cards he wants to give away and checks if he has given enough
	 * @param lumber
	 * @param brick
	 * @param grain
	 * @param ore
	 * @param wool
	 * @param discardCount
	 * @return
	 */
	public boolean canGiveCards(int lumber, int brick, int grain, int ore, int wool, int discardCount){
		return((lumber+brick+ grain+ ore+ wool) == discardCount) && catan.getPlayerClient().getHand().containsRes(lumber, brick, wool, ore, grain);
	}
	
	/**
	 * Creates a frame at the bottom and the top of the window that can be moved by pressing the mouse
	 * @return
	 */
	public Pane initWindow(){
		AnchorPane area = new AnchorPane();
		
		AnchorPane dragArea = new AnchorPane();
		dragArea.setId("window");
		dragArea.setPrefHeight(20);
		dragArea.setPrefWidth(Settings.GAME_SKIN_WIDTH);
		dragArea.setLayoutX(0);
		dragArea.setLayoutY(-1);
		dragArea.setPickOnBounds(true);
		
		VBox upper = new VBox();
		upper.setPickOnBounds(false);
		upper.getChildren().addAll(dragArea,statsControl);
		
		
		AnchorPane dragArea2 = new AnchorPane();
		dragArea2.setId("window");
		dragArea2.setPrefHeight(20);
		dragArea2.setPrefWidth(Settings.GAME_SKIN_WIDTH);
		dragArea2.setLayoutX(0);
		dragArea2.setLayoutY(Settings.GAME_SKIN_HEIGHT+1);
		dragArea2.setPickOnBounds(true);
		
		area.getChildren().addAll(upper,dragArea2);
		area.setPickOnBounds(false);
		getChildren().add(area);
		
		
		area.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        area.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                catan.getGameManager().getApp().getStage().setX(event.getScreenX() - xOffset);
                catan.getGameManager().getApp().getStage().setY(event.getScreenY() - yOffset);
            }
        });
		
		Text title = new Text("Settlers of Catan - " +  catan.getPlayerClient().getName());
		title.setFill(Color.WHITE);
		title.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		title.setLayoutX(Settings.GAME_SKIN_WIDTH /2);
		title.setLayoutY(18);
		title.setTextAlignment(TextAlignment.CENTER);
		
		Text closeIcon=FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.CLOSE,"12");
		closeIcon.setFill(Color.WHITE);
		closeIcon.setLayoutX(Settings.GAME_SKIN_WIDTH - 22);
		closeIcon.setLayoutY(18);
		closeIcon.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		dragArea.getChildren().addAll(title,closeIcon);
		return area;
		
	}
	/**
	 * Open when Player has to give all Cards of one resource to the player who
	 * player the Development Card Monopoly
	 */
	public void handCardsToPlayerStage(String resource, String name) {
		
		String instruction = name + " just played MONOPOLY and robbed the resource: " + resource + "!";
		
		catan.getGameManager().getApp().popUpMessage(instruction);
		
	}
	
	/**
	 * locates the dices on the gameSkin
	 * @param dice
	 */
	private void locateDice(Dice dice){
		
		Rectangle dice1 = dice.getDiceSkin().getDice1();
		Rectangle dice2 = dice.getDiceSkin().getDice2();
		
		dice1.setTranslateX(Settings.DICE1_X);
		dice1.setTranslateY(Settings.DICE1_Y);
		dice2.setTranslateX(Settings.DICE2_X);
		dice2.setTranslateY(Settings.DICE2_Y);
		
		getChildren().add(dice1);
		getChildren().add(dice2);
		
	}
	
	/**
	 * changes font and fill of a text
	 * @param text
	 */
	private void changeFontAndFill(Text text){
		text.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		text.setFill(Color.WHITE);
	}
	
	/**
	 * Getter for IslandSkin
	 * @return the island skin
	 */
	public IslandSkin getIslandSkin(){
		return this.islandSkin;
	}
	
	/**
	 * Getter for DevelopmentCardSkin
	 * @return the developmentCard skin
	 */
	public DevelopmentCardSkin getDevelopmentCardSkin(){
		return this.developmentCardSkin;
	}
	
	/**
	 * Getter for TradeSkin
	 * @return the tradeSkin
	 */
	public TradeSkin getTradeSkin() {
		return tradeSkin;
	}
	
	/**
	 * Getter for StatsControlSkin
	 * @return
	 */
	public StatsControlSkin getStatsControl() {
		return statsControl;
	}
}
