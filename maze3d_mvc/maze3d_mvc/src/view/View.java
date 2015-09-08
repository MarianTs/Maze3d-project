package view;



public interface View {
	public void start();

	public void displayDirPath(String[] dirArray);
	public void displayError(String message);
	public void displayHelp();
	public void displayGenerate3dMaze(String message);
	public void displayCommandDisplayName(byte[] byteArr);
	public void displayCommandDisplayCrossSectionBy(int[][] crossSection);
}
