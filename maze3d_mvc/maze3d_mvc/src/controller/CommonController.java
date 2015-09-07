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
	}
	protected abstract void initCommands();

	public HashMap<String, Command> getStringToCommand() {
		return stringToCommand;
	}

	public void setStringToCommand(HashMap<String, Command> stringToCommand) {
		this.stringToCommand = stringToCommand;
	}
	
	
	
	
	

}
