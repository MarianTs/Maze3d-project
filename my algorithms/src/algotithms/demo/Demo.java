package algotithms.demo;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
/**
 * Demonstrating the use of searching algorithms over searching problems
 * @author Marian
 *
 */
public class Demo 
{
	public void run()
	{
		Maze3dGenerator m3dg=new MyMaze3dGenerator();
		Maze3d maze=m3dg.generate(5, 10, 10);
		
		System.out.println("My maze 3D: "+maze+"\nEntrance: "+maze.getStartPosition()+",Exit: "+maze.getGoalPosition()+"\n");
		
		Maze3dSearchable ms=new Maze3dSearchable(maze);
		
		Searcher<Position> s_bfs=new BFS<Position>();
		Solution<Position> sol=s_bfs.search(ms);
		
		System.out.println("BFS Solution: \n"+sol);
		
		Searcher<Position> s_AStar_man=new AStar<Position>(new MazeManhattenDistance());
		
		sol=s_AStar_man.search(ms);
		System.out.println("AStar with Manhatten distance Solution: \n"+sol);
		
		Searcher<Position> s_AStar_air=new AStar<Position>(new MazeAirDistance());
		
		sol=s_AStar_air.search(ms);
		System.out.println("AStar with air distance Solution: \n"+sol);
		
		
		System.out.println("Nodes avaluted by bfs search: "+s_bfs.getNumberOfNodesEvaluated());
		System.out.println("Nodes avaluted by A* with Manhatten distance heuristic: "+s_AStar_man.getNumberOfNodesEvaluated());
		System.out.println("Nodes avaluted by A* with air distance heuristic: "+s_AStar_air.getNumberOfNodesEvaluated());
		
	}
}
