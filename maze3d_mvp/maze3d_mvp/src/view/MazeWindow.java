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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
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
	private Button reset;
	private Button exit;
	

	

	public MazeWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection) {
		super(title, width, height,listenerCollection);
 		initWidgets();
 		mazeCanvas.setUpButton(up);
		mazeCanvas.setDownButton(down);
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
		start.setText("Start New Game");
		//start.setImage(new Image(display, "./resources/win_pic.png"));
		start.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		start.addListener(SWT.Selection, listenerCollection.get("start"));
		//---
		
		
		//canvas with the maze game
		mazeCanvas=new Maze2dDisplay(shell, SWT.NONE,new MyGameCharacter());
		mazeCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6));
		
		mazeCanvas.addKeyListener(new KeyListener() 
		{
			
			@Override
			public void keyReleased(KeyEvent arg) {
				//arg.doit=true;
				if(arg.keyCode==SWT.ARROW_RIGHT)
				{
					mazeCanvas.moveRight();
				}
				else if(arg.keyCode==SWT.ARROW_LEFT)
				{
					mazeCanvas.moveLeft();
				}
				else if(arg.keyCode==SWT.ARROW_UP)
				{
					mazeCanvas.moveUp();
					
				}
				else if(arg.keyCode==SWT.ARROW_DOWN)
				{
					mazeCanvas.moveDown();
				}
				else if(arg.keyCode==SWT.PAGE_DOWN)
				{
					mazeCanvas.moveBelow();
				}
				else if(arg.keyCode==SWT.PAGE_UP)
				{
					mazeCanvas.moveAbove();
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg) 
			{

				
			}
		});
		
		//----
		
		
		//solve button
		solve=new Button(shell, SWT.PUSH);
		solve.setText("solve");
		solve.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		setEnableToSolve(false);
		solve.addListener(SWT.Selection, listenerCollection.get("solve"));
		
		
		//hint button
		hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		setEnableToHint(false);
		hint.addListener(SWT.Selection, listenerCollection.get("hint"));
		
		//reset button
		reset=new Button(shell, SWT.PUSH);
		reset.setText("reset");
		reset.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		reset.addListener(SWT.Selection,listenerCollection.get("reset"));
		setEnableToReset(false);
		
		//up button
		up=new Button(shell, SWT.ARROW|SWT.UP);
		up.setBackground(new Color(null, 169,245,40));
		up.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		setEnableToUp(false);
		
		//down button
		down=new Button(shell, SWT.ARROW|SWT.DOWN);
		down.setBackground(new Color(null, 34,105,34));
		down.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		setEnableToDown(false);
		
		
		
//		mazeCanvas.addPaintListener(new PaintListener() {
//			
//			@Override
//			public void paintControl(PaintEvent arg0) {
//				if(mazeCanvas.canBeMovedUp())
//				{
//					up.setEnabled(true);
//				}
//				else
//				{
//					up.setEnabled(false);
//				}
//				if(mazeCanvas.canBeMovedDown())
//				{
//					down.setEnabled(true);
//				}
//				else
//				{
//					down.setEnabled(false);
//				}
//			}
//		});
		
		
		//exit button
		Button exit=new Button(shell, SWT.PUSH);
		exit.setText("exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));

		exit.addListener(SWT.Selection,listenerCollection.get("exit"));
		shell.addListener(SWT.Close,listenerCollection.get("exit"));
	}
	
	
	


	public void setMazeCanvas(Maze2dDisplay mazeCanvas) {
		this.mazeCanvas = mazeCanvas;
	}

//	public Maze3d getMaze() {
//		return maze;
//	}
	
	
	//method that passes information to canvas
	public void setMazeInCanvas(Maze3d maze) 
	{

		mazeCanvas.setMaze(maze);
	}
	public void solveInCanvas(Solution<Position> solution)
	{
		mazeCanvas.solve(solution);
	}
	public void hintInCanvas(Solution<Position> solution)
	{
		mazeCanvas.hint(solution);
	}
	
	public void showMessageBox(String error)
	{
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_ERROR| SWT.OK);
		messageBox.setMessage(error);
		messageBox.setText("Error");
		messageBox.open();
	}
	public void resetTheCharacterInCanvas()
	{
		mazeCanvas.reset();
	}
	public void closeCanvas()
	{
		mazeCanvas.close();
	}
	
	//--------------------
	
	
	
	public void close()
	{
		//mazeCanvas.close();
		shell.dispose();

	}
	public Position getCharacterPlace()
	{
		return mazeCanvas.getCharacterPlace();
	}
	
	
	
	//enabling or disabling buttons
	public void setEnableToReset(Boolean isEnable)
	{
		reset.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the menuBar button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToMenuBar(Boolean isEnable)
	{
		menuBar.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "start new game" button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToStart(Boolean isEnable)
	{
		start.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "solve" button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToSolve(Boolean isEnable)
	{
		solve.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "hint" button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToHint(Boolean isEnable)
	{
		hint.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "up" button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToUp(Boolean isEnable)
	{
		up.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "down" button
	 * @param isEnable true-to enable,false-to disable
	 */
	public void setEnableToDown(Boolean isEnable)
	{
		down.setEnabled(isEnable);
	}
	/**
	 * enabling or disabling the "exit" button
	 * @param isEnable true-to enable,false-to disable
	 */
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
