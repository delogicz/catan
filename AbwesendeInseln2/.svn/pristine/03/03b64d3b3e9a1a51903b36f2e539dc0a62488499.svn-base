package view;

import controller.LogMaster;
import game.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Creates the view to choose what logging is wanted
 * @author Felixi
 */
public class LogLevelView {
	CheckBox all, thr,bot,mod,net,exc,ui;
	
	/**
	 * Constructor
	 */
	public LogLevelView(){
		Stage logLevel = new Stage();
		logLevel.setTitle("Choose the Log Level");
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: dodgerblue; -fx-padding: 10;");
		
		all = new CheckBox("All");
		all.setSelected(true);
		Settings.changeFont(all, true);
		
		thr = new CheckBox("Thread");
		thr.setSelected(true);
		Settings.changeFont(thr, true);
		
		bot = new CheckBox("Bot");
		bot.setSelected(true);
		Settings.changeFont(bot, true);
		
		mod = new CheckBox("Model");
		mod.setSelected(true);
		Settings.changeFont(mod, true);
		
		net = new CheckBox("Network");
		net.setSelected(true);
		Settings.changeFont(net, true);
		
		exc = new CheckBox("Exception");
		exc.setSelected(true);
		Settings.changeFont(exc, true);
		
		ui = new CheckBox("UI");
		ui.setSelected(true);
		Settings.changeFont(ui, true);
		
		Button ok = new Button("OK");
		ok.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(all.isSelected()){
					LogMaster.setFilter(true);
				}
				LogMaster.setFilter(thr.isSelected(),bot.isSelected(),mod.isSelected(),net.isSelected(),exc.isSelected(),ui.isSelected());
				logLevel.close();
			}
		});
	
		all.setOnAction(event -> {
			thr.setSelected(all.isSelected());
			bot.setSelected(all.isSelected());
			mod.setSelected(all.isSelected());
			net.setSelected(all.isSelected());
			exc.setSelected(all.isSelected());
			ui.setSelected(all.isSelected());
		});
		
		pane.add(all,0,0);
		pane.add(thr, 1, 0);
		pane.add(bot, 0, 1);
		pane.add(mod, 1, 1);
		pane.add(net, 0, 2);
		pane.add(exc, 1, 2);
		pane.add(ui, 0, 3);
		pane.add(ok, 1, 3);
		
		pane.setHgap(25);
		pane.setVgap(15);
		logLevel.setScene(new Scene(pane,220,150));
		logLevel.show();
	}
	
}
