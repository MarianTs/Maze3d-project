package algorithms.search;
/**
 * Adding the features of the Astar to the basic BFS
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */
public class AStar<T> extends CommonBFS<T> 
{
	Heuristic<T> h;
	/**
	 * constructor that gets the type of the heuristics
	 * @param h
	 */
	public AStar(Heuristic<T> h) 
	{
		super();
		this.h = h;
	}
	/**
	 * calculating the cost between two states in Astar(depending on each heuristics)
	 */
	@Override
	public double calculateCost(State<T> currState, State<T> neighbor, Searchable<T> s) {
		if(currState==s.getStartState())
		{
			return g(currState,neighbor,s)+h.h(neighbor, s.getGoalState());
		}
		else
		{
			return g(currState,neighbor,s)+h.h(neighbor, s.getGoalState())-h.h(currState,s.getGoalState());
		}
		
	}
	
	
	
}