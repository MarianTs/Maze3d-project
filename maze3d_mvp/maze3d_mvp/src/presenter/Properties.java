package presenter;

import java.io.Serializable;



public class Properties implements Serializable {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = -7347981088324820375L;
	
	
	int numberOfThreads;
	String algorithmToSearch;
	String algorithmToGenerateMaze;
	
	
	public Properties(int numberOfThreads, String algorithmToSearch, String algorithmToGenerateMaze) {
		super();
		this.numberOfThreads = numberOfThreads;
		this.algorithmToSearch = algorithmToSearch;
		this.algorithmToGenerateMaze = algorithmToGenerateMaze;
	}
	
	public Properties(Properties p)
	{
		this.numberOfThreads=p.numberOfThreads;
		this.algorithmToGenerateMaze=p.algorithmToGenerateMaze;
		this.algorithmToSearch=p.algorithmToSearch;
	}
	public int getNumberOfThreads() {
		return numberOfThreads;
	}
	
	
	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	
	
	public String getAlgorithmToSearch() {
		return algorithmToSearch;
	}
	
	
	public void setAlgorithmToSearch(String algorithmToSearch) {
		this.algorithmToSearch = algorithmToSearch;
	}
	
	
	public String getAlgorithmToGenerateMaze() {
		return algorithmToGenerateMaze;
	}
	
	
	public void setAlgorithmToGenerateMaze(String algorithmToGenerateMaze) {
		this.algorithmToGenerateMaze = algorithmToGenerateMaze;
	}


	public Properties() 
	{
		super();

	}


	
	
	
	
	
	
	
	
	
	
	


	
	
	
}
