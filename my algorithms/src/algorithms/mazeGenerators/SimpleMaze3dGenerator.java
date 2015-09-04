package algorithms.mazeGenerators;

import java.util.Random;
/**
 * Generate simple maze with random walls and paths
 * @author Marian
 *
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenarator 
{
    /**
     * {@inheritDoc}
     */
	@Override
	public Maze3d generate(int size_x,int size_y,int size_z) 
	{
		Maze3d myMaze=new Maze3d(size_x,size_y,size_z);
		int i,j,n;//i for x axis,j for y axis,n for z axis
		Random rand=new Random();
		
		//put 0 and 1 randomly in maze
		for(i=0;i<size_x;i++)
		{
			for(j=0;j<size_y;j++)
			{
				for(n=0;n<size_z;n++)
				{
					
					myMaze.setValueInPlace(new Position(i,j,n), rand.nextInt(2));
				}
			}
		}
		
		
		//building path,in order to provide at least one path
		i=0;
		j=0;
		n=0;
		int direction;
		myMaze.setStartPosition(new Position(0,0,0));
		
		
		
		while ((i<size_x) && (j<size_y) && (n<size_z) && (i>=0) && (j>=0) && (n>=0))
		{
	
			myMaze.setValueInPlace(new Position(i,j,n), 0);
			
			direction=rand.nextInt(3);
			
			switch (direction) 
			{
			case 0:
				i++;
				break;
			case 1:
				j++;
				break;
			case 2:
				n++;
				break;
			default:
				i++;
				break;
			}
			
		}
		//initializing the exit of the maze
		if(i==size_x)
		{
			myMaze.setGoalPosition(new Position(i-1, j, n));
		}
		else if(j==size_y)
		{
			myMaze.setGoalPosition(new Position(i, j-1, n));
		}
		else if(n==size_z)
		{
			myMaze.setGoalPosition(new Position(i, j, n-1));
		}
		
		return myMaze;
		
	}
	
	

}
