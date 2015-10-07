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
import boot.Run;
import presenter.Properties;
/**
 * class that generates the connection between the gui and the mvp(model view presenter)
 * @author Marian
 *
 */
public class GUI extends CommonView 
{
	String message;
	HashMap<String,Listener> listenerCollection;
	MazeWindow mainWindow;
	GenericWindow genericWindow;
	boolean isSolve;
	
	private String path;
	
	/**
	 * constructor 
	 */
	public GUI() 
	{
		listenerCollection=new HashMap<String,Listener>();
		message=new String();
		initListeners();
		this.mainWindow=new MazeWindow("Minion Maze",600,400,listenerCollection);
		isSolve=false;
	}
	
	
	
	/**
	 * starting the main window
	 */
	@Override
	public void start() {
		mainWindow.run();

	}
	/**
	 * Initialize the listeners that will be sent to the MazeWindow
	 */
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
					 mainWindow.closeCanvas();
					 message="exit";
					 setChanged();
					 notifyObservers();
					 mainWindow.close();
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
				 fd.setText("xml loader");
				 fd.setFilterPath("*.xml");
				 String[] filterExt = {  "*.xml" };
				 fd.setFilterExtensions(filterExt);
				 path = fd.open();
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
				if(mp==null)
				{
					return;
				}
				int x=mp.getHeight();
				int y=mp.getWidth();
				int z=mp.getDepth();
				
				message="generate 3d maze m "+x+" "+y+" "+z;
				setChanged();
				notifyObservers();
				mainWindow.setEnableToSolve(true);
				mainWindow.setEnableToHint(true);
				mainWindow.setEnableToReset(true);
				
			}
		});
		listenerCollection.put("solve", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) 
			{

				int x=mainWindow.getCharacterPlace().getX();
				int y=mainWindow.getCharacterPlace().getY();
				int z=mainWindow.getCharacterPlace().getZ();
				message="solve from m "+x+" "+y+" "+z;
				isSolve=true;
				setChanged();
				notifyObservers();
				
				//mainWindow.setEnableToExit(false);
				
			}
		});
		listenerCollection.put("hint", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {

				int x=mainWindow.getCharacterPlace().getX();
				int y=mainWindow.getCharacterPlace().getY();
				int z=mainWindow.getCharacterPlace().getZ();
				message="solve from m "+x+" "+y+" "+z;
				isSolve=false;
				setChanged();
				notifyObservers();
				
				
			}
		});
		listenerCollection.put("reset", new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				mainWindow.resetTheCharacterInCanvas();
				
			}
		});
		
		
	}
	
	@Override
	public String getUserCommand() 
	{
		return message;
	}

	@Override
	public void showError(String error) 
	{
		mainWindow.showMessageBox(error);

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
	
			mainWindow.setMazeInCanvas(maze);
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
	public void showExit() 
	{
		mainWindow.close();
	}

	@Override
	public void showHelp() {
		// TODO Auto-generated method stub

	}
	public void showSolveFrom(String message)
	{

		this.message="display half solution m";
		setChanged();
		notifyObservers();
	}
	public void showDisplayHalfSolution(Solution<Position> solution)
	{
		if(isSolve)
		{
			mainWindow.solveInCanvas(solution);
		}
		else
		{
			mainWindow.hintInCanvas(solution);
		}
		
	}

	@Override
	public void showLoadXML(Properties p) 
	{
		if(p.getTypeOfUserInterfece().intern()=="cli")
		{
			mainWindow.closeCanvas();
			showExit();
			String[] s=new String[1];
			s[0]=path;
			Run.main(s);
		}
		
	}
	

}
