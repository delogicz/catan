package view;



import controller.LogMaster;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import game.Settings;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Player;
import model.PlayerColor;
import model.SetUp;

/**
 * The view of a SetUp
 * @author Stefanie Kloss
 *
 */
public class SetUpSkin extends Pane{
	
	private SetUp setUp;
	private TextField enterName;
	private RadioButton red, orange, blue, white;
	private ToggleGroup toggleGroupColor;
	private Button loginButton, startMatchButton;
	private Text instruction,title,closeIcon;
	private Pane dragArea;
	private ObservableList<Player> data;
	private TableView<Player> table;
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
	 * @param setUp the setUp the skin is representing
	 */
	public SetUpSkin(SetUp setUp){
		
		super();
		this.setId("stackpane");
		data = FXCollections.observableArrayList();
		this.setUp = setUp;
		this.setPrefHeight(Settings.LOGIN_SKIN_HEIGHT);
		this.setPrefWidth(Settings.LOGIN_SKIN_WIDTH);
		addElements();
		
	}
	
	/**
	 * Adds texts, textfields, radiobuttons, etc .
	 */
	@SuppressWarnings("unchecked")
	private void addElements() {
		
		GridPane gridPane = new GridPane();
		
		dragArea = new Pane();
		dragArea.setPrefHeight(Settings.LOGIN_SKIN_HEIGHT);
		dragArea.setPrefWidth(Settings.LOGIN_SKIN_WIDTH);
		dragArea.setLayoutX(Settings.WINDOW_BAR_LAYOUT_X);
		dragArea.setLayoutY(Settings.WINDOW_BAR_LAYOUT_Y);
		
		dragArea.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        dragArea.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUp.getGameManager().getApp().getStage().setX(event.getScreenX() - xOffset);
                setUp.getGameManager().getApp().getStage().setY(event.getScreenY() - yOffset);
            }
        });
		
		title = new Text("Settlers of Catan");
		title.setFill(Color.WHITE);
		title.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		title.setLayoutX(38);
		title.setLayoutY(22);
		
		closeIcon=FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.CLOSE,"12");
		closeIcon.setFill(Color.WHITE);
		closeIcon.setLayoutX(772);
		closeIcon.setLayoutY(22);
		closeIcon.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		this.getChildren().addAll(dragArea,closeIcon,title);
		
		Text welcome = new Text("Welcome to \"The Settlers of Catan\"");
		welcome.setFill(Color.ANTIQUEWHITE);
		welcome.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_30));
		instruction = new Text(generateInstructionText());
		instruction.setFill(Color.ANTIQUEWHITE);
		instruction.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		Text name = new Text("Name:            ");
		name.setFill(Color.ANTIQUEWHITE);
		name.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		this.enterName = new TextField();
		enterName.setPromptText("Nickname");
		enterName.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		HBox hBoxName = new HBox();
		hBoxName.getChildren().addAll(name, enterName);
		hBoxName.setAlignment(Pos.BASELINE_LEFT);
		
		Text color = new Text("Pick a color:  ");
		color.setFill(Color.ANTIQUEWHITE);
		color.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		red = new RadioButton("Red");
		red.setTextFill(Color.ANTIQUEWHITE);
		red.setSelected(true);
		red.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		orange = new RadioButton("Orange");
		orange.setTextFill(Color.ANTIQUEWHITE);
		orange.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		white = new RadioButton("White");
		white.setTextFill(Color.ANTIQUEWHITE);
		white.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		blue = new RadioButton("Blue");
		blue.setTextFill(Color.ANTIQUEWHITE);
		blue.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		toggleGroupColor = new ToggleGroup();
		red.setToggleGroup(toggleGroupColor);
		orange.setToggleGroup(toggleGroupColor);
		white.setToggleGroup(toggleGroupColor);
		blue.setToggleGroup(toggleGroupColor);
		
		VBox vBoxColor = new VBox();
		vBoxColor.getChildren().addAll(red, orange, white, blue);
		vBoxColor.setSpacing(3);
		HBox hBoxColor = new HBox();
		hBoxColor.getChildren().addAll(color, vBoxColor);
		
		this.loginButton = new Button("JOIN");
		loginButton.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		
		this.startMatchButton = new Button("START MATCH");
		startMatchButton.setId("play");
		startMatchButton.setDisable(true);
		startMatchButton.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		
		loginButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				setUp.clientSendPlayerRequest(enterName.getText(), getChosenColor());
			}
		});
		
		startMatchButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				setUp.getGameManager().getClient().sendStartMatch();
			}
		});
		table = new TableView<Player>();
		TableColumn<Player,String> playerName = new TableColumn<Player,String>("Name");
		TableColumn<Player,String> playerColour = new TableColumn<Player,String>("Colour");
		TableColumn<Player,String> playerStatus = new TableColumn<Player,String>("Status");
		playerName.setStyle( "-fx-background-color: black;");
		playerName.setCellValueFactory( new PropertyValueFactory<>("Name"));
		playerName.setMinWidth(100);
		playerColour.setCellValueFactory( new PropertyValueFactory<>("playerColor"));
		playerColour.setStyle( "-fx-background-color: black;");
		playerColour.setMinWidth(90);
		playerStatus.setCellValueFactory( new PropertyValueFactory<>("status"));
		playerStatus.setMinWidth(170);
		playerStatus.setStyle( "-fx-background-color: black;");
		table.getColumns().addAll(playerName,playerColour,playerStatus);
		table.setMaxHeight(200);

	    
	    table.setItems(data);
		gridPane.add(welcome, 0, 0, 2, 1);
		gridPane.add(instruction, 0, 1, 2, 1);
		gridPane.add(hBoxName, 0, 2, 2, 1);
		gridPane.add(hBoxColor, 0, 3, 2, 1);
		gridPane.add(loginButton, 1, 5);
		GridPane.setHalignment(loginButton, HPos.CENTER);
		gridPane.add(startMatchButton, 0, 5);
		gridPane.add(table, 2, 0, 1, 6);
		gridPane.setHgap(10); 
		gridPane.setVgap(22);
		gridPane.setPadding(new Insets(70, 30, 0, 15));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPickOnBounds(false);
		
		
		this.getChildren().add(gridPane);
		
	}
	
	/**
	 * Scene that is showed until match starts
	 */
	public void waitView(){
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
				getChildren().clear();
				Label waitingText = new Label("Please wait for the other\nplayers to join in.");
				waitingText.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.FONT_SIZE_30));
				waitingText.setAlignment(Pos.CENTER);
				waitingText.setLayoutY(150);
				waitingText.setLayoutX(90);

				getChildren().addAll(table);
				getChildren().add(waitingText);
				getChildren().addAll(dragArea,closeIcon,title);
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
	 * generates the string with the instruction
	 * @return the instruction text
	 */
	public String generateInstructionText(){
		
		String endText = "\nPlease enter your name and pick a color to join in."; 

		if(setUp.getPlayers() != null){
			int playersCount = setUp.getPlayers().size();
			if(playersCount == 1){
				return "Currently one other person wants to play." + endText;
			}
			return "Currently " + playersCount + " people want to play." + endText;
		}
		
		return "Be the first one to join this game." + endText;
	}
	
	/**
	 * Adds a Player to the table
	 * @param player
	 */
	public void addToTable(Player player){
		switch(player.getPlayerColor().toServerString()){
			case("Rot"): this.red.setSelected(false);	this.red.setDisable(true);	 break;
			case("Weis"):this.white.setSelected(false);this.white.setDisable(true);	break;
			case("Blau"):this.blue.setSelected(false);this.blue.setDisable(true);	break;
			case("Orange"):this.orange.setSelected(false);this.orange.setDisable(true);	break;
		}
		data.add(player);
	}
	
	/**
	 * Adjusts a player in the table
	 * @param player
	 */
	public void adjustPlayer(Player player){
		for(int i = 0; i < data.size();i++){
			if(data.get(i).getPlayerID() == player.getPlayerID()){
				data.set(i, player);
			}
		}
	}
	
	/**
	 * updates the Table
	 */
	public void updateTable(){
		for(Player p: setUp.getPlayers()){
			adjustPlayer(p);
		}
	}
	/**
	 * makes the RadioButtons disable
	 */
	public void disableRadioButtons() {
		this.white.setDisable(true);
		this.blue.setDisable(true);
		this.red.setDisable(true);
		this.orange.setDisable(true);
	}
	/**
	 * Getter for instruction
	 * @return
	 */
	public Text getInstruction() {
		return instruction;
	}

	/**
	 * getter for ChosenColor
	 * @return PlayerColor matching to color of selected radio button
	 */
	private PlayerColor getChosenColor(){
		RadioButton selected = (RadioButton)toggleGroupColor.getSelectedToggle();
		if(selected == red) 		return PlayerColor.RED;
		else if(selected == orange) return PlayerColor.ORANGE;
		else if(selected == blue) 	return PlayerColor.BLUE;
		else 						return PlayerColor.WHITE;
	}

	/**
	 * Getter for the toggleGroupColor
	 * @return
	 */
	public ToggleGroup getToggleGroupColor() {
		return toggleGroupColor;
	}

	/**
	 * Getter for StartMatchButton
	 * @return the startMatchButton
	 */
	public Button getStartMatchButton() {
		return startMatchButton;
	}

	/**
	 * Getter for loginButton
	 * @return
	 */
	public Button getLoginButton(){
		return loginButton;
	}

	/**
	 * Getter for EnterName
	 * @return the entered Name
	 */
	public TextField getEnterName() {
		return enterName;
	}

	/**
	 * Setter for instruction
	 * @param instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction.setText(instruction);;
	}
}
