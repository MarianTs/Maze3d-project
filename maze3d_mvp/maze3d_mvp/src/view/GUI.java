package view;



import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GUI extends CommonView 
{
	String message;
	HashMap<String,Listener> listenerCollection;
	MazeWindow mainWindow;
	GenericWindow genericWindow;
	boolean isSolve;
	
	public GUI() 
	{
		listenerCollection=new HashMap<String,Listener>();
		message=new String();
		initListeners();
		this.mainWindow=new MazeWindow("bla bla",600,400,listenerCollection);
		isSolve=false;
	}
	
	
	
	
	@Override
	public void start() {
		mainWindow.run();

	}
	
	public void initListeners()
	{
		listenerCollection.put("exit",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 MessageBox messageBox = new MessageBox(mainWindow.getShell(),  SWT.ICON_QUESTION| SWT.YES | SWT.NO);
				 messageBox.setMessage("Do you really want to exit?");
				 messageBox.setText("Exiting Application");
				 if(messageBox.open()==SWT.YES)
				 {
					 mainWindow.close();
					 message="exit";
					 setChanged();
					 notifyObservers();
					 event.doit=true;
				 }
				 else
				 {
					 event.doit=false;
				 }
				 

			 }
		}); 
		listenerCollection.put("file dialog",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 FileDialog fd=new FileDialog(mainWindow.getShell(),SWT.OPEN);
				 fd.setText("open");
				 fd.setFilterPath(".");
				 String[] filterExt = {  ".xml", "*.*" };
				 fd.setFilterExtensions(filterExt);
				 String path = fd.open();
				 message="load xml "+ path;
				 setChanged();
				 notifyObservers();
				 

			 }
		}); 
		listenerCollection.put("start", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				genericWindow=new GenericWindow(200,200,listenerCollection,new MazeProperties());
				genericWindow.run();
				MazeProperties mp=(MazeProperties)genericWindow.getObj();
				int x=mp.getHeight();
				int y=mp.getWidth();
				int z=mp.getDepth();
				
				message="generate 3d maze m "+x+" "+y+" "+z+" prim";
				setChanged();
				notifyObservers();
				
				
			}
		});
		listenerCollection.put("solve", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) 
			{

				int x=mainWindow.getMazeCanvas().getCharacterPlace().getX();
				int y=mainWindow.getMazeCanvas().getCharacterPlace().getY();
				int z=mainWindow.getMazeCanvas().getCharacterPlace().getZ();
				message="solve from m bfs "+x+" "+y+" "+z;
				isSolve=true;
				setChanged();
				notifyObservers();
			}
		});
		listenerCollection.put("hint", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {

				int x=mainWindow.getMazeCanvas().getCharacterPlace().getX();
				int y=mainWindow.getMazeCanvas().getCharacterPlace().getY();
				int z=mainWindow.getMazeCanvas().getCharacterPlace().getZ();
				message="solve from m bfs "+x+" "+y+" "+z;
				isSolve=false;
				setChanged();
				notifyObservers();
				
				
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
		//!
		this.message="display m";

		setChanged();
		notifyObservers();
	}

	@Override
	public void showDisplayName(byte[] byteArr) {
		try 
		{
			Maze3d maze=new Maze3d(byteArr);
	
			mainWindow.setMaze(maze);
		}
		catch (IOException e) 
		{

			e.printStackTrace();
		}
		

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
	public void showSolveMaze(String message)
	{
		

	}

	@Override
	public void showDisplaySolution(Solution<Position> solution) 
	{
		
		
	}

	@Override
	public void showExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showHelp() {
		// TODO Auto-generated method stub

	}
	public void showSolveFrom(String message)
	{
		//System.out.println("1 "+message);
		this.message="display half solution m";
		setChanged();
		notifyObservers();
	}
	public void showDisplayHalfSolution(Solution<Position> solution)
	{
		System.out.println("2:"+solution);
		mainWindow.getMazeCanvas().setSolution(solution);
		if(isSolve)
		{
			mainWindow.getMazeCanvas().solve();
		}
		else
		{
			mainWindow.getMazeCanvas().hint();
		}
		
	}
	

}
