package boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class ClientRun {

	
	public static void main(String[] args) 
	{

		try {
			PrintWriter outToServer;
			System.out.println("Client Side");
			Socket theServer;
			
			//now we connected to the server
			theServer = new Socket("localhost",5400);
			System.out.println("connected to server!");
			
			outToServer = new PrintWriter(theServer.getOutputStream());
			outToServer.println("hello\n");
			outToServer.flush();
			
			BufferedReader in=new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			System.out.println(in.readLine());// ok
			
			
			Maze3dGenerator mg=new MyMaze3dGenerator();
			
			Maze3d maze=mg.generate(10, 10, 10);
			
			System.out.println("maze in client: \n"+maze);
			
			ObjectOutputStream mazeToServer=new ObjectOutputStream(theServer.getOutputStream());
			mazeToServer.writeObject(maze);
			mazeToServer.flush();
			
			ObjectInputStream solutionFromServer=new ObjectInputStream(theServer.getInputStream());
			Solution<Position> sol=(Solution<Position>)solutionFromServer.readObject();
			
			System.out.println("the solution is:" +sol);
			
			outToServer.println("exit");
			outToServer.flush();
			

			mazeToServer.close();
			solutionFromServer.close();
			outToServer.close();
			
			theServer.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
