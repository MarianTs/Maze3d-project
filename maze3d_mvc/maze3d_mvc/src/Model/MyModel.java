package Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import controller.Controller;

public class MyModel extends CommonModel {
	HashMap<String,Maze3d> mazeCollection;
	
	public MyModel(Controller c) {
		super(c);
		mazeCollection=new HashMap<String,Maze3d>();
		
	}
	public void HandleDirPath(String[] args)
	{
		if(args.length==0)
		{
			c.passError("Invalid path");
			return;
		}
		File f=new File(args[0].toString());
		
		if(f.list()!=null)
		{
			c.passDirPath(f.list());
		}
		else
		{
			c.passError("Invalid path");
		}
		
	}
	public void handleGenerate3dMaze(String[] paramArray)
	{
		if(paramArray.length<5)
		{
			c.passError("Invalid number of parameters");
			return;
		}
		if((Integer.parseInt(paramArray[paramArray.length-4])<=0)||(Integer.parseInt(paramArray[paramArray.length-3])<=0)||(Integer.parseInt(paramArray[paramArray.length-2])<=0))
		{//checking if the sizes of the maze passed valid
			c.passError("Invalid parameters");
			return;
		}
		
		new Thread(new Runnable() {
			public void run() 
			{
				StringBuilder name=new StringBuilder();
				
				
				
				
				Maze3dGenerator mg;
				if(paramArray[paramArray.length-1].intern()=="simple")
				{
					mg=new SimpleMaze3dGenerator();
				}
				else if(paramArray[paramArray.length-1].intern()=="prim")
				{
					mg=new MyMaze3dGenerator();
				}
				else
				{
					c.passError("Invalid algorithm name");
					return;
				}
				
				//constructing the name of the maze
				for(int i=0;i<paramArray.length-4;i++)//running till the end of the name,4 cells before the end(where the sizes and algorithm allocated
				{
					if(i==paramArray.length-5)
					{
						name.append(paramArray[i]);//end of string,without space
					}
					else
					{
						name.append(paramArray[i]+" ");
					}
					
				}
				
				if(mazeCollection.containsKey(name.toString()))
				{
					c.passError("This name already exists,choose another one.");
					return;
				}
				
				mazeCollection.put(name.toString(), mg.generate(Integer.parseInt(paramArray[paramArray.length-4]),Integer.parseInt(paramArray[paramArray.length-3]),Integer.parseInt(paramArray[paramArray.length-2])));
				//generate maze with specified algorithm ,with specified sizes.
				
				c.passGenerate3dMaze("maze "+name.toString()+" is ready");
			}
		}).start();
		
	}
	
	public void handleDisplayName(String[] paramArray)
	{
		if(paramArray.length==0)
		{
			c.passError("Error:Please insert name");
		}
		StringBuilder name=new StringBuilder();
		int i;
		for(i=0;i<paramArray.length;i++)
		{
			if(i==paramArray.length-1)
			{
				name.append(paramArray[i]);
			}
			else
			{
				name.append(paramArray[i]+" ");
			}
			
			
		}
		
		try {
			if(!mazeCollection.containsKey(name.toString()))
			{
				c.passError("Maze doesn't exists");
				return;
			}
			
			c.passDisplayName(mazeCollection.get(name.toString()).toByteArray());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public void handleDisplayCrossSectionBy(String[] paramArray)
	{
		if(paramArray.length<4)
		{
			c.passError("Invalid amount of parameters");
		}
		StringBuilder name=new StringBuilder();
		int i;
		for(i=3;i<paramArray.length;i++)
		{
			if(i==paramArray.length-1)
			{
				name.append(paramArray[i]);
			}
			else
			{
				name.append(paramArray[i]+" ");
			}
		}
		if(!mazeCollection.containsKey(name.toString()))
		{
			c.passError("This maze doesn't exists");
			return;
		}
		
		
		
		//Maze3d maze=mazeCollection.get(key);
		if(paramArray[0].intern()=="x")
		{
			Maze3d maze=mazeCollection.get(name.toString());
			try
			{
				int[][] crossSection=maze.getCrossSectionByX(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			}
			catch (NumberFormatException e)
			{
				c.passError("Invalid parameters");
			}
			catch (IndexOutOfBoundsException e)
			{
				c.passError("Invalid x coordinate");
			}
			
		}
		else if(paramArray[0].intern()=="y")
		{
			Maze3d maze=mazeCollection.get(name);
			try
			{
				int[][] crossSection=maze.getCrossSectionByY(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			}
			catch (NumberFormatException e)
			{
				c.passError("Invalid parameters");
			}
			catch (IndexOutOfBoundsException e)
			{
				c.passError("Invalid y coordinate");
			}
		}
		else if(paramArray[0].intern()=="z")
		{
			Maze3d maze=mazeCollection.get(name);
			try
			{
				int[][] crossSection=maze.getCrossSectionByZ(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			}
			catch (NumberFormatException e)
			{
				c.passError("Invalid parameters");
			}
			catch (IndexOutOfBoundsException e)
			{
				c.passError("Invalid z coordinate");
			}
		}
		else
		{
			c.passError("Invalid parameters");
		}
	}
	
	
	
	
	
	
}
