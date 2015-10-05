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
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow 
{
	private Button solve;
	private Button hint;
	private Maze3d maze;
	
	private Maze2dDisplay mazeCanvas;
	

	public MazeWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection) {
		super(title, width, height,listenerCollection);
		
		
	}

	@Override
	void initWidgets() {
		//Image im=new Image(display, "pics/hot_air_ballon.jpg");
		
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Super Maze game");
		
		Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);

        MenuItem menuItem = new MenuItem(fileMenu, SWT.PUSH);
        menuItem.setText("&maze configuration");
        shell.setMenuBar(menuBar);

		
        menuItem.addListener(SWT.Selection,listenerCollection.get("file dialog"));
		
		
		//Label label=new Label(mainShell,SWT.HORIZONTAL|SWT.VERTICAL);
		//label.setText("Please try to solve the maze,\nwith arrow keys:");
		//label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		
		Button start=new Button(shell, SWT.PUSH|SWT.BOLD|SWT.CENTER);
		start.setText("start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		start.addListener(SWT.Selection, listenerCollection.get("start"));
		
		mazeCanvas=new Maze2dDisplay(shell, SWT.COLOR_DARK_GRAY,new MyGameCharacter());
		mazeCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6));
		
		
		
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
		
		solve=new Button(shell, SWT.PUSH);
		solve.setText("solve");
		solve.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		solve.setEnabled(false);
		solve.addListener(SWT.Selection, listenerCollection.get("solve"));
		
		hint=new Button(shell, SWT.PUSH);
		hint.setText("hint");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		hint.setEnabled(false);
		hint.addListener(SWT.Selection, listenerCollection.get("hint"));
		
		Button up=new Button(shell, SWT.ARROW|SWT.UP);
		up.setBackground(new Color(null, 169,245,40));
		up.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));

		
		
		Button down=new Button(shell, SWT.ARROW|SWT.DOWN);
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
	public void setEnableToSolve()
	{
		solve.setEnabled(true);
	}
	public void setEnableToHint()
	{
		hint.setEnabled(true);
	}
	@Override
	public Shell getShell() 
	{

		return shell;
	}

}
