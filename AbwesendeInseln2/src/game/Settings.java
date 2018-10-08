package game;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Static variables
 * @author Daniel Panangian, Samantha Meindl, Stefanie Kloss
 */
public class Settings {
		
		public static final int MAX_PLAYER_COUNT = 4;
		public static final int MIN_PLAYER_COUNT = 3;
		
		public static final int FIRST_FOUNDATION_PHASE = 1;
		public static final int SECOND_FOUNDATION_PHASE = 2;
		
		public static final int MAX_TRADE_ID = 1000;
		
		// Graphics
		// Sizing & layout
		/**
		 * scale factor
		 */
		public static final double SCALE = 63;
		public static final double BORDER_WIDTH = 1.6 * SCALE;
		public static final double OFFSET_X = 344 ;
		public static final double OFFSET_Y = 190 ; 
		public static final double HALF_SQRT_THREE = 0.866025;
		
		public static final double LOGIN_SKIN_WIDTH = 800;
		public static final double LOGIN_SKIN_HEIGHT = 471;
		
		public static final double WINDOW_BAR_WIDTH = 800;
		public static final double WINDOW_BAR_HEIGHT = 35;
		public static final double WINDOW_BAR_LAYOUT_X = 0;
		public static final double WINDOW_BAR_LAYOUT_Y = -1;
		
		public static final double GAME_SKIN_WIDTH = 1100;
		public static final double GAME_SKIN_HEIGHT = 800;
		
		public static final double ISLAND_SKIN_WIDTH = 1100;
		public static final double ISLAND_SKIN_HEIGHT = 800;
		
		public static final double MARITIME_TRADE_SKIN_WIDTH = 550;
		public static final double MARITIME_TRADE_SKIN_HEIGHT = 200;
		
		public static final double EDGE_LENGTH = 0.68;
		public static final double EDGE_WIDTH = 0.07;
		
		public static final int[] HEXAGON_LAYOUT = new int[] { 3, 4, 5, 4, 3 };
		public static final int[] VERTEX_LAYOUT = new int[] { 7, 9, 11, 11, 9, 7 };
		/**
		 * // outerEdges is array with all positions of Edges that can contain a harbour
		 */
		public static final int[] OUTEREDGES_POSITION = {0,2,3,5,6,8,9,21,22,37,38,52,53,64,65,71,70,69,
				                                         68,67,66,56,54,41,39,24,23,11,10,1};
		
		public static final double CITY_WIDTH = 0.4;
		public static final double CITY_HEIGHT = 0.25;
		public static final double CITY_STROKEWIDTH = 0.03;
		
		public static final int BUILD_AND_BUY_BUTTONS_POS_X = 0;
		public static final int BUILD_AND_BUY_BUTTONS_POS_Y = 500;
		
		public static final int ALL_INFO_POS_X = 0;
		public static final int ALL_INFO__POS_Y = 650;
		
		public static final int OTHER_BUTTONS_POS_X = 900;
		public static final int OTHER_BUTTONS_POS_Y = 175;
		
		public static final int CHAT_BOX_POS_X = 0;
		public static final int CHAT_BOX_POS_Y = 195;
		
		public static final double PLAYER_BOX_WIDTH = 275;
		public static final double PLAYER_BOX_HEIGHT = 44.1;
		
		
		/**
		 * hgap and vgap for the choiceboxes in GameSkin
		 */
		public static final int CHOICEBOXES_GAP = 8;
		
		/**
		 * The maximum width of the chat window
		 */
		public static final int CHAT_WIDTH = 150;
		
		/**
		 * The maximum height of the chat window
		 */
		public static final int CHAT_HEIGHT = 150;
		public static final double CHAT_INPUT_HEIGHT = 30;
		
		/**
		 * The height and width of a dice
		 */
		public static final int DICE_SIZE = 35;
		
		/**		
		 * The amount of hexes in the game
		 */
		public static final int AMOUNT_OF_HEXES = 19;
		
