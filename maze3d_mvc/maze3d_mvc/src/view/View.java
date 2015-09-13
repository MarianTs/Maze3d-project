package view;



public interface View {
	public void start();

	public void showDirPath(String[] dirArray);
	public void showError(String message);
	public void showHelp();
	public void showGenerate3dMaze(String message);
	public void showDisplayName(byte[] byteArr);
	public void showDisplayCrossSectionBy(int[][] crossSection);
	
	public void showSaveMaze(String str);
	public void showLoadMaze(String str);
}
