package view;

import controller.LogMaster;
import game.Settings;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Harbour;
import model.Vertex;

/**
 * This class is the Skin of the harbours
 * @author Felixi
 *
 */
public class HarbourSkin extends ImageView{

		private Vertex vertex;

		/**
		 * Constructor
		 * @param har
		 * @param ver
		 * @param islandSkin
		 */
		public HarbourSkin(Harbour har, Vertex ver,IslandSkin islandSkin) {
			super(new Image(HarbourSkin.class.getResourceAsStream("/img/resourcetypes/"+har.getFileName()),42,42,true,true));
			
			this.vertex = ver;
			setTranslateX(vertex.getSkin().getShape().getTranslateX()-21);
			setTranslateY(vertex.getSkin().getShape().getTranslateY()-21);	
			
			this.setMouseTransparent(true);
			
			HarbourSkin skin = this;
			
			Platform.runLater(new Runnable() { 
				@Override
				public void run() {
					try{
					islandSkin.getChildren().add(skin);
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

}

