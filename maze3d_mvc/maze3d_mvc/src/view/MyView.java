package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;

public class MyView extends CommonView
{
	CLI cli;
	HashMap<String,Command> stringToCommand;
	
	public MyView(Controller c) 
	{
		super(c);
		stringToCommand=c.getStringToCommand();
		cli=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out),stringToCommand);
	}

	@Override
	public void start() {
		cli.start();
		
	}
	public void showDirPath(String[] dirArray)
	{
		System.out.println("The files and directories in this path are:");
		for(String s:dirArray)
		{
			System.out.println(s);
		}
	}
	public void showError(String message)
	{
		System.out.println(message);
	}
	
	public void showGenerate3dMaze(String message)
	{
		System.out.println(message);
	}
	
	public void showDisplayName(byte[] byteArr)
	{
		try {
			Maze3d maze3d=new Maze3d(byteArr);
			int size_x=maze3d.getSize_x();
			int size_y=maze3d.getSize_y();
			int size_z=maze3d.getSize_z();
			int[][][] maze=maze3d.getMaze();
			
			
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
			System.out.println(aString);
			System.out.println("The start position: ("+maze3d.getStartPosition().getX()+","+maze3d.getStartPosition().getY()+","+maze3d.getStartPosition().getZ()+")");
			System.out.println("The goal position:  ("+maze3d.getGoalPosition().getX()+","+maze3d.getGoalPosition().getY()+","+maze3d.getGoalPosition().getZ()+")");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void showDisplayCrossSectionBy(int[][] crossSection)
	{
		for(int[] a:crossSection)
		{
			for(int b:a)
			{
				System.out.print(b);
			}
			System.out.println();
		}
	}
	public void showSaveMaze(String str)
	{
		System.out.println(str);
	}
	public void showLoadMaze(String str)
	{
		System.out.println(str);
	}
	
	public void showHelp()
	{
		System.out.println("Help Center:");
		System.out.println("dir <path>                                           -display the files and directories in this specific path.");
		System.out.println("generate 3d maze <name> <x> <y> <z> <algorithm>      -generating maze with the specified name,with xyz dimensions with algorith:simple/prim");
		System.out.println("display <name>                                       -display the specified maze");
		System.out.println("display cross section by {x,y,z} <index> for <name>  -diplaying cross section(x,y or z,chose one) in the index specified for maze with this name");
		System.out.println("save maze <name> <file name>                         -save maze in file name specified");
		System.out.println("load maze <file name> <name>                         -load maze from file specified");
		System.out.println("maze size <name>                                     -display the size of maze in ram");
		System.out.println("file size <name>                                     -display the size of maze in file");
		System.out.println("solve <name> <algorithm>                             -solve maze with specified algorithm:BFS/Astar ManhattenDistance/Astar air distance");
		System.out.println("display solution <name>                              -solve the maze and show the solution");
		System.out.println("exit                                                 -exit the program");
		
		System.out.println();
		System.out.println("<> -You have to write the requested string inside,{} -choose one of the fallwing and write inside the brackets");
	}
}
