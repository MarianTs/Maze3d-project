package algorithms.search;

import algorithms.mazeGenerators.Position;
/**
 * Define the Manhattan distance heuristic
 * @author Marian
 *
 */
public class MazeManhattenDistance implements Heuristic<Position>
{
	/**
	 * calculating the estimated cost between two points reliant upon Manhattan distance heuristic
	 */
	@Override
	public double h(State<Position> currState,State<Position> goalState) {
		int x;
		int y;
		int z;
		
		x=Math.abs(goalState.getState().getX()-currState.getState().getX());
		y=Math.abs(goalState.getState().getY()-currState.getState().getY());
		z=Math.abs(goalState.getState().getZ()-currState.getState().getZ());
		return (double)(x+y+z);
	}
	

	
}
