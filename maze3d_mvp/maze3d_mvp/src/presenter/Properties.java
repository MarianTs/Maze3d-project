package presenter;

import java.io.Serializable;


/**
 * a class that holds the properties of the maze creation
 * @author Marian
 *
 */
public class Properties implements Serializable {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = -7347981088324820375L;
	
	
	private int numberOfThreads;
	private String algorithmToSearch;
	private String algorithmToGenerateMaze;
	private String typeOfUserInterfece;
	
	/**
	 * constructor using fields
	 * @param numberOfThreads number of threads allowed to run in the model
	 * @param algorithmToSearch which algorithm to search the maze:astar air distance,astar Manhattan distance,bfs
	 * @param algorithmToGenerateMaze which algorithm to generate the maze:prim/simple
	 * @param typeOfUserInterfece which user interface to use:cli/gui
	 */
	public Properties(int numberOfThreads, String algorithmToSearch, String algorithmToGenerateMaze,String typeOfUserInterfece) 
	{
		super();
		this.numberOfThreads = numberOfThreads;
		this.algorithmToSearch = algorithmToSearch;
		this.algorithmToGenerateMaze = algorithmToGenerateMaze;

		this.typeOfUserInterfece=typeOfUserInterfece;
	}
	/**
	 * constructor without fields
	 */
	public Properties() 
	{
		super();

	}
	/**
	 * copy constructor
	 * @param p properties object(containing the properties of the project)
	 */
	public Properties(Properties p)
	{
		this.numberOfThreads=p.numberOfThreads;
		this.algorithmToGenerateMaze=p.algorithmToGenerateMaze;
		this.algorithmToSearch=p.algorithmToSearch;
	}
	/**
	 * return the the type of user interface
	 * @return String with the type of user interface:cli/gui
	 */
	public String getTypeOfUserInterfece() {
		return typeOfUserInterfece;
	}
	/**
	 * setting the type of user interface
	 * @param typeOfUserInterfece String with the type of user interface:cli/gui
	 */
	public void setTypeOfUserInterfece(String typeOfUserInterfece) {
		this.typeOfUserInterfece = typeOfUserInterfece;
	}
	/**
	 * return the number of threads allowed
	 * @return number of threads
	 */
	public int getNumberOfThreads() 
	{
		return numberOfThreads;
	}
	
	
	
	/**
	 * setting the number of threads
	 * @param numberOfThreads number of threads
	 */
	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	
	/**
	 * getting the algorithm,with whom we search the maze:astar air distance,astar Manhattan distance,bfs
	 * @return String with the name of the algorithm
	 */
	public String getAlgorithmToSearch() {
		return algorithmToSearch;
	}
	
	/**
	 * setting the algorithm we search with it the path from the begging to the end of the game
	 * @param algorithmToSearch name of the algorithm(astar air distance,astar Manhattan distance,bfs)
	 */
	public void setAlgorithmToSearch(String algorithmToSearch) {
		this.algorithmToSearch = algorithmToSearch;
	}
	
	/**
	 * getting the algorithm we generate with,the maze(prim ,simple)
	 * @return name of the algorithm
	 */
	public String getAlgorithmToGenerateMaze() {
		return algorithmToGenerateMaze;
	}
	
	/**
	 * setting the algorithm the generate with the maze
	 * @param algorithmToGenerateMaze name of algorithm(prim ,simple)
	 */
	public void setAlgorithmToGenerateMaze(String algorithmToGenerateMaze) {
		this.algorithmToGenerateMaze = algorithmToGenerateMaze;
	}


	


	
	
	
	
	
	
	
	
	
	
	


	
	
	
}
