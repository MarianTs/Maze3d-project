package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
/**
 * Define the BFS algorithm
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */
public abstract class CommonBFS<T> extends CommonSearcher<T>
{
	HashMap<T,State<T>> visitedCells;
	/**
	 * Default constructor
	 */
	public CommonBFS() 
	{
		super();
		this.visitedCells = new HashMap<T,State<T>>();
	}
	/**
	 * BfS algorithm for finding the solution of the searching problem
	 */
	@Override
	public Solution<T> search(Searchable<T> s) 
	{
		
		if(s==null)
		{
			return null;
		}
		if(!s.checkIfSerachableIsValid())
		{
			return null;
		}
		addToOpenList(s.getStartState());
		HashSet<State<T>> closedSet=new HashSet<State<T>>();
		
		

		while(openList.size()>0)
		{
			State<T> n=popOpenList();// dequeue
			
			closedSet.add(n);

			if(n.equals(s.getGoalState()))
			{
				return backTrace(n, s.getStartState());
			}
				
			// private method, back traces through the parents

			ArrayList<State<T>> successors=s.getAllPossibleStates(n); //however it is implemented 
			for(State<T> state : successors)//this state is without cost
			{
				double currCost=calculateCost(n, state, s);
				if(currCost==-1)
				{
					return null;
				}
				if(!closedSet.contains(state) && ! openListContains(state))
				{
					state.setCameFrom(n);
					state.setCost(currCost);
					addToOpenList(state);
				}
				else if(visitedCells.get(state.getState()).getCost()>currCost)
				{
					if(openListContains(state)==false)
					{
						
						addToOpenList(visitedCells.get(state.getState()));
					}
					else
					{
						openList.remove(state);
						state.setCost(currCost);
						state.setCameFrom(n);
						addToOpenList(state);
						
					}
				}
			}
		}
		return null;
	}
	/**
	 * go from the goal state to the start state to find the solution
	 * @param exit the the goal position
	 * @param entrence the start position
	 * @return the solution of the problem
	 */
	public Solution<T> backTrace(State<T> exit,State<T> entrence )
	{
		ArrayList<State<T>> AL=new ArrayList<>();
		State<T> temp=exit;
		AL.add(temp);
		while(!(temp.equals(entrence)))
		{
			AL.add(temp.getCameFrom());
			temp=temp.getCameFrom();
		}
		
		Collections.reverse(AL);
		
		return new Solution<T>(AL);
		
	}
	/**
	 * {@inheritDoc}
	 */
	protected void addToOpenList(State<T> s) 
	{
		 
		 super.addToOpenList(s);
		 visitedCells.put(s.getState(), s);
		 
		 
	}
	/**
	 * Estimated cost between two states
	 * @param currState the state we want to calculate from
	 * @param neighbor the state we want to calculate till it
	 * @param s the searching problem
	 * @return the estimated cost between two states
	 */
	public abstract double calculateCost(State<T> currState,State<T> neighbor,Searchable<T> s);
	/**
	 * calculating the cost between two states,in the different implementations of BFS we have another factor we add
	 * @param currState the state we want to calculate from
	 * @param neighbor the state we want to calculate till it
	 * @param s the searching problem
	 * @return the basic cost between two states
	 */
	public double g(State<T> currState,State<T> neighbor,Searchable<T> s)
	{
		return currState.getCost()+ s.getPassageCost(currState, neighbor);
	}
	
}
