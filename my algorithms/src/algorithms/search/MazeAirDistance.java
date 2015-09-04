package algorithms.search;

import algorithms.mazeGenerators.Position;
/**
 * Define the air distance heuristic
 * @author Marian
 *
 */
public class MazeAirDistance implements Heuristic<Position>
{
	/**
	 * calculating the estimated cost between two points reliant upon air distance heuristic
	 */
	@Override
	public double h(State<Position> currState, State<Position> goalState) {
		double x;
		double y;
		double z;
		
		x=Math.pow(goalState.getState().getX()-currState.getState().getX(),2);
		y=Math.pow(goalState.getState().getY()-currState.getState().getY(),2);
		z=Math.pow(goalState.getState().getZ()-currState.getState().getZ(),2);
		return Math.sqrt(x+y+z);
		
	}
	
}
