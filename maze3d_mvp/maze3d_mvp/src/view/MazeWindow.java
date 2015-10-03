package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;

public class MazeWindow extends BasicWindow 
{

	Maze3d maze;
	
	Maze2dDisplay mazeCanvas;
	

	public MazeWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection) {
		super(title, width, height,listenerCollection);
		
		
	}

	@Override
	void initWidgets() {
		//Image im=new Image(display, "pics/hot_air_ballon.jpg");
		
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Super Maze game");
		
		
		//Label label=new Label(mainShell,SWT.HORIZONTAL|SWT.VERTICAL);
		//label.setText("Please try to solve the maze,\nwith arrow keys:");
		//label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		
		Button start=new Button(shell, SWT.PUSH|SWT.BOLD|SWT.CENTER);
		start.setText("start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		start.addListener(SWT.Selection, listenerCollection.get("start"));
		
		mazeCanvas=new Maze2dDisplay(shell, SWT.COLOR_DARK_GRAY);
		mazeCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		
		
		
		mazeCanvas.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg) 
			{
	
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
				//arg.doit=true;
				if(arg.keyCode==SWT.RIGHT)
				{
					mazeCanvas.moveRight();
				}
				else if(arg.keyCode==SWT.LEFT)
				{
					mazeCanvas.moveLeft();
				}
				else if(arg.keyCode==SWT.UP)
				{
					mazeCanvas.moveUp();
					
				}
				else if(arg.keyCode==SWT.DOWN)
				{
					mazeCanvas.moveDown();
				}
				else if((arg.stateMask & SWT.PAGE_DOWN)!=0)
				{
					mazeCanvas.moveBelow();
				}
				else if((arg.stateMask & SWT.PAGE_UP)!=0)
				{
					mazeCanvas.moveAbove();
				}
				
			}
		});		
		
		Button hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		
		
		Button exit=new Button(shell, SWT.PUSH);
		exit.setText("exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));

		exit.addListener(SWT.Selection,listenerCollection.get("exit"));
		shell.addListener(SWT.Close, listenerCollection.get("exit"));
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
	
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public boolean DisplayExitMessageBox()
	{
		MessageBox messageBox = new MessageBox(shell,  SWT.ICON_QUESTION| SWT.YES | SWT.NO);
		 messageBox.setMessage("Do you really want to exit?");
		 messageBox.setText("Exiting Application");
		 int response = messageBox.open();
		 if (response == SWT.YES)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	public void close()
	{
		
		shell.dispose();

		
	}

}