package model;

import java.io.Serializable;
/**
 * holds the properties of the server
 * @author Marian & Lidor
 *
 */
public class ServerProperties implements Serializable 
{

	/**
	 * serial number for serialization
	 */
	private static final long serialVersionUID = 1722398377892436497L;
	
	
	protected int numberOfClients;
	protected int port;
	
	/**
	 * constructor
	 */
	public ServerProperties() {
		super();
	}
	
	/**
	 * constructor using fields
	 * @param numberOfClients number Of Clients that connects to the server
	 * @param port port of the server
	 */
	public ServerProperties(int numberOfClients,int port)
	{
		this.numberOfClients=numberOfClients;
		this.port=port;
	}
	
	
	/**
	 * return number of clients
	 * @return number of clients
	 */
	public int getNumberOfClients() {
		return numberOfClients;
	}
	
	/**
	 * set number of clients
	 * @param numberOfClients set number of clients
	 */
	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}
	
	/**
	 * return the port of the server
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * setting port of the server
	 * @param port set the port of the server
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	

}
