package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import controller.LogMaster;
/**
 * Handles communication between Server and a Client
 * 
 * @author georgschwab
 *
 */
public class ServerHandler extends Thread{
	private Socket client;
	private Server server;
	private int clientId;
	private InputStream input;
	
	/**
	 * Constructor creates new handler for client
	 * @param client Client, with whom server wants to communicate over handler
	 * @param server Server
	 */
	public ServerHandler(Socket client, Server server){
		this.client = client;
		this.server = server;
		this.server.sendHello(this);
		LogMaster.log("[Net]Started Handler"+ " [Thread : "+ Thread.currentThread().getId() + "]");
	}
	
	@Override
	/**
	 * 
	 * Receives and deals with messages of its client
	 */
	public void run(){
		Scanner in = null;
		try {
			input = client.getInputStream();
		} catch (IOException e1) {
			
			LogMaster.logExc("[Exc]Exception while receiving from Client: " + e1.getMessage());
		}
		in = new Scanner(input);
		while(!client.isClosed()){
			String text = null;
			try{
				text = in.nextLine();
			} catch (java.util.NoSuchElementException e) {
				try {
					client.close();
					in.close();
				} catch (IOException e1) {
				}
				
				server.handleLostConnection(clientId);
				LogMaster.log("[Exc]Exception: Line not found at Handler " + clientId + " :" + e.getMessage() + " [Thread : " + Thread.currentThread().getId() + "]"  );
				in.close();
			}
			if(text != null){
				LogMaster.log("[Net]Handler " + clientId + " got: " + text+ " [Thread : "+ Thread.currentThread().getId() + "]");
				server.handleInput(text,this);
			}
		}
		in.close();
	}	

	/**
	 * Sends message to the handler's client
	 * @param message the message to be sent
	 */
	public void sendMessage(String message) {
		LogMaster.log("[Net]Handler "+clientId+" sends: " + message+ " [Thread : "+ Thread.currentThread().getId() + "]");
		try {
		PrintWriter out;
		out = new PrintWriter( client.getOutputStream(), true );
		out.println(message);
		} catch (IOException e) {
			LogMaster.log("[Exc]Server throws IOException while sending Text: " + e.getMessage() + " [Thread : "+ Thread.currentThread().getId() + "]");
		}
	}
	
	/**
	 * Sends the message to only the client with the right id
	 * @param targetId			client id
	 * @param message			message
	 */
	public void sendIf(int targetId,String message){
		if(clientId==targetId){
			LogMaster.log("[Net]Server sends: ["+message +"] to Client: "+targetId + " [Thread : "+ Thread.currentThread().getId() + "]");
			sendMessage(message);
		}
	}
	
	/**
	 * Sends the message to all clients but not to the one with the right ID
	 * @param targetId			client id not to send the message
	 * @param message			the message to be sent
	 */
	public void sendIfNot(int targetId,String message){
		if(clientId!=targetId){
			LogMaster.log("[Net]Server sends: ["+message +"] to Client: "+ targetId + " [Thread : "+ Thread.currentThread().getId() + "]");
			sendMessage(message);
		}
	}
	
	
	/**
	 * Closes the connection between client and server
	 */
	public void close(){
		try {
			client.close();
		} catch (IOException e) {
			LogMaster.logExc("[Exc]Exception while disconnecting Client: " + e.getMessage());
		}
	}
	
	/**
	 * Setter for clientId
	 * @param id the id
	 */
	public void setId(int id){
		this.clientId = id;
	}
	
	/**
	 * Getter for clientId
	 * @return clientId
	 */
	public int getClientId(){
		return clientId;
	}
}
