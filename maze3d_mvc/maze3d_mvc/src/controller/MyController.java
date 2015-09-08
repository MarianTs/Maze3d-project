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
				v.displayHelp();
				
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
	}
	public void passDirPath(String[] dirArray)
	{
		v.displayDirPath(dirArray);
	}
	public void passError(String message)
	{
		v.displayError(message);
	}
	public void passGenerate3dMaze(String message)
	{
		v.displayGenerate3dMaze(message);
	}
	
	public void passDisplayName(byte[] byteArr)
	{
		v.displayCommandDisplayName(byteArr);
	}
	
	public void passDisplayCrossSectionBy(int[][] crossSection)
	{
		v.displayCommandDisplayCrossSectionBy(crossSection);
	}
}
