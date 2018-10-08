package game;

import controller.LogMaster;
import controller.PlayerControl;
import javafx.application.Platform;
import javafx.scene.Scene;
import model.Catan;
import model.SetUp;
import network.BotClient;
import network.Client;
import view.ClientLogin;
import view.GameSkin;

/**
 * The game manager initializes a new game.
 * @author Daniel Panangian, Stefanie Kloss
 */
public class GameManager {
	
	private App app;
	private Client client;
	private SetUp setUp;
	private Scene clientLoginScene;
	
	private Scene gameScene;
	private Catan catan;
	private GameSkin gameSkin; 
	private PlayerControl gameControl;
	private String host;
	private int port;
	/**
	 * Constructor
	 * @param app 
	 */
	public GameManager(App app) {
		this.app = app;
		this.clientLoginScene = new Scene(new ClientLogin(this));
		String css = this.getClass().getResource("/loginSkin.css").toExternalForm();
		clientLoginScene.getStylesheets().add(css);
	}
	
	/**
	 * Constructor for Bots
	 */
	public GameManager() {

	}
	
	/**
	 * Starts the set up of the game
	 */
	private void setGameUp() {
		
		this.setUp = new SetUp(this);
		Scene setUpScene = new Scene(setUp.getSetUpSkin());
		String css = this.getClass().getResource("/loginSkin.css").toExternalForm();
		setUpScene.getStylesheets().add(css);
		app.changeScene(setUpScene);

	}

	/**
	 *  Establishes new Game after all players joined (setUp)
	 *  Creates a new instance of GameSkin
	 */
	public void newGame(Catan catan) {
		
		this.catan = catan;
		
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
					gameSkin = new GameSkin(catan);
					catan.setGameSkin(gameSkin);
					gameScene = new Scene(gameSkin);
					String css = this.getClass().getResource("/gameSkin.css").toExternalForm();
					gameScene.getStylesheets().add(css);
					catan.getIsland().moveVerticesAndEdgesToFront();
					try{
						app.changeScene(gameScene);
					}catch(Exception e){
						
					}
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
	 * Initializes the Client
	 * @param host
	 * @param port
	 * @throws Exception Exception in case connection to server gets refused
	 */
	public void initializeClient(String host, int port) throws Exception{

			this.client = new Client(host, port, this);
			this.client.start();
			setGameUp();
	}
	
	/**
	 * Initializes the Bot Client
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public void initializeBotClient(String host, int port) throws Exception {
		
		this.client = new BotClient(host, port, this);
		this.client.start();
		setGameUp();
	}

	/**
	 * Initializes the Bots when using add bot button
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public void initializeBots(String host, int port) throws Exception{
		this.client = new BotClient(host, port, this);
		this.client.start();
		setBotGameUp();
	}

	/**
	 * Quits the game
	 */
	public void quit() {
		gameScene.getWindow().hide();
	}

	/**
	 * Setter for Login information
	 * @param host
	 * @param portInt
	 */
	public void setLoginInformation(String host, int portInt) {
		this.host = host;
		this.port = portInt;
	}

	/**
	 * Creates SetUp for bot
	 */
	private void setBotGameUp() {
		this.setUp = new SetUp(this);
	}

	/**
	 * @return the gameScene
	 */
	public Scene getGameScene() {
		return gameScene;
	}
	
	/**
	 * @return the gameControl
	 */
	public PlayerControl getGameControl() {
		return gameControl;
	}
	
	/**
	 * @return the catan
	 */
	public Catan getCatan() {
		return catan;
	}
	
	/**
	 * @return the clientLoginScene
	 */
	public Scene getClientLoginScene() {
		return this.clientLoginScene;
	}
	
	/**
	 * @return the app
	 */
	public App getApp() {
		return this.app;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @return the gameSkin
	 */
	public GameSkin getGameSkin() {
		return gameSkin;
	}
	
	/**
	 * @return the setUp
	 */
	public SetUp getSetUp() {
		return setUp;
	}

	/**
	 * Getter for Host
	 * @return
	 */
	public String getHost(){
		return this.host;
	}
	
	/**
	 * getter for Port
	 * @return
	 */
	public int getPort(){
		return this.port;
	}

	
}

				
