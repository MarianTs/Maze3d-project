package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import algotithms.demo.Maze3dSearchable;
/**
 * a client handler that handle solution for a specified maze
 * @author Marian & Lidor
 *
 */
public class MazeSolutionClientHandler implements ClientHandler
{
	
	HashMap<Maze3d, Solution<Position>> mazeSolutions;
	
	/**
	 * constructor
	 */
	public MazeSolutionClientHandler()
	{
		mazeSolutions=new HashMap<Maze3d,Solution<Position>>();
		
		File f2=new File("./resources/maze solutions.zip");
		if(f2.exists())
		{
			
			try 
			{
				//loading it compressed
				ObjectInputStream objectIn;
				objectIn = new ObjectInputStream(new GZIPInputStream(new FileInputStream("./resources/maze solutions.zip")));
				//reading entire object into our maze solution collection
				mazeSolutions=(HashMap<Maze3d, Solution<Position>>)objectIn.readObject();
				objectIn.close();
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}		
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) 
	{
		//server handling 1 client
		try{
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToClient);
			String line;

			// wait for a hello from the client
			if((line=in.readLine()).equals("hello"))
			{
				//returning ok to the client
				out.println("ok");
				out.flush();
				
				//reading the arraylist from client with maze and algorithm
				ObjectInputStream mazeFromClientt=new ObjectInputStream(inFromClient);
				ArrayList<Object> packetFromClient=(ArrayList<Object>)mazeFromClientt.readObject();
				String algo=(String)packetFromClient.get(0);
				Maze3d maze=new Maze3d((byte[])packetFromClient.get(1));


				ObjectOutputStream solutionToClient=new ObjectOutputStream(outToClient);

				
				Solution<Position> sol=new Solution<Position>(new ArrayList<State<Position>>());
				
				//checking if we have the solution in our collection
				if(mazeSolutions.containsKey(maze))
				{
					System.out.println("found in the collection");
					sol=mazeSolutions.get(maze);
				}
				
				//checking which algorithm to use when solving the maze
				if(algo.intern()=="bfs")
				{
					Maze3dSearchable ms=new Maze3dSearchable(maze);

					Searcher<Position> bfs=new BFS<Position>();
					sol=bfs.search(ms);
					mazeSolutions.put(maze, sol);

				}
				else if(algo.intern()=="astar air distance")
				{
					Maze3dSearchable ms=new Maze3dSearchable(maze);
					Searcher<Position> AstarAir=new AStar<Position>(new MazeAirDistance());

					sol=AstarAir.search(ms);

					mazeSolutions.put(maze, sol);

				}
				else if(algo.intern()=="astar manhatten distance")
				{
					Maze3dSearchable ms=new Maze3dSearchable(maze);
					Searcher<Position> AStarMan=new AStar<Position>(new MazeManhattenDistance());

					sol=AStarMan.search(ms);

					mazeSolutions.put(maze, sol);

				}
				
				//write solution to the client
				solutionToClient.writeObject(sol);
				solutionToClient.flush();


				mazeFromClientt.close();
				solutionToClient.close();
			}				

			in.close();//closing the input from client(both buffer and input from client)
			out.close();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 


	}
	
	/**
	 * {@inheritDoc}
	 */
	public void close()
	{
		try 
		{
			ObjectOutputStream objectOut;
			objectOut = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("./resources/maze solutions.zip")));
			objectOut.writeObject(mazeSolutions);
			objectOut.flush();
			objectOut.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
