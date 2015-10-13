package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CLI extends CommonView {
	
	BufferedReader in;
	PrintWriter out;
	boolean canBeClosed;
	String line;
	
	/**
	 * constructor using fields
	 * @param in input stream source
	 * @param out output stream source
	 */
	public CLI(BufferedReader in, PrintWriter out) 
	{
		this.in=in;
		this.out=out;
		this.line=new String();
		this.canBeClosed=false;
	}
	
	public void start() 
	{
		out.println("Hello,Welcome to server command line!\nPlease Enter command:\n\tto open the server type:open the server,\n\tto close type:close the server");
		out.flush();
		new Thread(new Runnable() 
		{
			public void run() 
			{
				
				while(canBeClosed==false)
				{
					try 
					{
						
						line=in.readLine();
						if(!line.isEmpty())
						{
							setChanged();
							notifyObservers();
						}
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
				}

			}
		}).start();
		
	}
	
	public String getUserCommand()
	{
		return line;
	}
	
	
	public void showOpenTheServer(String message)
	{
		out.println(message);
		out.flush();
	}
	public void showClose(String message)
	{
		canBeClosed=true;
		out.println(message);
		out.println("bye bye");
		out.flush();
	}
	public void showError(String message)
	{
		out.println(message);
		out.flush();
	}
	public void showMessages(String message)
	{
		out.println(message);
		out.flush();
	}
}
