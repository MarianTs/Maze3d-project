package algorithms.mazeGenerators;

import java.util.ArrayList;
/**
 * Abstract class of maze 3d generator,
 * @author Marian
 *
 */

public abstract class CommonMaze3dGenarator implements Maze3dGenerator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract Maze3d generate(int size_x,int size_y,int size_z);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String measureAlgorithmTime(int size_x,int size_y,int size_z) 
	{
		long startTime=System.nanoTime();
		generate(size_x,size_y,size_z);
		
		long endTime=System.nanoTime();
		return String.valueOf(endTime-startTime);
	}
	/**
	 * Insert all the walls around p into ArrayList(except the walls on the shell)
	 * @param p the position we work on
	 * @param myMaze the maze we work with
	 * @param AL ArrayList we fill in walls(we fill positions in the list)
	 */
	protected void fillArrayOfWalls(Position p,Maze3d myMaze,ArrayList<Position> AL)
	{
		int i=p.getX();
		int j=p.getY();
		int n=p.getZ();
		
		
		//take a cell and fill his neighboring wall in the array
		if((i-1>=1)&&(myMaze.getValueIn(i-1, j, n)==1)&&(AL.contains(new Position(i-1,j,n))==false))
		{
			AL.add(new Position(i-1,j,n));
		}
		if((i+1<myMaze.getSize_x()-1)&&(myMaze.getValueIn(i+1, j, n)==1)&&(AL.contains(new Position(i+1,j,n))==false))
		{
			AL.add(new Position(i+1,j,n));
		}
		if((j-1>=1)&&(myMaze.getValueIn(i, j-1, n)==1)&&(AL.contains(new Position(i,j-1,n))==false))
		{
			AL.add(new Position(i,j-1,n));
		}
		if((j+1<myMaze.getSize_y()-1)&&(myMaze.getValueIn(i, j+1, n)==1)&&(AL.contains(new Position(i,j+1,n))==false))
		{
			AL.add(new Position(i,j+1,n));
		}
		if((n-1>=1)&&(myMaze.getValueIn(i, j, n-1)==1)&&(AL.contains(new Position(i,j,n-1))==false))
		{
			AL.add(new Position(i,j,n-1));
		}
		if((n+1<myMaze.getSize_z()-1)&&(myMaze.getValueIn(i, j, n+1)==1)&&(AL.contains(new Position(i,j,n+1))==false))
		{
			AL.add(new Position(i,j,n+1));
		}
	}
	/**
	 * checks if some cell is a shell
	 * @param currPos the position we check
	 * @param maze the maze we work with
	 * @return true if this is a part of the shell,false otherwise
	 */
	protected boolean isAShell(Position currPos,Maze3d maze)
	{
		
		//checks if this is a shell
		if((currPos.getX()==0)||(currPos.getY()==0)||(currPos.getZ()==0)||(currPos.getX()==maze.getSize_x()-1)||(currPos.getY()==maze.getSize_y()-1)||(currPos.getZ()==maze.getSize_z()-1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
