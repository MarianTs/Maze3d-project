package view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class Maze2dDisplay extends MazeDisplay {

	private Position characterPlace;
	private Boolean isStartingPoint;
	private GameCharacter gameCharacterPicture;
	private Solution<Position> solution;
	private TimerTask timerTask;
	private Timer timer;
	

	

	public Position getCharacterPlace() {
		return characterPlace;
	}

	public Maze2dDisplay(Composite parent, int style,GameCharacter gCharacter) 
	{
		super(parent, style);
		setBackground(new Color(null, 255, 255, 255));
		isStartingPoint=true;
		characterPlace=new Position();
		this.gameCharacterPicture=gCharacter;
		
	}

	public void paintMaze() 
	{
		
		if (maze != null) 
		{

			addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					
					
					e.gc.setForeground(new Color(null, 0, 0, 0));
					e.gc.setBackground(new Color(null, 75,	0	,130));
					
					int[][][] mazeData = maze.getMaze();
					if(isStartingPoint)
					{
						characterPlace = maze.getStartPosition();
						isStartingPoint=false;
					}
					
					

					int width = getSize().x;// how many pixels there are in the canvas in the width(z in the maze)
					int depth = getSize().y;// how many pixels there is on the canvas in height(y in the maze)

					// the size of each cell in the canvas,according to the size of the changing window
					int cellX = width / mazeData[0][0].length;// the width of the cell
					int cellY = depth / mazeData[0].length; // the height of the cell
					
					
					

					// for calculating the size of the maze floor
					int lengthWidth = mazeData[0][0].length;// the length of z axis in the maze
					int lengthDepth = mazeData[0].length;// the length of y axis in the maze

					for (int i = 0; i < lengthDepth; i++) {
						for (int j = 0; j < lengthWidth; j++) {
							// this is the maze where i am starting to draw the
							// cell
							int pixelX = cellX * j;
							int pixelY = cellY * i;
							if (mazeData[characterPlace.getX()][i][j] != 0)
							{
								e.gc.fillRectangle(pixelX, pixelY, cellX, cellY);
								// drawing rectangle from the node cell size
							}
							
						}
					}
					//e.gc.setBackground(new Color(null,255,0,0));
					//e.gc.fillRectangle(maze.getStartPosition().getZ()*cellX, maze.getStartPosition().getY()*cellY, cellX, cellY);
					if(characterPlace.getX()==maze.getGoalPosition().getX())
					{
						e.gc.setBackground(new Color(null,0,255,0));
						e.gc.fillRectangle(maze.getGoalPosition().getZ()*cellX, maze.getGoalPosition().getY()*cellY, cellX, cellY);
					}
					/*if(characterPlace.getX()==maze.getStartPosition().getX())
					{
						e.gc.setBackground(new Color(null,0,45,0));
						e.gc.fillRectangle(maze.getStartPosition().getZ()*cellX, maze.getStartPosition().getY()*cellY, cellX, cellY);
					}*/
					
					
					
					gameCharacterPicture.paint(e,characterPlace.getZ()*cellX,characterPlace.getY()*cellY, cellX, cellY);

				}
			});
			
		}

	}
	public void setSolution(Solution<Position> solution)
	{
		this.solution = solution;
		System.out.println(solution);

		
	}
	public void solve()
	{

		timer=new Timer();

		ArrayList<State<Position>> al=solution.getAL();

		timerTask=new TimerTask() {

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() 
				{

					@Override
					public void run() 
					{
						if(al.isEmpty())
						{
							return;
						}
						Position p=al.remove(0).getState();


						int x=p.getX();
						int y=p.getY();
						int z=p.getZ();
						characterPlace.setXYZ(x, y, z);
						redraw();
					}
				});

			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 500);

	}
	
	public void hint()
	{

		ArrayList<State<Position>> al=solution.getAL();
		if(al.isEmpty())
		{
			return;
		}
		Position p=al.get(1).getState();
		if(p!=null)
		{
			int x=p.getX();
			int y=p.getY();
			int z=p.getZ();
			characterPlace.setXYZ(x, y, z);
			
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					paintMaze();
					activatePainting();
					
				}
			});
			
		}

		

	}
	public void setMaze(Maze3d maze)
	{
		System.out.println("5 \n"+maze);
		this.maze = maze;
		
		getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				paintMaze();
				activatePainting();
				
			}
		});
	}
	public boolean canBeMovedUp()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		
		if(maze!=null)
		{
			int[][][] mazeData=maze.getMaze();
			if((x<mazeData.length-1)&&(mazeData[x+1][y][z]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	public boolean canBeMovedDown()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();

		
		if(maze!=null)
		{
			int[][][] mazeData=maze.getMaze();
			if((x>0)&&(mazeData[x-1][y][z]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	
	
	public void moveUp()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((y>0)&&(mazeData[x][y-1][z]==0))
		{
			characterPlace.setXYZ(x, y-1, z);
			redraw();
			
		}
		
	}
	public void moveDown()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((y<mazeData[0].length-1)&&(mazeData[x][y+1][z]==0))
		{
			characterPlace.setXYZ(x, y+1, z);
			redraw();
			
		}
	}
	public void moveRight()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((z<mazeData[0][0].length-1)&&(mazeData[x][y][z+1]==0))
		{
			characterPlace.setXYZ(x, y, z+1);
			redraw();
			
		}
	}
	public void moveLeft()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((z>0)&&(mazeData[x][y][z-1]==0))
		{
			characterPlace.setXYZ(x, y, z-1);
			redraw();
			
		}
	}
	public void moveAbove()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((x<mazeData.length-1)&&(mazeData[x+1][y][z]==0))
		{
			characterPlace.setXYZ(x+1, y,z);
			redraw();
			
		}
	}
	public void moveBelow()
	{
		int x=characterPlace.getX();
		int y=characterPlace.getY();
		int z=characterPlace.getZ();
		int[][][] mazeData=maze.getMaze();
		if((x>0)&&(mazeData[x-1][y][z]==0))
		{
			characterPlace.setXYZ(x-1, y,z);
			redraw();
			
		}
	}
	
	public void activatePainting()
	{
		if(maze!=null)
		{
			redraw();
			isStartingPoint=true;
		}
	}
	public void close()
	{
		if(timerTask!=null)
		{
			while(timerTask.cancel()==false);
		}
		if(timer!=null)
		{
			timer.cancel();
		}
		
		
	}

}
