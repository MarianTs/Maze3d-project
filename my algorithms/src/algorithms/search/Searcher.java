package algorithms.search;
/**
 * Define the algorithm that searches in the searching problem(domain independent)
 * @author Marian
 *
 * @param <T> The type of the internal representation of state
 */
public interface Searcher<T> {
	/**
	 * finding the solution of a searching problem
	 * @param s our searching problem
	 * @return the solution of the problem
	 */
	public Solution<T> search(Searchable<T> s);
	/**
	 * calculating the number of nodes that has been evaluated in the searching algorithm
	 * @return
	 */
	public int getNumberOfNodesEvaluated() ;
}
