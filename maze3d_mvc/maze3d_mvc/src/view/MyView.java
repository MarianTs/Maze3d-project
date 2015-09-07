package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;

public class MyView extends CommonView
{
	CLI cli;
	HashMap<String,Command> stringToCommand;
	
	public MyView(Controller c) 
	{
		super(c);
		stringToCommand=c.getStringToCommand();
		cli=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out),stringToCommand);
	}

	@Override
	public void start() {
		cli.start();
		
	}
	public void displayDirPath(String[] dirArray)
	{
		System.out.println("The files and directories in this path are:");
		for(String s:dirArray)
		{
			System.out.println(s);
		}
	}
	public void displayError(String message)
	{
		System.out.println(message);
	}
}