		public static final int AMOUNT_OF_HILLS = 3;
		public static final int AMOUNT_OF_MOUNTAINS = 3;
		public static final int AMOUNT_OF_PASTURE = 4;
		public static final int AMOUNT_OF_FIELDS = 4;
		public static final int AMOUNT_OF_FOREST = 4;
		
		/**
		 * The amount of roads in the game
		 */
		public static final int AMOUNT_OF_ROADS = 15;
		
		/**
		 * The amount of settlements in the game
		 */
		public static final int AMOUNT_OF_SETTLEMENTS = 5;
		
		/**
		 * The amount of cities in the game
		 */
		public static final int AMOUNT_OF_CITIES = 4;
		
		//Victory Points
		public static final int VICTORY_POINTS_NEEDED_TO_WIN = 10;
		public static final int VICTORY_POINTS_FOR_SETTLEMENT = 1;
		public static final int VICTORY_POINTS_FOR_CITY = 2;
		public static final int VICTORY_POINTS_FOR_LONGEST_ROAD = 2;
		public static final int VICTORY_POINTS_FOR_LARGEST_ARMY = 2;
		public static final int VICTORY_POINTS_FOR_DEV_CARD = 1;
		
		// Colors

		public static final Color EDGE_HOVER_COLOR = Color.PALEGOLDENROD;
		public static final Color EDGE_SKIN_COLOR = Color.TRANSPARENT;


		public static final Color VERTEX_SKIN_COLOR = Color.TRANSPARENT;
		public static final Color VERTEX_HOVER_COLOR = Color.PALEGOLDENROD;
		
	
		public static final String OSWALD_PATH      = Settings.class.getResource("/Oswald-Regular.ttf").toString();
		public static final String RUMBLE_BRAVE_PATH = Settings.class.getResource("/Rumble Brave.otf").toString();
		
		public static final int STANDARD_FONT_SIZE = 14;
		public static final int LITTLE_FONT_SIZE = 12;
		public static final int BIG_FONT_SIZE = 16;
		public static final int HUGE_FONT_SIZE = 18;
		public static final int FONT_SIZE_22 = 22;
		public static final int FONT_SIZE_30 = 30;
		public static final int FONT_SIZE_40 = 40;
		
		public static final int HEIGHT_IMPORTANT_BUTTON = 35;
		public static final int BUTTON_NORMAL_WIDTH = 100;
		public static final int BUTTON_SLIM_WIDTH = 80;
		public static final int BUTTON_WIDE_WIDTH = 120;
		
		 //Cards
		 public static final int KNIGHT_CARDS = 14;
		 public static final int VICTORYPOINT_CHARDS = 5;
		 public static final int MONOPOLY_CARDS = 2;
		 public static final int ROADBUILDING_CARDS = 2;
		 public static final int YEAROFPLENTY_CARDS = 2;
		 public static final int RESOURCE_CARD_COUNT = 19;
		 
		 //Dice Location
		 public static final int DICE1_X = 415;
		 public static final int DICE1_Y = 355;
		 public static final int DICE2_X = 465;
		 public static final int DICE2_Y = 355;
		
		 //Player Status
		 public static final String SPIEL_STARTEN = "Spiel starten";
		 public static final String WARTEN_BEGINN = "Wartet auf Spielbeginn";
		 public static final String DORF_BAUEN = "Dorf bauen";
		 public static final String STRASSE_BAUEN = "Strasse bauen";
		 public static final String HANDELN_BAUEN = "Handeln oder Bauen";
		 public static final String WUERFELN = "Wuerfeln";
		 public static final String WARTEN = "Warten";
		 public static final String RAEUBER_VERSETZEN = "Raeuber versetzen";
		 public static final String KARTEN_ABGEBEN = "Karten wegen Raeuber abgeben";
		 public static final String VERBINDUNG_VERLOREN = "Verbindung verloren";
		 
