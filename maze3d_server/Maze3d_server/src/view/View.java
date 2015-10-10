package view;

public interface View 
{
	public void start();
	
	public String getUserCommand();
	
	public void showOpenTheServer(String message);
	
	public void showClose(String message);
	
	public void showError(String message);
}
