package network;

import controller.LogMaster;
import controller.ServerControl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application for the Server
 * @author Felixi
 *
 */
public class ServerApp extends Application{
		
		/**
		 * Starts the server
		 */
		@Override
		public void start(Stage stage) throws Exception {
			LogMaster.initialize();
			LogMaster.log("[Thr]Initializing Server [Thread : "+ Thread.currentThread().getId() + "]");
			new ServerControl(5506, false);
		}

		
		/**
		 * Main method for Server
		 * @param args
		 */
		public static void main(String[] args) {
			Application.launch(args);
		}

}