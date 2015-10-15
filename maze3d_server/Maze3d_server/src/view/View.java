package view;
/**
 * The user interface that have the functionality of the view
 * @author Marian & Lidor
 *
 */
public interface View 
{
	/** 
	 * Start the user interface
	 */
	public void start();
	
	/**
	 * return the command that the user entered
	 * @return string with the user command
	 */
	public String getUserCommand();
	
	/**
	 * display the phrase: the server is ready
	 * @param message string: the server is ready
	 */
	public void showOpenTheServer(String message);
	
	/**
	 * close the server and print server is closed
	 * @param message server is closed
	 */
	public void showClose(String message);
	
	/**
	 * display error messages
	 * @param message string with the error message
	 */
	public void showError(String message);
	
	/**
	 * display every message that the server generate
	 * @param message string with the message
	 */
	public void showMessages(String message);
}
