package presenter;

import java.io.Serializable;



public class Properties implements Serializable {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = -7347981088324820375L;
	
	
	private int numberOfThreads;
	private String algorithmToSearch;
	private String algorithmToGenerateMaze;
	private int numOfClients;
	private String typeOfUserInterfece;
	
	
	public Properties(int numberOfThreads, String algorithmToSearch, String algorithmToGenerateMaze,int numOfClients,String typeOfUserInterfece) 
	{
		super();
		this.numberOfThreads = numberOfThreads;
		this.algorithmToSearch = algorithmToSearch;
		this.algorithmToGenerateMaze = algorithmToGenerateMaze;
		this.numOfClients=numOfClients;
		this.typeOfUserInterfece=typeOfUserInterfece;
	}
	
	public Properties() 
	{
		super();

	}

	public Properties(Properties p)
	{
		this.numberOfThreads=p.numberOfThreads;
		this.algorithmToGenerateMaze=p.algorithmToGenerateMaze;
		this.algorithmToSearch=p.algorithmToSearch;
		this.numOfClients=p.numOfClients;
	}
	
	public String getTypeOfUserInterfece() {
		return typeOfUserInterfece;
	}

	public void setTypeOfUserInterfece(String typeOfUserInterfece) {
		this.typeOfUserInterfece = typeOfUserInterfece;
	}
	public int getNumberOfThreads() 
	{
		return numberOfThreads;
	}
	
	
	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
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


	


	
	
	
	
	
	
	
	
	
	
	


	
	
	
}