		 //Own status for development cards
		 public static final String ROADBUILDING_ONE_ROAD = "RoadBuilding: Build one road";
		 public static final String ROADBUILDING_TWO_ROADS = "RoadBuilding: Build two roads";
		 public static final String MONOPOLY = "Monopoly: Choose the monopoly-resource";
		 public static final String YEAR_OF_PLENTY = "Year of Plenty: Choose two resources";
		 public static final String KNIGHT = "Knight: Relocate robber";
		 public static final String YOU_WON = "You won";
		 public static final String GAME_OVER = "Game over";
		 
		 //Strings for decryption of messages from server 
		 public static final String DORF = "Dorf";
		 public static final String STRASSE = "Strasse";
		 public static final String STADT = "Stadt";
		 public static final String SPIELER = "Spieler";
		 public static final String UNBEKANNT = "Unbekannt";
		 public static final String MITSPIELER = "Mitspieler";
		 public static final String HANDEL_ID = "Handel id";
		 public static final String FARBE = "Farbe";
		 public static final String ROT = "Rot";
		 public static final String BLAU = "Blau";
		 public static final String WEISS = "Weiss";
		 public static final String ORANGE = "Orange";
		 
		 //ResourceTypes
		 public static final String HOLZ = "Holz";
		 public static final String LEHM = "Lehm";
		 public static final String WOLLE = "Wolle";
		 public static final String GETREIDE = "Getreide";
		 public static final String ERZ = "Erz";
		 
		 //General - Errors
		 public static final String RESOURCES_ERROR = "You don't have enough resources!";
		 public static final String WRONG_STATUS_ERROR = "You cannot do this action right now.\nPlease pay attention to your status.";
		 
		 //DevelopmentCatd - Errors
		 public static final String ONE_DEVCARD_PER_TURN = "You can't play more than\none DevelopmentCard in a turn";
		 public static final String NOT_OWNING_DEVCARD = "You can't play this development card "
		 							+ "either\nbecause you don't own it or because\nyou just bought it this round.";
		 public static final String TRY_AGAIN_ROADBUILDING =  "The action \"roadbuilding\" was unsuccessful."
									+ "\nTry again and make sure to choose edges you can build on.";
		 public static final String UNSUCCESSFUL_ROADBUILDING = "The action \"roadbuilding\" was unsuccessful.\n"
									+ "Check the location and the amount of roads you have left.";

		 //Robber - Errors
		 public static final String ROBBER_ON_SAME_TILE = "You can not put the robber on the same tile";
		 public static final String CANNOT_ROB = "Player has no resources";
		 
		 //Trade - Errors
		 public static final String TRADE_ALREADY_DONE = "This trade is already completed!";
		 public static final String INVALID_OFFER = "You can only offer resources you currently own!";
		 public static final String CANNOT_GIVE_FOR_FREE = "It's not allowed to give away/ receive cards for free!";
		 public static final String TRADING_NOT_POSSIBLE =  "A problem occured. Seems like one of\nyou hasn't "
		 							+ "got the needed resources.";
		 
		 //Building - Errors
		 public static final String NO_ROADS_LEFT = "You don't have any roads left!";
		 public static final String NO_SETTLEMENTS_LEFT = "You don't have any settlements left!";
		 public static final String NO_CITIES_LEFT = "You don't have any cities left!";
		 
		 /**Message for Client who won the game**/
		 public static final String GAME_WON_MESSAGE = "Congrats! You won the game! :)";
		 

		 
		 //Thread sleep durations
		 public static final long SLEEP_SHORT = 10;
		 public static final long SLEEP_LONG = 100; /*Verzögerung des Sendens von Roadbuilding*/
		 public static final long SLEEP_BOT = 700; 
		public static final int AMOUNT_OF_VERTEX_IN_HEX = 5;
		
		 
		 /**
		  * Settings for DevelopmentCardSkin
		  */
		 public static final int OVERVIEW_GRIDPANE_VGAP = 10;
		 public static final int OVERVIEW_GRIDPANE_HGAP = 35;
		 public static final int DEVCARDS_COUNT = 0;
		 public static final int RESMON_OVERVIEW_GRIDPANE_VGAP = 10;
		 public static final int RESMON_OVERVIEW_GRIDPANE_HGAP = 5;
		 public static final int RESMON_OVERVIEW_GRIDPANE_PREFWIDTH = 610;
		 
