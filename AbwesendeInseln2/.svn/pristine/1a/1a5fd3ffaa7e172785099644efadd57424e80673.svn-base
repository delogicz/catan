package view;

import controller.PlayerControl;
import game.Settings;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Player;

/**
 * Skin for the chat
 * @author Felixi
 *
 */
public class ChatSkin extends VBox {
	private TextArea messages;
	private TextField input;
	private PlayerControl control;
	
	/**
	 * Constructor
	 * @param controller
	 */
	public ChatSkin(PlayerControl controller){
		control = controller;
		messages = new TextArea();
	    messages.setEditable(false);
	    messages.setMinSize(Settings.CHAT_WIDTH, Settings.CHAT_HEIGHT);
	    messages.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
	    input = new TextField();
	    input.setStyle("-fx-text-fill: white;");
	    input.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
	    input.setMinSize(Settings.CHAT_WIDTH, Settings.CHAT_INPUT_HEIGHT);
	    setSpacing(2);
		getChildren().addAll(messages,input);
		
		 this.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
	            	if (event.getCode().equals(KeyCode.ENTER))
	                {
	            		String message = input.getText();
	            		control.getPlayer().getCatan().getGameManager().getClient().sendChat(message);
	            		input.clear();
	                }
	            }
	        });
	}
	
	/**
	 * Appends text to Chat
	 * @param author
	 * @param message
	 */
	public void appendText(Player author, String message){
		messages.setStyle("-fx-text-fill: WHITE;");
		if(author != null){
			messages.appendText(author.getName() + ": "+ message);
		}
		else messages.appendText("Server: "+ message);
		messages.appendText("\n");
	}
	
}
