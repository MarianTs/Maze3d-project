package controller;



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
}
