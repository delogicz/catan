package network;

import java.util.ArrayList;

import controller.LogMaster;

/**
 * Class for Client queue message 
 * @author Georg, Felixi
 *
 */
public class ClientQueueHandler extends Thread{
	private ArrayList<String> messageQueue;
	private Client c;
	private final Object lockObject = new Object(); //java is stupid
	
	/**
	 * Constructor
	 * @param client
	 */
	public ClientQueueHandler(Client client){
		messageQueue = new ArrayList<String>();
		c = client;
	}
	
	/**
	 * Adds messages to queue
	 * @param message
	 */
	public void addMessage(String message){
		synchronized(lockObject){
			LogMaster.log("[Net]Added to Client Queue: " + message + " [Thread : "+ Thread.currentThread().getId() + "]");
			messageQueue.add(message);
			LogMaster.log("[Thr]Waking up Thread: " + this.getId() + " [Thread : "+ Thread.currentThread().getId() + "]");
			lockObject.notify();
		}
	}
	/**
	 * Pops message every couple of miliseconds
	 */
	private void popMessage(){
		if(messageQueue.size() > 0){
			String s = messageQueue.get(0);
			messageQueue.remove(0);
			c.handleInput(s);
		}
	}
	
	/**
	 * Pops the messages
	 */
	public void run(){
		while(true){
			try{
				synchronized(lockObject){
					if(messageQueue.size() > 0){
							popMessage();
					}else{
						try {
							LogMaster.log("[Thr]Thread waits [Thread : "+ Thread.currentThread().getId() + "]");
							lockObject.wait();
						} catch (InterruptedException e) {
							LogMaster.log("[Exc]Wait throws InterruptedException:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
						}
					}
				}
			}catch(Exception e){
				LogMaster.log("[Exc]ClientQueueHandler throws uncaught Exception:  " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
			}
		}
	}
}
