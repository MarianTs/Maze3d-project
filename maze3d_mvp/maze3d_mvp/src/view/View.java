package view;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

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
	
	public void showSolveMaze(String message);
	public void showDisplaySolution(Solution<Position> solution);
	
	public void showSolveFrom(String message);
	
	public void showDisplayHalfSolution(Solution<Position> solution);
	
	public void showExit();
	
	public void showHelp();
	
	public void showLoadXML(Properties p);
}
