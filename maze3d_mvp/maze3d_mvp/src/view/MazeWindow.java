package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow 
{
	private Maze2dDisplay mazeCanvas;
	
	private Menu menuBar;
	private Button start;
	private Button solve;
	private Button hint;
	private Button up;
	private Button down;
	private Button exit;
	
	
	
	private Maze3d maze;
	
	
	

	public MazeWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection) {
		super(title, width, height,listenerCollection);
		
	}

	@Override
	void initWidgets() {
		//Image im=new Image(display, "pics/hot_air_ballon.jpg");
		
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Super Maze game");
		
		//setting the menu bar
		menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);

        MenuItem menuItem = new MenuItem(fileMenu, SWT.PUSH);
        menuItem.setText("&maze configuration");
        shell.setMenuBar(menuBar);
        
		
        menuItem.addListener(SWT.Selection,listenerCollection.get("file dialog"));
		//-----
		
		//Label label=new Label(mainShell,SWT.HORIZONTAL|SWT.VERTICAL);
		//label.setText("Please try to solve the maze,\nwith arrow keys:");
		//label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		//start button
		start=new Button(shell, SWT.PUSH);
		start.setText("start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		start.addListener(SWT.Selection, listenerCollection.get("start"));
		//---
		
		
		//canvas with the maze game
		mazeCanvas=new Maze2dDisplay(shell, SWT.COLOR_DARK_GRAY,new MyGameCharacter());
		mazeCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6));
		//----
		
		
		//solve button
		solve=new Button(shell, SWT.PUSH);
		solve.setText("solve");
		solve.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		solve.setEnabled(false);
		solve.addListener(SWT.Selection, listenerCollection.get("solve"));
		
		
		//hint button
		hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		hint.setEnabled(false);
		hint.addListener(SWT.Selection, listenerCollection.get("hint"));
		
		
		//up button
		up=new Button(shell, SWT.ARROW|SWT.UP);
		up.setBackground(new Color(null, 169,245,40));
		up.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));

		
		//down button
		down=new Button(shell, SWT.ARROW|SWT.DOWN);
		down.setBackground(new Color(null, 34,105,34));
		down.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		
		
		
		mazeCanvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				if(mazeCanvas.canBeMovedUp())
				{
					up.setEnabled(true);
				}
				else
				{
					up.setEnabled(false);
				}
				if(mazeCanvas.canBeMovedDown())
				{
					down.setEnabled(true);
				}
				else
				{
					down.setEnabled(false);
				}
			}
		});
		
		
		//exit button
		Button exit=new Button(shell, SWT.PUSH);
		exit.setText("exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));

		exit.addListener(SWT.Selection,listenerCollection.get("exit"));
		shell.addListener(SWT.Close,listenerCollection.get("exit"));
	}
	
	
	
	public Maze2dDisplay getMazeCanvas() {
		return mazeCanvas;
	}

	public void setMazeCanvas(Maze2dDisplay mazeCanvas) {
		this.mazeCanvas = mazeCanvas;
	}

	public Maze3d getMaze() {
		return maze;
	}
	
	public void setMaze(Maze3d maze) 
	{

		this.maze = maze;
		mazeCanvas.setMaze(maze);
	}
	public void setSolution(Solution<Position> solution)
	{
		mazeCanvas.setSolution(solution);
	}
	
	
	public void close()
	{
		//mazeCanvas.close();
		
		shell.dispose();
		
		
		
		
	}
	
	
	//enabling or disabling buttons
	public void setEnableToMenuBar(Boolean isEnable)
	{
		menuBar.setEnabled(isEnable);
	}
	public void setEnableToStart(Boolean isEnable)
	{
		start.setEnabled(isEnable);
	}
	public void setEnableToSolve(Boolean isEnable)
	{
		solve.setEnabled(isEnable);
	}
	public void setEnableToHint(Boolean isEnable)
	{
		hint.setEnabled(isEnable);
	}
	public void setEnableToUp(Boolean isEnable)
	{
		up.setEnabled(isEnable);
	}
	public void setEnableToDown(Boolean isEnable)
	{
		down.setEnabled(isEnable);
	}
	public void setEnableToExit(Boolean isEnable)
	{
		exit.setEnabled(isEnable);
	}
	
	@Override
	public Shell getShell() 
	{

		return shell;
	}

}
