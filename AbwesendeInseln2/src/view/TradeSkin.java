package view;


import java.net.URISyntaxException;
import java.util.ArrayList;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Catan;
import model.Player;
import model.PlayerClient;
import model.ResourceType;
import model.TradeOffer;

/**
 * Contains all different scenes of stages of trade
 * @author Lena Sonnleitner, Stefanie Kloss
 *
 */
public class TradeSkin {
	
	private Catan catan;
	private Stage tradeStage;
	
	private Scene maritimeTradeScene;
	private Scene newOfferScene;
	private Scene tradeOptionsScene;
	private Scene offerOverviewScene;
	
	private FlowPane offerOverviewFlowPane;

	private ChoiceBox<Integer> lumberOffer, brickOffer, woolOffer, grainOffer, oreOffer;
	private ChoiceBox<Integer> lumberRequest, brickRequest, woolRequest, grainRequest, oreRequest;
	private Button confirmNewOffer, cancelNewOffer, back;
	private Button tradeMaritimeLumber, tradeMaritimeBrick, tradeMaritimeWool, tradeMaritimeGrain, tradeMaritimeOre;
	private RadioButton maritimeLumberRequest, maritimeBrickRequest, maritimeWoolRequest, maritimeGrainRequest, maritimeOreRequest;
	/**
	 * Constructor
	 * @param catan
	 */
	public TradeSkin(Catan catan) {

		this.catan = catan;
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				tradeStage = new Stage();
				try{
					catan.getGameManager().getApp().addPopUpStage(tradeStage);
				}catch(Exception e){
				}
				
				tradeStage.setTitle("Trade");
				tradeStage.setResizable(false);
				try{
				tradeStage.initOwner(catan.getGameManager().getApp().getStage());
				}catch(Exception e){
				}
				offerOverviewFlowPane = new FlowPane();
				offerOverviewScene = new Scene(offerOverviewFlowPane);
				
				createNewOfferScene();
				createMaritimeTradeScene();
				createTradeOptionsScene();
				
				back = new Button("Go back");
				back.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent event) {
						setTradeStageScene(tradeOptionsScene);
					}
					
				});
				
				setTradeStageScene(tradeOptionsScene);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
		try {
			LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		
	}
	
	/**
	 * Updates the FlowPane offerOverviewFlowPane
	 * @param allTradeOffers
	 * @param show true if new scene should be opened right away, false if not
	 */
	public void updateOfferOverview(ArrayList<TradeOffer> allTradeOffers, boolean show){
		
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				offerOverviewFlowPane = new FlowPane();
				offerOverviewFlowPane.setStyle("-fx-background-color: #dddd; -fx-padding: 10;");
				offerOverviewFlowPane.setPadding(new Insets(5, 0, 5, 0));
				offerOverviewFlowPane.setVgap(10);
				offerOverviewFlowPane.setHgap(5);
				offerOverviewFlowPane.setPrefWrapLength(610);
				if(allTradeOffers.size()>0){
					for(TradeOffer tradeOffer : allTradeOffers){
						offerOverviewFlowPane.getChildren().add(tradeOffer.getSkin());
					}
				}
				else{
					Label noTradesText = new Label("There are no trades online right now.");
					Settings.changeFontBig(noTradesText, false);
					offerOverviewFlowPane.getChildren().addAll(noTradesText, back);
				}

				offerOverviewScene = new Scene(offerOverviewFlowPane);
				if(show){
					tradeStage.setScene(offerOverviewScene);
					tradeStage.show();
				}
				LogMaster.log("[UI]New Offerview with " + allTradeOffers.size() + " Offers"+ " [Thread : "+ Thread.currentThread().getId() + "]");
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	/**
	 * Creates the scene opened for new trade offer
	 */
	private void createNewOfferScene() {

		StackPane newOfferPane = new StackPane();

		GridPane gridPane = new GridPane();

		ImageView backgroundTrade = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/grass.jpg"),500,200,false,false));
		newOfferPane.getChildren().add(backgroundTrade);	
	    
		addElements(gridPane);
		newOfferPane.getChildren().add(gridPane);
		
		addButtonEventHandler();
		
		this.newOfferScene =  new Scene(newOfferPane, 500, 200);
	}
	
	/**
	 * Creates the scene opened for new trade offer
	 */
	private void createMaritimeTradeScene() {

		StackPane newMaritimePane = new StackPane();

		GridPane gridPane = new GridPane();
		
		String path = "";
		try {
			path = SetUpSkin.class.getResource("/img/trade-offer.jpg" ).toURI().toString();
		} catch (URISyntaxException e) {
			LogMaster.log("[UI]" + e.getMessage() + " Couldn't find image of TradeSkin."+ " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		Image image = new Image(path, Settings.MARITIME_TRADE_SKIN_WIDTH, Settings.MARITIME_TRADE_SKIN_HEIGHT, false, false);
		ImageView backgroundTrade = new ImageView(image);
		newMaritimePane.getChildren().add(backgroundTrade);	
	    
		addMaritimeElements(gridPane);
		newMaritimePane.getChildren().add(gridPane);
		
		addMaritimeButtonEventHandler();
		
		this.maritimeTradeScene =  new Scene(newMaritimePane, Settings.MARITIME_TRADE_SKIN_WIDTH, Settings.MARITIME_TRADE_SKIN_HEIGHT);
	}
	
	/**
	 * Creates the tradeOptionsScene
	 */
	private void createTradeOptionsScene() {
		
		StackPane pane = new StackPane();
		pane.setStyle("-fx-background-color: darkred; -fx-padding: 20;");
		
		GridPane gridPane = new GridPane();
		
		Label instruction = new Label("What are you here for?\nPlease select one of the options underneath.");
		changeFont(instruction, true);
		
		RadioButton tradeOverView = new RadioButton("Get an overview of all Internal Trade Offers");
		tradeOverView.setSelected(true);
		changeFont(tradeOverView, true);
		RadioButton internalTradeOffer = new RadioButton("Place a new Internal Trade Offer");
		changeFont(internalTradeOffer, true);
		RadioButton maritimeTrade = new RadioButton("Enter a Maritime Trade");
		changeFont(maritimeTrade, true);
		
		ToggleGroup toggleGroup = new ToggleGroup();
		tradeOverView.setToggleGroup(toggleGroup);
		internalTradeOffer.setToggleGroup(toggleGroup);
		maritimeTrade.setToggleGroup(toggleGroup);
		
		Button confirm = new Button("OK");
		changeFont(confirm, false);
		
		confirm.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				switch(((RadioButton)toggleGroup.getSelectedToggle()).getText().substring(0, 3)){
				case "Get": updateOfferOverview(catan.getTrades(), true); break; 
				case "Pla": setTradeStageScene(newOfferScene); 		break;
				case "Ent": setTradeStageScene(maritimeTradeScene); break; 
				
				}
				
			}
			
		});
		
		gridPane.add(instruction, 0, 0);
		gridPane.add(tradeOverView, 0, 1);
		gridPane.add(internalTradeOffer, 0, 2);
		gridPane.add(maritimeTrade, 0, 3);
		gridPane.add(confirm, 1, 4);
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		pane.getChildren().add(gridPane);
		
		this.tradeOptionsScene = new Scene(pane);
	}
	
	/**
	 * shows trade done
	 */
	public void showSealTheDeal(ArrayList<Integer> acceptedTraderIDs, int tradeID, Player player){
		Label instruction = new Label("With which player do you want to seal the deal?");
		changeFontBig(instruction, true);
		RadioButton[] playerButtons = new RadioButton[acceptedTraderIDs.size()];
		ToggleGroup toggleGroup = new ToggleGroup();
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().add(instruction);
		
		for(int i = 0; i < acceptedTraderIDs.size(); i++){
			Player p = player.getCatan().getPlayerWithID(acceptedTraderIDs.get(i));
			playerButtons[i] = new RadioButton(p.getName() + ", " + p.getPlayerColor().toServerString());
			playerButtons[i].setToggleGroup(toggleGroup);
			playerButtons[i].setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
			playerButtons[i].setTextFill(Color.WHITE);
			vBox.getChildren().add(playerButtons[i]);
		}
		
		Button cancel = new Button("Cancel");
		cancel.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		
		Button confirm = new Button("OK");
		confirm.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		
		HBox buttons = new HBox();
		buttons.setSpacing(20);
		buttons.getChildren().addAll(confirm, cancel);
		
		vBox.getChildren().add(buttons);
		
		Stage popUpStage = new Stage();
		popUpStage.initOwner(tradeStage);
		vBox.setStyle("-fx-background-color: darkred; -fx-padding: 15;");
		Scene popUpScene = new Scene(vBox);
		popUpStage.setScene(popUpScene);
		popUpStage.centerOnScreen();
		popUpStage.show();
		
		catan.getGameManager().getApp().popUpPane(vBox);
		
		confirm.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				for(int i = 0; i < acceptedTraderIDs.size(); i++){
					if(playerButtons[i].isSelected()){	
						player.getCatan().getGameManager().getApp().closeAllPopUpStages();
						player.getCatan().getGameManager().getClient().
						            sendSuccessfullTrade(tradeID, acceptedTraderIDs.get(i));
						tradeStage.close();
				}	}
			}
		});	
		
		cancel.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				popUpStage.close();
			}
		});	
		
	}
	
	/**
	 * Adds the EventHandler to the buttons "ok" and "cancel" of the newOfferPane
	 */
	private void addButtonEventHandler() {
		confirmNewOffer.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
				int[] offer = {lumberOffer.getValue(), brickOffer.getValue(), woolOffer.getValue(), 
						       grainOffer.getValue(), oreOffer.getValue()};
				int[] request = {lumberRequest.getValue(), brickRequest.getValue(), woolRequest.getValue(), 
					             grainRequest.getValue(), oreRequest.getValue()};
				
				catan.getGameManager().getClient().sendTradeOffer(offer, request);
			}
			
		});
		
		cancelNewOffer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				tradeStage.close();
			}
		});
		
	}
	/**
	 * Updates the button texts on MaritimeTradePane to match the prices
	 * @param prices prices for each button in order: Lumber, Bricks, Wool, Grain, Ore
	 */
	public void updateMaritimeButtons(int[] prices){
		tradeMaritimeLumber.setText(prices[0] + " Lumber");
		tradeMaritimeBrick.setText(prices[1] + " Bricks");
		tradeMaritimeWool.setText(prices[2] + " Wool");
		tradeMaritimeGrain.setText(prices[3] + " Grain");
		tradeMaritimeOre.setText(prices[4] + " Ore");
	}
	
	/**
	 * Adds the EventHandler to the Buttons and RadioButtons in MaritimeTradePane
	 */
	private void addMaritimeButtonEventHandler() {
		PlayerClient active = catan.getPlayerClient();
		//RadioButtons
		maritimeLumberRequest.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				updateMaritimeButtons(active.getMaritimePrices(ResourceType.LUMBER));
				disableOtherRadioButtons(0);
			}
		});
		
		maritimeBrickRequest.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				updateMaritimeButtons(active.getMaritimePrices(ResourceType.BRICK));
				disableOtherRadioButtons(1);
			}
		});
		
		maritimeWoolRequest.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				updateMaritimeButtons(active.getMaritimePrices(ResourceType.WOOL));
				disableOtherRadioButtons(2);
			}
		});
		
		maritimeGrainRequest.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				updateMaritimeButtons(active.getMaritimePrices(ResourceType.GRAIN));
				disableOtherRadioButtons(3);
			}
		});
		
		maritimeOreRequest.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				updateMaritimeButtons(active.getMaritimePrices(ResourceType.ORE));
				disableOtherRadioButtons(4);
			}
		});
		
		//Buttons
		tradeMaritimeLumber.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				doMaritimeTrade(ResourceType.LUMBER);
			}
		});
		
		tradeMaritimeBrick.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				doMaritimeTrade(ResourceType.BRICK);
			}
		});
		
		tradeMaritimeWool.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				doMaritimeTrade(ResourceType.WOOL);
			}
		});
		
		tradeMaritimeGrain.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				doMaritimeTrade(ResourceType.GRAIN);
			}
		});
		
		tradeMaritimeOre.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				doMaritimeTrade(ResourceType.ORE);
			}
		});
	}
	
	/**
	 * Disables all other RadioButtons for requests
	 * @param except
	 */
	private void disableOtherRadioButtons(int except){
		RadioButton[] rbutts = {maritimeLumberRequest,maritimeBrickRequest,maritimeWoolRequest,
				maritimeGrainRequest,maritimeOreRequest};
		for(int i=0; i<5;i++){
			if(i != except){
				rbutts[i].setSelected(false);
			}
		}
	}
	
	/**
	 * Executes a maritime Trade with the given payment and the (through RadioButtons) selected request
	 * @param payment Type of payment
	 */
	private void doMaritimeTrade(ResourceType payment){
		PlayerClient active = catan.getPlayerClient();
		
		//Request
		ResourceType req = null;
		int[] request = {0,0,0,0,0};
		ResourceType[] res = {ResourceType.LUMBER,ResourceType.BRICK,ResourceType.WOOL,
				ResourceType.GRAIN,ResourceType.ORE};
		RadioButton[] rbutts = {maritimeLumberRequest,maritimeBrickRequest,maritimeWoolRequest,
				maritimeGrainRequest,maritimeOreRequest};
		for(int i = 0; i<5;i++){
			if(rbutts[i].isSelected()){
				req = res[i];
				request[i] = 1;
			}
		}
		if(req==null){
			LogMaster.log("[UI]Error: You have to select a Request first"+ " [Thread : "+ Thread.currentThread().getId() + "]");
			return;
		}
		//Payment
		int[] offer = {0,0,0,0,0};
		int payInt = -1;
		switch(payment){
		case LUMBER: payInt = 0;break;
		case BRICK: payInt = 1;break;
		case WOOL: payInt = 2;break;
		case GRAIN: payInt = 3;break;
		case ORE: payInt = 4;break;
		}
		offer[payInt] = active.getMaritimePrices(req)[payInt];
	catan.getGameManager().getClient().sendMaritimeTrade(offer, request);
	}
	
	/**
	 * Adds Buttons and TextFields to the newOfferPane
	 */
	private void addElements(GridPane gridPane) {
		
		ImageView lumber = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/resourcetypes/lumber.png"),55,55,true,true));
		ImageView brick = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/resourcetypes/brick.png"),55,55,true,true));
		ImageView wool = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/resourcetypes/wool.png"),55,55,true,true));
		ImageView grain = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/resourcetypes/grain.png"),55,55,true,true));
		ImageView ore = new ImageView(new Image(TradeSkin.class.getResourceAsStream("/img/resourcetypes/ore.png"),55,55,true,true));
		
		Label offerLabel = new Label("Offer: ");
		Label requestLabel = new Label("Request: ");
		Label lumberLabel = new Label("Lumber");
		Label brickLabel = new Label("Brick");
		Label woolLabel = new Label("Wool");
		Label grainLabel = new Label("Grain");
		Label oreLabel = new Label("Ore");

		changeFontBig(offerLabel, true);
		changeFontBig(requestLabel, true);
		changeFontBig(lumberLabel, true);
		changeFontBig(brickLabel, true);
		changeFontBig(woolLabel, true);
		changeFontBig(grainLabel, true);
		changeFontBig(oreLabel, true);
		
		this.confirmNewOffer = new Button("OK");
		changeFont(confirmNewOffer, false);
		this.cancelNewOffer = new Button("Cancel");
		changeFont(cancelNewOffer, false);
		
		this.lumberOffer = new ChoiceBox<Integer>();
		this.brickOffer = new ChoiceBox<Integer>();
		this.woolOffer = new ChoiceBox<Integer>();
		this.grainOffer = new ChoiceBox<Integer>();
		this.oreOffer = new ChoiceBox<Integer>();
		this.lumberRequest = new ChoiceBox<Integer>();
		this.brickRequest = new ChoiceBox<Integer>();
		this.woolRequest = new ChoiceBox<Integer>();
		this.grainRequest = new ChoiceBox<Integer>();	
		this.oreRequest = new ChoiceBox<Integer>();
		
		for(int i = 0; i < 15; i++){
			lumberOffer.getItems().add(i);
			brickOffer.getItems().add(i);
			woolOffer.getItems().add(i);
			grainOffer.getItems().add(i);
			oreOffer.getItems().add(i);
			lumberRequest.getItems().add(i);
			brickRequest.getItems().add(i);
			woolRequest.getItems().add(i);
			grainRequest.getItems().add(i);
			oreRequest.getItems().add(i);
		}
		
		lumberOffer.getSelectionModel().selectFirst();
		brickOffer.getSelectionModel().selectFirst();
		woolOffer.getSelectionModel().selectFirst();
		grainOffer.getSelectionModel().selectFirst();
		oreOffer.getSelectionModel().selectFirst();
		lumberRequest.getSelectionModel().selectFirst();
		brickRequest.getSelectionModel().selectFirst();
		woolRequest.getSelectionModel().selectFirst();
		grainRequest.getSelectionModel().selectFirst();
		oreRequest.getSelectionModel().selectFirst();
		
		gridPane.add(lumber, 1, 0);
		gridPane.add(brick, 2, 0);
		gridPane.add(wool, 3, 0);
		gridPane.add(grain, 4, 0);
		gridPane.add(ore, 5, 0);
		gridPane.add(lumberLabel, 1,1);
		gridPane.add(brickLabel, 2,1);
		gridPane.add(woolLabel, 3,1);
		gridPane.add(grainLabel, 4,1);
		gridPane.add(oreLabel, 5,1);
		gridPane.add(offerLabel, 0,2);
		gridPane.add(lumberOffer, 1,2);
		gridPane.add(brickOffer, 2,2);
		gridPane.add(woolOffer, 3,2);
		gridPane.add(grainOffer, 4,2);
		gridPane.add(oreOffer, 5,2);
		gridPane.add(requestLabel, 0,3);
		gridPane.add(lumberRequest, 1,3);
		gridPane.add(brickRequest, 2,3);
		gridPane.add(woolRequest, 3,3);
		gridPane.add(grainRequest, 4,3);
		gridPane.add(oreRequest, 5,3);
	
		gridPane.add(cancelNewOffer, 4,4);
		gridPane.add(confirmNewOffer, 5,4);
		
		gridPane.setHgap(23);
		gridPane.setVgap(5);
		
		StackPane.setMargin(gridPane, new Insets(10,10,10,20));

		GridPane.setHalignment(cancelNewOffer, HPos.CENTER);
		GridPane.setHalignment(confirmNewOffer, HPos.CENTER);
		GridPane.setHalignment(lumber, HPos.CENTER);
		GridPane.setHalignment(brick, HPos.CENTER);
		GridPane.setHalignment(wool, HPos.CENTER);
		GridPane.setHalignment(grain, HPos.CENTER);
		GridPane.setHalignment(ore, HPos.CENTER);
		GridPane.setHalignment(lumberLabel, HPos.CENTER);
		GridPane.setHalignment(brickLabel, HPos.CENTER);
		GridPane.setHalignment(woolLabel, HPos.CENTER);
		GridPane.setHalignment(grainLabel, HPos.CENTER);
		GridPane.setHalignment(oreLabel, HPos.CENTER);
		GridPane.setHalignment(lumberOffer, HPos.CENTER);
		GridPane.setHalignment(brickOffer, HPos.CENTER);
		GridPane.setHalignment(woolOffer, HPos.CENTER);
		GridPane.setHalignment(grainOffer, HPos.CENTER);
		GridPane.setHalignment(oreOffer, HPos.CENTER);
		GridPane.setHalignment(lumberRequest, HPos.CENTER);
		GridPane.setHalignment(brickRequest, HPos.CENTER);
		GridPane.setHalignment(woolRequest, HPos.CENTER);
		GridPane.setHalignment(grainRequest, HPos.CENTER);
		GridPane.setHalignment(oreRequest, HPos.CENTER);
		
	}
	
	/**
	 * Adds Buttons and TextFields to the MaritimeTradePane
	 */
	private void addMaritimeElements(GridPane gridPane) {
		//Labels
		Label requestLabel = new Label("Request: ");
		changeFontBig(requestLabel, true);
		gridPane.add(requestLabel, 0, 1);
		GridPane.setHalignment(requestLabel, HPos.CENTER);
		
		Label paymentLabel = new Label("Payment: ");
		changeFontBig(paymentLabel, true);
		gridPane.add(paymentLabel, 0, 2);
		GridPane.setHalignment(paymentLabel, HPos.CENTER);
		//Buttons
		this.tradeMaritimeLumber = new Button("X Lumber");
		changeFont(tradeMaritimeLumber, false);
		gridPane.add(tradeMaritimeLumber, 1,2);
		GridPane.setHalignment(tradeMaritimeLumber, HPos.CENTER);
		
		this.tradeMaritimeBrick = new Button("X Bricks");
		changeFont(tradeMaritimeBrick, false);
		gridPane.add(tradeMaritimeBrick, 2,2);
		GridPane.setHalignment(tradeMaritimeBrick, HPos.CENTER);
		
		this.tradeMaritimeWool = new Button("X Wool");
		changeFont(tradeMaritimeWool, false);
		gridPane.add(tradeMaritimeWool, 3,2);
		GridPane.setHalignment(tradeMaritimeWool, HPos.CENTER);
		
		this.tradeMaritimeGrain = new Button("X Grain");
		changeFont(tradeMaritimeGrain, false);
		gridPane.add(tradeMaritimeGrain, 4,2);
		GridPane.setHalignment(tradeMaritimeGrain, HPos.CENTER);
		
		this.tradeMaritimeOre = new Button("X Ore");
		changeFont(tradeMaritimeOre, false);
		gridPane.add(tradeMaritimeOre, 5,2);
		GridPane.setHalignment(tradeMaritimeOre, HPos.CENTER);
		//RadioButtons
		this.maritimeLumberRequest = new RadioButton("Lumber");
		changeFont(maritimeLumberRequest, true);
		gridPane.add(maritimeLumberRequest, 1,1);
		GridPane.setHalignment(maritimeLumberRequest, HPos.CENTER);
		
		this.maritimeBrickRequest = new RadioButton("Bricks");
		changeFont(maritimeBrickRequest, true);
		gridPane.add(maritimeBrickRequest, 2,1);
		GridPane.setHalignment(maritimeBrickRequest, HPos.CENTER);
		
		this.maritimeWoolRequest = new RadioButton("Wool");
		changeFont(maritimeWoolRequest, true);
		gridPane.add(maritimeWoolRequest, 3,1);
		GridPane.setHalignment(maritimeWoolRequest, HPos.CENTER);
		
		this.maritimeGrainRequest = new RadioButton("Grain");
		changeFont(maritimeGrainRequest, true);
		gridPane.add(maritimeGrainRequest, 4,1);
		GridPane.setHalignment(maritimeGrainRequest, HPos.CENTER);
		
		this.maritimeOreRequest = new RadioButton("Ore");
		changeFont(maritimeOreRequest, true);
		gridPane.add(maritimeOreRequest, 5,1);
		GridPane.setHalignment(maritimeOreRequest, HPos.CENTER);	
		
		//Window
		gridPane.setHgap(23);
		gridPane.setVgap(5);
		StackPane.setMargin(gridPane, new Insets(77,10,10,25));
	}
	
	/**
	 * opens the TradeStage with tradeOptions as Scene
	 */
	public void showTradeOptionsStage(){
		setTradeStageScene(tradeOptionsScene);
		this.tradeStage.show();
	}
	
	/**
	 * changes font of a labeled object									
	 * @param labeled
	 * @param changeColor
	 */
	private void changeFont(Labeled labeled, boolean changeColor){
		if(changeColor) labeled.setTextFill(Color.ANTIQUEWHITE);
		labeled.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
	}
	
	/**
	 * changes font of a labeled object
	 * @param labeled
	 */
	private void changeFontBig(Labeled labeled, boolean changeColor){
		if(changeColor) labeled.setTextFill(Color.ANTIQUEWHITE);
		labeled.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.HUGE_FONT_SIZE));
	}

	/**
	 * Shows the TradeOfferview
	 */
	public void showTradeOfferview() {
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				setTradeStageScene(offerOverviewScene);
				tradeStage.show();
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
		try {
			LogMaster.log("[Thr]Thread sleeps for 10ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
		
	}
	
	/**
	 * TradeAccepted window
	 * @param message
	 * @param primaryStage
	 */
	public void tradeAccepted(String message, Stage primaryStage) {
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				Stage popUpStage = new Stage();
				popUpStage.initOwner(primaryStage);
				VBox vBox = new VBox();
				vBox.setSpacing(10);
				Button viewTrades = new Button("View Trades");
				
				viewTrades.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent event) {
						popUpStage.close();
						showTradeOfferview();
					}
					
				});
				
				viewTrades.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
				vBox.setStyle("-fx-background-color: darkred; -fx-padding: 15;");
				Text popUpMessage = new Text("\n"+message+"\n");
				popUpMessage.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
				popUpMessage.setFill(Color.WHITE);
				vBox.getChildren().addAll(popUpMessage, viewTrades);
				Scene popUpScene = new Scene(vBox);
				popUpStage.setScene(popUpScene);
				popUpStage.centerOnScreen();
				popUpStage.show();
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});

		
	}

	/**
	 * Getter for the TradeStage
	 * @return the tradeStage
	 */
	public Stage getTradeStage() {
		return tradeStage;
	}

	/**
	 * Sets the scene of the tradeStage
	 */
	private void setTradeStageScene(Scene scene) {
		
		tradeStage.setScene(scene);
	
	}


}
