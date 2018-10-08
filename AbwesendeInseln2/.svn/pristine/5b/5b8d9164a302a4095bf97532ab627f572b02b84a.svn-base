package game;

import java.util.ArrayList;

import controller.LogMaster;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Application. Run to start a new game of "The Settlers of Catan".
 * @author Daniel Panangian
 */
public class App extends Application {
		
	private Stage stage;
	private ArrayList<Stage> popUpStages;
	private GameManager gameManager; 
	/**
	 * Starts the whole APP
	 */
	@Override
	public void start(Stage stage) throws Exception {
		LogMaster.initialize();
		this.stage = stage;
		popUpStages = new ArrayList<Stage>();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		gameManager = new GameManager(this);
		Scene scene = gameManager.getClientLoginScene();
		stage.setTitle("Settlers of Catan");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
	}	
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);

	}
	
	/**
	 * Changes the scene of the main stage
	 */
	public void changeScene(Scene scene){
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
					stage.setScene(scene);
					stage.centerOnScreen();
					stage.setResizable(false);
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
	 * Creates PopUp-Stage to show a message
	 * @param message message to show in PopUp-Stage
	 */
	public void popUpMessage(String message){
		
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
					Stage popUpStage = new Stage();
					popUpStage.initOwner(stage);
					StackPane pane = new StackPane();
					pane.setStyle("-fx-background-color: dodgerblue; -fx-padding: 15;");
					Text popUpMessage = new Text("\n"+message+"\n");
					popUpMessage.setFont(Font.loadFont(Settings.OSWALD_PATH, 
							       Settings.BIG_FONT_SIZE));
					popUpMessage.setFill(Color.WHITE);
					pane.getChildren().add(popUpMessage);
					Scene popUpScene = new Scene(pane);
					popUpStage.initStyle(StageStyle.UTILITY);
					popUpStage.setScene(popUpScene);
					popUpStage.centerOnScreen();
					popUpStage.show();
					popUpStages.add(popUpStage);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
		try {
			LogMaster.log("[Thr]Thread sleeps for 20ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
	}
	
	/**
	 * Creates PopUp-Stage to show a pane
	 * @param pane pane to show in PopUp-Stage
	 */
	public void popUpPane(Pane pane){

		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
					Stage popUpStage = new Stage();
					popUpStage.initOwner(stage);
					pane.setStyle("-fx-background-color: dodgerblue; -fx-padding: 15;");
					Scene popUpScene = new Scene(pane);
					popUpStage.setScene(popUpScene);
					popUpStage.centerOnScreen();
					popUpStage.initStyle(StageStyle.UNDECORATED);
					popUpStage.show();
					popUpStages.add(popUpStage);
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
	});
		try {
			LogMaster.log("[Thr]Thread sleeps for 20ms [Thread : "+ Thread.currentThread().getId() + "]");
			Thread.sleep(Settings.SLEEP_SHORT);
		} catch (InterruptedException e) {
			LogMaster.log("[Exc]Thread.sleep throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
	}
	
	/**
	 * Closes all PopUpStages
	 */
	public void closeAllPopUpStages() {
		Platform.runLater(new Runnable() { 
			@Override
			public void run() {
				try{
					for(Stage stage: popUpStages){
						stage.close();
					}
				
				}catch(Exception e){
					LogMaster.log("[Exc]Platform run later uncaught exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
				}
			}
		});
	}
	
	
	final Task task = new Task(){

		@Override protected
		Object call() throws Exception{
		AudioClip audio = new AudioClip(getClass().getResource("dreams.mp3").toExternalForm());
		audio.setVolume(0.5f);
		audio.setCycleCount(AudioClip.INDEFINITE);
		audio.play();
		return null;}};
		
	Thread thread = new Thread(task);
	
	/**
	 * Adds a stage to the List popUpStages
	 * @param stage the stage to be added
	 */
	public void addPopUpStage(Stage stage) {
		popUpStages.add(stage);
		
	}
		
	/**
	 * @return the stage
	 */
	public Stage getStage(){
		return this.stage;
	}
	
	/**
	 * Changes the title of the stage
	 * @param status
	 */
	public void setStageTitle(String status) {
		this.stage.setTitle(status);
		
	}
		

	
	/**
	 * Amount of stages in popUupStage
	 * @return
	 */
	public int getPUSSize() {
		return this.popUpStages.size();
	}
	

	

}