		 /**
		  * Sequence to spread tokens spirally 
		  */
		 public static final int[] SEQUENCE_UPPER_RIGHT_CORNER =  {2,1,0,3,7,12,16,17,18,15,11,6,5,4,8,13,14,10,9};
		 public static final int[] SEQUENCE_UPPER_LEFT_CORNER =   {0,3,7,12,16,17,18,15,11,6,2,1,4,8,13,14,10,5,9};
		 public static final int[] SEQUENCE_MIDDLE_LEFT_CORNER =  {7,12,16,17,18,15,11,6,2,1,0,3,8,13,14,10,5,4,9};
		 public static final int[] SEQUENCE_BOTTOM_LEFT_CORNER =  {16,17,18,15,11,6,2,1,0,3,7,12,13,14,10,5,4,8,9};
		 public static final int[] SEQUENCE_BOTTOM_RIGHT_CORNER = {18,15,11,6,2,1,0,3,7,12,16,17,14,10,5,4,8,13,9};
		 public static final int[] SEQUENCE_MIDDLE_RIGHT_CORNER = {11,6,2,1,0,3,7,12,16,17,18,15,10,5,4,8,13,14,9};

		 
		 /**
		  * the opacity for the picture of the longest road
		  */
		 public static final double LONGEST_ROAD_OPACITY = 0.4;
		 
		 /**
		  * the opacity for the picture of the largest knight
		  */
		 public static final double LARGEST_KNIGHT_OPACITY = 0.4;
		 
		 /**
		  * the vgap for the build and buy buttons
		  */
		 public static final int BUILD_AND_BUY_BUTTONS_VGAP = 30;
		 
		 /**
		  * the hgap for the build and buy buttons
		  */
		 public static final int BUILD_AND_BUY_BUTTONS_HGAP = 20;
		 
		 /**
		  * The spacing of the vbox that contains the trade button and the developmentcards button
		  */
		 public static final int TRADE_AND_DEV_BOX_SPACING = 30;
		 
		 /**
		  * The spacing of the vbox that contains the roll dice button and the end turn button
		  */
		 public static final int DICE_AND_END_BOX_SPACING = 30;
		 
		 //Bot
		 
		 public static final Map<Integer, Integer> tokenProbability = new HashMap<Integer, Integer>(){
				private static final long serialVersionUID = 1L;
			{put(2, 1);put(3, 2);put(4, 3);put(5, 4);put(6, 5);
			 put(7, 6);put(8, 5);put(9, 4);put(10, 3);put(11, 2);
			 put(12, 1);}};
			 
		public static final int TOKEN_DIVERSITY_WEIGHT = 2;
		public static final int TOKEN_NOT_OWNED_WEIGHT = 4;
		public static final int TERRAIN_DIVERSITY_WEIGHT = 2;
		public static final int TERRAIN_NOT_OWNED_WEIGHT = 4;
		public static final int TERRAIN_PAIR_WEIGHT = 3;
			 
		/**
		 * changes font of a labeled object									
		 * @param labeled
		 * @param changeColor
		 */
		public static void changeFont(Labeled labeled, boolean changeColor){
			if(changeColor) labeled.setTextFill(Color.ANTIQUEWHITE);
			labeled.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		}
		
		/**
		 * changes font of a text 									
		 * @param text
		 */
		public static void changeFont(Text text){
			text.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.STANDARD_FONT_SIZE));
		}
		
		/**
		 * changes font of a labeled object									
		 * @param labeled
		 * @param changeColor
		 */
		public static void changeFontBig(Labeled labeled, boolean changeColor){
			if(changeColor) labeled.setTextFill(Color.ANTIQUEWHITE);
			labeled.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		}
		
		
		/**
		 * changes font of a text								
		 * @param text
		 */
		public static void changeFontBig(Text text){
			text.setFont(Font.loadFont(Settings.OSWALD_PATH, Settings.BIG_FONT_SIZE));
		}
}
