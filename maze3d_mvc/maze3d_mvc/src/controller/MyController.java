package controller;



import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public class MyController extends CommonController 
{

	public MyController() {
		super();
		
	}

	@Override
	protected void initCommands() 
	{
		stringToCommand.put("dir",new Command()
		{
			@Override
			public void doCommand(String[] args) {
				m.HandleDirPath(args);
			}
		});
		
		stringToCommand.put("generate 3d maze", new Command()
		{

			@Override
			public void doCommand(String[] args) {
				m.handleGenerate3dMaze(args);
				
			}
			
		});
		
		stringToCommand.put("help", new Command(){

			@Override
			public void doCommand(String[] args) {
				v.showHelp();
				
			}
			
		});
		stringToCommand.put("display", new Command()
		{

			@Override
			public void doCommand(String[] args) {
				m.handleDisplayName(args);
				
			}
			
		});
		
		stringToCommand.put("display cross section by", new Command()
		{

			@Override
			public void doCommand(String[] args) 
			{
				m.handleDisplayCrossSectionBy(args);
				
			}
		
		});
		stringToCommand.put("save maze",new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleSaveMaze(args);
				
			}
		});
		stringToCommand.put("load maze", new Command(){

			@Override
			public void doCommand(String[] args) {
				m.handleLoadMaze(args);
				
			}
			
		});
		
		stringToCommand.put("maze size", new Command(){

			@Override
			public void doCommand(String[] args) {
				m.handleMazeSize(args);
				
			}
		
		});
		stringToCommand.put("file size", new Command(){

			@Override
			public void doCommand(String[] args) {
				m.handleFileSize(args);
				
			}
			
		});
		stringToCommand.put("solve", new Command(){

			@Override
			public void doCommand(String[] args) {
				m.handleSolve(args);
				
			}
			
		});
		stringToCommand.put("display solution", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				m.handleDisplaySolution(args);
				
			}
		});
		
		
	}
	public void passDirPath(String[] dirArray)
	{
		v.showDirPath(dirArray);
	}
	public void passError(String message)
	{
		v.showError(message);
	}
	public void passGenerate3dMaze(String message)
	{
		v.showGenerate3dMaze(message);
	}
	
	public void passDisplayName(byte[] byteArr)
	{
		v.showDisplayName(byteArr);
	}
	
	public void passDisplayCrossSectionBy(int[][] crossSection)
	{
		v.showDisplayCrossSectionBy(crossSection);
	}
	
	public void passSaveMaze(String str)
	{
		v.showSaveMaze(str);
	}
	public void passLoadMaze(String str)
	{
		v.showLoadMaze(str);
	}
	public void passMazeSize(int size)
	{
		v.showMazeSize(size);
	}
	public void passFileSize(long length)
	{
		v.showFileSize(length);
	}
	public void passSolve(String message)
	{
		v.showSolve(message);
		
	}
	public void passDisplaySolution(Solution<Position> sol )
	{
		v.showDisplaySolution(sol);
	}

	
}
