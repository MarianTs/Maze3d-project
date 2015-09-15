package controller;



import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * specify the current controller,which passes the commands from view to model
 * @author Marian
 *
 */
public class MyController extends CommonController 
{
	/**
	 * constructor
	 */
	public MyController() {
		super();
		
	}
	/**
	 * {@inheritDoc}
	 */
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
	/**
	 * {@inheritDoc}
	 */
	public void passDirPath(String[] dirArray)
	{
		v.showDirPath(dirArray);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passError(String message)
	{
		v.showError(message);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passGenerate3dMaze(String message)
	{
		v.showGenerate3dMaze(message);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passDisplayName(byte[] byteArr)
	{
		v.showDisplayName(byteArr);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passDisplayCrossSectionBy(int[][] crossSection)
	{
		v.showDisplayCrossSectionBy(crossSection);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passSaveMaze(String str)
	{
		v.showSaveMaze(str);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passLoadMaze(String str)
	{
		v.showLoadMaze(str);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passMazeSize(int size)
	{
		v.showMazeSize(size);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passFileSize(long length)
	{
		v.showFileSize(length);
	}
	/**
	 * {@inheritDoc}
	 */
	public void passSolve(String message)
	{
		v.showSolve(message);
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void passDisplaySolution(Solution<Position> sol )
	{
		v.showDisplaySolution(sol);
	}

	
}
