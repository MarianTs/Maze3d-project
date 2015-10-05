package algorithms.mazeGenerators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Defines 3D maze
 * @author Marian
 *
 */
public class Maze3d implements Serializable
{	
	/**
	 * serial number for the serialization
	 */
	private static final long serialVersionUID = 8052684751480662687L;
	
	
	private int maze[][][];
	private int size_x;
	private int size_y;
	private int size_z;
	private Position startPosition;
	private Position goalPosition;
	
	
	/**
	 * Construct 3D maze with the following sizes.
	 * @param size_x the height of the maze
	 * @param size_y the width of the maze
	 * @param size_z the length of the maze
	 */
	public Maze3d(int size_x, int size_y, int size_z) 
	{
		super();
		
		this.size_x = size_x;
		this.size_y = size_y;
		this.size_z = size_z;
		maze=new int[size_x][size_y][size_z];
		
		
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				for(int n=0;n<size_z;n++)
				{
					maze[i][j][n]=0;
				}
			}
		}
	}
	
	
	/**
	 * construct default maze 10*10*10 diameter
	 */
	public Maze3d()
	{
		this.size_x = 10;
		this.size_y = 10;
		this.size_z = 10;
		maze=new int[size_x][size_y][size_z];
		
		
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				for(int n=0;n<size_z;n++)
				{
					maze[i][j][n]=0;
				}
			}
		}
	}
	/**
	 * constructor that gets byte array and construct a maze3d from its bytes
	 * @param byteArr byte array
	 * @throws IOException 
	 */
	public Maze3d(byte[] byteArr) throws IOException
	{
		ByteArrayInputStream in=new ByteArrayInputStream(byteArr);
		DataInputStream dis=new DataInputStream(in);
		//creating a stream that reads primitive types easier
		
		this.size_x=dis.readInt();
		this.size_y=dis.readInt();
		this.size_z=dis.readInt();
		
		maze=new int[size_x][size_y][size_z];
		
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				for(int n=0;n<size_z;n++)
				{
					maze[i][j][n]=dis.read();//reads byte
					
				}
			}
		}
		startPosition=new Position(dis.readInt(), dis.readInt(), dis.readInt());
		
		goalPosition=new Position(dis.readInt(), dis.readInt(), dis.readInt());
	}
	
	/**
	 * insert walls in every cell of the maze
	 */
	public void fillWithWalls()
	{
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				for(int n=0;n<size_z;n++)
				{
					maze[i][j][n]=1;
				}
			}
		}
	}
	
	/**
	 * calculate the entrance of the maze
	 * @return position of entrance
	 */
	public Position getStartPosition() {
		return startPosition;
	}
	
	/**
	 * set the entrance of the maze
	 * @param startPosition entrance point
	 */
	public void setStartPosition(Position startPosition) 
	{
		this.startPosition = startPosition;
	}
	
	/**
	 * get the exit of the maze
	 * @return exit of the maze
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}
	/**
	 * set the exit of the maze
	 * @param goalPosition exit of the maze
	 */
	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}
	/**
	 * return a 3D array representing the maze
	 * @return 3D array which represent the maze
	 */
	public int[][][] getMaze() {
		return maze;
	}
	/**
	 * Insert a maze(3D array)
	 * @param maze 3D array
	 */
	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}
	/**
	 * get the size of x axis
	 * @return the size of x axis
	 */
	public int getSize_x() {
		return size_x;
	}
	/**
	 * set size of x axis
	 * @param size_x height of the maze
	 */
	public void setSize_x(int size_x) {
		this.size_x = size_x;
	}
	/**
	 * return the size of y axis
	 * @return the size of y axis
	 */
	public int getSize_y() {
		return size_y;
	}
	/**
	 * set size of y axis
	 * @param size_y width of the maze
	 */
	public void setSize_y(int size_y) {
		this.size_y = size_y;
	}
	/**
	 * get the size of z axis
	 * @return length of the maze
	 */
	public int getSize_z() {
		return size_z;
	}
	/**
	 * set size of z axis
	 * @param size_z lenght of the maze
	 */
	public void setSize_z(int size_z) {
		this.size_z = size_z;
	}
	
	
	/**
	 * set value into p cell in the maze
	 * @param p the cell 
	 * @param var the value we put inside
	 */
	public void setValueInPlace(Position p,int var)
	{
		setValueInPlace(p.getX(),p.getY(),p.getZ(), var);
	}
	/**
	 * set value into (x,y,z) cell in the maze
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @param var value we insert into cell
	 */
	public void setValueInPlace(int x,int y,int z,int var)
	{
		maze[x][y][z]=var;
	}
	
	
	
	/**
	 * retrieve the value in position p
	 * @param p cell's coordinates
	 * @return the value inside the cell
	 */
	public int getValueIn(Position p)
	{
		return getValueIn(p.getX(), p.getY(), p.getZ());
	}
	/**
	 * retrieve the value in position x,y,z
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return value
	 */
	public int getValueIn(int x,int y,int z)
	{
		return maze[x][y][z];
	}
	
	
	
	/**
	 * get cross section by x
	 * @param x the x coordinate
	 * @return 2D array
	 * @throws IndexOutOfBoundsException
	 */
	public int [][] getCrossSectionByX(int x)throws IndexOutOfBoundsException
	{
		if((x<0)||(x>=size_x))
		{
			throw new IndexOutOfBoundsException();
		}
		
		int[][] maze2d=new int[size_y][size_z];
		for(int i=0;i<size_y;i++)
		{
			for(int j=0;j<size_z;j++)
			{
				maze2d[i][j]=maze[x][i][j];
			}
		}
		return maze2d;
	}
	/**
	 * get cross section by y
	 * @param y the y coordinate
	 * @return 2D array
	 * @throws IndexOutOfBoundsException
	 */
	public int [][] getCrossSectionByY(int y)throws IndexOutOfBoundsException
	{
		if((y<0)||(y>=size_y))
		{
			throw new IndexOutOfBoundsException();
		}
		
		int[][] maze2d=new int[size_x][size_z];
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_z;j++)
			{
				maze2d[i][j]=maze[i][y][j];
			}
		}
		return maze2d;
	}
	/**
	 * get cross section by z
	 * @param z the z coordinate
	 * @return 2D array
	 * @throws IndexOutOfBoundsException
	 */
	public int [][] getCrossSectionByZ(int z)throws IndexOutOfBoundsException
	{
		if((z<0)||(z>=size_z))
		{
			throw new IndexOutOfBoundsException();
		}
		
		int[][] maze2d=new int[size_x][size_y];
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				maze2d[i][j]=maze[i][j][z];
			}
		}
		return maze2d;
	}
	
	/**
	 * checks that two mazes are equal
	 * return true if equals otherwise return false
	 */
	public boolean equals(Object obj)
	{

		if(obj==null)
		{
			return false;
		}
		if(this==obj)
		{
			return true;
		}
		if(!(obj instanceof Maze3d))
		{
			return false;
		}
		Maze3d other=(Maze3d)obj;//now we know it is worker
		
		if(this.size_x!=other.size_x)
		{
			return false;
		}
		if(this.size_y!=other.size_y)
		{
			return false;
		}
		if(this.size_z!=other.size_z)
		{
			return false;
		}
		
		for(int i=0;i<size_x;i++)
		{
			for(int j=0;j<size_y;j++)
			{
				for(int n=0;n<size_z;n++)
				{
					if(this.maze[i][j][n]!=other.maze[i][j][n])
					{
						return false;
					}
				}
			}
		}
		if(this.startPosition.equals(other.startPosition)==false)
		{
			return false;
		}
		if(this.goalPosition.equals(other.goalPosition)==false)
		{
			return false;
		}
		return true;
	}
	/**
	 * gets position and return the possible moves from this position(where there are no walls)
	 * @param p current position
	 * @return array of strings with all the possible moves
	 */
	public String[] getPossibleMoves(Position p)
	{
		ArrayList<String> moves=new ArrayList<String>();
		
		int x=p.getX();
		int y=p.getY();
		int z=p.getZ();

		if((x-1>=0)&&(maze[x-1][y][z]==0))
		{
			moves.add("Down");

		}
		if((x+1<size_x)&&(maze[x+1][y][z]==0))
		{
			moves.add("Up");

		}
		if((y-1>=0)&&(maze[x][y-1][z]==0))
		{
			moves.add("Forward");

		}
		if((y+1<size_y)&&(maze[x][y+1][z]==0))
		{
			moves.add("Backward");

		}
		if((z-1>=0)&&(maze[x][y][z-1]==0))
		{
			moves.add("Left");

		}
		if((z+1<size_z)&&(maze[x][y][z+1]==0))
		{
			moves.add("Right");

		}
		if((p.equals((Object)goalPosition)==true)||(p.equals((Object)startPosition)==true))
		{
			if(x+1==size_x)
			{
				moves.add("Up");
			}
			else if(x-1==-1)
			{
				moves.add("Down");
			}
			else if(y+1==size_y)
			{
				moves.add("Backward");
			}
			else if(y-1==-1)
			{
				moves.add("Forward");
			}
			else if(z+1==size_z)
			{
				moves.add("Right");
			}
			else if(z-1==-1)
			{
				moves.add("Left");
			}
		}
		
		String[] arr = moves.toArray(new String[moves.size()]);
		
		return arr;
	}
	/**
	 * gets position and return the possible moves from this position(where there are no walls)
	 * @param p current position
	 * @return array of position(with coordinates)
	 */
	public ArrayList<Position> getAllPosibleStates(Position p)
	{
		ArrayList<Position> states=new ArrayList<Position>();
		int x=p.getX();
		int y=p.getY();
		int z=p.getZ();

		if((x-1>=0)&&(maze[x-1][y][z]==0))
		{
			states.add(new Position(x-1,y,z));

		}
		if((x+1<size_x)&&(maze[x+1][y][z]==0))
		{
			states.add(new Position(x+1,y,z));

		}
		if((y-1>=0)&&(maze[x][y-1][z]==0))
		{
			states.add(new Position(x,y-1,z));

		}
		if((y+1<size_y)&&(maze[x][y+1][z]==0))
		{
			states.add(new Position(x,y+1,z));

		}
		if((z-1>=0)&&(maze[x][y][z-1]==0))
		{
			states.add(new Position(x,y,z-1));

		}
		if((z+1<size_z)&&(maze[x][y][z+1]==0))
		{
			states.add(new Position(x,y,z+1));

		}
		return states;
		
	}
	
	/**
	 * returning all the maze3d data converted to byte array
	 * format:
	 * 4 bytes of size of x axis,4 bytes of size of y axis,4 bytes of size of z axis,
	 * all the cells of maze 3d matrix,each one as byte,
	 * the start position:3 integers represented by 4 bytes each
	 * the goal position :3 integers represented by 4 bytes each
	 * 
	 * @return byte array with the maze details
	 * @throws IOException 
	 */
	public byte[] toByteArray() 
	{
		//creating a stream that reads primitive types easier
		ByteArrayOutputStream bb=new ByteArrayOutputStream();
		DataOutputStream dis=new DataOutputStream(bb);
		try {
			dis.writeInt(size_x);

			dis.writeInt(size_y);
			dis.writeInt(size_z);
			for(int i=0;i<size_x;i++)
			{
				for(int j=0;j<size_y;j++)
				{
					for(int n=0;n<size_z;n++)
					{
						dis.write(maze[i][j][n]);
					}
				}
			}
			dis.writeInt(startPosition.getX());
			dis.writeInt(startPosition.getY());
			dis.writeInt(startPosition.getZ());

			dis.writeInt(goalPosition.getX());
			dis.writeInt(goalPosition.getY());
			dis.writeInt(goalPosition.getZ());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		return bb.toByteArray();
	}
	/**
	 * returning hashCode based on the content of the byte array representing the maze
	 */
	@Override
	public int hashCode()  
	{
		return Arrays.hashCode(this.toByteArray());
	}
	
	/**
	 * prints maze3D:
	 * floor 0:
	 * 01010
	 * 10101
	 * ...
	 * floor 1:
	 * 10101
	 * ...
	 * 
	 */
	public String toString()
	{
		String aString="";
		for(int i=0;i<size_x;i++)
		{
			aString+="floor "+i+":\n\n";
			for(int j=0;j<size_y;j++)
			{
				aString+="";
				for(int n=0;n<size_z;n++)
				{
					if(n==size_z-1)
					{
						aString+=maze[i][j][n];
					}
					else
					{
						aString+=maze[i][j][n]+" ";
					}
					
				}
				aString+="\n";
			}
			aString+="\n\n";
		}
		return aString;
	}
	
}
