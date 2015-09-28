package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Defines the solution of the searching problem
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */

public class Solution<T> implements Serializable
{
	
	
	
	/**
	 * serial number for the serialization
	 */
	private static final long serialVersionUID = -3544215782730571392L;
	
	
	ArrayList<State<T>> AL;
	
	
	
	/**
	 * returning the solution of the searching problem
	 * @return solution
	 */
	public ArrayList<State<T>> getAL() {
		return AL;
	}



	/**
	 * setting the solution in the data member of the searching problem
	 * @param aL arraylist with the solution
	 */
	public void setAL(ArrayList<State<T>> aL) {
		AL = aL;
	}



	/**
	 * constructor 
	 * @param aL array list which representing the solution
	 */
	public Solution(ArrayList<State<T>> aL) {
		super();
		AL = aL;
	}
	/**
	 * Returning the string with this representation:
	 * <state>,<state>...
	 */
	@Override
	public String toString() {
		String s="";
		for(int i=0;i<AL.size();i++)
		{
			if(i==AL.size()-1)
			{
				s=s+AL.get(i).getState()+"\n ";
			}
			else
			{
				s=s+AL.get(i).getState()+", ";
			}
			
		}
		return s;
	}
	

}
