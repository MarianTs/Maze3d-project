package Model;

public interface Model 
{
	public void handleDirPath(String[] path);
	
	public void handleGenerate3dMaze(String[] mazeParam);
	public void handleDisplayName(String[] paramArray);
	public void handleError(String[] paramArr);
	public void handleExit(String[] paramArr);
	public void handleDisplayCrossSectionBy(String[] paramArray);
	public int[][] getCrossSection();
	public void handleSaveMaze(String[] paramArray);
	public String getSaveMazeCode();
	public void handleLoadMaze(String[] paramArray);
	public void handleMazeSize(String[] paramArray);
	public void handleFileSize(String[] paramArray);
	
	
	
	public String getErrorCode();
	public String[] getDirList();
	public String getGenerate3dmazeCode();
	public byte[] getSpecificMazeFromColllection(String name);
	public String getLoadMazeCode();
	public int getMazeSize();
	public long getFileSize() ;
	//public void handleCommands(String[] args);
}
