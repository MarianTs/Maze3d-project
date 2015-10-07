package view;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class Maze2dDisplay extends MazeDisplay {

	private Position characterPlace;
	private MyGameCharacter characterPic;
	
	private Button up;
	private Button down;

	Thread thread;
//	private TimerTask timerTask;
//	private Timer timer;
	

	

	public Position getCharacterPlace() {
		return characterPlace;
	}

	public Maze2dDisplay(Composite parent, int style,MyGameCharacter characterPic) 
	{
		super(parent, style);
		setBackground(new Color(null, 255, 255, 255));
		this.characterPlace=null;
		this.characterPic=characterPic;

	}

	public void paintMaze() 
	{

		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				if(maze!=null)
				{


					e.gc.setForeground(new Color(null, 0, 0, 0));
					e.gc.setBackground(new Color(null, 75,	0	,130));

					int[][][] mazeData = maze.getMaze();



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

					if(characterPlace.getX()==maze.getGoalPosition().getX())
					{
						//e.gc.setBackground(new Color(null,0,255,0));
						//e.gc.fillRectangle(maze.getGoalPosition().getZ()*cellX, maze.getGoalPosition().getY()*cellY, cellX, cellY);
						Image image = new Image(e.display, "./resources/banana.jpg");
						e.gc.setBackground(new Color(null,200,0,0));						
						e.gc.drawImage(image, 0, 0, image.getBounds().width,image.getBounds().height,maze.getGoalPosition().getZ()*cellX,maze.getGoalPosition().getY()*cellY ,cellX ,cellY);							
						e.gc.setBackground(new Color(null,255,0,0));
						e.gc.setBackground(new Color(null,0,0,0));
					}
//					if(characterPlace.getX()==maze.getStartPosition().getX())
//					{
//						e.gc.setBackground(new Color(null,0,45,0));
//						e.gc.fillRectangle(maze.getStartPosition().getZ()*cellX, maze.getStartPosition().getY()*cellY, cellX, cellY);
//						e.gc.setBackground(new Color(null,0,45,0));
//					}
					if(characterPlace.equals(maze.getGoalPosition()))
					{
						Image im=new Image(getDisplay(), "./resources/FotorCreated.jpg");
						e.gc.drawImage(im,0,0,im.getBounds().width,im.getBounds().height,width/4,depth/4,width/2,depth/2);

					}


					characterPic.paint(e,characterPlace.getZ()*cellX,characterPlace.getY()*cellY, cellX, cellY);
				}
			}
		});

		

	}
	
	
	public void solve(Solution<Position> solution)
	{
		//timer=new ScheduledThreadPoolExecutor(10);
		//timer=new Timer();

		ArrayList<State<Position>> al=solution.getAL();
		
		
		thread=new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				for(State<Position> s:al)
				{
					Position p=s.getState();
					int x=p.getX();
					int y=p.getY();
					int z=p.getZ();
					characterPlace.setXYZ(x, y, z);
					getDisplay().syncExec(new Runnable() {
						
						@Override
						public void run() {
							redraw();
							
						}
					});
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
				
			}
		});
		thread.start();
		
//		timerTask=new TimerTask() {
//
//			@Override
//			public void run() {
//				getDisplay().syncExec(new Runnable() 
//				{
//
//					@Override
//					public void run() 
//					{
//						if(al.isEmpty())
//						{
//							return;
//						}
//						Position p=al.remove(0).getState();
//
//
//						int x=p.getX();
//						int y=p.getY();
//						int z=p.getZ();
//						characterPlace.setXYZ(x, y, z);
//						redraw();
//					}
//				});
//
//			}
//		};
//		//timer.scheduleAtFixedRate(timerTask, 0, 500, TimeUnit.MILLISECONDS);
//		timer.scheduleAtFixedRate(timerTask, 0, 500);

	}
	
	public void hint(Solution<Position> solution)
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
				public void run() 
				{
					redraw();
					//calling paint event
				}
			});
			
		}


	}
	
	public void reset()
	{
		if((thread!=null)&&(thread.isInterrupted()==false))
		{
			thread.interrupt();
		}
		characterPlace.setXYZ(maze.getStartPosition().getX(), maze.getStartPosition().getY(), maze.getStartPosition().getZ());
		redraw();
	}
	
	
	public void setUpButton(Button up)
	{
		this.up=up;
	}
	public void setDownButton(Button down)
	{
		this.down=down;
	}
	public void setMaze(Maze3d maze)
	{
		//only here I got maze
		this.maze = maze;
		this.characterPlace=new Position(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ());
		getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				//now I will initialize the the paint listener
				paintMaze();
				//activate paint listener
				redraw();
				
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
			if((characterPlace.equals(maze.getStartPosition()))||(characterPlace.equals(maze.getGoalPosition())))
			{
				return false;
			}
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

		//check the exit!!!!!!
		if(maze!=null)
		{
			int[][][] mazeData=maze.getMaze();
			if((characterPlace.equals(maze.getStartPosition()))||(characterPlace.equals(maze.getGoalPosition())))
			{
				return false;
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
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
			if(canBeMovedUp())
			{
				up.setEnabled(true);
			}
			if(canBeMovedDown())
			{
				down.setEnabled(true);
			}
			redraw();
			
		}
	}
	
	
	public void close()
	{
//		if(timerTask!=null)
//		{
//			timerTask.cancel();
//		}
//		if(timer!=null)
//		{
//			timer.cancel();
//			try 
//			{
//				while(timer.awaitTermination(10, TimeUnit.SECONDS));
//			} 
//			catch (InterruptedException e)
//			{
//
//				e.printStackTrace();
//			}
//		}
		if((thread!=null)&&(!thread.isInterrupted()))
		{
			thread.interrupt();
		}
		
		
	}

}
