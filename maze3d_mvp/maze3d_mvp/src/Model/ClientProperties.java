package Model;

import java.io.Serializable;

/**
 * class that hold the port and ip of the server,which the model contacts with
 * @author Marian & Lidor
 *
 */
public class ClientProperties implements Serializable
{
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -4828907294948792003L;
	
	
	protected String serverIp;//the ip of the server we connect to
	protected int serverPort;//the port of the server we connect with
	
	/**
	 * constructor
	 */
	public ClientProperties() {

	}
	/**
	 * constructor using fields
	 * @param ip the ip of the server
	 * @param port the port of the server
	 */
	public ClientProperties(String ip,int port)
	{
		this.serverIp=ip;
		this.serverPort=port;
	}
	/**
	 * get the IP of the server
	 * @return server's IP
	 */
	public String getServerIp() {
		return serverIp;
	}
	/**
	 * sets the server IP
	 * @param serverIp server IP 
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	/**
	 * getting the server port
	 * @return server port
	 */
	public int getServerPort() {
		return serverPort;
	}
	/**
	 * set the port of the server
	 * @param serverPort server port
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
}
