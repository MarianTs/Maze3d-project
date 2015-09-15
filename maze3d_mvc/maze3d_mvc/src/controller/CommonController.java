package controller;

import java.util.HashMap;

import Model.Model;
import view.View;

public abstract class CommonController implements Controller 
{
	
	Model m;
	View v;
	HashMap<String, Command> stringToCommand;
	
	
	public CommonController() 
	{
		super();
		
		stringToCommand=new HashMap<String, Command>();
		initCommands();
		
		
		stringToCommand.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				
				m.handleExitCommand(args);
			}
		});
		
		
	}

	public Model getM() {
		return m;
	}

	public void setM(Model m) {
		this.m = m;
	}

	public View getV() {
		return v;
	}

	public void setV(View v) {
		this.v = v;
		v.setStringToCommand(stringToCommand);
	}
	protected abstract void initCommands();

}
