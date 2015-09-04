package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * An abstract class that define the algorithm that searches in the searching problem(domain independent)
 * @author Marian
 *
 * @param <T>  The type of the internal representation of state
 */
public abstract class CommonSearcher<T> implements Searcher<T> {
	
	protected PriorityQueue<State<T>> openList;
	
	private int evaluatedNodes;
	/**
	 * constructor
	 */
	public CommonSearcher() 
	{
		 openList=new PriorityQueue<State<T>>(new Comparator<State<T>>()
		 {

			@Override
			public int compare(State<T> o1, State<T> o2) {
				return (int)(o1.getCost()-o2.getCost());
			}
			 
		 });
		 evaluatedNodes=0;
	}

	 /**
	  * take out the state with the lowest cost from the open list
	  * @return state with the lowest cost
	  */
	protected State<T> popOpenList() 
	{
		 evaluatedNodes++;
		 return openList.poll();
	}
	/**
	 * adding state to the open list and modifying the position of this state
	 * @param s the state we want to add
	 */
	protected void addToOpenList(State<T> s) 
	{
		 openList.add(s);
	}
	/**
	 * checks if the state is located in the open list
	 * @param s the state we want to check
	 * @return true if state is located and false otherwise
	 */
	protected boolean openListContains(State<T> s)
	{
		return openList.contains((Object)s);
		
	}
	
	

	@Override
	public abstract Solution<T> search(Searchable<T> s);

	@Override
	public int getNumberOfNodesEvaluated() 
	{
		return evaluatedNodes;

	}

}
