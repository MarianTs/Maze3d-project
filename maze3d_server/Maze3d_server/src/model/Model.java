package model;
/**
 * 
 * @author Marian & Lidor
 *
 */
public interface Model 
{
	
	/**
	 * open the server
	 */
	public void openTheServer();
	
	/**
	 * close the server
	 */
	public void closeServer();
	
	/**
	 * return the code: the server is open
	 * @return a string with the server is open
	 */
	public String getOpenCode();
	
	/**
	 * return the code: the server is closed
	 * @return a string with the server is closed
	 */
	public String getCloseCode();
	
	/**
	 * return the messages that come from the server
	 * @return a string with the message
	 */
	public String getMessageCode();
}
