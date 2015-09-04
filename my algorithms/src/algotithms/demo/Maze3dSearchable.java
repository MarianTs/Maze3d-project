package algotithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearchable;
import algorithms.search.State;

/**
 * object adapter,contains maze3D and inherit searchable
 * @author Marian
 *
 */
public class Maze3dSearchable extends CommonSearchable<Position> {
	
	
	Maze3d maze;
	/**
	 * constructor with one parameter
	 * @param maze maze3D
	 */
	public Maze3dSearchable(Maze3d maze) {
		super();
		this.maze = maze;
	}
	/**
	 * get the starting state of the maze
	 */
	@Override
	public State<Position> getStartState() 
	{
		
		State<Position> s=new State<Position>(maze.getStartPosition());
		return s;
	}
	/**
	 * getting the goal state of the maze
	 */
	@Override
	public State<Position> getGoalState() {
		return new State<Position>(maze.getGoalPosition());
	}
	/**
	 * get all the possible state from current point in the maze
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) 
	{
		ArrayList<State<Position>> AL=new ArrayList<State<Position>>();
		ArrayList<Position> ALP=maze.getAllPosibleStates(s.getState());
		for(int i=0;i<ALP.size();i++)
		{
			AL.add(new State<Position>(ALP.get(i)));
		}
		return AL;
	}
	/**
	 * get the cost between two states in the maze
	 */
	@Override
	public double getPassageCost(State<Position> a, State<Position> b) {
		return 1;
	}
	

}
