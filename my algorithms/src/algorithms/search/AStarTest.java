package algorithms.search;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algotithms.demo.Maze3dSearchable;
/**
 * Junit test for Aster class
 * @author Marian 
 *
 */
public class AStarTest 
{
	/**
	 * testing the Astar class
	 */
	@Test
	public void test() {
		
		//test number 1:heuristic is null
		
		//check if we insert null in the heuristic it will be null
		AStar<Position> astar1=new AStar<Position>(null);
		assertEquals(null,astar1.getH());
		
		//if the heuristic is null,so the calculate cost return -1,and than the search algorithm will fail and return null
		Maze3dGenerator m3dg1=new MyMaze3dGenerator();
		Maze3d maze1=m3dg1.generate(5, 10, 10);
		Maze3dSearchable ms1=new Maze3dSearchable(maze1);
		Solution<Position> sol1=astar1.search(ms1);
		assertEquals(null, sol1);
		
		
		
		//test number 2:maze is null
		
		//check if maze is null,so will return false that searchable is not valid
		Maze3dSearchable ms2=new Maze3dSearchable(null);
		assertEquals(false, ms2.checkIfSerachableIsValid());
		
		//check if the functions of searchable are working properly with maze that is null
		assertEquals(null,ms2.getStartState());
		assertEquals(null,ms2.getGoalState());
		//check if the maze is null we need to return a null solution
		AStar<Position> astar2=new AStar<Position>(new MazeAirDistance());
		Solution<Position> sol2=astar2.search(ms2);
		assertEquals(null, sol2);
		
		
		//test number 3:searchable is null
		
		//tests if serachable is null,what happen to the search algorithm
		AStar<Position> astar3=new AStar<Position>(new MazeAirDistance());
		Solution<Position> sol3=astar3.search(null);
		assertEquals(null, sol3);

	}

}
