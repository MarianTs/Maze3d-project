package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import controller.Command;

public class CLI extends Thread{
	BufferedReader in;
	PrintWriter out;
	HashMap<String,Command> stringToCommand;
	
	CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> stringToCommand) {
		super();
		this.in = in;
		this.out = out;
		this.stringToCommand=stringToCommand;
	}
	
	public void start()
	{
		new Thread(new Runnable() {
			public void run() {
				String line;
				Command command=null;
				try 
				{
					//out.println("Please Enter command:");
					while((line=in.readLine()).intern()!="exit")
					{

						ArrayList<String> paramArray=new ArrayList<String>();
						//paramArray.add(line.substring(line.lastIndexOf(" ")+1));
						
						while(!(line.isEmpty()))
						{
							
							if((command=stringToCommand.get(line)) != null)
							{
								
								Collections.reverse(paramArray);
								command.doCommand(paramArray.toArray(new String[paramArray.size()]));
								break;
							}
							if(line.indexOf(" ")==-1)//we ended the line
							{
								break;
							}
							
							paramArray.add(line.substring(line.lastIndexOf(" ")+1));
							line=line.substring(0, line.lastIndexOf(" "));	//cutting till " " (not including)
							
						}
						
						
						if(command==null)
						{
							out.println("Command not found!");
							out.flush();
						}
						
					}
					out.println("Bye bye!");
					out.flush();
					
				} 
				catch (IOException e) {

					e.printStackTrace();
				}
			}
		}).start();

	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public HashMap<String, Command> getStringToCommand() {
		return stringToCommand;
	}

	public void setStringToCommand(HashMap<String, Command> stringToCommand) {
		this.stringToCommand = stringToCommand;
	}
	
	
}
