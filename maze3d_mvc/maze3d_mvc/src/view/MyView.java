package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.Controller;

public class MyView extends CommonView
{
	CLI cli;
	
	public MyView(Controller c) {
		super(c);
		
		cli=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out), c.getStringToCommand());
	}

	@Override
	public void start() {
		cli.start();
		
	}
	
}
