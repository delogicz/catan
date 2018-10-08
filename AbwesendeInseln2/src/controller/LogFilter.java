package controller;

import java.util.logging.Filter;
import java.util.logging.LogRecord;


/**
 * This class is to filter what logging you get in your log file
 * @author Felixi
 */
public class LogFilter implements Filter{

	 private static boolean thread= true;
	 private static boolean gui = true;
	 private static boolean model= true;
	 private static boolean botLog= true;
	 private static boolean network= true;
	 private static boolean except= true;
	 
	 /**
	  * Checks what should be logged and filters the logging
	  * @param log This is the logrecord that is beeing logged
	  */
	@Override
	public boolean isLoggable(LogRecord log) {
		if(log.getLevel() == LogMaster.getThr()) return thread;
		if(log.getLevel() == LogMaster.getBot()) return botLog;
		if(log.getLevel() == LogMaster.getMod()) return model;
		if(log.getLevel() == LogMaster.getUi()) return gui;
		if(log.getLevel() == LogMaster.getExc()) return except;
		if(log.getLevel() == LogMaster.getNet()) return network;
		return true;
	}

	/**
	 * Setter for Thread
	 * @param thread
	 */
	public void setThread(boolean thread) {
		LogFilter.thread = thread;
	}

	/**
	 * Setter for Gui
	 * @param gui
	 */
	public void setGui(boolean gui) {
		LogFilter.gui = gui;
	}

	/**
	 * Setter for model
	 * @param model
	 */
	public void setModel(boolean model) {
		LogFilter.model = model;
	}

	/**
	 * Setter for botLog
	 * @param botLog
	 */
	public void setBotLog(boolean botLog) {
		LogFilter.botLog = botLog;
	}

	/**
	 * Setter for network
	 * @param network
	 */
	public void setNetwork(boolean network) {
		LogFilter.network = network;
	}

	/**
	 * Setter for except
	 * @param except
	 */
	public void setExcept(boolean except) {
		LogFilter.except = except;
	}
	
	

}
