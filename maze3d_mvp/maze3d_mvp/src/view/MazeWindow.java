package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
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
/**
 * generating the main window
 * @author Marian & Lidor
 *
 */
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
	
	private boolean isDisposed;//checking if shell is disposed,in order to close window properly
	

	private KeyListener arrowKeyListener;//I didn't succeded to pass key listener in the listenerCollection,so i passed it alone
	
	/**
	 * constructor using fields
	 * @param title the title of the window
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param listenerCollection listener collection of the window
	 */
	public MazeWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection,KeyListener arrowKeyListener) {
		super(title, width, height,listenerCollection);
		this.arrowKeyListener=arrowKeyListener;
 		initWidgets();
 		

	}
	/**
	 * {@inheritDoc}
	 */
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
		mazeCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,10));

		mazeCanvas.addKeyListener(arrowKeyListener);

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
		
		//reset button
		reset=new Button(shell, SWT.PUSH);
		reset.setText("reset");
		reset.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		reset.addListener(SWT.Selection,listenerCollection.get("reset"));
		reset.setEnabled(false);
		
		//up button
		up=new Button(shell, SWT.ARROW|SWT.UP);
		up.setBackground(new Color(null, 169,245,40));
		up.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		up.setEnabled(false);
		
		//down button
		down=new Button(shell, SWT.ARROW|SWT.DOWN);
		down.setBackground(new Color(null, 34,105,34));
		down.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		down.setEnabled(false);
		
//		Image im=new Image(display, "./resources/minion_banana_skiny.png");
//		CLabel label=new CLabel(shell, SWT.NONE);
//		label.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,3));
//		label.setImage(im);

		//exit button
		Button exit=new Button(shell, SWT.PUSH);
		exit.setText("exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));

		exit.addListener(SWT.Selection,listenerCollection.get("exit"));
		shell.addListener(SWT.Close,listenerCollection.get("exit"));
	}
	
	

	
	
	

	

	//method that passes information to canvas
	/**
	 * move the character in the canvas,and then draw it on the canvas
	 * @param x the x axis of the position that the character move to
	 * @param y the y axis of the position that the character move to
	 * @param z the z axis of the position that the character move to
	 * @param canBeMovedUp if in the new position there is an option to move up
	 * @param canBeMovedDown if in the new position there is an option to move down
	 * @param isStart if start was pressed
	 * @param isSolvingNow if now the character is moving in the maze
	 */
	public void moveCharacterInCanvas(int x, int y,int z,Boolean canBeMovedUp,Boolean canBeMovedDown,Boolean isStart,Boolean isSolvingNow)
	{
		
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				if(down!=null)
				{
					down.setEnabled(canBeMovedDown);
				}

			}
		});


		display.syncExec(new Runnable() {

			@Override
			public void run() {
				if(up!=null)
				{
					up.setEnabled(canBeMovedUp);
				}


			}
		});
		if(isStart)
		{
			display.syncExec(new Runnable() {
				
				@Override
				public void run() {
					solve.setEnabled(true);
					hint.setEnabled(true);
					reset.setEnabled(true);
					
				}
			});
			
			
		}
		
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				solve.setEnabled(!isSolvingNow);
				hint.setEnabled(!isSolvingNow);
				reset.setEnabled(!isSolvingNow);
				menuBar.setEnabled(!isSolvingNow);
				//exit.setEnabled(false);

			}
		});
		
		mazeCanvas.setCharacterInPlace(x,y,z);
		
	}
	
	/**
	 * Passing maze to the game board(mazeCanvas)
	 * @param maze maze to pass
	 */
	public void setMazeInCanvas(Maze3d maze) 
	{

		mazeCanvas.setMaze(maze);
		
	}


	/**
	 * showing a message box with error specified
	 * @param error the text of the message box
	 */
	public void showMessageBox(String error)
	{
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell,SWT.ICON_ERROR| SWT.OK);
				messageBox.setMessage(error);
				messageBox.setText("Error");
				messageBox.open();
				
			}
		});
		
	}

	
	
	//--------------------
	
	
	/**
	 * closing the main window
	 */
	public void close()
	{
		isDisposed = true;
		shell.dispose();
		

	}
	/**
	 * checks if the window was disposed
	 * @return true-if disposed,false-if not disposed
	 */
	public boolean IsDisposed()
	{
		return isDisposed;
	}
	/**
	 * getting the shell
	 * @return shell shell
	 */
	@Override
	public Shell getShell() 
	{

		return shell;
	}

}
