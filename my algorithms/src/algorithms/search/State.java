package algorithms.search;

/**
 * defines the state in the searching problem
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */

public class State<T>
{
	private T state;
	private double cost;
	private State<T> cameFrom;
	/**
	 * constructor that insert only state without cost and camefrom
	 * @param s state
	 */
	public State(T s)
	{
		this.state=s;
	}
	
	/**
	 * compare between two states (overriding the Object function of equals),return true if equal otherwise false
	 */
	@Override
    public boolean equals(Object obj)
	{ 
		// we override Object's equals method
        return state.equals(((State<?>)obj).state);
	}
	/**
	 * returning the state
	 * @return state
	 */
	public T getState() {
		return state;
	}
	/**
	 * setting the given state 
	 * @param state state you want to set
	 */
	public void setState(T state) {
		this.state = state;
	}
	
	/**
	 * return the cost of the path till the current state
	 * @return cost cost till this state
	 */
	public double getCost() {
		return cost;
	}
    /**
     * setting cost till this state
     * @param cost cost till this state
     */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * returning the state before the current state(in a path)
	 * @return the state in the path before the current one
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}
	/**
	 * setting the state before the current one in path
	 * @param cameFrom the state before the current one
	 */
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	/**
	 * overriding the hash code of String for state
	 */
	@Override
	public int hashCode() {
		
		return toString().hashCode();
	}
	/**
	 * Returning the string representation of state:<current state,cost,state we came from>
	 */
	@Override
	public String toString() {
		if(cameFrom==null)
		{
			return "<"+state.toString()+" , "+cost+" , null>";
		}
		else
		{
			return "<"+state.toString()+" , "+cost+" , "+cameFrom.getState()+">";
		}
		
	}
	


}
