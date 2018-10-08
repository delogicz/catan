package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is logging all the information to the log files
 * @author Felixi, Georg
 */
public class LogMaster {
	
	 static private LogFilter filter = null;
	 private static final Logger log = Logger.getLogger(LogMaster.class.getName());
	 private static final CustomLevel thr = new CustomLevel("THREAD",Level.FINE.intValue());
	 private static final CustomLevel bot = new CustomLevel("BOT",Level.FINE.intValue());
	 private static final CustomLevel mod = new CustomLevel("MODEL",Level.FINE.intValue());
	 private static final CustomLevel net = new CustomLevel("NETWORK",Level.FINE.intValue());
	 private static final CustomLevel ui = new CustomLevel("USER_INPUT",Level.FINE.intValue());
	 private static final CustomLevel exc = new CustomLevel("EXCEPTION",Level.WARNING.intValue());
	
	 /**
	  * initializes the Logging and creates a log folder and a log file
	  */
	public static void initialize(){
		log.setLevel(Level.ALL);
		FileHandler fh = null;
		filter = new LogFilter();
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		try {
			fh = new FileHandler("log/log" +timeStamp+ ".log");
			fh.setFilter(filter);
			LogFormatter sf = new LogFormatter();
			fh.setFormatter(sf);
			log.addHandler(fh);
		} catch (SecurityException | IOException e1) {
			File dire = new File("logging");
			dire.mkdir();
			//DirectoryChooser dir = new DirectoryChooser();
			//dir.setTitle("Choose a place for the log files:");
			//File selectedDirectory = dir.showDialog(new Stage());
			try {
				
				//fh = new FileHandler(selectedDirectory.getPath()+ "/log" +timeStamp+ ".log");
				fh = new FileHandler("logging/log" +timeStamp+ ".log");
				fh.setFilter(filter);
				SimpleFormatter sf = new SimpleFormatter();
				fh.setFormatter(sf);
				log.addHandler(fh);
			} catch (Exception e2) {
				//mimimi
			}
			
		}

	}
	
	/**
	 * logs the bot messages
	 * @param msg
	 */
	public static void logBot(String msg){
		log.log(bot,msg);
	}
	
	/**
	 * logs the mod messages
	 * @param msg
	 */
	public static void logMod(String msg){
		log.log(mod,msg);
	}
	
	/**
	 * logs the network messages
	 * @param msg
	 */
	public static void logNet(String msg){
		log.log(net,msg);
	}
	/**
	 * logs the ui messages
	 * @param msg
	 */
	public static void logUI(String msg){
		log.log(ui,msg);
	}
	/**
	 * logs the thread messages
	 * @param msg
	 */
	public static void logThr(String msg){
		log.log(thr,msg);
	}
	/**
	 * logs the exception messages
	 * @param msg
	 */
	public static void logExc(String msg){
		log.log(exc,msg);
	}
	
	/**
	 * decides what kind of log level this message is beeing used to log this information
	 * @param msg
	 */
	public static void log(String msg){
		String sub = msg.substring(0, 3);
		switch(sub){
		case "[Bo":logBot(msg);break;
		case "[Th":logThr(msg);break;
		case "[Mo":logMod(msg);break;
		case "[Ne":logNet(msg);break;
		case "[UI":logUI(msg);break;
		case "[Ex":logExc(msg);break;
		default: logExc("[Exc]Unknown Log: " + msg);
		}
	}
	
	/**
	 * Sets the filter of the logging
	 * @param thr
	 * @param bot
	 * @param mod
	 * @param net
	 * @param exc
	 * @param ui
	 */
	public static void setFilter( boolean thr, boolean bot, boolean mod, boolean net, boolean exc, boolean ui) {
		filter.setThread(thr);
		filter.setBotLog(bot);
		filter.setModel(mod);
		filter.setNetwork(net);
		filter.setExcept(exc);
		filter.setGui(ui);
	}
	
	/**
	 * Sets the filter for the logging in case all is getting logged
	 * @param allLog
	 */
	public static void setFilter(boolean allLog) {
			if (allLog){
				filter.setThread(true);
				filter.setBotLog(true);
				filter.setModel(true);
				filter.setNetwork(true);
				filter.setExcept(true);
				filter.setGui(true);
			}
	}
	
	/**
	 * Getter for Logger
	 * @return log
	 */
	public static Logger getLog() {
		return log;
	}
	/**
	 * Getter for Thread CustomLevel
	 * @return thr
	 */
	public static CustomLevel getThr() {
		return thr;
	}
	/**
	 * Getter for Bot CustomLevel
	 * @return bot
	 */
	public static CustomLevel getBot() {
		return bot;
	}
	/**
	 * Getter for Mod CustomLevel
	 * @return mod
	 */
	public static CustomLevel getMod() {
		return mod;
	}
	/**
	 * Getter for Network CustomLevel
	 * @return net
	 */
	public static CustomLevel getNet() {
		return net;
	}

	/**
	 * Getter for Ui CustomLevel
	 * @return ui
	 */
	public static CustomLevel getUi() {
		return ui;
	}
	
	/**
	 * Getter for Exception CustomLevel
	 * @return exc
	 */
	public static CustomLevel getExc() {
		return exc;
	}
	
}
