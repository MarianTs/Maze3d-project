package algorithms.search;

import java.util.ArrayList;
/**
 * defines the searching problem,domain dependent
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */


public interface Searchable<T>
{
	
	/**
	 * returning the start state of the searching problem
	 * @return starting state
	 */
	public State<T> getStartState();
	/**
	 * Returning the goal state of the searching problem
	 * @return goal state
	 */
	public State<T>  getGoalState();
	/**
	 * find all the possible moves from current point
	 * @param s the current state
	 * @return ArrayList of possible states
	 */
	public ArrayList<State<T>> getAllPossibleStates(State<T> s);
	/**
	 * calculate the cost between two states
	 * @param a basic state
	 * @param b the next state
	 * @return the cost to move between the basic passage to the next passage
	 */
	public double getPassageCost(State<T> a,State<T> b);
	/**
	 * checking if searchable is valid,if there is a search problem(maze)...(for junit)
	 * @return true is the searchable is valid
	 * 		   false if the searchable is invalid
	 */
	public boolean checkIfSerachableIsValid();
	
}
