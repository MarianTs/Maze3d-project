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
	
}
