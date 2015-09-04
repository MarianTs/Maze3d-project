package algorithms.search;


/**
 * Defines the heuristics of Astar
 * @author Marian
 *
 * @param <T>
 */
public interface Heuristic<T> 
{
	/**
	 * calculating the estimated cost between two states depends on each heuristics
	 * @param currState the state we calculate from
	 * @param goalState the state we calculate till this one
	 * @return cost
	 */
	public double h(State<T> currState,State<T> goalState);
}
