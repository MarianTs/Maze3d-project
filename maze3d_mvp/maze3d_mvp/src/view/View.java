package view;

public interface View {
	public void start();
	
	public String getUserCommand();
	
	public void showError(String error);

	public void showDirPath(String[] list);
	
	public void showGenerate3dMaze(String message);
	
	public void showDisplayName(byte[] byteArr);
	
	public void showDisplayCrossSectionBy(int[][] crossSection);
	
	public void showSaveMaze(String message);
	
	public void showLoadMaze(String message);
	
	public void showMazeSize(int size);
	
	public void showFileSize(long size);
	
	public void showExit();
	
	public void showHelp();
}
