package presenter;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import Model.Model;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.View;

public class Presenter implements Observer {
	
	Model m;
	View ui;
	LinkedHashMap<String,Command> viewCommands;
	HashMap<String,Command> modelCommands;
	//i use ordered hash map,in order to check my strings easily
	
	
	public Presenter(Model m,View ui) 
	{
		
		this.m=m;
		this.ui=ui;
		viewCommands=new LinkedHashMap<String,Command>();
		modelCommands=new HashMap<String,Command>();
		initCommands();
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(o == ui) 
		{
			Command command;
			String input = ui.getUserCommand();
			while(input.charAt(0)==' ')
			{
				input=input.substring(1);
			}
			String params;
			String[] paramArray;
			for (Map.Entry<String, Command> entry : viewCommands.entrySet()) 
			{
				String key = entry.getKey();
				if(input.startsWith(key))
				{
					command=entry.getValue();
					if(key.length()==input.length())
					{
						command.doCommand(null);
					}
					else
					{
						params=input.substring(key.length()+1);
						paramArray=params.split(" ");
						command.doCommand(paramArray);
					}
					return;
				}
			}
			command=viewCommands.get("error");
			command.doCommand(null);

		}
		else if(o == m) 
		{
			Command command;
			String[] input =(String[])arg;
			if(input.length==1)
			{
				command=modelCommands.get(input[0].toString());
				command.doCommand(null);
			}
			else
			{
				command=modelCommands.get(input[0].toString());
				String[] params=new String[1];
				params[0]=input[1];
				command.doCommand(params);
			}
			
			
		}
		


	}
	protected void initCommands() 
	{
		//commands that came from view
		viewCommands.put("dir",new Command()
		{
			@Override
			public void doCommand(String[] args) {
				m.handleDirPath(args);
			}
		});
		
		
		viewCommands.put("generate 3d maze", new Command()
		{

			@Override
			public void doCommand(String[] args) {
				m.handleGenerate3dMaze(args);
				
			}
			
		});
		viewCommands.put("display half solution", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				m.handleDisplayHalfSolution(args);
			}
		});
		viewCommands.put("display solution", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleDisplaySolution(args);
				
			}
		});
		viewCommands.put("display cross section by", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleDisplayCrossSectionBy(args);
				
			}
		});
		viewCommands.put("display", new Command()
		{

			@Override
			public void doCommand(String[] args) {
				m.handleDisplayName(args);
				
			}
			
		});
		viewCommands.put("error", new Command()
		{

			@Override
			public void doCommand(String[] args) {
				m.handleError(args);
				
			}
			
		});
		viewCommands.put("help", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				ui.showHelp();
				
			}
		});
		viewCommands.put("exit",new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleExit(args);
				
			}
		});
		viewCommands.put("save maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleSaveMaze(args);
				
			}
		});
		viewCommands.put("load xml", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleLoadXML(args);
				
			}
		});
		viewCommands.put("load maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleLoadMaze(args);
				
			}
		});
		viewCommands.put("maze size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleMazeSize(args);
				
			}
		});
		viewCommands.put("file size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleFileSize(args);
				
			}
		});
		viewCommands.put("solve from", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleSolveFrom(args);
				
			}
		});
		viewCommands.put("solve", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleSolveMaze(args);
				
			}
		});
		
		
		//--------------------------------------
		//commands that came from model
		modelCommands.put("error", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				String s=m.getErrorCode();
				ui.showError(s);
				
			}
		});
		modelCommands.put("load xml", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				Properties p=m.getProperties();
				ui.showLoadXML(p);
				
			}
		});
		modelCommands.put("dir", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				String[] s=m.getDirList();
				ui.showDirPath(s);
				
			}
		});
		modelCommands.put("generate 3d maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				String s=m.getGenerate3dmazeCode();
				ui.showGenerate3dMaze(s);
				
			}
		});
		
		modelCommands.put("display cross section by", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				int[][] crossSection=m.getCrossSection();
				ui.showDisplayCrossSectionBy(crossSection);
				
			}
		});
		modelCommands.put("display", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				byte[] byteArr=m.getSpecificMazeFromColllection(args[0].toString());
				ui.showDisplayName(byteArr);
				
			}
		});
		modelCommands.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				ui.showExit();
				
			}
		});
		modelCommands.put("save maze", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				String s=m.getSaveMazeCode();
				ui.showSaveMaze(s);
			}
		});
		modelCommands.put("load maze", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				String s=m.getLoadMazeCode();
				ui.showLoadMaze(s);
			}
		});
		modelCommands.put("maze size", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				int x=m.getMazeSize();
				ui.showMazeSize(x);
			}
		});
		modelCommands.put("file size", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				long x=m.getFileSize();
				ui.showFileSize(x);
			}
		});
		modelCommands.put("solve from", new Command() {
			
			@Override
			public void doCommand(String[] args)
			{
				
				String s=m.getSolveHalfMazeCode();
				ui.showSolveFrom(s);
			}
		});
		modelCommands.put("solve", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				String s=m.getSolveMazeCode();//taking the message out
				ui.showSolveMaze(s);
			}
		});
		modelCommands.put("display solution", new Command() {
			
			@Override
			public void doCommand(String[] args) 
			{
				Solution<Position> sol=m.getSpecificSolution(args[0].toString());
				ui.showDisplaySolution(sol);
			}
		});
		modelCommands.put("display half solution", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				Solution<Position> sol=m.getSpecificHalfSolution(args[0].toString());
				ui.showDisplayHalfSolution(sol);
				
			}
		});
	}
}
