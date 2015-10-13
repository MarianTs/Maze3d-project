package model;

import java.io.Serializable;

public class ServerProperties implements Serializable 
{

	/**
	 * serial number for serialization
	 */
	private static final long serialVersionUID = 1722398377892436497L;
	
	
	protected int numberOfClients;
	protected int port;
	
	public ServerProperties() {
		super();
	}
	public ServerProperties(int numberOfClients,int port)
	{
		this.numberOfClients=numberOfClients;
		this.port=port;
	}
	
	
	public int getNumberOfClients() {
		return numberOfClients;
	}
	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	

}
