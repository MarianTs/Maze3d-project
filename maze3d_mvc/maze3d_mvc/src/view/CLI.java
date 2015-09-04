package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
				Command command;
				try 
				{
					while((line=in.readLine()).intern()!="exit")
					{						
						if((command=stringToCommand.get(line)) != null)
						{
							command.doCommand();
						}
						else
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
