package presenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;
/**
 * connecting between the view and model
 * @author Marian & Lidor
 *
 */
public class Presenter implements Observer {

	Model m;
	View ui;
	HashMap<String, Command> viewCommands;
	HashMap<String, Command> modelCommands;
	
	/**
	 * constructor using fields
	 * @param m - model
	 * @param ui - user interface type
	 */
	public Presenter(Model m,View ui) 
	{
		
		this.m=m;
		this.ui=ui;
		viewCommands=new HashMap<String,Command>();
		modelCommands=new HashMap<String,Command>();
		initCommands();
	}
	
	/**
	 * get notification from the observables and activate the suitable command
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		if(o==ui)
		{
			String input=ui.getUserCommand();
			//deleting the spaces in the begining of the command
			while(input.charAt(0)==' ')
			{
				input=input.substring(1);
			}
			
			if(viewCommands.containsKey(input))
			{
				Command command=viewCommands.get(input);
				command.doCommand();
			}
			else
			{
				Command command=modelCommands.get("error");
				command.doCommand();
				
			}
		}
		if(o==m)
		{
			String input=(String)arg;
			if(modelCommands.containsKey(input))
			{
				Command command=modelCommands.get(input);
				command.doCommand();
			}
			
			else
			{
				Command command=modelCommands.get("error");
				command.doCommand();
				
			}
		}
	}
	
	/**
	 * initialize the commands
	 */
	public void initCommands()
	{
		//commands that came from view
		
		viewCommands.put("open the server", new Command() {
			
			@Override
			public void doCommand() {
				m.openTheServer();
				
			}
		});
		viewCommands.put("close the server",new Command() {
			
			@Override
			public void doCommand() {
				m.closeServer();
				
			}
		});
		
		//commands that came from model
		
		modelCommands.put("error", new Command() {
			
			@Override
			public void doCommand() {
				
				ui.showError("Invalid command");
				
			}
		});
		modelCommands.put("open the server", new Command() {
			
			@Override
			public void doCommand() {
				String s=m.getOpenCode();
				ui.showOpenTheServer(s);
				
				
			}
		});
		modelCommands.put("close the server", new Command() {
			
			@Override
			public void doCommand() {
				String s=m.getCloseCode();
				ui.showClose(s);
				
				
			}
		});
		modelCommands.put("pass messages", new Command() {
			
			@Override
			public void doCommand() {
				String s=m.getMessageCode();
				ui.showMessages(s);
				
				
			}
		});
	}

}
