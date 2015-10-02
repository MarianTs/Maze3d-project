package view;



import java.util.HashMap;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GUI extends CommonView 
{
	String message;
	HashMap<String,Listener> listenerCollection;
	BasicWindow basicWindow;
	GenericWindow genericWindow;
	
	public GUI() 
	{
		listenerCollection=new HashMap<String,Listener>();
		message=new String();
		initListeners();
		this.basicWindow=new MazeWindow("bla bla",600,400,listenerCollection);
	}
	
	
	
	
	@Override
	public void start() {
		basicWindow.run();

	}
	
	public void initListeners()
	{
		listenerCollection.put("exit",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 Boolean x=basicWindow.DisplayExitMessageBox();
				 if(x==true)
				 {
					 message="exit";
					 setChanged();
					 notifyObservers();
					 basicWindow.close();
					 
				 }

			 }
		}); 
		/*listenerCollection.put("cancel",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 genericWindow.close();

			 }
		}); */
		listenerCollection.put("start", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				genericWindow=new GenericWindow(50,100,listenerCollection,new MazeProperties());
				genericWindow.run();
				MazeProperties mp=(MazeProperties)genericWindow.getObj();
				int x=mp.getDepth();
				int y=mp.getWidth();
				int z=mp.getDepth();
				message="generate 3d maze m "+x+" "+y+" "+z+" prim";
				setChanged();
				notifyObservers();
				
				
			}
		});
		listenerCollection.put("ok", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) 
			{
				
				
			}
		});
		
	}
	
	@Override
	public String getUserCommand() 
	{
		return message;
	}

	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDirPath(String[] list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showGenerate3dMaze(String message) 
	{
		

	}

	@Override
	public void showDisplayName(byte[] byteArr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDisplayCrossSectionBy(int[][] crossSection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSaveMaze(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoadMaze(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMazeSize(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showFileSize(long size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSolveMaze(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDisplaySolution(Solution<Position> solution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showHelp() {
		// TODO Auto-generated method stub

	}
	
	

}
